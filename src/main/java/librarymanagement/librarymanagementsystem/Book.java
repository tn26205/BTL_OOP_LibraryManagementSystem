package librarymanagement.librarymanagementsystem;

import javafx.scene.image.Image;

import java.sql.Date;

public class Book {
    private int id;
     private String isbn;
     private String name;
     private String author;
     private Integer quantity;
     private String publisher;
     private String description;
     private Date date_received;
     private String cover;

     public Book(Integer id, String isbn, String name, String author,  Integer quantity,String publisher,
                 String description, Date date_received, String cover){
          this.id = id;
          this.isbn = isbn;
          this.name = name;
          this.author = author;
          this.quantity = quantity;
          this.publisher = publisher;
          this.description = description;
          this.date_received = date_received;
          this.cover = cover;
     }


    public int getId() {
         return id;
     }
     public void setId(int id) {
         this.id = id;
     }
     public String getIsbn() {
         return isbn;
     }
     public void setIsbn(String isbn) {
         this.isbn = isbn;
     }
     public String getName() {
         return name;
     }
     public void setName(String name) {
         this.name = name;
     }
     public String getAuthor() {
         return author;
     }
     public void setAuthor(String author) {
         this.author = author;
     }
     public Integer getQuantity() {
         return quantity;
     }
     public void setQuantity(Integer quantity) {
         this.quantity = quantity;
     }
     public Date getDate_received() {
         return date_received;
      }
      public void setDate_received(Date date_received) {
         this.date_received = date_received;
      }

      public String getCover() {
         return cover;
      }
      public void setCover(String cover) {
         this.cover = cover;
      }
      public String getPublisher() {
         return publisher;
      }
      public void setPublisher(String publisher) {
         this.publisher = publisher;
      }
      public String getDescription() {
         return description;
      }
      public void setDescription(String description) {
         this.description = description;
      }
}
