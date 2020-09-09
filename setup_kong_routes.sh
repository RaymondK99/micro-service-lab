#!/bin/bash

curl -i -X POST http://localhost:8001/services/consumer_service/routes   --data 'paths[]=/consumer'   --data 'name=consumer'
curl -i -X POST http://localhost:8001/services  --data name=consumer_service  --data url='http://consumer:8080/'

# Sample message
#curl -i --header Content-Type:application/json http://localhost:8000/consumer/api/tickerFeed/Binance/BTC


