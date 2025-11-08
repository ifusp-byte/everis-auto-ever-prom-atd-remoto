#!/bin/bash
find . -type f -name "*DAO.java" -exec rm -f {} +
mvn clean