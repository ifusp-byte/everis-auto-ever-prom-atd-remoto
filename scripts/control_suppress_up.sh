#!/bin/bash
find . -type f -name "*.java" | while read -r file; do
  sed -i 's/^\(\s*\)\/\/\s*@\s*SuppressWarnings/\1@SuppressWarnings/' "$file"
done
