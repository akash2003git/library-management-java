public class Book {
  private int id;
  private String title;
  private String author;
  private String isbn;
  private boolean isAvailable;

  public Book(int id, String title, String author, String isbn, boolean isAvailable) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.isAvailable = isAvailable;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getIsbn() {
    return isbn;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  @Override
  public String toString() {
    return String.format(
        "ID: %-3d | Title: %-30s | Author: %-25s | ISBN: %-15s | Available: %s",
        id, title, author, isbn, isAvailable ? "Yes" : "No");
  }
}
