package librarymanagement.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class IssueBook {
    private Integer book_id;
    private String user_id;
    private String status ;
    private String issue_date;
    private String Return_date;
    private String note;

   public Integer getBook_id() {
       return book_id;
   }
   public void setBook_id(Integer book_id) {
       this.book_id = book_id;
   }
   public String getUser_id() {
       return user_id;
   }
   public void setUser_id(String user_id) {
       this.user_id = user_id;
   }
   public String getStatus() {
       return status;
   }
   public void setStatus(String status) {
       this.status = status;
   }
   public String getIssue_date() {
       return issue_date;
   }
   public void setIssue_date(String issue_date) {
       this.issue_date = issue_date;
   }
   public String getReturn_date() {
       return Return_date;
   }
   public void setReturn_date(String return_date) {
       Return_date = return_date;
   }
   public String getNote() {
       return note;
   }
   public void setNote(String note) {
       this.note = note;
   }
   public IssueBook(Integer book_id, String user_id, String status, String issue_date, String return_date, String note) {
       this.book_id = book_id;
       this.user_id = user_id;
       this.status = status;
       this.issue_date = issue_date;
       Return_date = return_date;
       this.note = note;
   }
    public static void addIssue(Integer book_id, String user_id, String status, String issue_date, String return_date, String note) {
        // Kiểm tra Book ID
        if (book_id == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Book ID is required.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra User ID
        if (user_id == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("User ID is required.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra Issue Date
        if (issue_date == null || issue_date.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Issue Date is required.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra Return Date
        if (return_date == null || return_date.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Return Date is required.");
            alert.showAndWait();
            return;
        }

        Connection connection = Database.connectDb();
        String sql = "INSERT INTO issue_book (book_id, user_id, status, issue_date, return_date, note) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement prepare = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate issueDate = LocalDate.parse(issue_date, formatter);
            LocalDate returnDate = LocalDate.parse(return_date, formatter);

            // Kiểm tra nếu return_date nhỏ hơn issue_date
            if (returnDate.isBefore(issueDate)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Date");
                alert.setHeaderText(null);
                alert.setContentText("Return date cannot be before issue date.");
                alert.showAndWait();
                return;
            }

            prepare = connection.prepareStatement(sql);

            // Gán giá trị cho PreparedStatement
            prepare.setInt(1, book_id);
            prepare.setString(2, user_id);
            prepare.setString(3, status);
            prepare.setString(4, issue_date);
            prepare.setString(5, return_date);
            prepare.setString(6, note);

            int rowsAffected = prepare.executeUpdate();

            Alert alert;
            if (rowsAffected > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Issue added successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add issue.");
            }
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        } finally {
            // Đóng các tài nguyên
            try {
                if (prepare != null) prepare.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkBookAvailability(Integer book_id) {
        Connection connection = Database.connectDb();
        String sql = "SELECT count(*) AS total FROM `issue_book` WHERE book_id = ? AND status = 'issued'";
        PreparedStatement prepare = null;
        ResultSet resultSet = null;

        try {
            prepare = connection.prepareStatement(sql);
            prepare.setInt(1, book_id);
            resultSet = prepare.executeQuery();

            if (resultSet.next()) {
                int issuedCount = resultSet.getInt("total");

                // Kiểm tra tổng số lượng sách và số lượng sách đã mượn
                int availableQuantity = getBookTotalQuantity(book_id) - issuedCount;
                return availableQuantity > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (prepare != null) prepare.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Trả về false nếu không tìm thấy sách hoặc có lỗi xảy ra
        return false;
    }

    private static int getBookTotalQuantity(Integer book_id) {
        Connection connection = Database.connectDb();
        String sql = "SELECT quantity FROM books WHERE id = ?";
        PreparedStatement prepare = null;
        ResultSet resultSet = null;

        try {
            prepare = connection.prepareStatement(sql);
            prepare.setInt(1, book_id);
            resultSet = prepare.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (prepare != null) prepare.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Trả về 0 nếu không tìm thấy sách hoặc có lỗi xảy ra
        return 0;
    }
    public static ObservableList<IssueBook> issueBooksList(String status) {
        Connection connection = Database.connectDb();
        PreparedStatement prepare = null;
        ResultSet resultSet = null;
        ObservableList<IssueBook> issueBooks = FXCollections.observableArrayList();

        String sql;

        if (status.equals("all")) {
            sql = "SELECT * FROM issue_book";
        } else {
            sql = "SELECT * FROM issue_book WHERE status = ?";
        }



        try {
            prepare = connection.prepareStatement(sql);
            if (!status.equals("all")) {
                prepare.setString(1, status);
            }

            resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                int book_id = resultSet.getInt("book_id");
                String user_id = resultSet.getString("user_id");
                String _status = resultSet.getString("status");
                String issue_date = resultSet.getString("issue_date");
                String return_date = resultSet.getString("return_date");
                String note = resultSet.getString("note");

                IssueBook issueBook = new IssueBook(book_id, user_id, _status, issue_date, return_date, note);
                issueBooks.add(issueBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (prepare != null) prepare.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return issueBooks;
    }
    public static void updateIssue(Integer book_id, String user_id, String status, String issue_date, String return_date, String note) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            conn = Database.connectDb();

            // Câu lệnh SQL để cập nhật thông tin
            String sql = "UPDATE issue_book SET status = ?, return_date = ?, note = ? WHERE book_id = ? AND user_id = ? AND issue_date = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, return_date);
            pstmt.setString(3, note);
            pstmt.setInt(4, book_id);
            pstmt.setString(5, user_id);
            pstmt.setString(6, issue_date);

            // In các giá trị ra để kiểm tra
            System.out.println("SQL: " + sql);
            System.out.println("Status: " + status);
            System.out.println("Return Date: " + return_date);
            System.out.println("Note: " + note);
            System.out.println("Book ID: " + book_id);
            System.out.println("User ID: " + user_id);
            System.out.println("Issue Date: " + issue_date);

            // Thực hiện cập nhật
            int rowsUpdated = pstmt.executeUpdate();

            // Hiển thị thông báo cho người dùng
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Issue");
            alert.setHeaderText(null);

            if (rowsUpdated > 0) {
                alert.setContentText("Status Updated");
                alert.setAlertType(Alert.AlertType.INFORMATION);
            } else {
                alert.setContentText("Status Not Added");
                alert.setAlertType(Alert.AlertType.WARNING);
            }

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Book Issue");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the issue information: " + e.getMessage());
            alert.showAndWait();
        } finally {
            // Đóng kết nối và statement
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}
