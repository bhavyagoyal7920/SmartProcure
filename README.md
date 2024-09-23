After opening the Smart-Procure master into your IntelliJ:

  1.Need to step up the Mysql database:
  
   a. Go to MySQL workbench and create a dataBase: `Create DATABASE database_name;`
   
   b. Go to `application.properties` in IntelliJ and add these values:

     spring.datasource.url=jdbc:mysql://localhost:<port number>/database_name
     
     spring.datasource.username=username
     
     spring.datasource.password=password
  c. Now go to `SmartProcureApplication` and run the application. 
