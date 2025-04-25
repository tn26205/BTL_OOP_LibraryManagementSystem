package librarymanagement.librarymanagementsystem;
import com.google.gson.*;
import com.google.zxing.WriterException;
import javafx.concurrent.Task;
import javafx.scene.control.cell.TextFieldTableCell;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate; import java.time.format.DateTimeFormatter; import javafx.scene.control.DatePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageAdmin implements Initializable {
    private double x = 0;
    private double y = 0;
    @FXML
    private Button halfNav_home;

    @FXML
    private Button halfNav_issueBook;

    @FXML
    private Button halfNav_returnBook;

    @FXML
    private Button halfNav_user;

    @FXML
    private Button close;

    @FXML
    private Button halfNav_book;

    @FXML
    private Button minus;

    @FXML
    private Button signout_btn;


    @FXML
    private Button arrow_btn;

    @FXML
    private Button bar_btn;

    @FXML
    private AnchorPane nav_form;



    @FXML
    private Circle circle_image;

    @FXML
    private FontAwesomeIcon edit_btn;

    @FXML
    private Button managebook_btn;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private Circle smallCircle_image;

    @FXML
    private AnchorPane halfNav_form;

    //QR
    @FXML
    private AnchorPane qrCode;

    @FXML
    private ImageView qrImage;

    @FXML
    private Button generateQRCode;

    @FXML
    private void minimize() {
        Stage stage = (Stage) minus.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void close() {
        System.exit(0);
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void sliderArrow() {
        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-213);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(-208+90);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(false);
            bar_btn.setVisible(true);

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
        slide2.setToX(-92);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(true);
            bar_btn.setVisible(false);

        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    public void buttonSignout(){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sign Out Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to continue?");
                // Hiển thị hộp thoại và chờ người dùng phản hồi
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            Stage stage = (Stage) signout_btn.getScene().getWindow();
                            stage.hide();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanagement/frame/SignupPage.fxml"));
                            Parent root = loader.load();

                            root.setOnMousePressed((MouseEvent event) -> {
                                x = event.getSceneX();
                                y = event.getSceneY();
                            });
                            root.setOnMouseDragged((MouseEvent event) -> {
                                stage.setX(event.getScreenX() - x);
                                stage.setY(event.getScreenY() - y);

                            });
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    // slide user
    @FXML
    private TableView<UserManage> UserTable;

    @FXML
    private Button add_user;

    @FXML
    private TableColumn<UserManage, String> col_contact;

    @FXML
    private TableColumn<UserManage, String> col_email;

    @FXML
    private TableColumn<UserManage, String> col_gender;

    @FXML
    private TableColumn<UserManage, String> col_iduser;

    @FXML
    private TableColumn<UserManage, String> col_nameuser;

    @FXML
    private TableColumn<UserManage, String> col_password;

    @FXML
    private Button delete_user;

    @FXML
    private Button edit_user;

    @FXML
    private Button clear_user;

    @FXML
    private TextField txt_emailuser;

    @FXML
    private ComboBox<String> genderCombox;

    @FXML
    private TextField  txt_id_user;

    @FXML
    private TextField txt_nameuser;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_phoneuser;

    @FXML
    private Button select_image;


    @FXML
    private Label image_path;

    @FXML
    private Label empty_email;

    @FXML
    private Label empty_name;

    @FXML
    private Label empty_password;

    @FXML
    private Label empty_phone;

    public ImageView user_image;

    @FXML
    private ImageView select_imagepng;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane user_pane;

    @FXML
    private AnchorPane book_pane;

    @FXML
    private AnchorPane issue_pane;

    @FXML
    private AnchorPane return_pane;


    @FXML
    private AnchorPane dashhouse_pane;

    //thao tác với slide user
    private String comboBox[] = {"Male", "Female",};

    public void gender() {

        List<String> combo = new ArrayList<>();

        for (String data : comboBox) {

            combo.add(data);
        }

        ObservableList list = FXCollections.observableList(combo);

        genderCombox.setItems(list);

    }
    String name ;
    String password ;
    String email;
    String contact ;
    String gender ;  // Lấy giá trị từ ComboBox
    @FXML
    private void handleTextFieldClick(MouseEvent event) {
        // Hiển thị thông báo không thể chỉnh sửa
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cannot Edit");
        alert.setHeaderText(null);
        alert.setContentText("This field cannot be edited.");
        alert.showAndWait();

        // Nếu bạn muốn ngừng chỉnh sửa, có thể set trường thành chỉ đọc
        txt_id_user.setEditable(false); // Làm trường không thể chỉnh sửa
    }

    public void insertUser() {
        name = txt_nameuser.getText();
        password = txt_password.getText();
        email = txt_emailuser.getText();
        contact = txt_phoneuser.getText();
        gender = genderCombox.getValue();
        Alert alert;

        if (name.isEmpty()) {
            empty_name.setVisible(true);
        } else {
            empty_name.setVisible(false);
        }
        if (password.isEmpty()) {
            empty_password.setVisible(true);
        } else {
            empty_password.setVisible(false);
        }
        if (email.isEmpty()) {
            empty_email.setVisible(true);
        } else {
            empty_email.setVisible(false);
        }
        if (contact.isEmpty()) {
            empty_phone.setVisible(true);
        } else {
            empty_phone.setVisible(false);
        }
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || contact.isEmpty() || gender == null || imagePath == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the details");
            alert.showAndWait();
            return;
        }

        try {
            connect = Database.connectDb();

            String checkNameSql = "SELECT * FROM users WHERE name = ?";
            prepare = connect.prepareStatement(checkNameSql);
            prepare.setString(1, name);
            result = prepare.executeQuery();
            if (result.next()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("User Exists");
                alert.setHeaderText(null);
                alert.setContentText("The username already exists in the database.");
                alert.showAndWait();
                return;
            }

            String checkEmailSql = "SELECT * FROM users WHERE email = ?";
            prepare = connect.prepareStatement(checkEmailSql);
            prepare.setString(1, email);
            result = prepare.executeQuery();
            if (result.next()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("User Exists");
                alert.setHeaderText(null);
                alert.setContentText("The email already exists in the database.");
                alert.showAndWait();
                return;
            }

            String sql = "INSERT INTO users (name, password, email, contact, gender, picture) VALUES (?, ?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prepare.setString(1, name);
            prepare.setString(2, password);
            prepare.setString(3, email);
            prepare.setString(4, contact);
            prepare.setString(5, gender);
            prepare.setString(6, imagePath);

            int updatedRowCount = prepare.executeUpdate();

            if (updatedRowCount > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Record Inserted Successfully");
                alert.showAndWait();

                try (ResultSet generatedKeys = prepare.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        System.out.println("Inserted record ID: " + id);
                    }
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Record Insertion Failure");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while inserting the record: " + e.getMessage());
            alert.showAndWait();
        }
    }


    private String imagePath;

    @FXML
    private void selectImageBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        fileChooser.setInitialDirectory(new File("C:/"));
        Stage stage = (Stage) user_image.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            select_imagepng.setImage(image);

            // Lưu URI của hình ảnh
            imagePath = selectedFile.toURI().toString();

            image_path.setText("Image Selected");
        }
    }




    public ObservableList<UserManage> dataListUsers() {
            ObservableList<UserManage> listUsers = FXCollections.observableArrayList();

            String sql = "SELECT * FROM users";

            connect = Database.connectDb();

            try {
                UserManage user;

                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                while (result.next()) {
                    user = new UserManage(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("password"),
                            result.getString("email"),
                            result.getString("contact"),
                            result.getString("gender"),
                            result.getString("picture")
                    );

                    listUsers.add(user);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return listUsers;
        }

        private ObservableList<UserManage> listUsers;





    public void showAvailableUsers() {
        listUsers = dataListUsers();

        col_iduser.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nameuser.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));


        UserTable.setItems(listUsers);
        UserTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Hiển thị thông tin người dùng lên các trường
                txt_id_user.setText(String.valueOf(newValue.getId()));
                txt_nameuser.setText(newValue.getName());
                txt_password.setText(newValue.getPassword());
                txt_emailuser.setText(newValue.getEmail());
                txt_phoneuser.setText(newValue.getContact());
                genderCombox.setValue(newValue.getGender());

                // Hiển thị ảnh trong ImageView
                String imageUrl = newValue.getPicture();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Chuyển đổi URL thành đối tượng Image
                    Image image = new Image(imageUrl);
                    select_imagepng.setImage(image);
                    user_image.setImage(image);
                }
                else {
                    // Hiển thị hình ảnh mặc định nếu không có hình ảnh
                    select_imagepng.setImage(new Image(getClass().getResource("/image/defaultProfile.png").toExternalForm())); }
                    user_image.setImage(new Image(getClass().getResource("/image/defaultProfile.png").toExternalForm()));
            }
        });
    }
    @FXML

    private void ClearUser() {
        txt_id_user.clear();
        txt_nameuser.clear();
        txt_password.clear();
        txt_emailuser.clear();
        txt_phoneuser.clear();
        genderCombox.setValue(null);
        select_imagepng.setImage(null);
    }




    public void addUserBtn() {
            insertUser();
            if(!name.isEmpty() && !password.isEmpty() && !email.isEmpty() &&!contact.isEmpty() && gender != null && imagePath != null){
            showAvailableUsers();}
    }
    @FXML
    public void editUser() {
        Alert alert;
        String id = txt_id_user.getText();
        String name = txt_nameuser.getText();
        String password = txt_password.getText();
        String email = txt_emailuser.getText();
        String contact = txt_phoneuser.getText();
        String gender = genderCombox.getValue();  // Lấy giá trị từ ComboBox

        // Kiểm tra các trường không được để trống
        UserManage selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
            return;
        } else {
            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || contact.isEmpty() || gender == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the details");
                alert.showAndWait();
                return;
            }
        }

        connect = Database.connectDb();
        try {
            // Câu lệnh SQL để cập nhật thông tin người dùng
            String sql = "UPDATE users SET name = ?, password = ?, email = ?, contact = ?, gender = ?, picture = ? WHERE id = ?";

            prepare = connect.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số
            prepare.setString(1, name);
            prepare.setString(2, password);
            prepare.setString(3, email);
            prepare.setString(4, contact);
            prepare.setString(5, gender);
            prepare.setString(6, imagePath);
            prepare.setInt(7, Integer.parseInt(id));

            // Thực thi câu truy vấn
            int updatedRowCount = prepare.executeUpdate();

            // Kiểm tra kết quả cập nhật
            if (updatedRowCount > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User updated successfully.");
                alert.showAndWait();
                showAvailableUsers();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("User update failed.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the record: " + e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the record: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteUser() {
        UserManage selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this user?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    connect = Database.connectDb();
                    try {
                        String sql = "DELETE FROM users WHERE id = ?";
                        prepare = connect.prepareStatement(sql);
                        prepare.setInt(1, selectedUser.getId());
                        int deletedRowCount = prepare.executeUpdate();
                        if (deletedRowCount > 0) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("User Deleted Successfully");
                            successAlert.showAndWait();
                            showAvailableUsers();
                            // Xóa ảnh trong ImageView
                            select_imagepng.setImage(null);
                            // Xóa các trường thông tin
                            txt_id_user.clear();
                            txt_nameuser.clear();
                            txt_password.clear();
                            txt_emailuser.clear();
                            txt_phoneuser.clear();
                            genderCombox.setValue(null);
                        } else {
                            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                            failureAlert.setTitle("Failure");
                            failureAlert.setHeaderText(null);
                            failureAlert.setContentText("User Deletion Failure");
                            failureAlert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Database Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("An error occurred while deleting the user: " + e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }


// slide book


    @FXML
    private TableView<Book> BookTable;
    @FXML
    private TableColumn<Book,String> col_author;
    @FXML
    private TableColumn<Book, Date> col_date_received;

    @FXML
    private TableColumn<Book, String> col_isbn;

    @FXML
    private TableColumn<Book, String> col_name;

    @FXML
    private TableColumn<Book, Integer> col_quantity;

    @FXML
    private TableColumn<Book, String> col_description;

    @FXML
    private TableColumn<Book, String> col_publisher;

    @FXML
    private TableColumn<Book, Integer> col_id;

    @FXML
    private ImageView book_image;

    public ObservableList<Book> dataList() {
        ObservableList<Book> listBooks = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        connect = Database.connectDb();

        try {
            Book book;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();


            while (result.next()) {

                book = new Book(
                        result.getInt("id"),
                        result.getString("isbn"),
                        result.getString("name"),
                        result.getString("author"),
                        result.getInt("quantity"),
                        result.getString("publisher"),
                        result.getString("description"),
                        result.getDate("date_received"),
                        result.getString("cover_image")
                );
                listBooks.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    @FXML
    private void updateBook(TableColumn.CellEditEvent<Book, String> event) {
        Book selectedBook = event.getRowValue();
        TablePosition<Book, String> pos = event.getTablePosition();
        String newValue = event.getNewValue();
        String columnName = pos.getTableColumn().getText();

        switch (columnName) {
            case "author":
                selectedBook.setAuthor(newValue);
                break;

            case "isbn":
                selectedBook.setIsbn(newValue);
                break;
            case "name":
                selectedBook.setName(newValue);
                break;
            case "description":
                selectedBook.setDescription(newValue);
                break;
            case "publisher":
                selectedBook.setPublisher(newValue);
                break;
            case "date_received":
                selectedBook.setDate_received(Date.valueOf(newValue));
            default:
                break;
        }

        connect = Database.connectDb();
        try {
            String sql = "UPDATE books SET " + columnName.toLowerCase() + " = ? WHERE id = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, newValue);
            prepare.setInt(2, selectedBook.getId());
            int updatedRowCount = prepare.executeUpdate();

            if (updatedRowCount > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Book updated successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Book update failed.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the record: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML public void editBook() {
        BookTable.setEditable(true);
        col_author.setEditable(true);
        col_isbn.setEditable(true);
        col_name.setEditable(true);
        col_description.setEditable(true);
        col_publisher.setEditable(true);
        col_date_received.setEditable(true);

    }

    @FXML
    public void deleteBook() {
        Book selectedBook = BookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this book?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    connect = Database.connectDb();
                    try {
                        String sql = "DELETE FROM books WHERE id = ?";
                        prepare = connect.prepareStatement(sql);
                        prepare.setInt(1, selectedBook.getId());
                        int deletedRowCount = prepare.executeUpdate();
                        if (deletedRowCount > 0) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Book Deleted Successfully");
                            successAlert.showAndWait();
                            loadImagesFromDatabase();
                            showAvailableBooks();
                            // Xóa ảnh trong ImageView
                            book_image.setImage(null);
                            // Xóa các trường thông tin
                            col_author.setText(null);
                            col_date_received.setText(null);

                            col_isbn.setText(null);
                            col_name.setText(null);
                            col_quantity.setText(null);
                            col_description.setText(null);
                            col_publisher.setText(null);
                            col_id.setText(null);

                        } else {
                            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                            failureAlert.setTitle("Failure");
                            failureAlert.setHeaderText(null);
                            failureAlert.setContentText("Book Deletion Failure");
                            failureAlert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Database Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("An error occurred while deleting the book: " + e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to delete.");
            alert.showAndWait();
        }
    }





    private ObservableList<Book> Books;
    public void showAvailableBooks() {
        // Gọi phương thức để lấy danh sách các cuốn sách
        Books = dataList();

        // Thiết lập các cột trong bảng
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_date_received.setCellValueFactory(new PropertyValueFactory<>("date_received"));

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        // Đặt dữ liệu vào bảng

            BookTable.setItems(Books);

            // Lắng nghe sự thay đổi khi chọn một dòng trong bảng
            BookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    String bookImage = newSelection.getCover();
                    if (bookImage != null && !bookImage.isEmpty()) {
                        // Chuyển đổi URL thành đối tượng Image
                        Image image = new Image(bookImage);
                        book_image.setImage(image);
                    } else {
                        // Hiển thị hình ảnh mặc định nếu không có hình ảnh
                        book_image.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
                    }
                } else {
                    // Hiển thị hình ảnh mặc định nếu không có hình ảnh
                    book_image.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
                }
            });

    }


//issue book form
@FXML
private Label publisher_inf;

    @FXML
    private Label quantity_inf;

    @FXML
    private TextArea description_inf;
    @FXML
    private Label date_received_inf;

    @FXML
    private Label author_inf;




    @FXML
    private ImageView imagebook_inf;
    @FXML
    private Label isbn_inf;
    @FXML
    private Label nameb_inf;

    @FXML
    private AnchorPane book_inf_pane;
    @FXML
    private void showBookInfo() {
        book_inf_pane.setVisible(true);
    }
    @FXML
    private void hideBookInfo() {
        book_inf_pane.setVisible(false);
    }


//user inf card
    @FXML
    private Label contact_inf;
    @FXML
    private Label email_inf;
    @FXML
    private Label gender_inf;
    @FXML
    private Label id_inf;
    @FXML
    private ImageView image_user_inf;
    @FXML
    private Label nameu_inf;
    @FXML
    private Label password_inf;

    @FXML
    private AnchorPane member_inf_pane;


    @FXML
    private void showUserInfo() {
        member_inf_pane.setVisible(true);
    }
    @FXML
    private void hideUserInfo() {
        member_inf_pane.setVisible(false);
    }


    //slide issuebook
@FXML
private TextArea txt_note;

    @FXML
    private Button search_book;

    @FXML
    private Button search_member;

    @FXML
    private DatePicker return_date_txt;

    @FXML
    private DatePicker issue_date_txt;

    @FXML
    private Spinner<Integer> id_book_spi;

    @FXML
    private Spinner<Integer> id_user_spi;


    @FXML
    private Label status;

    @FXML
    private Label book_inf_card;

    @FXML
    private Label user_inf_card;

public  Book findBookById(int id) {
    String sql = "SELECT * FROM books WHERE id = ?";
    Book book = null;
    connect = Database.connectDb();;
    try {
        prepare = connect.prepareStatement(sql);
        prepare.setInt(1, id);
        ResultSet rs = prepare.executeQuery();

        if (rs.next()) {


            book = new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("name"),
                    rs.getString("author"),
                    rs.getInt("quantity"),
                    rs.getString("publisher"),
                    rs.getString("description"),
                    rs.getDate("date_received"),
                    rs.getString("cover_image")
            );
        }

    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }

    return book; // Trả về null nếu không tìm thấy sách
}




    @FXML
    private void search_book_btn() {
        int book_id = id_book_spi.getValue();
        try {
            Book selectedBook = findBookById(book_id);
            if (selectedBook != null) {
                book_inf_card.setText( selectedBook.getName() );
                isbn_inf.setText( selectedBook.getIsbn() );
                nameb_inf.setText( selectedBook.getName() );
                author_inf.setText( selectedBook.getAuthor() );
                publisher_inf.setText( selectedBook.getPublisher() );
                quantity_inf.setText(selectedBook.getQuantity().toString());
                date_received_inf.setText(selectedBook.getDate_received().toString());
                description_inf.setText(selectedBook.getDescription());
                if (selectedBook.getCover() != null && !selectedBook.getCover().isEmpty()) {
                    // Chuyển đổi URL thành đối tượng Image
                    Image image = new Image(selectedBook.getCover());
                    imagebook_inf.setImage(image);
                } else {
                    // Hiển thị hình ảnh mặc định nếu không có hình ảnh
                    imagebook_inf.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
                }

                boolean isAvailable = IssueBook.checkBookAvailability(book_id);
                if (isAvailable) {
                    status.setText("Yes"); }
                else { status.setText("No");}

            } else {
                book_inf_card.setText("Book not found.");
            }
        } catch (Exception e) {
            book_inf_card.setText("An error occurred while searching for the book: " + e.getMessage());
        }
    }
    public  UserManage findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        UserManage user = null;
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, id);
            result = prepare.executeQuery();

            if (result.next()) {

                user = new UserManage(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getString("contact"),
                        result.getString("gender"),
                        result.getString("picture")

                );
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return user; // Trả về null nếu không tìm thấy người dùng
    }
    @FXML
    private void search_user_btn() {
        int user_id = id_user_spi.getValue();
        try {
            UserManage selectedUser = findUserById(user_id);
            if (selectedUser != null) {
                user_inf_card.setText(selectedUser.getName());
                id_inf.setText(selectedUser.getId().toString());
                nameu_inf.setText( selectedUser.getName() );
                contact_inf.setText( selectedUser.getContact() );
                email_inf.setText(selectedUser.getEmail() );
                gender_inf.setText(selectedUser.getGender() );
                password_inf.setText( selectedUser.getPassword() );
                if (selectedUser.getPicture() != null && !selectedUser.getPicture().isEmpty()) {
                    // Chuyển đổi URL thành đối tượng Image
                    Image image = new Image(selectedUser.getPicture());
                    image_user_inf.setImage(image);
                } else {
                    // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                    image_user_inf.setImage(new Image(getClass().getResource("/image/defaultProfile.png").toExternalForm()));
                }


            } else {
                user_inf_card.setText("User not found.");
            }
        } catch (Exception e) {
            user_inf_card.setText("An error occurred while searching for the user: " + e.getMessage());
        }
    }
    @FXML
    private void issueBtn() {
        int book_id = id_book_spi.getValue();
        String user_id = id_user_spi.getValue();
        String note = txt_note.getText();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate issueDate = issue_date_txt.getValue();
        LocalDate returnDate = return_date_txt.getValue();

        if (issueDate != null && returnDate != null) {
            String issue_date = issueDate.format(dateFormat);
            String return_date = returnDate.format(dateFormat);

            // Gọi phương thức addIssue
            IssueBook.addIssue(book_id, user_id, "issued", issue_date, return_date, note);
        } else {
            // Hiển thị cảnh báo nếu ngày không hợp lệ
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Date");
            alert.setHeaderText(null);
            alert.setContentText("Please select valid issue and return dates.");
            alert.showAndWait();
        }
    }

//slide return book
@FXML
private TableView<IssueBook> table_return_book;
    @FXML
    private TableColumn<IssueBook, Integer> col_book_id;
    @FXML
    private TableColumn<IssueBook, Integer> col_user_id;
    @FXML
    private TableColumn<IssueBook, String> col_status;
    @FXML
    private TableColumn<IssueBook, String> col_issue_date;
    @FXML
    private TableColumn<IssueBook, String> col_return_date;
    @FXML
    private TableColumn<IssueBook, String> col_note;


    @FXML
    private ComboBox<String> book_status;

    @FXML
    private Label rBook_name;

    @FXML
    private Label rUser_name;
    @FXML
    private Spinner<Integer> txt_rBook_id;
    @FXML
    private Spinner<Integer> txt_rUser_id;
    @FXML
    private TextArea txt_rNote;
    private ObservableList<IssueBook> issueBooksList;
    @FXML
    private DatePicker txt_issue_date;

    @FXML
    private DatePicker txt_return_date;

    private void displaySelectedBook() {
        IssueBook selectedBook = table_return_book.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            txt_rBook_id.getValueFactory().setValue(selectedBook.getBook_id());
            txt_rUser_id.getValueFactory().setValue(selectedBook.getUser_id());

            Book book = findBookById(selectedBook.getBook_id());
            UserManage user = findUserById(selectedBook.getUser_id());

            rBook_name.setText(book.getName());
            rUser_name.setText(user.getName());
            isbn_infr.setText(book.getIsbn());
            nameb_infr.setText(book.getName());
            author_infr.setText(book.getAuthor());
            publisher_infr.setText(book.getPublisher());
            quantity_infr.setText(book.getQuantity().toString());
            date_received_infr.setText(book.getDate_received().toString());
            description_infr.setText(book.getDescription());
            id_infr.setText(user.getId().toString());
            nameu_infr.setText( user.getName() );
            contact_infr.setText( user.getContact() );
            email_infr.setText(user.getEmail() );
            gender_infr.setText(user.getGender() );
            password_infr.setText( user.getPassword() );
            if (user.getPicture() != null && !user.getPicture().isEmpty()) {
                // Chuyển đổi URL thành đối tượng Image
                Image image = new Image(user.getPicture());
                image_user_infr.setImage(image);
            } else {
                // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                image_user_infr.setImage(new Image(getClass().getResource("/image/defaultProfile.png").toExternalForm()));
            }

            if (book.getCover() != null && !book.getCover().isEmpty()) {
                // Chuyển đổi URL thành đối tượng Image
                Image image = new Image(book.getCover());
                imagebook_infr.setImage(image);
            } else {
                // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                imagebook_infr.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
            }

            // Chuyển đổi String thành LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate issueDate = LocalDate.parse(selectedBook.getIssue_date(), formatter);
            LocalDate returnDate = LocalDate.parse(selectedBook.getReturn_date(), formatter);

            // Thiết lập giá trị cho DatePicker
            txt_issue_date.setValue(issueDate);
            txt_return_date.setValue(returnDate);
            txt_rNote.setText(selectedBook.getNote());
        }
    }
@FXML
    private void returnBook() {
        int book_id = txt_rBook_id.getValue();
        int user_id = txt_rUser_id.getValue();
        String note = txt_rNote.getText();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Database.connectDb();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate issueDate = txt_issue_date.getValue();
            LocalDate returnDate = txt_return_date.getValue();
            if (issueDate == null || returnDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Return Book");
                alert.setHeaderText(null);
                alert.setContentText("Issue date or return date not be null.");
                alert.showAndWait();
                return; }
            String issueDateString = issueDate.format(formatter);
            String returnDateString = returnDate.format(formatter);

            // Kiểm tra nếu ngày trả sau ngày mượn
            if (returnDate.isAfter(issueDate)) {
                IssueBook.updateIssue(book_id,user_id,"returned",issueDateString,returnDateString,note);
                    // Cập nhật số lượng sách có sẵn
                    updateBookQuantity(conn, book_id);

                    // Đặt lại các trường nhập liệu sau khi hoàn tất
                    resetFormFields();
                    showIssueBooks("");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Return Book");
                alert.setHeaderText(null);
                alert.setContentText("The return date cannot be before the issue date.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Return Book");
            alert.setHeaderText(null);
             alert.setContentText("An error occurred while returning the book: " + e.getMessage());
            alert.showAndWait();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void lostBook() {
        int book_id = txt_rBook_id.getValue();
        int user_id = txt_rUser_id.getValue();
        String note = txt_rNote.getText();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Database.connectDb();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate issueDate = txt_issue_date.getValue();
            LocalDate returnDate = txt_return_date.getValue();
            if (issueDate == null || returnDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Return Book");
                alert.setHeaderText(null);
                alert.setContentText("Issue date or return date cannot be null.");
                alert.showAndWait();
                return; }
            String issueDateString = issueDate.format(formatter);
            String returnDateString = returnDate.format(formatter);

            // Kiểm tra nếu ngày trả sau ngày mượn
            if (returnDate.isAfter(issueDate)) {
                IssueBook.updateIssue(book_id,user_id,"lost",issueDateString,returnDateString,note);
                // Cập nhật số lượng sách có sẵn
                updateBookQuantity1(conn, book_id);

                // Đặt lại các trường nhập liệu sau khi hoàn tất
                resetFormFields();
                showIssueBooks("");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Return Book");
                alert.setHeaderText(null);
                alert.setContentText("The return date cannot be before the issue date.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Return Book");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while returning the book: " + e.getMessage());
            alert.showAndWait();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void updateBookQuantity(Connection conn, int book_id) throws Exception {
        String sql = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, book_id);
            pstmt.executeUpdate();
        }
    }
    private void updateBookQuantity1(Connection conn, int book_id) throws Exception {
        String sql = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, book_id);
            pstmt.executeUpdate();
        }
    }




    private void resetFormFields() {
        id_book_spi.getValueFactory().setValue(0);
        id_user_spi.getValueFactory().setValue(0);
        txt_note.clear();
        // Reset các trường khác nếu cần thiết, ví dụ:
        rBook_name.setText("");
        rUser_name.setText("");
        isbn_infr.setText("");
        nameb_infr.setText("");
        author_infr.setText("");

        publisher_infr.setText("");
        quantity_infr.setText("");
        date_received_infr.setText("");
        description_infr.setText("");
        imagebook_infr.setImage(null);
        txt_issue_date.setValue(null);
        txt_return_date.setValue(null);
        txt_rNote.setText("");
        id_infr.setText("");
        nameu_infr.setText("");
        contact_infr.setText("");
        email_infr.setText("");
        gender_infr.setText("");
        password_infr.setText("");
        image_user_infr.setImage(null);
    }
    @FXML
    private void comboBoxBtn() {
        String status = book_status.getSelectionModel().getSelectedItem();
        showIssueBooks(status);
    }



    @FXML
        public void showIssueBooks(String status) {
       issueBooksList = IssueBook.issueBooksList(status);
            // Thiết lập các cột của bảng
            col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            col_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            col_issue_date.setCellValueFactory(new PropertyValueFactory<>("issue_date"));
            col_return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
            col_note.setCellValueFactory(new PropertyValueFactory<>("note"));



            // Đặt danh sách vào bảng
            table_return_book.setItems(issueBooksList);
        }

    //return book form
    @FXML
    private Label publisher_infr;

    @FXML
    private Label quantity_infr;

    @FXML
    private TextArea description_infr;
    @FXML
    private Label date_received_infr;

    @FXML
    private Label author_infr;


    @FXML
    private ImageView imagebook_infr;
    @FXML
    private Label isbn_infr;
    @FXML
    private Label nameb_infr;

    @FXML
    private AnchorPane rbook_inf_pane;
    @FXML
    private void showrBookInfo() {
        rbook_inf_pane.setVisible(true);
    }
    @FXML
    private void hiderBookInfo() {
        rbook_inf_pane.setVisible(false);
    }

    //user inf card
    @FXML
    private Label contact_infr;
    @FXML
    private Label email_infr;
    @FXML
    private Label gender_infr;
    @FXML
    private Label id_infr;
    @FXML
    private ImageView image_user_infr;
    @FXML
    private Label nameu_infr;
    @FXML
    private Label password_infr;

    @FXML
    private AnchorPane rmember_inf_pane;

    @FXML
    private void showrUserInfo() {
        rmember_inf_pane.setVisible(true);
    }
    @FXML
    private void hiderUserInfo() {
        rmember_inf_pane.setVisible(false);
    }


    // Kiểm tra xem người dùng đã chọn tệp hay chưa
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Khởi tạo bất kỳ thành phần nào cần thiết hoặc dữ liệu
        showAvailableUsers();
        empty_email.setVisible(false);
        empty_name.setVisible(false);
        empty_password.setVisible(false);
        empty_phone.setVisible(false);
        gender();
        // Thêm sự kiện chọn hàng trong TableView
        UserTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String pictureUrl = newValue.getPicture(); // Lấy URL của hình ảnh
                if (pictureUrl != null && !pictureUrl.isEmpty()) {
                    // Chuyển đổi URL thành đối tượng Image
                    Image image = new Image(pictureUrl);
                    user_image.setImage(image);
                } else {
                    // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                    user_image.setImage(new Image(getClass().getResource("/image/defaultProfile.png").toExternalForm()));
                }
            }
        });





        BookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String bookImage = newValue.getCover();
                if (bookImage != null && !bookImage.isEmpty()) {
                    // Chuyển đổi URL thành đối tượng Image
                    Image image = new Image(bookImage);
                    user_image.setImage(image);
                } else {
                    // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                    user_image.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
                }
            } else {
                // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                user_image.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
            }
        });

        SpinnerValueFactory<Integer> valueFactoryB = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        id_book_spi.setValueFactory(valueFactoryB);
        id_book_spi.getValueFactory().setValue(0);
        SpinnerValueFactory<Integer> valueFactoryU = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        id_user_spi.setValueFactory(valueFactoryU);
        id_user_spi.getValueFactory().setValue(0);

            // Khởi tạo Spinner với các giá trị từ 1 đến 1000 (hoặc khoảng giá trị khác mà bạn mong muốn)
            SpinnerValueFactory<Integer> bookIdValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
            txt_rBook_id.setValueFactory(bookIdValueFactory);

            SpinnerValueFactory<Integer> userIdValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
            txt_rUser_id.setValueFactory(userIdValueFactory);
        table_return_book.setOnMouseClicked(event -> { if (event.getClickCount() == 1) {
            // Kiểm tra xem có phải là một click chuột không
            displaySelectedBook(); } });

        txt_issue_date.setEditable(false);
        txt_issue_date.setDisable(true);
        txt_id_user.setEditable(false);
        showIssueBooks("");
        book_status.setItems(FXCollections.observableArrayList("all", "issued", "returned", "lost"));
        displayCount1();
        displayCount2();



        col_author.setCellFactory(TextFieldTableCell.forTableColumn());

        col_isbn.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_description.setCellFactory(TextFieldTableCell.forTableColumn());

        col_publisher.setCellFactory(TextFieldTableCell.forTableColumn());
        showAvailableBooks();
        // Xử lý sự kiện khi kết thúc chỉnh sửa ô
        col_author.setOnEditCommit(event -> updateBook(event));

        col_isbn.setOnEditCommit(event -> updateBook(event));
        col_name.setOnEditCommit(event -> updateBook(event));
        col_description.setOnEditCommit(event -> updateBook(event));
        col_publisher.setOnEditCommit(event -> updateBook(event));
        setUpTableColumns();
        setUpBookSelectionListener();
        loadSearchResults();

        //Create book
        createQRCodeDirectory();

        BookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                qrImage.setImage(null); // Clear QR code
            }
        });
        loadImagesFromDatabase();
    }
    @FXML
    public void ManageBookBtn() {
        book_pane.setVisible(true);
        user_pane.setVisible(false);
        return_pane.setVisible(false);
        issue_pane.setVisible(false);
        dashhouse_pane.setVisible(false);
        api_pane.setVisible(false);
    }
    @FXML
    public void ManageUserBtn() {
        book_pane.setVisible(false);
        user_pane.setVisible(true);
        return_pane.setVisible(false);
        issue_pane.setVisible(false);
        dashhouse_pane.setVisible(false);
        api_pane.setVisible(false);
    }
    @FXML
    public void IssueBookBtn() {
        book_pane.setVisible(false);
        user_pane.setVisible(false);
        return_pane.setVisible(false);
        issue_pane.setVisible(true);
        dashhouse_pane.setVisible(false);
        api_pane.setVisible(false);
    }
    @FXML
    public void ReturnBookBtn() {
        book_pane.setVisible(false);
        user_pane.setVisible(false);
        return_pane.setVisible(true);
        issue_pane.setVisible(false);
        dashhouse_pane.setVisible(false);
        api_pane.setVisible(false);
    }
    @FXML
    public void DashhouseBtn() {
        book_pane.setVisible(false);
        user_pane.setVisible(false);
        return_pane.setVisible(false);
        issue_pane.setVisible(false);
        dashhouse_pane.setVisible(true);
        api_pane.setVisible(false);
    }
    @FXML
    public void SearchAPI() {
        book_pane.setVisible(false);
        user_pane.setVisible(false);
        return_pane.setVisible(false);
        issue_pane.setVisible(false);
        dashhouse_pane.setVisible(false);
        api_pane.setVisible(true);
    }
    //slide dashhouse_pane


    @FXML
    private Label countUser;

    @FXML
    private Label countBook;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox imageContainer;


        @FXML
        public void displayCount1() {
            ResultSet rs;
            String sql = "select count(*) as total from books";

            try (Connection conn = Database.connectDb();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                rs = stmt.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt("total");
                    countBook.setText(String.valueOf(count));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    @FXML
    public void displayCount2() {
        ResultSet rs;
        String sql = "select count(*) as total from users";

        try (Connection conn = Database.connectDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("total");
                countUser.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadImagesFromDatabase() {
        imageContainer.getChildren().clear();
        String query = "SELECT cover_image FROM books";  // Truy vấn ảnh từ bảng book

        try (Connection connect = Database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(query);
             ResultSet resultSet = prepare.executeQuery()) {

            while (resultSet.next()) {
                String imagePath = resultSet.getString("cover_image");

                // Tạo ImageView từ đường dẫn ảnh
                try {
                    Image image = null;
                    if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                        image = new Image(imagePath, 150, 200, true, true);
                    } else {
                        image = new Image("file:" + imagePath, 150, 200, true, true);
                    }

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    imageView.getStyleClass().add("image-view");  // Thêm class cho image view

                    imageContainer.getChildren().add(imageView);
                } catch (Exception e) {
                    System.err.println("Error loading image: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }



//api_slide

    @FXML
    private AnchorPane api_pane;

    @FXML
    private TableView<Book> api_table;
    @FXML
    private TableColumn<Book, String> author_api;

    @FXML
    private TableColumn<Book, String> isbn_api;
    @FXML
    private TableColumn<Book, String> publisher_api;

    @FXML
    private TableColumn<Book, String> name_api;


    @FXML
    private TableColumn<Book, Date> date_received_api;


    @FXML
    private TableColumn<Book, String> description_api;

    @FXML
    private ImageView api_imageView;

    @FXML
    private TextField searchField;



    private final ObservableList<Book> searchResults = FXCollections.observableArrayList();


    @FXML
    private void setUpTableColumns() {
        author_api.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbn_api.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisher_api.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        name_api.setCellValueFactory(new PropertyValueFactory<>("name"));
        date_received_api.setCellValueFactory(new PropertyValueFactory<>("date_received"));
        description_api.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setUpBookSelectionListener() {
        // Add a listener for when a book is selected from the table
        api_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                // Hiển thị hình ảnh mặc định nếu không có dòng nào được chọn
                api_imageView.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
                return;
            }

            String bookImage = newSelection.getCover();
            if (bookImage != null && !bookImage.isEmpty()) {
                // Chuyển đổi URL thành đối tượng Image
                Image image = new Image(bookImage);
                api_imageView.setImage(image);
            } else {
                // Hiển thị hình ảnh mặc định nếu không có URL hình ảnh
                api_imageView.setImage(new Image(getClass().getResource("/image/defaultBook.png").toExternalForm()));
            }
        });

    }


    @FXML

        private void saveSelectedBook() {
            // Attempt to save the selected book to the database
            Book selectedBook = api_table.getSelectionModel().getSelectedItem();
            if (selectedBook == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Save Book");
                alert.setHeaderText("Warning");
                alert.setContentText("Please select a book to save.");
                alert.showAndWait();
                return;
            }

            // Lưu thông tin sách vào cơ sở dữ liệu


            try {
                connect= Database.connectDb();

                // Tạo câu lệnh SQL để chèn thông tin sách
                String sql = "INSERT INTO books (author, isbn, publisher, name, date_received, description,quantity,cover_image) VALUES (?, ?, ?, ?, ?, ?,?,?)";
                prepare = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                prepare.setString(1, selectedBook.getAuthor());
                prepare.setString(2, selectedBook.getIsbn());
                prepare.setString(3, selectedBook.getPublisher());
                prepare.setString(4, selectedBook.getName());
                prepare.setDate(5, selectedBook.getDate_received());
                prepare.setString(6, selectedBook.getDescription());
                prepare.setInt(7, 10);
                prepare.setString(8,selectedBook.getCover());

                // Thực hiện câu lệnh chèn
                int rowsInserted = prepare.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Save Book");
                    alert.setHeaderText("Success");
                    alert.setContentText("Book saved successfully!");
                    alert.showAndWait();

                    try (ResultSet generatedKeys = prepare.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("Inserted record ID: " + id);
                        }
                    }
                    showAvailableBooks();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Book");
                alert.setHeaderText("Database Error");
                alert.setContentText("Failed to save the book: " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    if (prepare != null) prepare.close();
                    if (connect != null) connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    private void loadSearchResults() {
        // Populate the table with the list of search results
        api_table.setItems(FXCollections.observableArrayList(searchResults));
    }
    @FXML
    private void searchBook() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Book");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a search query.");
            alert.showAndWait();
            return;
        }
        // Proceed with searching books based on the query and update the results
        searchBooks(query);
        loadSearchResults();
    }
    private void searchBooks(String query) {
        // Perform the search operation asynchronously using a background task
        Task<Void> searchBooksTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Use the GoogleBooksAPI to fetch book data based on the query
                API googleBooksAPI = new API();
                try {
                    // Fetch JSON data from the API
                    String jsonData = googleBooksAPI.fetchBooksData(query);
                    JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                    JsonArray items = jsonObject.has("items") ? jsonObject.getAsJsonArray("items") : null;
                    // If no books found, exit early
                    if (items == null || items.isEmpty()) return null;
                    // Clear previous search results and populate new results
                    searchResults.clear();
                    // Loop through each book item in the JSON response
                    for (JsonElement item : items) {
                        JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

                        JsonElement idElement = item.getAsJsonObject().get("id");
                        String isbn;

                        if (idElement.isJsonObject()) {
                            JsonObject idObject = idElement.getAsJsonObject();
                            isbn = idObject.has("id") ? idObject.get("idr").getAsString() : "Unknown";
                        } else if (idElement.isJsonPrimitive()) {
                            isbn = idElement.getAsString();
                        } else {
                            isbn = "Unknown";
                        }
                        Integer quantity = 10;
                        String name = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown";
                        String author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").get(0).getAsString() : "Unknown";
                        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown";
                        String publishedDateStr = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "Unknown";
                        String imageLink = volumeInfo.has("imageLinks") && volumeInfo.getAsJsonObject("imageLinks").has("thumbnail")
                                ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString()
                                : null;

// Convert publishedDate from String to Date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date publishedDate = null;
                        String fallbackDateStr = "1970-01-01"; // Ngày cố định

                        if (!"Unknown".equals(publishedDateStr)) {
                            try {
                                java.util.Date utilDate = dateFormat.parse(publishedDateStr);
                                publishedDate = new java.sql.Date(utilDate.getTime());
                            } catch (ParseException e) {
                                System.err.println("Ngày xuất bản không hợp lệ: " + publishedDateStr + ". Sử dụng ngày cố định: " + fallbackDateStr);
                                try {
                                    java.util.Date fallbackUtilDate = dateFormat.parse(fallbackDateStr);
                                    publishedDate = new java.sql.Date(fallbackUtilDate.getTime());
                                } catch (ParseException ex) {
                                   ex.printStackTrace(); // Lỗi này không nên xảy ra vì fallbackDateStr là ngày hợp lệ
                                }
                            }
                        } else {
                            try {
                                java.util.Date fallbackUtilDate = dateFormat.parse(fallbackDateStr);
                                publishedDate = new java.sql.Date(fallbackUtilDate.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace(); // Lỗi này không nên xảy ra vì fallbackDateStr là ngày hợp lệ
                            }
                        }

                      // Create and add the Book object to search results
                        Book book = new Book(1, isbn, name, author, quantity, publisher, description, publishedDate, imageLink);
                        searchResults.add(book);


                    }

                } catch (Exception e) {
                    e.printStackTrace();  // Log any exceptions
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // Once the search is complete, update the UI with the new search results
                loadSearchResults();
            }

            @Override
            protected void failed() {
                super.failed();
                // Show an error alert if the search operation fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setHeaderText(null);
                alert.setContentText("Error occurred while searching for books.");
                alert.showAndWait();
            }
        };

        // Execute the search task in a new thread (background operation)
        Thread searchBooksThread = new Thread(searchBooksTask);
        searchBooksThread.setDaemon(true); // Make it a daemon thread so it doesn't block application exit
        searchBooksThread.start();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Generates a QR code for the selected book, containing its details as JSON.
     * The QR code is saved as an image file and displayed in the UI.
     */
    @FXML
    //private void qrButton() {
    private void generateQRCode() {
        // Get selected book to generate QR Code
        Book selectedBook =BookTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.ERROR, "Generate QR Code", "Please select a book to generate QR code.");
            return;
        }

        //Ensure the directory exists
        createQRCodeDirectory();

        // Generate QR Code in a background thread
        Task<Void> generateQRCodeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Format book data as JSON for the QR Code
                String qrData = String.format(
                        "{ \"bookID\": %d, \"title\": \"%s\", \"author\": \"%s\", \"publisher\": \"%s\", \"publishedDate\": \"%s\" }",
                        selectedBook.getId(),
                        selectedBook.getName(),
                        selectedBook.getAuthor(),
                        selectedBook.getPublisher(),
                        selectedBook.getDate_received());
                //String qrData = new Gson().toJson(selectedBook);

                //Generate QR Code and save it to the specified file path
                String filePath = "src/main/resources/QR_Image/Book_" + selectedBook.getId() + ".png";

                try {
                    QR.generateQRCodeImage(qrData, 200, 200, filePath);
                    Image qrCodeImage = new Image(new File(filePath).toURI().toString());
                    qrImage.setImage(qrCodeImage);
                } catch (WriterException | IOException e) {
                    e.printStackTrace();
                    //showAlert(Alert.AlertType.ERROR, "Generate QR code", "Error generating QR Code: " + e.getMessage());
                    throw new RuntimeException("Error generating QR code: " + e.getMessage());
                }

                return null;
            }

            @Override
            protected void succeeded() {
                // Show success message once the QR Code is generated
                showAlert(Alert.AlertType.INFORMATION, "QR Code Generated", "QR Code saved successfully.");
            }

            @Override
            protected void failed() {
                // Show error message if QR Code generation fails
                showAlert(Alert.AlertType.ERROR, "Generate QR Code", "Error generating QR Code.");
            }
        };

        Thread qrCodeThread = new Thread(generateQRCodeTask);
        qrCodeThread.setDaemon(true);
        qrCodeThread.start();
    }

    /**
     * Creates a directory for saving QR code images if it does not already exist.
     */
    private void createQRCodeDirectory() {
        try {
            File directory = new File("src/main/resources/QR_Image");
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directory created: " + directory.getPath());
                } else {
                    throw new IOException("Failed to create directory: " + directory.getPath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Directory Creation Error", "Error creating directory for QR codes: " + e.getMessage());
        }
    }

}
