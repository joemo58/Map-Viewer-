package mapViewer;

import java.io.IOException;

public interface ModelI {

	public void setBoatX (double bx);
	public void setBoatY (double by);
	public void setAxeX (double ax);
	public void setAxeY (double ay);
	public double getBoatX();
	public double getBoatY();
	public double getAxeX();
	public double getAxeY();
	public int getAxeNumber();
	public void setAxeNumber(int axeNumber);
	public int getBoatNumber();
	public void setBoatNumber(int boatNumber);
	public void setLastItemSet(int i);
	public int getBoatNumber(int boatNumber);
	public int getLastItemSet();
	public void writeBoatToFile() throws IOException;
	public void writeAxeToFile() throws IOException;
	public Double getOldBoatX();
	public Double getOldBoatY();
	public Double getOldAxeX();
	public Double getOldAxeY();
	public void setOldAxeX(Double axeX);
	public void setOldAxeY(Double axeX);
	public void setOldBoatX(Double boatX);
	public void setOldBoatY(Double boatY);
	public int getOldBoatNumber();
	public int getOldAxeNumber();
	public void setOldAxeNumber(int i);
	public void setOldBoatNumber(int i);
}
