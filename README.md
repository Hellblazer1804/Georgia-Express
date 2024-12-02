# Georgia-Express
DBMS Class term project

## Team Members:
1. Michael Robinson
2. Bandhan Patel
3. Het Pathak
4. Santos Ochoa
5. Dias Mashikov

## Instructions to Setup and Run the Project:

### Pre-requisites:
Make sure the following is installed.
1. Docker (https://docs.docker.com/engine/install/)
2. Maven (https://maven.apache.org/install.html) (On Mac just do if you have home brew: `brew install maven`)
3. Java 22
4. Any IDE (IntelliJ recommended)
5. Postman (https://www.postman.com/downloads/)
6. Node.js and npm (https://nodejs.org/en)

### Backend:

#### First steps:
1. Unzip the file or clone the repository.
2. Make sure docker is running
3. Open terminal and navigate to the docker folder
4. Spin up the database using `docker-compose up -d`

#### Running the backend using IntelliJ:
1. Open IntelliJ, go to File -> New -> Project from Existing Sources -> Select the unzipped folder.
2. Select Import Project from External Model -> Maven -> Next -> Next -> Finish.
3. Wait for the project to load.
4. Go to Maven Tab on the right hand corner -> Click on the Download Button
5. Run the file GeorgiaExpressApplication.java and that should spin up the backend on Port 8080.

#### Running the backend using terminal (in case intelliJ doesn't work):
1. Open terminal and navigate to the backend folder.
2. Run `mvn clean install` and if that fails then `mvn clean install -DskipTests`
3. Run `mvn spring-boot:run`
4. The backend should be running on port 8080.

### Frontend:
1. Open a new terminal and navigate to the frontend folder.
2. Run `npm install`
3. Run `npm run dev`
4. The frontend should be running on port 3000. You can access it on the browser using `http://localhost:3000`

### Database UI:
1. Make sure the postgres docker container is running. You can check this by running `docker ps`
2. Open pgAdmin (https://www.pgadmin.org/download/)
3. Create a new server with the following details:
    - Host: `localhost`
    - Port: `5432`
    - Username: `postgres`
    - Password: `test1234`
4. To get the tables, go to Servers -> Databases -> postgres -> Schemas -> public -> Tables

