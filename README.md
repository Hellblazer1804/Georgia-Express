# Georgia-Express
DBMS Class term project

## Instructions to Setup:
### Backend:
1. On IntelliJ, use the setup from version control to setup the repo
2. It should give you a prompt to load from Maven at the bottom right, click the Load button and the backend will be setup
3. To setup the database:
   - `cd docker`
   - `docker-compose up -d`
4. In the `backend` directory, run `mvn clean install` to initialize
5. Run the application, run the java file `GeorgiaExpressApplication.java`
6. To setup the Data Source on IntelliJ , click the database logo on the right hand corner of IntelliJ
7. Select the add logo here, ![datasource_field](images/image1.png)
8. This is how the adding look like: ![datasource_add](images/image2.png)
9. Get the password from the docker file and the database should appear
