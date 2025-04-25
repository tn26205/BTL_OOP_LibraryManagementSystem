package librarymanagement.librarymanagementsystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private Circle circle_image;

    @FXML
    private Label txt_username_label;

    @FXML
    private Label studentNumber_label;

    @FXML
    private Label studentEmail_label;

    @FXML
    private ImageView studentImage_imageView;

    @FXML
    private Button HOME;

    @FXML
    private Button BACK;

    @FXML
    private Button MENU;

    @FXML
    private Button BORROWED_BOOKS;

    @FXML
    private Button RETURNED_BOOKS;

    @FXML
    private Button FAVORITE_BOOKS;

    @FXML
    private Button BUTTON_SIGNOUT;

    @FXML
    private Button BUTTON_SIGNOUT2;

    @FXML
    private Button minus;

    @FXML
    private AnchorPane homeBook_forms;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private AnchorPane halfNav_form;

    @FXML
    private TableView<AvailableBooks> availableBook_table;

    @FXML
    private TableColumn<AvailableBooks, String> Book_Title;

    @FXML
    private TableColumn<AvailableBooks, String> Author;

    @FXML
    private TableColumn<AvailableBooks, String> Book_Type;

    @FXML
    private TableColumn<AvailableBooks, Date> Published_Date;

    @FXML
    private ImageView homeBooks_imageView;

    @FXML
    private Label homeBooks_title;

    @FXML
    private Label TITLE;

    @FXML
    private Label current_form_label;

    @FXML
    private Button BORROW;

    @FXML
    private Button halfNav_borrowBtn;

    @FXML
    private Button halfNav_favouriteBtn;

    @FXML
    private Button halfNav_homeBtn;

    @FXML
    private Button halfNav_returnBtn;

    @FXML
    private AnchorPane borrow_form;

    @FXML
    private AnchorPane savedBook_form;

    @FXML
    private AnchorPane availableBook_form;

    @FXML
    private TextField favourite_Firstname;

    @FXML
    private TextField favourite_Lastname;

    @FXML
    private TextField favourite_BookTitle;

    @FXML
    private AnchorPane favourite_Borrow;

    @FXML
    private Label favourite_BorrowDate;

    @FXML
    private ComboBox<?> favourite_Genre;

    @FXML
    private Label favourite_StudentNumber;

    @FXML
    private Label favourite_author_label;

    @FXML
    private Button favourite_clearBtn;

    @FXML
    private Label favourite_date_label;

    @FXML
    private Label favourite_genre_label;

    @FXML
    private ImageView favourite_imageView;

    @FXML
    private Label favourite_title_label;

    @FXML
    private AnchorPane returnBook_form;

    @FXML
    private TableColumn<returnBook, String> return_author;

    @FXML
    private TableColumn<returnBook, String> return_bookTitle;

    @FXML
    private TableColumn<returnBook, String> return_bookType;

    @FXML
    private Button return_button;

    @FXML
    private TableColumn<returnBook, String> return_dateIssued;

    @FXML
    private ImageView return_imageView;

    @FXML
    private TableView<returnBook> return_tableView;

    @FXML
    private TableView<saveBook> save_tableView;

    @FXML
    private TableColumn<saveBook, String> col_saveAuthor;

    @FXML
    private TableColumn<saveBook, String> col_saveDate;

    @FXML
    private TableColumn<saveBook, String> col_saveGenre;

    @FXML
    private TableColumn<saveBook, String> col_saveTitle;

    @FXML
    private Button unsaveBtn;

    @FXML
    private ImageView save_imageView;

    @FXML
    private Button FAVORITE;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    private String comboBox[] = {"man", "woman"};

    public void gender() {

        List<String> combo = new ArrayList<>();

        for (String data : comboBox) {

            combo.add(data);
        }

        ObservableList list = FXCollections.observableList(combo);

        favourite_Genre.setItems(list);

    }
