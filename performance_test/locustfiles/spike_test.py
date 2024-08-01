import json
import time

from datetime import datetime
from random import randint

from locust import HttpUser, between, task, events, LoadTestShape, TaskSet, User

from performance_test.common.stomp import StompClient

class SpikeTestTasks(TaskSet):
    host = "127.0.0.1"
    port = 8080
    endpoint = "chat"

    def __init__(self, parent: User):
        super().__init__(parent)
        self.room_id = randint(1, 6)
        self.stompClient = StompClient(self.host, self.port, self.endpoint)
        self.user_id = 1 #TODO: 유저아이디 수정

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
        self.environment.events.request.fire(request_type="STOMP", name="send_hello", response_time=total_time)


class SpikeTestUser(HttpUser):
    wait_time = between(1, 2)
    tasks = [SpikeTestTasks]


class SpikeTestShape(LoadTestShape):
    # step_time = 30 # how long to hold each step in seconds
    # step_load = 10 # how many users to add each step as well as how many users to start with
    # spawn_rate = 10 # what rate to spawn the users per second
    # time_limit = 600 # how long to run the test in seconds

    stages = [
        {"duration": 100, "users": 10, "spawn_rate": 10}, # 100초 안에 10명에서 초당 10명씩 증가
        {"duration": 100, "users": 10, "spawn_rate": 10},
        {"duration": 100, "users": 10, "spawn_rate": 10},
        # {"duration": 220, "users": 30, "spawn_rate": 10},
        # {"duration": 230, "users": 10, "spawn_rate": 10},
        # {"duration": 240, "users": 1, "spawn_rate": 1},
    ]

    def tick(self):
        run_time = self.get_run_time()

        for stage in self.stages:
            if run_time < stage["duration"]:
                tick_data = (stage["users"], stage["spawn_rate"])
                return tick_data

        return None


@events.request.add_listener
def my_request_handler(request_type, name, response_time, response_length, response,
                       context, exception, start_time, url, **kwargs):
    if exception:
        print(f"Request to {name} failed with exception {exception}")
    else:
        print(f"Successfully made a request to: {name}")
        print(f"The response was {response.text}")