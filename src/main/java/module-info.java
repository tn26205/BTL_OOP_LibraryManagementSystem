module librarymanagement.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires transitive fontawesomefx;
    requires ojdbc14;
    requires com.google.gson;
    requires com.google.zxing;
    requires com.google.zxing.javase;


    opens librarymanagement.librarymanagementsystem to javafx.fxml;
    exports librarymanagement.librarymanagementsystem;
}