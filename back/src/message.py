from dataclasses import Field
from datetime import datetime, timezone
from typing import Optional

class Message:
    def __init__(self, sender, content, timestamp):
        self.sender = sender
        self.content = content
        self.timestamp = timestamp

    def to_dict(self):
        return {
            'sender': self.sender,
            'content': self.content,
            'timestamp': self.timestamp
        }

    @classmethod
    def from_dict(cls, message_dict):
        return cls(
            sender=message_dict['sender'],
            content=message_dict['content'],
            timestamp=message_dict['timestamp']
        )