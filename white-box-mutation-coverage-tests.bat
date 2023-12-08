#!/bin/bash

echo "---- Running White-Box mutation coverage technique"

./mvnw test-compile org.pitest:pitest-maven:mutationCoverage

echo "---- White-Box code mutation technique ran successfully"

open ./target/pit-reports/index.html