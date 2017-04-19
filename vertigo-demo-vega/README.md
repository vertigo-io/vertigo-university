# Vertigo demo application API [![Codacy Badge](https://api.codacy.com/project/badge/Grade/24b7fdb0ed1b4ff2b129d77feb97bd2e)](https://www.codacy.com/app/npi2loup/vertigo-demo-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=KleeGroup/vertigo-demo-api&amp;utm_campaign=Badge_Grade)
## Purpose

This application sets API for Focus-demo-app

## How to install it ?

### Install Vertigo Snapshot into your repo (needed for this version only)
Clone or download vertigo code sources branch __develop__.
Install vertigo snapshot :

```shell
mvn clean source:jar install -DskipTests
```

### Generate vertigo demo standalone jar :

Clone or download vertigo-demo code sources.

Generate standalone runnable jar :

```shell
mvn clean compile assembly:single
```

## How to launch it ?

1- Prepare database directory
```shell
mkdir d:/pandora
mkdir d:/pandora/data
mkdir d:/pandora/search
```

2- Copy database into d:/pandora/data

3- Just launch the generated standalone jar :
```shell
java -jar target/vertigo-demo-api-1.1-SNAPSHOT-jar-with-dependencies.jar
```
The API serve datas at this URL : `http://localhost:8080/`.

You could check API at this URL : `http://localhost:8080/swaggerUi`.

## Change home directory
You can change the home directory by setting a system property `pandora.home` on command line.
```shell
java -Dpandora.home=/opt/app/pandora -jar target/vertigo-demo-api-1.1-SNAPSHOT-jar-with-dependencies.jar
```

