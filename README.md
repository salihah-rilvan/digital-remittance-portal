
# IS442 - G1T2 SMU-X (TechG)

The objective of this project is to develop a Digital Remittance Portal for TechG.

The Digital Remittance Portal is a web application which helps to automate the process of checking if the required fields are mapped correctly
upon transaction using the Springboot framework and the PostgreSQL database.

2 key functions:
- Allow new users to upload transaction headers into the portal and check if the fields are mapped properly against Single Source of Truth (SSOT).
The system will also help by recommending the suggested mapping. Users can edit if it is not appropriate and submit for the onboarding process.
- The user can upload transactions and the field contents will be mapped and directed to the backend API to process the transaction.



[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=7000818&assignment_repo_type=AssignmentRepo)

## Prerequisites

- Java 11
- Maven 3.6.0
- <a href="https://www.timescale.com/blog/how-to-install-psql-on-mac-ubuntu-debian-windows/" target="_blank">Postgresql 14.2</a>

## Installation
On a WSL terminal, clone repository with SSH into current directory.
```bash
git clone git@github.com:SCIS-CS102-2022/project-g1t02.git
```
## Configure database
After downloading the repository, configure application properties

```bash
# On WSL terminal
nano project-g1t02\springboot\src\main\resources\application.properties
```

### For default use
By default, Hibernate is used to created entities in a Postgresql database on localhost. 

```bash
# In application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=password
```
Start posgres and log in to psql as postgres user. Type your password for sudo user when prompted.
```bash
# On WSL terminal
sudo service postgresql start
sudo -u postgres psql
```
In the psql shell, run the following command to change postgres user password to "password"

```bash
# In psql shell
ALTER USER postgres WITH PASSWORD 'password';
```

### Custom database configurations

Instead of the default settings, you can replace the variables however you want it

`server_url` = Url to your server E.g. localhost

`database_name` = Name of the database in the server

`username` = Name of username for the database

`password` = Password for username in database

```bash
# In project-g1t02\springboot\src\main\resources\application.properties
spring.datasource.url=jdbc:postgresql://${server_url}:{port_number}/{database_name}
spring.datasource.username={username}
spring.datasource.password={password}
```






## Run Locally
After downloading the repository and configuring the database, run the application.



Go to the project directory

```bash
cd project-g1t02/springboot
```



Start the application

```bash
mvn spring-boot:run
```


## Check if application is running

Application will take a few seconds to start-up. Click link below to visit.

[http://localhost:8080/login](http://localhost:8080/login)


## Project team

- Lim Shen Jie (Jeff)
- Siti Salihah Binte Mohamed Rilvan
- Lin Xin Rose
- Kwak Yoon Joo
- Cha Da Eun
- Bui Phuong Thao
