FROM python:3
WORKDIR /chalk-api
COPY . .
RUN pip install flask openai redis tiktoken
CMD python server.py
