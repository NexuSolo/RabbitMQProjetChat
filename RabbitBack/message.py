import datetime
import json

class Message:
    def __init__(self, user_id, username, content):
        self.user_id = user_id
        self.username = username
        self.content = content
        self.timestamp = datetime.datetime.now()

    def to_dict(self):
        return {
            'user_id': self.user_id,
            'username': self.username,
            'content': self.content,
            'timestamp': self.timestamp.strftime('%Y-%m-%d %H:%M:%S'),
        }

    def to_json(self):
        return json.dumps(self.to_dict())