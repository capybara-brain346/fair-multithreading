#!/bin/bash

NUM_REQUESTS=5

make_request() {
    local id=$1
    echo "Starting request $id"
    curl -s http://localhost:8080 &
    echo "Request $id sent"
}

for i in $(seq 1 $NUM_REQUESTS); do
    make_request $i
done

wait

echo "All requests completed"
