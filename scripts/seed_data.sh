#!/usr/bin/env bash

# Wait for the Simple-List-Service to become available
until curl -s http://${hostname}/healthz
do
    echo "Waiting for Simple-List-Service to come up"
    sleep 5
done

# Begin POSTs to the service to seed data
echo "Simple-List-Service is up! Seeding data"

sleep 5

echo "Posting DCU Topics"
curl -sX POST http://${hostname}/legacy/list/TopicList/DCU -F "file=@/app/data/DCU_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting UKVI Topics"
curl -sX POST http://${hostname}/legacy/list/TopicList/UKVI -F "file=@/app/data/UKVI_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting Unit and Team structures"
curl -sX POST http://${hostname}/legacy/units -F "file=@/app/data/Unit_Team_Structure.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting DCU Users"
curl -sX POST http://${hostname}/legacy/users/DCU -F "file=@/app/data/DCU_Users.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting FOI Users"
curl -sX POST http://${hostname}/legacy/users/FOI -F "file=@/app/data/FOI_Users.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting HMPO CCC Users"
curl -sX POST http://${hostname}/legacy/users/HMPOCCC -F "file=@/app/data/HMPO_CCC_Users.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting HMPO COL Users"
curl -sX POST http://${hostname}/legacy/users/HMPOCOL -F "file=@/app/data/HMPO_Collectives_Users.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting UKVI Users"
curl -sX POST http://${hostname}/legacy/users/UKVI -F "file=@/app/data/UKVI_Users.csv" \
 -H "Content-Type: multipart/form-data"

# Perform a series of GETs to prime the cache for the seeded data
echo "Priming cache on resources"

sleep 5

curl -s -o /dev/null -v http://${hostname}/legacy/list/TopicList