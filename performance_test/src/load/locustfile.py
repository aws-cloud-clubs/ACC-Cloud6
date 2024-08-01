import time
from locust import User, task, between

from src.common.stomp import StompClient
from src.utils.atomic import AtomicCounter


global_room_id = AtomicCounter()


class ChatLoadTestUser(User):
    host = "localhost"
    port = 8080
    endpoint = "chat"

    vuser = 6

    wait_time = between(1, 2)

    def on_start(self):
        global_room_id.increment()
        self.room_id = global_room_id.value()

        self.__stomp_clients: list[StompClient] = []
        for i in range(self.vuser):
            self.__stomp_clients.append(StompClient(self.host, self.port, self.endpoint))
            self.__stomp_clients[i].connect()
            self.__stomp_clients[i].subscribe(f"/topic/chat/{self.room_id}")

    def on_stop(self):
        for i in range(self.vuser):
            self.__stomp_clients[i].disconnect()

    @task
    def send_receive_hello(self):
        start_time = time.time()

        self.__stomp_clients[0].send("/topic/chat", "Hello, world!")

        for i in range(1, self.vuser):
            msg = self.__stomp_clients[i].receive()

        total_time = int((time.time() - start_time) * 1000)
        self.environment.events.request.fire(request_type="STOMP", name="send_receive_hello", response_time=total_time, response_length=len(msg))
