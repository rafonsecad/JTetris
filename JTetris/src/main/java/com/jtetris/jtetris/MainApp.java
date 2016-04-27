package com.jtetris.jtetris;

import java.util.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        TetrisPane tetrispane = new TetrisPane();
        Scene scene = new Scene(tetrispane,450,600);
        scene.getStylesheets().add("/styles/Styles.css");
        
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(tetrispane.getTetrisHandler(), 0, 200);
        scene.setOnKeyPressed(tetrispane);
        
        stage.setTitle("JTetris");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
