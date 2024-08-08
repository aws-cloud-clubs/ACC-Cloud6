from locust import events
import websocket
import stomper
import time


class StompClient:
    def __init__(self, host, endpoint) -> None:
        self.__ws_uri = f"ws://{host}/{endpoint}"
        self.__ws = websocket.WebSocket()
    
    def __del__(self):
        self.close()

    def close(self):
        if self.__ws:
            self.__ws.close()
    
    def connect(self):
        start_time = time.time()

        self.__ws.connect(self.__ws_uri, timeout=100000)
        self.__ws.send("CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n")
        self.__ws.recv()

        # try:
        #     # self.__ws = websocket.create_connection(self.__ws_uri)
        #     self.__ws.connect(self.__ws_uri, timeout=100000)
        #     self.__ws.send("CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n")
        #     # self.__ws.send(stomper.connect())
        #     self.__ws.recv()

        # except Exception as e:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="connect", response_time=total_time, exception=e)
        
        # else:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="connect", response_time=total_time)

    def subscribe(self, destination, id):
        start_time = time.time()

        self.__ws.send(stomper.subscribe(destination, id))

        # try:
        #     self.__ws.send(stomper.subscribe(destination, id))
        #     # self.__ws.recv()

        # except Exception as e:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="subscribe", response_time=total_time, exception=e)
        
        # else:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="subscribe", response_time=total_time)

    def send(self, destination, message):
        start_time = time.time()

        self.__ws.send(stomper.send(destination, message, content_type="application/json"))

        # try:
        #     self.__ws.send(stomper.send(destination, message, content_type="application/json"))
        #     # self.__ws.recv()

        # except Exception as e:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="send", response_time=total_time, exception=e)
        
        # else:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="send", response_time=total_time)

    def receive(self):
        start_time = time.time()
        message = ""

        message = self.__ws.recv()

        # try:
        #     # print("try...")
        #     message = self.__ws.recv()

        # except Exception as e:
        #     print("except:", e)
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="receive", response_time=total_time, exception=e)
        
        # else:
        #     print('else...')
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="receive", response_time=total_time, response_length=len(message))

        # print("Received message:", message)
        return message

    def disconnect(self):
        start_time = time.time()

        self.__ws.send(stomper.disconnect())

        # try:
        #     self.__ws.send(stomper.disconnect())
        #     # self.__ws.recv()

        # except Exception as e:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="disconnect", response_time=total_time, exception=e)
        
        # else:
        #     total_time = int((time.time() - start_time) * 1000)
        #     events.request.fire(request_type="STOMP", name="disconnect", response_time=total_time)
