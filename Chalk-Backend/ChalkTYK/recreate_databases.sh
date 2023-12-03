#!/bin/bash
# The containers should be up to run the script

# Get the directory of the script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Use a file relative to the script directory
file_path="$SCRIPT_DIR/mongo_recreate_db.js"

# Check if the first container exists
if docker ps -a --format '{{.Names}}' | grep -q "^chalktyk_postgres_1$"; then
    # Connect to PostgreSQL and drop/create 'chalktyk' database
    docker exec -it chalktyk_postgres_1 psql -U postgres -c "DROP DATABASE IF EXISTS chalktyk;"
    docker exec -it chalktyk_postgres_1 psql -U postgres -c "CREATE DATABASE chalktyk;"

    # Run "recreate mongo db" script
    docker exec chalktyk_mongodb_1 mongosh -u root -p password \
            --eval 'use chalktyk' \
            --eval 'db.getCollectionNames().forEach(function(c) { if (c.indexOf("system.") == -1) db.getCollection(c).drop(); })' \
            --eval 'exit'

else
    # Connect to PostgreSQL and drop/create 'chalktyk' database
    docker exec -it chalktyk-postgres-1 psql -U postgres -c "DROP DATABASE IF EXISTS chalktyk;"
    docker exec -it chalktyk-postgres-1 psql -U postgres -c "CREATE DATABASE chalktyk;"

    # Run "recreate mongo db" script
    docker exec chalktyk-mongodb-1 mongosh -u root -p password \
            --eval 'use chalktyk' \
            --eval 'db.getCollectionNames().forEach(function(c) { if (c.indexOf("system.") == -1) db.getCollection(c).drop(); })' \
            --eval 'exit'
fi