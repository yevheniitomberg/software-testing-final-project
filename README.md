---

### This application was created to check/calculate currency rates

---

### I have done following tasks:

- User features:
  - User can register with (name, email, password)
  - Password will be encrypted via BCrypt encoder
  - User cannot register without password
  - User can log in only with email and password
  - User can update personal information
  - User can delete own account but info about this user will be stored
  - User with deleted account cannot log in
  - After deleting user can register same email again but data from previous account will be not allowed
  - Only authenticated users can use exchange calculator
- Admin features:
  - Admin can see all users
  - Admin can activate/inactivate simple users (cannot do this for admins)
  - Can update any information about other users except email
  - Simple user cannot visit admin panel
- Currency calculator:
  - I was using external API to get information about currencies (https://exchangerate.host/)
  - Every authenticated user can user exchange calculator
  - User can select by drop-down from currency, to currency and insert amount
- Logging:
  - Every LOGIN/LOGOUT are logged
  - Every API request/response are logged
  - All logged data is storing in app.log file
- Validation:
  - Application Services are covered by JUnit/Mockito unit tests
  - Unit tests will work on any computer cause of remote database connection
  - WebMvc is covered by JUnit/Mockito unit tests
  - POST data has validation via javax.validation annotations
  - All user's mistakes will be displayed to user via html view

---
### In this application I was using following technologies:

- Java 11
- Spring Boot with Spring Security
- Gradle
- Javascript
- Thymeleaf
- Bootstrap
- MySQL database
- JUnit5
- Mockito
- Docker
- Selenium
- TestNG

---
### Log in information

You can create your own account via registration, but you will have only "USER" role permissions.
To log in via administrator you should use following credentials:
- Email/Username: yevhenii@tomberg.com
- Password: password

#### NB! ADMIN user will be created automatically. It's not possible to create ADMIN manually

---
### Docker instructions (Spring boot + MySQL):

To dockerize this application via Docker you should execute my own batch file in project directory
> .\docker-init.bat

#### Explaination commands in batch file

We should make following steps to let Spring boot connect to MySQL in a one network via containers
Step 1:
Pull the mysql image from docker hub
> docker pull mysql:5.7

Step 2:
Create a docker network for Spring app and MySQL communication
> docker network create network-name

Step 3:
Run mysql container in this network
> docker run --name dbname --network network-name -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=database-name -e MYSQL_USER=username -e MYSQL_PASSWORD=password -d mysql:5.7

Step 4:
Update application.properties
```
spring.datasource.url=jdbc:mysql://swedbank-database:3306/swedbankdb
spring.datasource.username=as
spring.datasource.password=yevhenii_tomberg
```


Step 5:
Create Dockerfile file

Step 6:
Create Spring docker image
> docker build -t image-name .

Step 7:
Start Spring Boot App container in the same network 
> docker run --network network-name --name app-container -p hostport:vmport -d image-name



