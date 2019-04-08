package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.SgmToXmlConverter;

import java.util.ArrayList;

import static javafx.application.Application.launch;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        /*
        ArrayList<String> paths = new ArrayList<>();
        for(int i=0; i<22; i++)
        {
            paths.add( "./examples/sgmFiles/reut2-0" + String.format("%02d", i) + ".sgm");
        }
        SgmToXmlConverter.convertToXml(paths, "./examples/reuters/reuters.xml");
        */

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
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
