from datetime import datetime
import threading
import time
from flask import Flask, render_template, request
from flask_socketio import SocketIO, emit
from message import Message
import pika
import json

from usermanager import UserManager

app = Flask(__name__)
socketio = SocketIO(app, cors_allowed_origins="*")

# Paramètres de connexion RabbitMQ
RABBITMQ_HOST = 'localhost'
RABBITMQ_PORT = 5672
RABBITMQ_USERNAME = 'guest'
RABBITMQ_PASSWORD = 'guest'
RABBITMQ_VIRTUAL_HOST = '/'

# Se connecter à RabbitMQ
credentials = pika.PlainCredentials(RABBITMQ_USERNAME, RABBITMQ_PASSWORD)
parameters = pika.ConnectionParameters(RABBITMQ_HOST, RABBITMQ_PORT, RABBITMQ_VIRTUAL_HOST, credentials)
connection = pika.BlockingConnection(parameters)
channel = connection.channel()

# Déclaration de l'échange et de la file d'attente
channel.exchange_declare(exchange='chat', exchange_type='direct')
channel.queue_declare(queue='chatroom', durable=True)
channel.queue_bind(exchange='chat', queue='chatroom', routing_key='')

user_manager = UserManager()

@socketio.on('connect')
def handle_connect():
    try:
        user_id = request.sid
        username = request.args.get('username')  # Supposons que le nom d'utilisateur est passé en tant que paramètre de requête
        if username is None:
            raise ValueError("Username is required")
        user_manager.connect(user_id, username)
    except Exception as e:
        print(f"Error during connection: {str(e)}")

@socketio.on('disconnect')
def handle_disconnect():
    try:
        user_id = request.sid
        user_manager.disconnect(user_id)
    except Exception as e:
        print(f"Error during disconnection: {str(e)}")

@socketio.on('message')
def handle_message(content):
    try:
        user_id = request.sid
        username = user_manager.get_username(user_id)
        if username is None:
            raise ValueError("User not found")
        message = Message(user_id, username, content)
        channel.basic_publish(exchange='chat', routing_key='', body=message.to_json())
        print("Message envoyé:", message.to_dict())
    except Exception as e:
        print(f"Error during message handling: {str(e)}")

def receive_message(ch, method, properties, body):
    try:
        message_dict = json.loads(body)
        message = Message(message_dict['user_id'], message_dict['username'], message_dict['content'])
        message.timestamp = datetime.strptime(message_dict['timestamp'], '%Y-%m-%d %H:%M:%S')
        print("Nouveau message reçu:", message.to_dict())
        socketio.emit('message', message.to_dict())
    except Exception as e:
        print(f"Error during message reception: {str(e)}")
        
def start_consuming():
    channel.basic_consume(queue='chatroom', on_message_callback=receive_message, auto_ack=True)
    channel.start_consuming()

if __name__ == '__main__':
    thread = threading.Thread(target=start_consuming)
    thread.start()
    socketio.run(app, debug=True, allow_unsafe_werkzeug=True)
