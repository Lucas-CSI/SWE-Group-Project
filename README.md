```
Installation Guide:

Install:
Java Version: 21
Node-js version: v22.10.7
MySQL Server - run it on port 3306 and make sure the service is running (windows: check services.msc)

1. Download the project files from the github / canvas
2. Navigate to SWE-Group-Project/seaSideEscape in cmd line
3. Run mvn clean install in cmd line
4. Navigate to SWE-Group-Project/frontend in cmd line
5. Run npm install --force  in cmd line

Import MySQL database:
1. Create a new database called seasideescapedb
2. Import the seasideescapedb.sql file in SWE-Group-Project directory
3. Create a user in mysql that has permissions to alter every table in seasideescapedb
4. In application.properties (SWE-Group-Project/seaSideEscape/main/resources) set the mySQL database username and password for the sql user created above
  - spring.datasource.username=usernamehere
  - spring.datasource.password=password

Running Project:
1. Run npm start build in cmd line in SWE-Group-Project/frontend to run the frontend
2. Run mvn spring-boot:run in SWE-Group-Project/seaSideEscape to run the backend
3. The backend is hosted on localhost:8080
4. Frontend is hosted on localhost:3000

To use the website, open your web browser to localhost:3000


Endpoints:
/admin/homepage - admin portal
/userPortal - user portal
/rooms - rooms


Run Javadoc: ./SWE-Group-Project/index.html
