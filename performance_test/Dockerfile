FROM python:3.12-slim
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo Asia/Seoul > /etc/timezone

WORKDIR /locust

COPY src/ ./src/
COPY pyproject.toml ./

RUN pip install poetry
RUN poetry install

EXPOSE 8089
ENTRYPOINT ["poetry", "run", "locust", "-f", "src/load/locustfile.py", "--host", "http://0.0.0.0:8089"]