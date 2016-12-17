package mapViewer;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			root.setMaxHeight(150);
			Scene scene = new Scene(root);
			Parent content = FXMLLoader.load(getClass().getResource("view.fxml"));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			root.setCenter(content); 
			primaryStage.setTitle("Map Viewer");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
