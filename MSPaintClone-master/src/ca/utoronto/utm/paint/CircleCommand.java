package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.PrintWriter;

public class CircleCommand implements PaintCommand {
	private Circle circle;
	public CircleCommand(Circle circle){
		this.circle=circle;
	}
	
	/**
	 * Returns a string of the attributes in the format
	 * of the save file.
	 * @return (This circle's attributes in save file format.)
	 */
	public String saveShape(){
		String s = "";
		s+="Circle\n";
		s+= this.circle.toString();
		int xPoint = this.circle.getCentre().getX();
		int yPoint = this.circle.getCentre().getY();
		int radius = this.circle.getRadius();
		s+= "\tcenter:("+ xPoint + ","+ yPoint+")\n";
		s+= "\tradius:"+ radius+"\n";
		s+= "End Circle\n";
		
		return s;
	}
	
	public void execute(Graphics2D g2d){
		g2d.setColor(circle.getColor());
		int x = this.circle.getCentre().x;
		int y = this.circle.getCentre().y;
		int radius = this.circle.getRadius();
		if(circle.isFill()){
			g2d.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		} else {
			g2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		}
	}
}
