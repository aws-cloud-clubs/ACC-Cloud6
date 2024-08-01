import json
import time
from datetime import datetime
from random import randint

from locust import HttpUser, between, task, User

from performance_test.common.stomp import StompClient


class StressTestUser(HttpUser):
    wait_time = between(1, 2)
    host = "127.0.0.1"
    port = 8080
    endpoint = "chat"

    def __init__(self, parent: User):
        super().__init__(parent)
        self.room_id = randint(1, 6)
        self.stompClient = StompClient(self.host, self.port, self.endpoint)
        self.user_id = 1  # TODO: 유저아이디 수정

    def on_start(self):
        self.stompClient.connect()
        self.stompClient.subscribe("/topic/chat", self.room_id)

    def on_stop(self):
        self.stompClient.disconnect()

    # Header
    @task
    def send_chat(self):
        start_time = time.time()
        msg = json.dumps(
            {
                "chatRoomId": self.room_id,
                "type": "MESSAGE",
                "createdBy": self.user_id,
                "text": "안녕하세요",
                "createdAt": datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
            }
        )

        with self.stompClient.send("/topic/chat", msg) as response:
            if response.text != "Success":
                response.failure("Got wrong response")
            elif response.elapsed.total_seconds() > 0.5:
                response.failure("Request took too long")

        total_time = int((time.time() - start_time) * 1000)
        self.environment.events.request.fire(request_type="STOMP", name="send_hello", response_time=total_time,)