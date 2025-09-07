# Library Management System

This is a simple console-based library management system built with Java and MariaDB. It allows for basic database operations to manage books, members, and book loans.

## Project Structure

- `Book.java`: Java class for the Book entity.
- `DatabaseManager.java`: Handles all database connection and CRUD (Create, Read, Update, Delete) operations.
- `LibraryApp.java`: The main application class with the primary logic.
- `Member.java`: Java class for the Member entity.
- `library.sql`: SQL script to create the database schema and insert initial data.
- `mariadb-java-client-3.1.4.jar`: The JDBC driver for connecting Java to MariaDB.
- `config.properties`: Configuration file for storing database connection details.

## Prerequisites

To run this project locally, you need the following software installed on your system:

- **Java Development Kit (JDK) 8 or higher**
- **MariaDB Server**
- **Maven** or **Gradle** (recommended for dependency management) or the ability to manually manage the classpath.

## Local Setup Instructions

### Step 1: Clone the Repository

First, clone this repository to your local machine using git:

```bash
git clone https://github.com/akash2003git/library-management-java.git
cd library-management-java
```

### Step 2: Set Up the MariaDB Database

You'll need to create the database and tables using the provided SQL script.

1. Log in to your MariaDB server.

   ```Bash
   sudo mariadb -u root -p
   ```

2. Run the `library.sql` script to create the database and tables.

   ```SQL
   SOURCE /path/to/your/project/folder/library.sql;
   ```

### Step 3: Configure the Database Connection

Create a `config.properties` file in the root directory of the project and add your database credentials.

```
db.url=jdbc:mariadb://localhost:3306/library_db
db.user=root
db.password=your_strong_password
```

### Step 4: Run the Application

Now, you can compile and run the Java application from your terminal.

1. Compile the Java files:

   ```Bash
   javac -cp .:mariadb-java-client-3.1.4.jar *.java
   ```

2. Run the main application:

   ```Bash
   java -cp .:mariadb-java-client-3.1.4.jar LibraryApp

   ```

## Future Enhancements

- Add more advanced features like user authentication.
- Implement a command-line interface (CLI) for better user interaction.
- Use `PreparedStatement` to prevent SQL injection attacks.
- Integrate with a web framework like Spring Boot to create a web-based application.
