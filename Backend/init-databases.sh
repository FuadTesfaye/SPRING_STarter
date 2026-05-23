#!/bin/bash
set -e

# Creates each database if it doesn't already exist.
# Called automatically by the postgres Docker image on first run.

create_database() {
    local db=$1
    echo "Creating database: $db"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-SQL
        SELECT 'CREATE DATABASE $db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$db')\gexec
SQL
}

create_database authdb
create_database orderdb
create_database paymentdb
create_database inventorydb
create_database shippingdb
