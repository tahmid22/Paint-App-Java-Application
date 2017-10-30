package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;

public interface PaintCommand {
	public void execute(Graphics2D g2d);
	public String saveShape(); //returns a shape's attributes in a string in a save file's format.
}
