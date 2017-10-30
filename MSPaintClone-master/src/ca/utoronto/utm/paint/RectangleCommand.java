package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RectangleCommand implements PaintCommand {
	private Rectangle rectangle;
	public RectangleCommand(Rectangle rectangle){
		this.rectangle = rectangle;
	}
	
	/**
	 * Returns a string of the attributes in the format
	 * of the save file.
	 * @return This rectangle's attributes in save file format.
	 */
	public String saveShape(){
		String s ="";
		s+="Rectangle\n";
		s+= this.rectangle.toString();
		int xPoint1 = this.rectangle.getP1().getX();
		int yPoint1 = this.rectangle.getP1().getY();
		int xPoint2 = this.rectangle.getP2().getX();
		int yPoint2 = this.rectangle.getP2().getY();
		s+= "\tp1:("+xPoint1+","+yPoint1+")\n";
		s+= "\tp2:("+xPoint2+","+yPoint2+")\n";
		s+= "End Rectangle\n";
		
		return s;
	}

	public void execute(Graphics2D g2d){
		g2d.setColor(rectangle.getColor());
		Point topLeft = this.rectangle.getTopLeft();
		Point dimensions = this.rectangle.getDimensions();
		if(rectangle.isFill()){
			g2d.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		} else {
			g2d.drawRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		}
	}
}
