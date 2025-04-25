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
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {
    private double x = 0;
    private double y = 0;
    @FXML
    private Button close;

    @FXML
    private Button loginbtn;

    @FXML
    private Button minus;

    @FXML
    private TextField txt_admin;

    @FXML
    private PasswordField txt_password;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void loginAdmin() {
        String sql ="SELECT * FROM admin WHERE adminname = ? and password = ? ";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txt_admin.getText());
            prepare.setString(2, txt_password.getText());

            result = prepare.executeQuery();
            Alert alert;

            if(txt_admin.getText().isEmpty() || txt_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()){

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    loginbtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/librarymanagement/frame/HomePageAdmin.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Khởi tạo bất kỳ thành phần nào cần thiết hoặc dữ liệu
    }
    public void close() {
        System.exit(0);
    }
    @FXML
    private void minimize() {
        Stage stage = (Stage) minus.getScene().getWindow();
        stage.setIconified(true);
    }
}
