import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
  private String url;
  private String user;
  private String password;

  public DatabaseManager() {
    // Load database connection properties from config.properties
    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream("config.properties")) {
      properties.load(fis);
      this.url = properties.getProperty("db.url");
      this.user = properties.getProperty("db.user");
      this.password = properties.getProperty("db.password");
    } catch (IOException e) {
      System.err.println("Error loading config.properties file: " + e.getMessage());
      System.exit(1);
    }
  }

  // Establishes a connection to the database
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  // Method to add a new book
  public void addBook(String title, String author, String isbn) {
    String sql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, title);
      pstmt.setString(2, author);
      pstmt.setString(3, isbn);
      pstmt.executeUpdate();
      System.out.println("Book added successfully!");
    } catch (SQLException e) {
      System.err.println("Error adding book: " + e.getMessage());
    }
  }

  // Method to list all books
  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM books";
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        books.add(new Book(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("isbn"),
            rs.getBoolean("is_available")));
      }
    } catch (SQLException e) {
      System.err.println("Error fetching books: " + e.getMessage());
    }
    return books;
  }

  // Method to add a new member
  public void addMember(String firstName, String lastName, String email) {
    String sql = "INSERT INTO members (first_name, last_name, email) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, firstName);
      pstmt.setString(2, lastName);
      pstmt.setString(3, email);
      pstmt.executeUpdate();
      System.out.println("Member added successfully!");
    } catch (SQLException e) {
      System.err.println("Error adding member: " + e.getMessage());
    }
  }

  // Method to list all members
  public List<Member> getAllMembers() {
    List<Member> members = new ArrayList<>();
    String sql = "SELECT * FROM members";
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        members.add(new Member(
            rs.getInt("member_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email")));
      }
    } catch (SQLException e) {
      System.err.println("Error fetching members: " + e.getMessage());
    }
    return members;
  }

  // Method to lend a book
  public void lendBook(int bookId, int memberId) {
    String updateBookSql = "UPDATE books SET is_available = FALSE WHERE book_id = ? AND is_available = TRUE";
    String insertLoanSql = "INSERT INTO loans (book_id, member_id, loan_date) VALUES (?, ?, CURDATE())";

    try (Connection conn = getConnection()) {
      conn.setAutoCommit(false); // Start transaction

      try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql);
          PreparedStatement insertStmt = conn.prepareStatement(insertLoanSql)) {

        updateStmt.setInt(1, bookId);
        int rowsAffected = updateStmt.executeUpdate();

        if (rowsAffected > 0) {
          insertStmt.setInt(1, bookId);
          insertStmt.setInt(2, memberId);
          insertStmt.executeUpdate();
          conn.commit(); // Commit the transaction
          System.out.println("Book lent successfully!");
        } else {
          System.out.println("Error: Book is either not available or does not exist.");
          conn.rollback(); // Rollback if update fails
        }
      } catch (SQLException e) {
        conn.rollback(); // Rollback on any error
        System.err.println("Transaction failed: " + e.getMessage());
      } finally {
        conn.setAutoCommit(true); // Restore default
      }
    } catch (SQLException e) {
      System.err.println("Connection error: " + e.getMessage());
    }
  }

  // Method to return a book
  public void returnBook(int bookId) {
    String updateBookSql = "UPDATE books SET is_available = TRUE WHERE book_id = ?";
    String updateLoanSql = "UPDATE loans SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL";

    try (Connection conn = getConnection()) {
      conn.setAutoCommit(false); // Start transaction

      try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql);
          PreparedStatement updateLoanStmt = conn.prepareStatement(updateLoanSql)) {

        updateBookStmt.setInt(1, bookId);
        int bookRowsAffected = updateBookStmt.executeUpdate();

        if (bookRowsAffected > 0) {
          updateLoanStmt.setInt(1, bookId);
          updateLoanStmt.executeUpdate();
          conn.commit(); // Commit the transaction
          System.out.println("Book returned successfully!");
        } else {
          System.out.println("Error: Book does not exist.");
          conn.rollback(); // Rollback if update fails
        }
      } catch (SQLException e) {
        conn.rollback(); // Rollback on any error
        System.err.println("Transaction failed: " + e.getMessage());
      } finally {
        conn.setAutoCommit(true); // Restore default
      }
    } catch (SQLException e) {
      System.err.println("Connection error: " + e.getMessage());
    }
  }
}
