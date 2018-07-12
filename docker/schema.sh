#!/usr/bin/env bash

echo "Running schema.cql"
docker exec cassandra_docker /bin/bash cqlsh -f schema.cql
echo "Finished running schema.cql"