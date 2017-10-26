#!/usr/bin/env bash

# Wait for the Simple-List-Service to become available
until curl -s http://${hostname}/healthz
do
    echo "Waiting for Simple-List-Service to come up"
    sleep 5
done

# Begin POSTs to the service to seed data
echo "Simple-List-Service is up! Seeding data"

echo "Posting DCU Topics"
curl -vX POST http://${hostname}/legacy/list/TopicList/DCU -F "file=@/app/data/DCU_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting UKVI Topics"
curl -vX POST http://${hostname}/legacy/list/TopicList/UKVI -F "file=@/app/data/UKVI_Topics.csv" \
 -H "Content-Type: multipart/form-data"

echo "Posting Unit and Team structures"
curl -vX POST http://${hostname}/legacy/units -F "file=@/app/data/Unit_Team_Structure.csv" \
 -H "Content-Type: multipart/form-data"

# Perform a series of GETs to prime the cache for the seeded data
echo "Priming cache on resources"
curl -s -o /dev/null -v http://${hostname}/list/UnitTeams
curl -s -o /dev/null -v http://${hostname}/list/TopicListDCU
curl -s -o /dev/null -v http://${hostname}/list/TopicListUKVI
curl -s -o /dev/null -v http://${hostname}/legacy/list/TopicList
curl -s -o /dev/null -v http://${hostname}/legacy/units/UnitTeams
