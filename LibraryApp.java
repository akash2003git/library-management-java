import java.util.List;
import java.util.Scanner;

public class LibraryApp {
  private static DatabaseManager dbManager;
  private static Scanner scanner;

  public static void main(String[] args) {
    dbManager = new DatabaseManager();
    scanner = new Scanner(System.in);
    System.out.println("Welcome to the Command Line Library Management System!");

    while (true) {
      displayMenu();
      String choice = scanner.nextLine();

      switch (choice) {
        case "1":
          addBook();
          break;
        case "2":
          listAllBooks();
          break;
        case "3":
          addMember();
          break;
        case "4":
          listAllMembers();
          break;
        case "5":
          lendBook();
          break;
        case "6":
          returnBook();
          break;
        case "7":
          System.out.println("Exiting application. Goodbye!");
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
      System.out.println(); // Add a blank line for readability
    }
  }

  private static void displayMenu() {
    System.out.println("-------------------------------------");
    System.out.println("1. Add a new book");
    System.out.println("2. List all books");
    System.out.println("3. Add a new member");
    System.out.println("4. List all members");
    System.out.println("5. Lend a book");
    System.out.println("6. Return a book");
    System.out.println("7. Exit");
    System.out.println("-------------------------------------");
    System.out.print("Enter your choice: ");
  }

  private static void addBook() {
    System.out.print("Enter book title: ");
    String title = scanner.nextLine();
    System.out.print("Enter book author: ");
    String author = scanner.nextLine();
    System.out.print("Enter book ISBN: ");
    String isbn = scanner.nextLine();
    dbManager.addBook(title, author, isbn);
  }

  private static void listAllBooks() {
    System.out.println("\n--- Library Books ---");
    List<Book> books = dbManager.getAllBooks();
    if (books.isEmpty()) {
      System.out.println("No books found.");
    } else {
      books.forEach(System.out::println);
    }
  }

  private static void addMember() {
    System.out.print("Enter member's first name: ");
    String firstName = scanner.nextLine();
    System.out.print("Enter member's last name: ");
    String lastName = scanner.nextLine();
    System.out.print("Enter member's email: ");
    String email = scanner.nextLine();
    dbManager.addMember(firstName, lastName, email);
  }

  private static void listAllMembers() {
    System.out.println("\n--- Library Members ---");
    List<Member> members = dbManager.getAllMembers();
    if (members.isEmpty()) {
      System.out.println("No members found.");
    } else {
      members.forEach(System.out::println);
    }
  }

  private static void lendBook() {
    listAllBooks();
    System.out.print("Enter the Book ID to lend: ");
    int bookId = Integer.parseInt(scanner.nextLine());
    listAllMembers();
    System.out.print("Enter the Member ID: ");
    int memberId = Integer.parseInt(scanner.nextLine());
    dbManager.lendBook(bookId, memberId);
  }

  private static void returnBook() {
    System.out.print("Enter the Book ID to return: ");
    int bookId = Integer.parseInt(scanner.nextLine());
    dbManager.returnBook(bookId);
  }
}
