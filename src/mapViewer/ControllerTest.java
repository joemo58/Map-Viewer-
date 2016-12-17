package mapViewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import javafx.scene.control.ComboBox;


public class ControllerTest {

	Controller c = new Controller();
		
	@Test
	public void checkModelIsUpdated(){
		c.addAxe(322.0, 339.0);
		double axeX = c.getAxeX();
		System.out.println(axeX);
		assertEquals(400.0, axeX, 0.001);
	}
	@Test
	public void checkUndo() throws IOException{
		c.onUndo();
		assertEquals(0.0, c.getAxeX(), 0.001);
	}
	@Test 
	public void checkComboBoxNotVisibleAtStart(){
		assertFalse(c.item_combo_box.isVisible());
	}
	
	
}
