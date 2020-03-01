import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // 画面サイズ取得
            Rectangle2D d = Screen.getPrimary().getVisualBounds();
            int width = (int)Math.min(1280, d.getWidth());
            int height = (int)Math.min(720, d.getHeight());

            // Scene初期化
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainUI.fxml"));
            Scene scene = new Scene(loader.load(), width, height);

            // Stage初期化
            stage.setTitle("Volcanic Rock Simulator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