//
//    public void borrowBook() {
//
//        Date date = new Date();
//        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//
//        String insertSql = "INSERT INTO borrow VALUES (?,?,?,?,?,?,?,?,?,?)";
//        String checkSql = "SELECT COUNT(*) AS count FROM borrow WHERE name = ? AND checkReturn = 'Not Return'";
//        String bookSql = "SELECT cover_image FROM books WHERE name = ?";
//
//        connect = Database.connectDb();
//
//        if (connect == null) {
//            System.out.println("Database connection failed!");
//            return;
//        }
//
//        try {
//            Alert alert;
//
//            // Kiểm tra thông tin đầu vào
//            if (favourite_Firstname.getText().isEmpty()
//                    || favourite_Lastname.getText().isEmpty()
//                    || favourite_Genre.getSelectionModel().isEmpty()) {
//
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Please type complete Student Details");
//                alert.showAndWait();
//            } else if (favourite_title_label.getText().isEmpty()) {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Please indicate the book you want to take.");
//                alert.showAndWait();
//            } else {
//// Kiểm tra nếu sách đã được mượn
//                PreparedStatement checkStmt = connect.prepareStatement(checkSql);
//                checkStmt.setString(1, favourite_title_label.getText());
//                ResultSet checkResult = checkStmt.executeQuery();
//
//                if (checkResult.next() && checkResult.getInt("count") > 0) {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Admin Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("This book is already borrowed and not returned yet!");
//                    alert.showAndWait();
//                    return; // Ngăn không cho mượn sách
//                }
//                // Lấy đường dẫn ảnh của sách từ bảng books
//                PreparedStatement bookStmt = connect.prepareStatement(bookSql);
//                bookStmt.setString(1, favourite_title_label.getText());
//                ResultSet bookResult = bookStmt.executeQuery();
//                String imagePath = null;
//
//                if (bookResult.next()) {
//                    imagePath = bookResult.getString("cover_image"); // Lấy đường dẫn ảnh
//                }
//
//                // Kiểm tra nếu không có ảnh
//                if (imagePath == null) {
//                    imagePath = ""; // Hoặc bạn có thể set giá trị mặc định
//                }
//
//                PreparedStatement prepare = connect.prepareStatement(insertSql);
//                prepare.setString(1, favourite_StudentNumber.getText());
//                prepare.setString(2, favourite_Firstname.getText());
//                prepare.setString(3, favourite_Lastname.getText());
//                prepare.setString(4, (String) favourite_Genre.getSelectionModel().getSelectedItem());
//                prepare.setString(5, favourite_title_label.getText());
//                prepare.setString(6, favourite_author_label.getText());
//                prepare.setString(7, favourite_genre_label.getText());
//
//                //prepare.setString(8, getData.path);
//                //String imagePath = result.
//                prepare.setString(8, imagePath);
//                prepare.setDate(9, sqlDate); // Đảm bảo sqlDate được khởi tạo đúng
//
//// Thêm trạng thái sách chưa trả về
//                String check = "Not Return";
//                prepare.setString(10, check);
//
//// Thực hiện câu lệnh update để lưu thông tin mượn sách
//                prepare.executeUpdate();
//
//// Hiển thị thông báo thành công
//                alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Successfully borrowed the book!");
//                alert.showAndWait();
//
//// Xóa dữ liệu sau khi mượn thành công
//                clearTakeData(); // Xóa dữ liệu sau khi mượn thành công, sửa lại
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void borrowBook() {
//
//        Date date = new Date();
//        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//
//        String insertIssueBookSql = "INSERT INTO issue_book (book_id, user_id, status, issue_date, return_date, note) VALUES (?,?,?,?,?,?)";
//        String checkSql = "SELECT COUNT(*) AS count FROM borrow WHERE name = ? AND checkReturn = 'Not Return'";
//        String bookSql = "SELECT cover_image FROM books WHERE name = ?";
//
//        connect = Database.connectDb();
//
//        if (connect == null) {
//            System.out.println("Database connection failed!");
//            return;
//        }
//
//        try {
//            Alert alert;
//
//            // Kiểm tra thông tin đầu vào
//            if (favourite_Firstname.getText().isEmpty()
//                    || favourite_Lastname.getText().isEmpty()
//                    || favourite_Genre.getSelectionModel().isEmpty()) {
//
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Please type complete Student Details");
//                alert.showAndWait();
//            } else if (favourite_title_label.getText().isEmpty()) {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Please indicate the book you want to take.");
//                alert.showAndWait();
//            } else {
//
//                // Kiểm tra nếu sách đã được mượn
//                PreparedStatement checkStmt = connect.prepareStatement(checkSql);
//                checkStmt.setString(1, favourite_title_label.getText());
//                ResultSet checkResult = checkStmt.executeQuery();
//
//                if (checkResult.next() && checkResult.getInt("count") > 0) {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Admin Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("This book is already borrowed and not returned yet!");
//                    alert.showAndWait();
//                    return; // Ngăn không cho mượn sách
//                }
//
//                // Lấy book_id và cover_image từ bảng books
//                PreparedStatement bookStmt = connect.prepareStatement(bookSql);
//                bookStmt.setString(1, favourite_title_label.getText());
//                ResultSet bookResult = bookStmt.executeQuery();
//                int bookId = 0;
//                String imagePath = "";
//
//                if (bookResult.next()) {
//                    //bookId = bookResult.getInt("book_id"); // Lấy book_id
//                    imagePath = bookResult.getString("cover_image"); // Lấy đường dẫn ảnh
//                }
//
//                // Kiểm tra nếu không có ảnh
//                if (imagePath == null) {
//                    imagePath = ""; // Hoặc bạn có thể set giá trị mặc định
//                }
//
//                // Thêm sách vào bảng issue_book để đợi duyệt
//                PreparedStatement issueBookStmt = connect.prepareStatement(insertIssueBookSql);
//                issueBookStmt.setInt(1, bookId); // book_id từ bảng books
//                //issueBookStmt.setInt(2, Integer.parseInt(favourite_StudentNumber.getText())); // user_id (dùng student number làm user_id)
//
//                issueBookStmt.setString(3, "Pending"); // Trạng thái "Pending" khi chờ duyệt
//                issueBookStmt.setDate(4, sqlDate); // Ngày mượn (issue_date)
//                issueBookStmt.setDate(5, null); // return_date (null vì chưa trả)
//                issueBookStmt.setString(6, ""); // Ghi chú (có thể để trống hoặc bạn có thể sử dụng các thông tin khác)
//                issueBookStmt.executeUpdate();
//
//                // Hiển thị thông báo thành công
//                alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Admin Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Successfully added to the issue book table. Awaiting Admin approval.");
//                alert.showAndWait();
//
//                // Xóa dữ liệu sau khi mượn thành công
//                clearTakeData(); // Xóa dữ liệu sau khi mượn thành công
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void borrowBook() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String insertIssueBookSql = "INSERT INTO issue_book (book_id, user_id, status, issue_date, return_date, note) VALUES (?,?,?,?,?,?)";
        String checkSql = "SELECT COUNT(*) AS count FROM borrow WHERE name = ? AND checkReturn = 'Not Return'";
        String bookSql = "SELECT book_id, cover_image FROM books WHERE name = ?";  // Lấy book_id từ bảng books

        connect = Database.connectDb();

        if (connect == null) {
            System.out.println("Database connection failed!");
            return;
        }

        try {
            Alert alert;

            // Kiểm tra thông tin đầu vào
            if (favourite_Firstname.getText().isEmpty()
                    || favourite_Lastname.getText().isEmpty()
                    || favourite_Genre.getSelectionModel().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please type complete Student Details");
                alert.showAndWait();
            } else if (favourite_title_label.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please indicate the book you want to take.");
                alert.showAndWait();
            } else {

                // Kiểm tra nếu sách đã được mượn
                PreparedStatement checkStmt = connect.prepareStatement(checkSql);
                checkStmt.setString(1, favourite_title_label.getText());
                ResultSet checkResult = checkStmt.executeQuery();

                if (checkResult.next() && checkResult.getInt("count") > 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("This book is already borrowed and not returned yet!");
                    alert.showAndWait();
                    return; // Ngăn không cho mượn sách
                }

                // Lấy book_id và cover_image từ bảng books
                PreparedStatement bookStmt = connect.prepareStatement(bookSql);
                bookStmt.setString(1, favourite_title_label.getText());
                ResultSet bookResult = bookStmt.executeQuery();
                int bookId = 0;
                String imagePath = "";

                if (bookResult.next()) {
                    bookId = bookResult.getInt("id"); // Lấy book_id từ bảng books
                    imagePath = bookResult.getString("cover_image"); // Lấy đường dẫn ảnh
                }

                // Kiểm tra nếu không có ảnh
                if (imagePath == null) {
                    imagePath = ""; // Hoặc bạn có thể set giá trị mặc định
                }

                // Lấy user_id từ favourite_StudentNumber (dùng student number làm user_id)
                String userId = favourite_StudentNumber.getText();  // Giả sử user_id là student number và là String

                // Thêm sách vào bảng issue_book để đợi duyệt
                PreparedStatement issueBookStmt = connect.prepareStatement(insertIssueBookSql);
                issueBookStmt.setInt(1, bookId); // book_id từ bảng books
                issueBookStmt.setString(2, userId); // user_id là String
                issueBookStmt.setString(3, "Pending"); // Trạng thái "Pending" khi chờ duyệt
                issueBookStmt.setDate(4, sqlDate); // Ngày mượn (issue_date)
                issueBookStmt.setDate(5, null); // return_date (null vì chưa trả)
                issueBookStmt.setString(6, ""); // Ghi chú (có thể để trống hoặc bạn có thể sử dụng các thông tin khác)
                issueBookStmt.executeUpdate();

                // Hiển thị thông báo thành công
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added to the issue book table. Awaiting Admin approval.");
                alert.showAndWait();

                // Xóa dữ liệu sau khi mượn thành công
                clearTakeData(); // Xóa dữ liệu sau khi mượn thành công
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void findBook(ActionEvent event) {

        clearFindData();

        //String sql = "SELECT * FROM book WHERE bookTitle = '" + favourite_BookTitle.getText() + "'";
        String sql = "SELECT * FROM books WHERE name = '" + favourite_BookTitle.getText() + "'";

        try (Connection connect = Database.connectDb();
            PreparedStatement prepare = connect.prepareStatement(sql)){

            //prepare.setString(1, favourite_BookTitle.getText());

            try (ResultSet result = prepare.executeQuery()) {
                boolean check = false;

                while (result.next()) {

                    favourite_title_label.setText(result.getString("name"));
                    favourite_author_label.setText(result.getString("author"));
                    favourite_genre_label.setText(result.getString("publisher"));
                    favourite_date_label.setText(result.getString("date_received"));
//
//                    getData.path = result.getString("cover_image");
//
//                    String uri = "file:" + getData.path;
//
//                    image = new Image(uri, 171, 217, false, true);
//                    favourite_imageView.setImage(image);


                    // Lấy đường dẫn ảnh từ kết quả truy vấn
                    String imagePath = result.getString("cover_image");
                    System.out.println("Image Path: " + imagePath); // xem đường dẫn ảnh.

                    try {
                        if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                            // Nếu là URL
                            image = new Image(imagePath, 171, 217, false, true);
                        } else {
                            // Nếu là đường dẫn cục bộ
                            File file = new File(imagePath);
                            if (file.exists()) {
                                String uri = file.toURI().toString();
                                image = new Image(uri, 171, 217, false, true);
                            } else {
                                System.out.println("File not found: " + imagePath);
                                throw new Exception("File not found");
                            }
                        }

                        // Hiển thị ảnh lên ImageView
                        favourite_imageView.setImage(image);

                    } catch (Exception e) {
                        System.err.println("Error loading image: " + e.getMessage());
                        favourite_imageView.setImage(null); // Xóa ảnh nếu có lỗi
                    }

                    check = true;
                }

                if (!check) {
                    favourite_title_label.setText("Book is not available!");
                    favourite_author_label.setText("");
                    favourite_genre_label.setText("");
                    favourite_date_label.setText("");
                    favourite_imageView.setImage(null);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Error accessing the database. Please try again later.");
            alert.showAndWait();
        }
    }

    public void studentNumberLabel() {
        favourite_StudentNumber.setText(getData.studentNumber);
    }

    public void clearTakeData() {

        favourite_BookTitle.setText("");
        favourite_title_label.setText("");
        favourite_author_label.setText("");
        favourite_genre_label.setText("");
        favourite_date_label.setText("");
        favourite_imageView.setImage(null);
        favourite_Firstname.setText("");
        favourite_Lastname.setText("");
    }

    public void clearFindData() {

        favourite_title_label.setText("");
        favourite_author_label.setText("");
        favourite_genre_label.setText("");
        favourite_date_label.setText("");
        favourite_imageView.setImage(null);

    }

    public void displayDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new java.util.Date());

        favourite_BorrowDate.setText(date);
    }

    public ObservableList<returnBook> returnBook() {

        ObservableList<returnBook> bookReturnData = FXCollections.observableArrayList();
        if (connect != null) {
            System.out.println("Connected to database!!");
        }
        String check = "Not Return";

        String sql = "SELECT * FROM borrow WHERE checkReturn = '" + check + "' and studentNumber = '"
        + getData.studentNumber + "'";

        connect = Database.connectDb();

        try {
            returnBook rBook;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                rBook = new returnBook(result.getString("name"),
                        result.getString("author"),
                        result.getString("publisher"),
                        result.getString("cover_image"),
                        result.getDate("date_received"));
                bookReturnData.add(rBook);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookReturnData;

    }

    public void returnBooks() {

        String sql = "UPDATE borrow SET checkReturn = 'Returned' WHERE name = '" + getData.takeBookTitle + "'";

        connect = Database.connectDb();

        Alert alert;

        try {

            if (return_imageView.getImage() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book you want to return");
                alert.showAndWait();

            } else {

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully returned!");
                alert.showAndWait();

                showBookReturn();
                return_imageView.setImage(null);
            }
//            } else {
//                // Sử dụng PreparedStatement để bảo mật và tránh lỗi cú pháp
//                prepare = connect.prepareStatement(sql);
//                prepare.setString(1, getData.takeBookTitle); // Gán giá trị cho tham số ?
//
//                int rowsUpdated = prepare.executeUpdate();
//                if (rowsUpdated > 0) {
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Admin Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Successfully returned!");
//                    alert.showAndWait();
//
//                    showBookReturn(); // Cập nhật lại danh sách sách đã trả
//                    return_imageView.setImage(null); // Xóa ảnh sau khi trả sách
//                } else {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Admin Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Failed to return the book. Please try again.");
//                    alert.showAndWait();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ObservableList<returnBook> retBook;

    public void showBookReturn() {

        retBook = returnBook();

        return_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        return_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        return_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
        return_dateIssued.setCellValueFactory(new PropertyValueFactory<>("date"));

        return_tableView.setItems(retBook);

    }

    public void selectReturnBook() {

        returnBook rBook = return_tableView.getSelectionModel().getSelectedItem();
        int num = return_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

//        String uri = "file:" + rBook.getImage();
//
//        image = new Image(uri, 156, 205, false, true);
//        return_imageView.setImage(image);

        String imagePath = rBook.getImage();

        try {
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                // Nếu là URL
                image = new Image(imagePath, 171, 217, false, true);
            } else {
                // Nếu là đường dẫn cục bộ
                File file = new File(imagePath);
                if (file.exists()) {
                    String uri = file.toURI().toString();
                    image = new Image(uri, 171, 217, false, true);
                } else {
                    System.out.println("File not found: " + imagePath);
                    throw new Exception("File not found");
                }
            }

            // Hiển thị ảnh lên ImageView
            return_imageView.setImage(image);

        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return_imageView.setImage(null); // Xóa ảnh nếu có lỗi
        }

        getData.takeBookTitle = rBook.getTitle();
    }

    public ObservableList<AvailableBooks> dataList() {

        ObservableList<AvailableBooks> listBooks = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/library", "root", "");
            if (connect != null) {
                System.out.println("Connected to database!");
            }
            String sql = "select * from books";
            connect = Database.connectDb();
            AvailableBooks aBooks;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()) {
                aBooks = new AvailableBooks(result.getString("name"),
                                            result.getString("author"),
                                            result.getString("publisher"),
                                            result.getString("cover_image"),
                                            result.getDate("date_received"));
                listBooks.add(aBooks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    public ObservableList<saveBook> savedBooksData() {

        ObservableList<saveBook> listSaveData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM save WHERE studentNumber = '"+ getData.studentNumber +"'";

        connect = Database.connectDb();

        try {
            saveBook sBook;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                sBook = new saveBook(result.getString("name"),
                        result.getString("author"),
                        result.getString("publisher"),
                        result.getString("cover_image"),
                        result.getDate("date_received"));

                listSaveData.add(sBook);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listSaveData;
    }

    private ObservableList<saveBook> sBookList;

    public void showSavedBooks() {

        sBookList = savedBooksData();

        col_saveTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_saveAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_saveGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_saveDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        save_tableView.setItems(sBookList);

    }


    public void selectSavedBooks() {

        saveBook sBook = save_tableView.getSelectionModel().getSelectedItem();
        int num = save_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

//        String uri = "file:" + sBook.getImage();
//
//        image = new Image(uri, 160, 215, false, true);
//        save_imageView.setImage(image);
//
//        getData.savedImage = sBook.getImage();

        String imagePath = sBook.getImage();

        try {
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                // Nếu là URL
                image = new Image(imagePath, 171, 217, false, true);
            } else {
                // Nếu là đường dẫn cục bộ
                File file = new File(imagePath);
                if (file.exists()) {
                    String uri = file.toURI().toString();
                    image = new Image(uri, 171, 217, false, true);
                } else {
                    System.out.println("File not found: " + imagePath);
                    throw new Exception("File not found");
                }
            }

            // Hiển thị ảnh lên ImageView
            save_imageView.setImage(image);

        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            save_imageView.setImage(null); // Xóa ảnh nếu có lỗi
        }

        getData.savedTitle = sBook.getTitle();
    }


    public void saveBooks() {

        String sql = "INSERT INTO save VALUES (?,?,?,?,?,?)";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (TITLE.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, getData.studentNumber);
                prepare.setString(2, getData.savedTitle);
                prepare.setString(3, getData.savedAuthor);
                prepare.setString(4, getData.savedGenre);
                prepare.setString(5, getData.savedImage);
                prepare.setDate(6, getData.savedDate);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Saved.");
                alert.showAndWait();

                showSavedBooks();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void unsaveBooks() {

        String sql = "DELETE FROM save WHERE name = '" + getData.savedTitle + "'"
                + " and studentNumber = '" + getData.studentNumber + "'";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (save_imageView.getImage() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select the book you want to unsave");
                alert.showAndWait();

            } else {

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully unsaved.");
                alert.showAndWait();

                showSavedBooks();

                save_imageView.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ObservableList<AvailableBooks> listBook;

    // Hiển thị danh sách Book trong database.
    public void showAvailableBooks() {
        listBook = dataList();

        Book_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Book_Type.setCellValueFactory(new PropertyValueFactory<>("genre"));
        Published_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableBook_table.setItems(listBook);
    }
    public void selectionAvailableBooks() {
        // Lấy dữ liệu dòng được chọn
        AvailableBooks bookData = availableBook_table.getSelectionModel().getSelectedItem();
        int num = availableBook_table.getSelectionModel().getFocusedIndex();

        // Kiểm tra nếu không có dòng nào được chọn
        if (bookData == null || num < 0) {
            System.out.println("No row selected");
            return;
        }

        // Cập nhật giao diện
        TITLE.setText(bookData.getTitle());

        // Kiểm tra và tải ảnh (từ URL hoặc tệp cục bộ)
        try {
            String imagePath = bookData.getImage();
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                // Nếu là URL web
                image = new Image(imagePath, 173, 227, false, true);
            } else {
                // Nếu là đường dẫn cục bộ
                File file = new File(imagePath);
                if (file.exists()) {
                    String uri = file.toURI().toString();
                    image = new Image(uri, 173, 227, false, true);
                } else {
                    System.out.println("File not found: " + imagePath);
                    throw new Exception("File not found");
                }
            }
            homeBooks_imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getMessage());
            // Đặt ảnh mặc định khi không thể tải ảnh
            homeBooks_imageView.setImage(new Image("path/to/default/image.png"));
        }

        // Lưu thông tin sách vào đối tượng getData
        saveBookData(bookData);
    }

    // Hàm phụ để lưu thông tin sách
    private void saveBookData(AvailableBooks bookData) {
        getData.takeBookTitle = bookData.getTitle();
        getData.savedTitle = bookData.getTitle();
        getData.savedAuthor = bookData.getAuthor();
        getData.savedGenre = bookData.getGenre();
        getData.savedImage = bookData.getImage();
        getData.savedDate = bookData.getDate();
    }


//    public void abBorrowButaton(ActionEvent event) {
//        if(event.getSource() == BORROW) {
//            borrow_form.setVisible(true);
//            availableBook_form.setVisible(false);
//            savedBook_form.setVisible(false);
//            returnBook_form.setVisible(false);
//            favourite_BookTitle.set
//
//            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
//            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//
//            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
//            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
//
//            current_form_label.setText("Issue Books");
//        }
//    }
@FXML
    public void abBorrowButaton(ActionEvent event) {
        if (event.getSource() == BORROW) {
            // Hiển thị form Borrow và ẩn các form khác
            borrow_form.setVisible(true);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            // Kiểm tra xem người dùng đã chọn dòng nào chưa
            if (availableBook_table.getSelectionModel().getSelectedItem() != null) {
                // Lấy sách được chọn từ bảng
                AvailableBooks selectedBook = availableBook_table.getSelectionModel().getSelectedItem();

                // Hiển thị tên sách lên favourite_BookTitle
                favourite_BookTitle.setText(selectedBook.getTitle()); // Đảm bảo `Book` có phương thức `getTitle()`
            } else {
                // Nếu chưa chọn sách, hiển thị thông báo
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Book Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a book before borrowing.");
                alert.showAndWait();
            }

            // Đặt lại kiểu cho các nút
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            // Cập nhật tiêu đề form
            current_form_label.setText("Issue Books");
        }
    }

    public void studentNumber() {
        studentNumber_label.setText(getData.studentNumber);
    }

    public void studentEmail() {
        String sql = "SELECT * FROM users WHERE name = ?";
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            if (getData.studentNumber != null && !getData.studentNumber.isEmpty()) {
                pstmt.setString(1, getData.studentNumber);
            } else {
                System.out.println("Student number is invalid or null.");
            }

            ResultSet rs = pstmt.executeQuery();
            // Duyệt kết quả nếu cần
            while (rs.next()) {
                String email = rs.getString("email"); // Lấy cột email từ cơ sở dữ liệu
                studentEmail_label.setText(email);   // Hiển thị email
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sideNavButtonDesign(ActionEvent event) {

        if (event.getSource() == halfNav_homeBtn) {

            availableBook_form.setVisible(true);
            borrow_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Available Books");

        } else if (event.getSource() == halfNav_borrowBtn) {

            borrow_form.setVisible(true);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Issue Books");

        } else if (event.getSource() == halfNav_returnBtn) {

            borrow_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(true);

            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Return Books");

            showBookReturn();

        } else if (event.getSource() == halfNav_favouriteBtn) {

            borrow_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(true);
            returnBook_form.setVisible(false);

            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Saved Books");

            showSavedBooks();
        }

    }

    public void navButtonDesign(ActionEvent event) {

        if (event.getSource() == HOME) {

            availableBook_form.setVisible(true);
            borrow_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Available Books");

        } else if (event.getSource() == BORROWED_BOOKS) {

            borrow_form.setVisible(true);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Issue Books");

        } else if (event.getSource() == RETURNED_BOOKS) {

            borrow_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(true);

            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Saved Books");

            showBookReturn();

        } else if (event.getSource() == FAVORITE_BOOKS) {

            borrow_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBook_form.setVisible(true);
            returnBook_form.setVisible(false);

            HOME.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            BORROWED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            RETURNED_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            FAVORITE_BOOKS.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");

            halfNav_favouriteBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            current_form_label.setText("Returned Books");

            showSavedBooks();

        }
    }

    private double x = 0.0;
    private double y = 0.0;

    public void sliderArrow() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-223);

        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(-90);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {
            BACK.setVisible(false);
            MENU.setVisible(true);
        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    public void sliderBars() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(-76);

        slide.setOnFinished((ActionEvent event) -> {
            BACK.setVisible(true);
            MENU.setVisible(false);
        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    public void logout(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == BUTTON_SIGNOUT || sourceButton == BUTTON_SIGNOUT2) {
            sourceButton.getScene().getWindow().hide();
            loadLoginPage();
        }
    }

    private void loadLoginPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/librarymanagement/frame/LoginPage.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) minus.getScene().getWindow(); //minus là tên nút minisize.
        stage.setIconified(true);
    }

    @FXML
    public void close() {
        System.exit(0);
    }
    @FXML
    private Button selectImageUser;
    @FXML
    private ImageView image_user;



    @FXML FontAwesomeIcon editfont;

    @FXML
    private void selectImageUserBtn() {
        try {
            // Mở hộp thoại chọn ảnh
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");

            // Chỉ cho phép chọn các tệp có định dạng ảnh
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            // Đặt thư mục mặc định (nếu tồn tại)
            fileChooser.setInitialDirectory(new File("C:/"));

            // Lấy Stage hiện tại từ ImageView
            Stage stage = (Stage) image_user.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                // Hiển thị ảnh lên ImageView
                Image image = new Image(selectedFile.toURI().toString());
                editfont.setDisable(true);
                image_user.setImage(image);

            }
        } catch (Exception e) {
            System.err.println("Error selecting image: " + e.getMessage());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Hiển thị những quyển sách có sẵn.
        showAvailableBooks();
        studentNumber();
        studentEmail();

        gender();
        studentNumberLabel();
        displayDate();
        showSavedBooks();
        editfont.setDisable(true);
        try {
            showBookReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}