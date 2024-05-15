from datetime import datetime


class UserManager:
    def __init__(self):
        self.connected_users = {}

    def connect(self, user_id, username):
        self.connected_users[user_id] = {
            'username': username,
        }
        print('Client connected:', self.connected_users[user_id])

    def disconnect(self, user_id):
        print('Client disconnected:', self.connected_users.pop(user_id, {}))

    def get_username(self, user_id):
        return self.connected_users[user_id]['username']