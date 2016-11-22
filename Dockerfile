# Build version of dockerfile  
# needs to be root for docker context to include parent
# does nothing but mvn clean install
FROM maven:3.3.9-jdk-8-onbuild-alpine
