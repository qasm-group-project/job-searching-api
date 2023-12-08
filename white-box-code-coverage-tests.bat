#!/bin/bash

echo "---- Running White-Box code coverage technique"

./mvnw test

echo "---- White-Box code coverage technique ran successfully"
echo "---- To see the results open the file located ./target/site/jacoco/index.html"

