```
Installation Guide:

Install:
Java Version: 21
Node-js version: v22.10.7
MySQL Server - run it on port 3306 and make sure the service is running (windows: check services.msc)
Download the project files from the github / canvas
Navigate to SWE-Group-Project/seaSideEscape in cmd line
Run mvn clean install in cmd line
Navigate to SWE-Group-Project/frontend in cmd line
Run npm install --force  in cmd line
Import MySQL database:
Create a new database called ```seasideescapedb```
Import the seasideescapedb.sql file in SWE-Group-Project directory
Create a user in mysql that has permissions to alter every table in seasideescapedb
In ```application.properties``` (SWE-Group-Project/seaSideEscape/main/resources) set the mySQL database username and password for the sql user created above
```spring.datasource.username=usernamehere```
```spring.datasource.password=password```

Running Project:
Run npm start build in cmd line in SWE-Group-Project/frontend to run the frontend
Run mvn spring-boot:run. in SWE-Group-Project/seaSideEscape to run the backend
The backend is hosted on localhost:8080
Frontend is hosted on localhost:3000```

```To use the website, open your web browser to localhost:3000```




