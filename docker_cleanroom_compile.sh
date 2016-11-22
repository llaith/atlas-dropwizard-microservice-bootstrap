#!/usr/bin/env bash

echo "Note: Used only for a clean-room compile, to checking you are not dependent on jars in your local maven cache." 
echo "      Use only as a last step before distribution. Use 'mvn clean install' for normal builds."
echo "      If a normal build works and this one fails, that means you have errant dependencies in your local maven cache."

echo -e "\nBuild docker:"
docker build --no-cache -t atlas-build .

