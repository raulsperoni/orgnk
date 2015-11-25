#!/bin/bash

TEST=`gosu postgres postgres --single <<- EOSQL
   SELECT 1 FROM pg_database WHERE datname='$POSTGRES_DB';
EOSQL`

echo "******CREATING DOCKER DATABASE******"
if [[ $TEST == "1" ]]; then
    # database exists
    # $? is 0
    exit 0
else
gosu postgres postgres --single <<- EOSQL
   CREATE ROLE $POSTGRES_USER WITH LOGIN ENCRYPTED PASSWORD '${POSTGRES_PASSWORD}' CREATEDB;
EOSQL

gosu postgres postgres --single <<- EOSQL
   CREATE DATABASE $POSTGRES_DB WITH OWNER $POSTGRES_USER TEMPLATE template0 ENCODING 'UTF8';
EOSQL

gosu postgres postgres --single <<- EOSQL
   GRANT ALL PRIVILEGES ON DATABASE $POSTGRES_DB TO $POSTGRES_USER;
EOSQL
fi

echo ""
echo "******DOCKER DATABASE CREATED******"
