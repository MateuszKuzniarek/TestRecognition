package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();


        /*

        System.out.println("Hello, World");
        int x = 2;
        int y = 3;
        double z = (double)x/y;
        System.out.println(z);

        try
        {
            List<TextSample> examples2 = ExampleLoader.loadFromAllFiles("PLACES");
            examples2 = ExampleLoader.filterPlaces(examples2);
            //System.out.println(examples2.size());
            List<TextSample> examples = examples2.subList(0, 1000);
            int count = examples.size();
            int trainingCount = (int) (0.6*count);
            List<TextSample> trainingSamples = examples.subList(0, trainingCount);
            List<TextSample> testSamples = examples.subList(trainingCount, count);

            KNNClassification knn = new KNNClassification(7);
            knn.setFeatureExtractor(new TFIDFExtractor(10));
            knn.setMetric(new EuclideanMetric());
            knn.train(trainingSamples);
            int correctAnswers = 0;
            for(TextSample sample : testSamples)
            {
                String answer = knn.classify(sample);
                if(answer.equals(sample.getLabels().get(0))) correctAnswers++;
            }
            System.out.println((double)correctAnswers/testSamples.size());

            //for(logic.TextSample example : examples) System.out.println(example);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
         */
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
