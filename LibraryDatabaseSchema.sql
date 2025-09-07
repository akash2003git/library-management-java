-- Drop the database if it already exists to ensure a clean slate
DROP DATABASE IF EXISTS library_db;

-- Create the database
CREATE DATABASE library_db;

-- Use the newly created database
USE library_db;

-- Table for storing book information
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE
);

-- Table for storing member information
CREATE TABLE members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Table for managing book loans
-- This table links books and members, and tracks the loan status
CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE
);

-- Insert some initial data into the tables
INSERT INTO books (title, author, isbn) VALUES
('The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', '978-0345391803'),
('Dune', 'Frank Herbert', '978-0441172719'),
('The Lord of the Rings', 'J.R.R. Tolkien', '978-0618053267'),
('Foundation', 'Isaac Asimov', '978-0553293357');

INSERT INTO members (first_name, last_name, email) VALUES
('John', 'Doe', 'john.doe@example.com'),
('Jane', 'Smith', 'jane.smith@example.com');
