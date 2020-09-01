#!/bin/bash

curl -i -X POST http://localhost:8001/services/producer_service/routes   --data 'paths[]=/producer'   --data 'name=producer'
curl -i -X POST http://localhost:8001/services  --data name=producer_service  --data url='http://producer:8080/'

# Sample message
#curl -i -X PUT --header Content-Type:application/json http://localhost:8000/producer/api/message/123456

