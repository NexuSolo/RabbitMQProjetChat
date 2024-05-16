import { Injectable } from '@angular/core';
import { Socket, io } from 'socket.io-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private url = 'http://localhost:5000';
  private socket !: Socket;
  username: String = "";
  messages: {username: String, message: String}[] = [];
  
  constructor() { }
  
  connect(username: String) {
    this.username = username;
    this.socket =  io(this.url + "?username=" + username, { query: { username: username } });
    this.socket.on('connect', () => {
      console.log('Connected to server');
    });
  }
  
  getSocket() {
    return this.socket;
  }

  sendMessage(message: string) {
    this.socket.emit('message', message);
  }

}
