#!/usr/bin/env bash

# Start the java process
./scripts/start.sh &
$PROCESS_1 = $! &
# Start the data seeding process
./scripts/seed_data.sh &
$PROCESS_2 = $!

# Wait for processes to complete
wait $PROCESS_1 $PROCESS_2