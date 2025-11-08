#!/bin/bash
PROJECT_DIR=$(pwd)
find "$PROJECT_DIR" -type f -regex ".*\(copy\|DUPLICATE\|_copy\| - Copy\).*" | while read -r file; do
    echo "Removendo arquivo duplicado: $file"
    rm -f "$file"
done
mvn clean