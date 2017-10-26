#!/usr/bin/env bash

until curl http://sls:8080/healthz
do
    echo "Waiting for Simple-List-Service to come up"
    sleep 2
done

echo "Simple-List-Service is up! Seeding data"

echo "Posting DCU Topics"
curl -vX POST http://sls:8080/legacy/list/TopicList/DCU -F "file=@/app/data/DCU_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting UKVI Topics"
curl -vX POST http://sls:8080/legacy/list/TopicList/UKVI -F "file=@/app/data/UKVI_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting Unit and Team structures"
curl -vX POST http://sls:8080/legacy/units -F "file=@/app/data/Unit_Team_Structure.csv" \
 -H "Content-Type: multipart/form-data"