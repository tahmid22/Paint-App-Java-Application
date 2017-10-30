package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SquiggleCommand implements PaintCommand {
	private Squiggle squiggle;
	public SquiggleCommand(Squiggle squiggle){
		this.squiggle = squiggle;
	}
	
	/**
	 * Returns a string of the attributes in the format
	 * of the save file.
	 * @return This squiggle's attributes in save file format.
	 */
	public String saveShape(){
		String s = "";
		s+= "Squiggle\n";
		s+= this.squiggle.toString();
		s+= "\tpoints\n";
		for(Point sPoints: this.squiggle.getPoints()){
			int xSPoint = sPoints.getX();
			int ySPoint = sPoints.getY();
			s+= "\t\tpoint:("+xSPoint+","+ySPoint+")\n";
		}
		s+= "\tend points\n";
		s+="End Squiggle\n";
		
		return s;
	}

	public void execute(Graphics2D g2d){
		ArrayList<Point> points = this.squiggle.getPoints();
		g2d.setColor(squiggle.getColor());
		for(int i=0;i<points.size()-1;i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
}
