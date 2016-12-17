package mapViewer;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public interface ControllerI {

	public void addModel(Model m);
	public void initialize();
	public void onLoadMap();
	@FXML public void onHelpClicked(ActionEvent event);
	@FXML public void onAddItem(ActionEvent event);
	@FXML public String getComboSelection();

}
