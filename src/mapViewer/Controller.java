package mapViewer;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.neet.DiamondHunter.Main.Game;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

public class Controller implements ControllerI 
{
	private TileMap m = new TileMap(16);
	ModelI model = new Model(); 
	
	@FXML GridPane grid_pane;
	@FXML MenuItem load_map;
	@FXML BorderPane border_pane;
	@FXML ComboBox<String> item_combo_box;
	@FXML Button add_item_button;
	@FXML AnchorPane grid_anchor_pane;
	@FXML ImageView axe_button_IV;
	@FXML ImageView boat_button_IV;
	@FXML TextArea text_area;
	@FXML TextArea info_text_area;
	@FXML Button play_game_button;
	@FXML Label item_add_label;

	public Controller() {
		System.out.println(this);	/*Controller created automatically by javaFX*/
	}
	
	@Override
	public void addModel(Model model) {
		this.model = model;
	}

	 public void initialize() {
		 
		 ObservableList<String> items = FXCollections.observableArrayList(
				 "Boat",
				 "Axe"
				 );
		 item_combo_box.setItems(items);
		 item_combo_box.setVisible(false);
		 

		 text_area.setFont(Font.font ("Verdana", FontWeight.EXTRA_BOLD, 12));
		 updateTextArea();
		 text_area.setEditable(false);
		 
		 item_add_label.setVisible(false);
	 }

	public void onLoadMap() {
		//System.out.println("model = " + m);
		
		m.loadTiles("/Tilesets/testtileset.gif");
		m.loadMap("/Maps/testmap.map");
		
		int numRows = m.getNumRows();
		int numCols = m.getNumCols();
		
		drawMap(numRows, numCols);
	}
	
