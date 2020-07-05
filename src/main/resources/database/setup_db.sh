#!/bin/sh

# execute with sudo privileges

user="order"
password="order"
database="order"
host="localhost"
port=5432

if [ ! -z "$1" ]; then
   user=$1
fi

if [ ! -z "$2" ]; then
    password=$2
fi

if [ ! -z "$3" ]; then
    host=$3
fi

if [ ! -z "$4" ]; then
    port=$4
fi

sudo -u postgres psql -f init.sql
psql postgresql://${user}:${password}@${host}:${port} -f schema.sql

echo "script executed"
