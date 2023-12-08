#!/bin/bash

echo "---- Running White-Box code coverage technique"

./mvnw test

echo "---- White-Box code coverage technique ran successfully"

open ./target/site/jacoco/index.html