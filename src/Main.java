import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.net.URL;
import java.net.URLClassLoader;

import util.Util;
import controller.MainUIController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 画面サイズ取得
        Rectangle2D d = Screen.getPrimary().getVisualBounds();
        int width = (int)Math.min(1280, d.getWidth());
        int height = (int)Math.min(800, d.getHeight());

        // Property
        URL propURLs[] = { getClass().getResource("/fxml/locale/") };
        URLClassLoader urlLoader = new URLClassLoader(propURLs);

        // ウィンドウ起動
        Stage mainStage = Util.genStage(
            width,
            height,
            "Galileo",      // title
            "/fxml/MainUI.fxml",            // fxml path
            new MainUIController(),         // controller
            ResourceBundle.getBundle("locale", Locale.getDefault(), urlLoader)      // resource
        );
        mainStage.show();
    }

}