	public void drawMap(int numRows, int numCols){
		GridPane grid_pane = new GridPane();
		grid_anchor_pane.getChildren().add(grid_pane);
		grid_anchor_pane.setCursor(Cursor.CROSSHAIR);
		grid_pane.setAlignment(Pos.CENTER);
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				
				BufferedImage rcImage = m.getSquaresImage(row,col); /*returns the tile bufferedImage for current map r,c location*/
				ImageView pictureViews = new ImageView(); /*Image view for picture*/
				Image finalRcImage = SwingFXUtils.toFXImage(rcImage, null); // convert buffered image to fx Image															
				pictureViews.setImage(finalRcImage);
				
				
				grid_pane.add(pictureViews, col, row);
			}
		}
		grid_pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMapMouseClick);

	}

	@FXML public void onHelpClicked(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to use:");
        alert.setContentText("1) Load map from file\n2) Select item and select where you want to add it on the map");
        alert.showAndWait();

	}
	
	public void invalidPositionMessage(String item){
		Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Invalid move");
        alert.setHeaderText("You can't move the " + item + " here!");
        alert.setContentText("Select a square without an obstacle in the way to place the " + item + "\n");
        alert.showAndWait();
	}

	@FXML public void onAddItem(ActionEvent event) {
		item_add_label.setVisible(true);
		item_combo_box.setVisible(true);
		add_item_button.setVisible(false);
	}
	
	@FXML public String getComboSelection(){
		String selected = item_combo_box.getSelectionModel().getSelectedItem();
		System.out.println("Selection = "+selected);
		updateMenuPicture(selected);
		return selected;
	}
	
	/** Updates the map with a picture of the item added. 
	 * @param x x coordinate 
	 * @param y y coordinate 
	 * @param item The item to be added to map*/
	public void updateMap(Double x, Double y, String item){
		BufferedImage sprite;
		if (item == "Boat"){
			sprite = Content.ITEMS[1][0];
		} else {
			sprite = Content.ITEMS[1][1];	
		}
		
		ImageView item_image_view = new ImageView(); /*Image view for picture*/
		Image spriteImage = SwingFXUtils.toFXImage(sprite, null); // convert buffered image to fx Image															
		item_image_view.setImage(spriteImage);
		
		grid_anchor_pane.getChildren().add(item_image_view);
		item_image_view.setX(Math.round(x));
		item_image_view.setY(Math.round(y));
	}

	/** Adds boat to game by writting coordinates to a file, then calls updateMap to show an image on map
	 * @param x x coordinate 
	 * @param y y coordinate*/
	public void addBoat(Double x, Double y){
		
		int x_tile = x.intValue() / m.getTileSize();
		int y_tile = y.intValue() / m.getTileSize();
		
		System.out.println("x_tile = " + x_tile + " " + y_tile);
			
		if (m.getType(y_tile, x_tile) == 1) {
			invalidPositionMessage(getComboSelection());
		} else {
			if (model.getBoatNumber() <= 0){
				model.setBoatX(Math.round(x));
				model.setBoatY(Math.round(y));
				updateMap(x, y, "Boat");
				model.setBoatNumber(1);
				model.setLastItemSet(0);
				updateTextArea();
			}
		}
		
		try {
			model.writeBoatToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Boat x, y = " + model.getBoatX() + model.getBoatY());
	} 
	
	private void updateTextArea() {
		text_area.setText("");
		String boatText = "\n \nBoats left to add: " + (1 - model.getBoatNumber()) + "\n \n";
		String axeText = "\nAxes  left to add: " + (1 - model.getAxeNumber())  + "\n";
		text_area.appendText(boatText);
		text_area.appendText(axeText);
	}

	/** Adds axe to map in game by writing to file, then calls updateMap to show an image
	 * @param x x coordinate 
	 * @param y y coordinate*/
	public void addAxe(Double x, Double y) {
		
		int x_tile = x.intValue() / m.getTileSize();
		int y_tile = y.intValue() / m.getTileSize();
		
		if (m.getType(y_tile, x_tile) == 1) {
			invalidPositionMessage(getComboSelection());
		} else {
			if (model.getAxeNumber() <= 0){
				model.setAxeX(Math.round(x));
				model.setAxeY(Math.round(y));
				updateMap(x, y, "Axe");
				model.setAxeNumber(1);
				model.setLastItemSet(0);
				updateTextArea();
			}
		}
		
		try {
			model.writeAxeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Axe x, y = " + model.getAxeX() + model.getAxeY());
	}
	
	EventHandler<MouseEvent> onMapMouseClick = new EventHandler<MouseEvent>(){
		public void handle(MouseEvent event){
			Double x = event.getX();
			Double y = event.getY();
			
			if (getComboSelection() == "Boat"){
				addBoat(x, y);
			}
			else if (getComboSelection() == "Axe"){
				addAxe(x, y);
			}
		}
	};
	
	
	public void updateMenuPicture(String item){
		BufferedImage sprite;
		Image spriteImage;
		if (item == "Boat"){
			 sprite = Content.ITEMS[1][0]; /*boat*/
			 spriteImage = SwingFXUtils.toFXImage(sprite, null); // convert buffered image to fx Image															
			 boat_button_IV.setImage(spriteImage);
		} else {
			 sprite = Content.ITEMS[1][1]; /*boat*/
			 spriteImage = SwingFXUtils.toFXImage(sprite, null); // convert buffered image to fx Image															
			 boat_button_IV.setImage(spriteImage);
		}
	}
	
	
	/** Writes boat coordinates to file*/

	/** Closes the program*/
	@FXML public void onClose() {
		Platform.exit();
	}
	
	public void setRedoCoordinates(){
		model.setOldAxeX(model.getAxeX());
		model.setOldAxeY(model.getAxeY());
		model.setOldBoatX(model.getBoatX());
		model.setOldBoatY(model.getBoatY());
	}

	/** Removes any items that have been added to the map*/
	@FXML public void onUndo() throws IOException {
		model.setOldAxeNumber(model.getAxeNumber());
		model.setOldBoatNumber(model.getBoatNumber());
		setRedoCoordinates();
		model.setAxeX(0.0);
		model.setAxeY(0.0);
		model.setBoatX(0.0);
		model.setBoatY(0.0);
		model.setBoatNumber(0);
		model.setAxeNumber(0);
		drawMap(m.getNumRows(), m.getNumCols());
		model.writeAxeToFile();
		model.writeBoatToFile();
		updateTextArea();
	}
	
	@FXML public void onRedo() throws IOException {
		model.setAxeX(model.getOldAxeX());
		model.setAxeY(model.getOldAxeY());
		model.setBoatX(model.getOldBoatX());
		model.setBoatY(model.getOldBoatY());
		model.setAxeNumber(model.getOldAxeNumber());
		model.setBoatNumber(model.getOldBoatNumber());
		
		
		updateMap(model.getOldAxeX(), model.getOldAxeY(), "Axe");
		updateMap(model.getOldBoatX(), model.getOldBoatY(), "Boat");
		model.writeAxeToFile();
		model.writeBoatToFile();
		updateTextArea();
	}
	public double getAxeX(){
		return model.getAxeX();
	}
	
	@FXML public boolean getComboBox(){
		return item_combo_box.isVisible();
	}

	@FXML public void onPlayGame() {
		Game.main(null);
	}
}
