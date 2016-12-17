package mapViewer;

import java.io.IOException;
import java.io.PrintWriter;

public class Model implements ModelI{
	
	private double boat_x;
	private double boat_y;
	private double axe_x;	
	private double axe_y;
	
	private double old_boat_x = 0.0;
	private double old_boat_y = 0.0;
	private double old_axe_x = 0.0;
	private double old_axe_y = 0.0;
	
	private int boatNumber;//boats left to add to map
	private int axeNumber;//axes left to add to map
	
	private int oldBoatNumber;
	private int oldAxeNumber;
	
	private int lastItemSet;
	
	public static final int BOAT = 0;
	public static final int AXE = 1;
	
	public void setBoatX (double bx){
		boat_x = bx;
	}
	public void setBoatY (double by){
		boat_y = by;
	}
	public void setAxeX (double ax){
		axe_x = ax;
	}
	public void setAxeY (double ay){
		axe_y = ay;
	}
	
	public double getBoatX(){
		return boat_x;
	}
	
	public double getBoatY(){
		return boat_y;
	}
	public double getAxeX(){
		return axe_x;
	}
	
	public double getAxeY(){
		return axe_y;
	}
	public int getAxeNumber() {
		return axeNumber;
	}
	public void setAxeNumber(int axeNumber) {
		this.axeNumber = axeNumber;
	}
	public int getBoatNumber() {
		return boatNumber;
	}
	public void setBoatNumber(int boatNumber) {
		this.boatNumber = boatNumber;
	}
	@Override
	/** Sets which item was added last - used for undo
	 * @param item item to be added*/
	public void setLastItemSet(int item) {
		this.lastItemSet = item;
		
	}
	@Override
	public int getBoatNumber(int boatNumber) {
		return this.boatNumber;
	}
	@Override
	public int getLastItemSet() {
		return this.lastItemSet;
	}
	
	public void writeBoatToFile() throws IOException {
		
	    PrintWriter writer = new PrintWriter("boat_cos.txt", "UTF-8");
	    writer.println(getBoatX());
	    writer.println(getBoatY());
	    writer.close();
	    writer.flush();
}

	public void writeAxeToFile() throws IOException {
	    PrintWriter writer = new PrintWriter("axe_cos.txt", "UTF-8");
	    writer.println(getAxeX());
	    writer.println(getAxeY());
	    writer.close();
	    writer.flush();
}
	
	public Double getOldBoatX(){
		return old_boat_x;
	}
	public Double getOldBoatY(){
		return old_boat_y;
	}
	public Double getOldAxeX(){
		return old_axe_x;
	}
	public Double getOldAxeY(){
		return old_axe_y;
	}

	@Override
	public void setOldAxeX(Double axeX) {
		this.old_axe_x = axeX;
		
	}
	@Override
	public void setOldAxeY(Double axeY) {
		this.old_axe_y = axeY;
		
	}
	@Override
	public void setOldBoatX(Double boatX) {
		this.old_boat_x = boatX;
		
	}
	@Override
	public void setOldBoatY(Double boatY) {
		this.old_boat_y = boatY;
		
	}
	@Override
	public int getOldBoatNumber() {
		// TODO Auto-generated method stub
		return oldBoatNumber;
	}
	@Override
	public int getOldAxeNumber() {
		// TODO Auto-generated method stub
		return oldAxeNumber;
	}
	@Override
	public void setOldAxeNumber(int i) {
		this.oldAxeNumber = i;
		
	}
	@Override
	public void setOldBoatNumber(int i) {
		this.oldBoatNumber = i;
	}
}
