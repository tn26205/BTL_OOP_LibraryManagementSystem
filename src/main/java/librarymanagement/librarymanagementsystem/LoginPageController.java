package librarymanagement.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static librarymanagement.librarymanagementsystem.getData.studentNumber;

public class LoginPageController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button minus;

    @FXML
    private Button loginbtn;

    @FXML
    private Button signupbtn;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    @FXML
    public void minimize() {
        Stage stage = (Stage) minus.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void close() {
        System.exit(0);
    }
    public void insertloginDetails () {
        String username = txt_username.getText();
        String password = txt_password.getText();
        Alert alert;


            // Kiểm tra nếu bất kỳ giá trị nào là null hoặc trống
            if(username.isEmpty()||password.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the details");
                alert.showAndWait();
            }
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/library", "root", "");
                prepare = connect.prepareStatement("select  * from users where name = ? and password = ?");
                connect = Database.connectDb();
                prepare.setString(1, username);
                prepare.setString(2, password);
                result = prepare.executeQuery();
                Alert alter;

                if(result.next()){

                    getData.studentNumber = txt_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();

                    //Ẩn cửa sổ đăng nhập.
                    loginbtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("/librarymanagement/frame/HomePage.fxml") );
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    //Ẩn cửa sổ đăng nhập.
                    loginbtn.getScene().getWindow().hide();

                    root.setOnMousePressed((MouseEvent event) ->{

                        x = event.getSceneX();
                        y = event.getSceneY();

                    });

                    root.setOnMouseDragged((MouseEvent event) ->{

                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                    });

                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                    alert.showAndWait();
                }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //Kiêm tra nếu username nhập vào là chữ thì sẽ gạch đỏ, còn nếu có chứa số ( không hợp lệ) thì nó sẽ vẫn là gạch trắng.
    public void numbersOnly(KeyEvent event) {
        if(event.getCharacter().matches("[a-zA-Z]")) {
            event.consume(); //nhận diện kí tự không trong trường văn bản.
            txt_username.setStyle("-fx-border-color:#e04040");
        }
        else {
            txt_username.setStyle("-fx-border-color:#fff");
        }
    }

    @FXML
    private void buttonLogIn() {
        insertloginDetails();
    }

    @FXML
    private void buttonSignUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/librarymanagement/frame/SignupPage.fxml") );
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Khởi tạo bất kỳ thành phần nào cần thiết hoặc dữ liệu
    }

}
