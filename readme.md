# OTUS micro-services course homework

build project
````
mvn clean package
````

build project module
````
mvn --projects 'common,user-service' clean package -DskipTests=true
````