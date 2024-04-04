package cookbook;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        Label title = new Label("JavaFX");
        Label mysql;

        String sqlQuery = "SELECT * FROM Members;";

        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
            mysql = new Label("Driver found and connected");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            rs.next();
            String members = rs.getString(3);
            System.out.println(members);

        } catch (SQLException e) {
            mysql = new Label("An error has occurred: " + e.getMessage());
        }

        root.getChildren().addAll(title, mysql);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.setTitle("JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(App.class);
    }

}
