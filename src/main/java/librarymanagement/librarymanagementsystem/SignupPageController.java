package librarymanagement.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignupPageController  implements Initializable {
    private double x = 0;
    private double y = 0;


    @FXML
   private Button close;
    @FXML
    private Button adminlogin;
    @FXML
    private Button signupbtn;

    @FXML
    private TextField txt_contact;

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private Button loginbtn;

    @FXML
    private Button minus;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void insertSignupDetails () {
        String username = txt_username.getText();
        String contact = txt_contact.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();
        Alert alert;
        // Kiểm tra nếu bất kỳ giá trị nào là null hoặc trống
        if(username.isEmpty()||password.isEmpty()
                ||email.isEmpty()||contact.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the details");
            alert.showAndWait();
            return ;
        }
        connect = Database.connectDb();
        try {
            String checkUsernameSql = "SELECT * FROM users WHERE name = ?";
            prepare = connect.prepareStatement(checkUsernameSql);
            prepare.setString(1, username);
            result= prepare.executeQuery();
            if (result.next()) { // Nếu username đã tồn tại
                 alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("Username Exists");
                 alert.setHeaderText(null);
                 alert.setContentText("The username already exists in the database.");
                 alert.showAndWait(); return;
                 // Dừng phương thức nếu username đã tồn tại
                }
            // Kiểm tra xem email có tồn tại hay không
            String checkEmailSql = "SELECT * FROM users WHERE email = ?";
            prepare = connect.prepareStatement(checkEmailSql);
            prepare.setString(1, email);
            result = prepare.executeQuery();
            if (result.next()) {
                // Nếu email đã tồn tại
                 alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("Email Exists");
                 alert.setHeaderText(null);
                 alert.setContentText("The email already exists in the database.");
                 alert.showAndWait();
                 return; // Dừng phương thức nếu email đã tồn tại
            }
            String sql = "insert into users(name,password,email,contact,gender,picture) values(?,?,?,?,?,?)";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,username);
            prepare.setString(2,password);
            prepare.setString(3,email);
            prepare.setString(4,contact);
            prepare.setString(5, "Male");
            // Đường dẫn của ảnh (ảnh ở dạng String)
            String imagePath = "E:/BTL/src/main/resources/librarymanagement/frame/SignupPage.png/book-1773756_640.png";
            prepare.setString(6, imagePath);  // Lưu đường
            int updatedRowCount = prepare.executeUpdate();


                if (updatedRowCount > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Record Inserted Successfully");
                    alert.showAndWait();
                    try {
                        Stage stage = (Stage) loginbtn.getScene().getWindow();
                        stage.hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanagement/frame/LoginPage.fxml"));
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


                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Record Insertion Failure");
                    alert.showAndWait();
                }


        }catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while inserting the record: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void minimize() {
        Stage stage = (Stage) minus.getScene().getWindow();
        stage.setIconified(true);
    }

   @FXML
    private void buttonSignUp() {
        insertSignupDetails();
    }

   @FXML
   private void buttonAdminLogin() {
       try {
           Stage stage = (Stage) adminlogin.getScene().getWindow();
           stage.hide();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanagement/frame/AdminLogin.fxml"));
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

    @FXML
    private void buttonLogin() {
     try {
         Stage stage = (Stage) loginbtn.getScene().getWindow();
         stage.hide();
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanagement/frame/LoginPage.fxml"));
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
    public void close() {
        System.exit(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Khởi tạo bất kỳ thành phần nào cần thiết hoặc dữ liệu

    }
}