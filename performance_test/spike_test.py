from locust import HttpUser, between, task


class UserApi(HttpUser):
    wait_time = between(1, 2)

    # Header
    @task
    def get_user_info(self):
        headers = {"userId": "12345678911"}
        self.client.get("/users/", headers=headers)

    # Body
    @task
    def post_user(self):
        self.client.post("/v2/user", json={"name": "userqa", "email": "userqa@userqa.com"})

    # Params
    @task
    def get_user(self):
        params = {"id": 1, "name": "userqa"}
        self.client.get("/user", params=params)
        with self.client.get("/", catch_response=True) as response:
            if response.text != "Success":
                response.failure("Got wrong response")
            elif response.elapsed.total_seconds() > 0.5:
                response.failure("Request took too long")