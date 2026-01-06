# A2A

```

ollama run llama3.2 

docker exec -it broker kafka-console-consumer \
  --topic weather-events \
  --bootstrap-server localhost:9092 

docker exec -it broker kafka-console-consumer \
  --topic weather-archive \
  --bootstrap-server localhost:9092 

docker exec -it broker kafka-console-consumer \
  --topic random-events \
  --bootstrap-server localhost:9092 

```