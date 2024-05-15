from fastapi import FastAPI, WebSocket
from starlette.middleware.cors import CORSMiddleware
from typing import List

app = FastAPI()

# Middleware CORS pour autoriser les requêtes provenant de toutes les origines
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Liste des utilisateurs connectés
connected_users: List[str] = []

# Endpoint pour la connexion WebSocket
@app.websocket("/ws/{username}")
async def websocket_endpoint(websocket: WebSocket, username: str):
    await websocket.accept()
    connected_users.append(username)
    print(f"User {username} connected")
    try:
        while True:
            message = await websocket.receive_text()
            print(f"Received message from {username}: {message}")
            await websocket.send_text(f"Message received: {message}")
    except Exception as e:
        print(f"User {username} disconnected")
        connected_users.remove(username)