import time
import json
from datetime import datetime
from locust import User, task, between

from src.common.stomp import StompClient
from src.utils.atomic import AtomicCounter


global_room_id = AtomicCounter()


class ChatLoadTestUser(User):
    host = "cloud6-lb-997344652.ap-northeast-2.elb.amazonaws.com"
    endpoint = "ws"

    vuser = 6

    wait_time = between(1, 2)

    def on_start(self):
        global_room_id.increment()
        self.room_id = global_room_id.value()

        self.__stomp_clients: list[StompClient] = []
        for i in range(self.vuser):
            self.__stomp_clients.append(StompClient(self.host, self.endpoint))
            self.__stomp_clients[i].connect()
            self.__stomp_clients[i].subscribe(f"/topic/{self.room_id}", f"user{i}")

    def on_stop(self):
        for i in range(self.vuser):
            self.__stomp_clients[i].disconnect()

    @task
    def send_receive_hello(self):
        try:
            start_time = time.time()

            payload = json.dumps({
                "roomId": self.room_id,
                "memberId": f"00000",
                "type": "MESSAGE",
                "createdBy": "user0",
                "comment": "Hello, world!",
                "createdAt": datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
            })
            self.__stomp_clients[0].send(f"/app/chat/{self.room_id}", payload)

            for i in range(self.vuser):
                msg = self.__stomp_clients[i].receive()

            total_time = int((time.time() - start_time) * 1000)
            
        except Exception as e:
            self.environment.events.request.fire(request_type="STOMP", name="send_receive_hello", response_time=total_time, response_length=0, exception=e)
        else:
            self.environment.events.request.fire(request_type="STOMP", name="send_receive_hello", response_time=total_time, response_length=len(msg))
