# Account and Loan Management Project
This project is a Java application that uses Javalin as the web framework and PostgreSQL as the database. It allows managing user accounts, loans, and authentication through sessions.


## How to Run the Application
###  Running with Maven
1. Clone the repository or download the source code.
2.  Open a terminal in the project's root directory.
3. Run the following command to compile and execute the application: `mvn clean compile exec:java`

### Running with java -jar
1. First, compile the project and generate an executable JAR file: `mvn clean package`
    This will generate a JAR file in the target folder.
2. Then, run the JAR file with the following command: `java -jar target/project-name.jar`

**Note:** The application will be available at:
      `http://localhost:7070`


## Database Configuration
### 1. Requirements
- PostgreSQL: Ensure PostgreSQL is installed and running.
- Database: Create a database named bank_db (or any name you prefer).

### 2. Configuration
- Open the database configuration file at: `src/main/resources/application.properties.`
- Modify the following properties with your credentials and connection details:
```
db.url=jdbc:postgresql://localhost:5432/bank_db
db.username=your_username
db.password=your_password
```
`db.url:` The database connection URL.

`db.username:` Your PostgreSQL username.

`db.password:` Your PostgreSQL password.
### 3. Save the changes


## Running Unit Tests
### To run the unit tests, follow these steps:
1. Open a terminal in the project's root directory.
2. Run the following command: `mvn test`

This will execute all the unit tests defined in the `src/test/java folder`.


## Available Endpoints
### Accounts (/accounts)
- POST `/accounts`: Create a new account.
- GET `/accounts`: Get all accounts (managers only).
- GET `/accounts/{id}`: Get an account by its ID.
- PUT `/accounts/{id}`: Update an account (managers or account owner only).

### Loans (/loans)
- POST `/loans`: Create a new loan.
- GET `/loans`: Get all loans (managers only) or loans for the authenticated user.
- GET `/loans/{id}`: Get a loan by its ID.
- PUT `/loans/{id}`: Update a loan (managers or loan owner only).

### Authentication (/login and /logout)
- POST `/login`: Log in.
- POST `/logout`: Log out.


## Usage Examples with Postman
### 1. Create an Account
- Method: POST
- URL: `http://localhost:7070/accounts`
- Body (JSON):
```
{
    "email": "test@example.com",
    "password": "password123",
    "is_manager": false
}
```

### 2. Log In
- Method: POST
- URL: `http://localhost:7070/login`
- Body (JSON):
```
{
    "email": "test@example.com",
    "password": "password123"
}
```

### 3. Get All Loans (Manager)
- Method: GET
- URL: `http://localhost:7070/loans`



## Technologies Used
- Java: Primary programming language.
- Javalin: Web framework for building RESTful APIs.
- PostgreSQL: Relational database.
- JUnit and Mockito: For unit testing.
- Maven: For dependency management and project building.






