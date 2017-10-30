package ca.utoronto.utm.paint;
import java.awt.Color;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author CSC207 Group
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; 										// the current line being parsed
	private String errorMessage =""; 									// error encountered during parse
	private ArrayList<PaintCommand> commandList = new ArrayList<>(); 	// created as a result of the parse
	
	//Below are Patterns used in parsing 
	 
	//start and end of the file
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	//regex of color and fill for the shapes. The patters are same for all shape for fill and color
	private Pattern pColor = Pattern.compile("^color:(0*[0-9][0-9]?|0*1[0-9][0-9]|0*2[0-4][0-9]|0*25[0-5]),"
			+ "(0*[0-9][0-9]?|0*1[0-9][0-9]|0*2[0-4][0-9]|0*25[0-5]),"
			+ "(0*[0-9][0-9]?|0*1[0-9][0-9]|0*2[0-4][0-9]|0*25[0-5])$");
	private Pattern pFilled = Pattern.compile("^filled:(true|false)$");
	
	//start, end regex of circles. Followed by center and radius regex
	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");
	
	private Pattern pCenter = Pattern.compile("^center:\\((0*[0-9][0-9]*),(0*[0-9][0-9]*)\\)");	//(group1, group2) = (x-coor, y-coor)
	private Pattern pRadius = Pattern.compile("^radius:(0*[1-9][0-9]*)$");
	
	//start and end regex of Rectangle. Followed by p1 and p2 regex.
	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");
	
	private Pattern pRecP1 = Pattern.compile("^p1:\\((0*[0-9][0-9]*),(0*[0-9][0-9]*)\\)$");		//(group1, group2) = (x-coor, y-coor)
	private Pattern pRecP2 = Pattern.compile("^p2:\\((0*[0-9][0-9]*),(0*[0-9][0-9]*)\\)$");		
	
	//start and end regex of squiggle. Followed by start and end regex of the the points that makes up the squiggle. Followed by the regex for the points.
	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");
	
	private Pattern pPoints = Pattern.compile("^points$");
	private Pattern pEndPoints = Pattern.compile("^endpoints$");
	private Pattern pPointSquiggle = Pattern.compile("^point:\\((0*[0-9][0-9]*),(0*[0-9][0-9]*)\\)$");	//(group1, group2) = (x-coor, y-coor)	
	
	
	/**
	 * Stores an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg (the error message in a string)
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+". "+mesg;
		
	}
	/**
	 * 
	 * @return (the PaintCommands resulting from the parse)
	 */
	public ArrayList<PaintCommand> getCommands(){
		return this.commandList;
	}
	/**
	 * 
	 * @return (the error message resulting from an unsuccessful parse)
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream 	(inputStream holds the textFile)
	 * @return 				(a boolean saying whether the complete file was successfully parsed)
	 */
	public boolean parse(BufferedReader inputStream) {
		// During the parse, we will be building one of the following shapes. 
		// As we parse the file, we modify the appropriate shape.
		
		//initially there are no shape before staring reading the file, and so is the returnVal is false
		Circle circle = null; 
		Rectangle rectangle = null;
		Squiggle squiggle = null;
		boolean returnVal = false;
		
		ArrayList<String> attributeList = new ArrayList<>();		//used to store the attributes such as color, fill for associated shapes
		ArrayList<Point> squigglePointList = new ArrayList<>();		//used to store the points of a squiggle
		
		try {	
			int state=0; 			//begins with state 0
			String l; 				//l is a string containing each line from top to bottom of the file
			Matcher m, fileEnd_m, circleStart_m, rectangleStart_m, squiggleStart_m, squigglePoint_m, squigglePointEnd_m; 
			
			//while there is a line in the file
			while ((l = inputStream.readLine()) != null) {
				
				l = l.replaceAll("\\s+", "");	//removes all the empty space from line l
				this.lineNumber++;
				
				if (l.length()>0){
					switch(state){
						case 0: //looking for "Starting line" of the file
							m=pFileStart.matcher(l);
							if(m.matches()){
								state=1;
								break;
							}
							else{
								error("Expected Start of Paint Save File.");
								return false;
							}
						
						
						case 1: // Looking for the start of a new shape or end of the save file.
							fileEnd_m = pFileEnd.matcher(l);
							circleStart_m = pCircleStart.matcher(l);
							rectangleStart_m = pRectangleStart.matcher(l);
							squiggleStart_m = pSquiggleStart.matcher(l);
							
							if(circleStart_m.matches()){
								state=2; 	//start of circle state
								break;
							}
							
							else if(rectangleStart_m.matches()){
								state=6; 	//start of rectangle state
								break;
							}
							
							else if(squiggleStart_m.matches()){
								state = 10;	//start of squiggle state
								break;
							}
							
							else if (fileEnd_m.matches()){
								state = 17; //accepting state 
								break;
							}
													
							else{
								error("Expected start of shape or end of file.");
								return false;
							}
	
							
//______________________start of circle parser______________________	
							
						case 2: //case to parse Circle's Color
							m = pColor.matcher(l);
							if (m.matches()){
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								attributeList.add(m.group(3));
								state = 3;
								break;
							}
							else{
								error("Expected circle color.");
								return false;	
							}
							
							
						case 3: //case to parse Circle's fill status
							m = pFilled.matcher(l);
							if (m.matches()){
								attributeList.add(m.group(1));
								state = 4;
								break;
							}
							else{
								error("Expected circle fill boolean.");
								return false;	
							}
										
						
						case 4: //case to parse Circle's center point
							m = pCenter.matcher(l);
							if(m.matches()){
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								state = 5;
								break;
							}
							else{
								error("Expected circle center point.");
								return false;	//not an accepting state yet
							}
							
						
						case 5: //case to parse Circle's radius
							m = pRadius.matcher(l);
							if(m.matches()){
								attributeList.add(m.group(1));
								//at this stage, we have all the attributes to create the circle command. Go to state 14 where the circle command will be created
								state = 14;		//state where instance of circle command is created
								break;
							}
							else{
								error("Expected circle radius.");
								return false;	//not an accepting state yet
							}
							
					
//______________________Start of rectangle parser______________________	
						
						case 6: //case to parse Rectangle's color
							m = pColor.matcher(l);
							if (m.matches()){
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								attributeList.add(m.group(3));
								state = 7;
								break;
							}
							else{
								error("Expected rectangle color.");
								return false;	//not an accepting state yet
							}
														
						
						case 7: //case to parse Rectangle's fill status
							m = pFilled.matcher(l);
							if (m.matches()){
								attributeList.add(m.group(1));
								state = 8;
								break;
							}
							else{
								error("Expected rectangle fill boolean.");
								return false;	//not an accepting state yet
							}
						
													
						case 8: //case to parse Rectangle's p1
							m = pRecP1.matcher(l);
							if (m.matches()){
								//x-coor of p1, followed by y-coor
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								state = 9;
								break;
							}
							else{
								error("Expected initial rectangle point.");
								return false;	//not an accepting state yet
							}
						
													
						case 9: //case to parse Rectangle's p2 points
							m = pRecP2.matcher(l);
							if (m.matches()){
								//x-coor of p2, followed by y-coor
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								//at this stage, we have all the attributes to create the rectangle command. Go to state 15 where the rectangle command will be created
								state = 15;		//state where instance of rectangle command is created
								break;
							}
							else{
								error("Expected second rectangle point.");
								return false;	//not an acceptiong state yet
							}
													
							
//______________________Start of Squiggle parser______________________
						
						case 10: //case to parse Squiggle's Color
							m = pColor.matcher(l);
							if (m.matches()){
								//r,g,b values of the colors
								attributeList.add(m.group(1));
								attributeList.add(m.group(2));
								attributeList.add(m.group(3));
								state = 11;
								break;
							}
							else{
								error("Expected squiggle color.");
								return false;	//not an accepting state yet
							}
							
							
						case 11: //case to parse Squiggle's fill status
							m = pFilled.matcher(l);
							if (m.matches()){
								attributeList.add(m.group(1));
								state = 12;
								break;
							}
							else{
								error("Expected squiggle fill boolean.");
								return false;
							}
							
							
						case 12: //case to parse that start ("points") of the points that makes the squiggle
							m = pPoints.matcher(l);
							if(m.matches()){
								state = 13;
								break;
							}
							else{
								error("Expected string 'points' to appear.");
								return false;
							}
						
							
						//case to parse all the points that makes a Squiggle and add them to the point list
						case 13:
							//stays in this state and adds the points as long as l matches PointSquihggle regex. 
							//In other word, adds all the points in the Squiggle's point list while being parsed.
							
							squigglePoint_m = pPointSquiggle.matcher(l);
							squigglePointEnd_m = pEndPoints.matcher(l);
							
							if(squigglePoint_m.matches()){
								//group1 -> x-coor, group2 -> y-coor
								squigglePointList.add(new Point(Integer.valueOf(squigglePoint_m.group(1)),Integer.valueOf(squigglePoint_m.group(2))));
								break;
							}
							
							//after we have all the point of the squiggle, we then can make a squiggle command. 
							//Go to state 16 where the squiggle command is created.
							else if(squigglePointEnd_m.matches()){
								state = 16;		//state where instance of Squiggle command is created
								break;
							}
							else{
								error("Expected a point or the 'end point' string.");
								return false;
							}
						
						
//______________________Start of creation of shape commands______________________
							
						//circle end
						case 14:
							m = pCircleEnd.matcher(l);
							if(m.matches()){
								//attributeList[0,1,2,3,4,5,6] = 
								//[rgb_red(int), rgb_green(int), rgb_blue(int), fill(bool), center(x), center(y), radius(int)]
								
								Color circleColor = new Color(Integer.valueOf(attributeList.get(0)),
										Integer.valueOf(attributeList.get(1)),Integer.valueOf(attributeList.get(2)));
								circle = new Circle(new Point(Integer.valueOf(attributeList.get(4)),
											Integer.valueOf(attributeList.get(5))),Integer.valueOf(attributeList.get(6)));
								circle.setColor(circleColor);
								circle.setFill(Boolean.valueOf(attributeList.get(3)));
								
								commandList.add(new CircleCommand(circle));
								attributeList.clear();
								state = 1;		//goes to state 1 which then checks if there is any shape to parse or its the end of the file
								break;
							}
							else{
								error("Expected 'end circle' string.");
								return false;
							}
							
						//rectangle end
						case 15:
							m = pRectangleEnd.matcher(l);
							if(m.matches()){						
								//attributeList[0,1,2,3,4,5] = 
								//[reb_red(int), rgb_green(int), rgb_blue(int), fill(bool), p1(x-coor),p1(y-coor),p2(x-coor),p2(y-coor)]
								
								Point p1 = new Point(Integer.valueOf(attributeList.get(4)),
										Integer.valueOf(attributeList.get(5)));
								Point p2 = new Point(Integer.valueOf(attributeList.get(6)),
										Integer.valueOf(attributeList.get(7)));
								Color rectColor = new Color(Integer.valueOf(attributeList.get(0)),
										Integer.valueOf(attributeList.get(1)),Integer.valueOf(attributeList.get(2)));
								rectangle = new Rectangle(p1,p2);
								rectangle.setColor(rectColor);
								rectangle.setFill(Boolean.valueOf(attributeList.get(3)));
								
								commandList.add(new RectangleCommand(rectangle));
								attributeList.clear();
								state = 1;		//goes to state 1 which then checks if there is any shape to parse or its the end of the file
								break;
							}
							else{
								error("Expected 'end rectangle' string.");
								return false;
							}
						
						//squiggle end
						case 16:
							m = pSquiggleEnd.matcher(l);
							if (m.matches()){
								squiggle = new Squiggle();
								
								//clones the list of points we parsed and sets it to squiggle's list of points
								squiggle.setPointList((ArrayList<Point>) squigglePointList.clone());	
								Color squiggleColor = new Color(Integer.valueOf(attributeList.get(0)),
										Integer.valueOf(attributeList.get(1)),Integer.valueOf(attributeList.get(2)));
								squiggle.setColor(squiggleColor);
								squiggle.setFill(true);
								
								commandList.add(new SquiggleCommand(squiggle));
								attributeList.clear();
								squigglePointList.clear();
								state = 1;		//after making squiggle command, goes to accepting state 1
								break;
							}
							else{
								error("Expected 'end squiggle' string.");
								return false;
							}
						
						//The accepting state
						//When the string 'EndPaintSaveFile' has appeared this will only trigger false if there is anything after.
						case 17:
							if (l!=null){
								error("Saved text file did not end with 'EndPaintSaveFile'.");
								return false;
							}
					}
				}
			}
			
			if (state == 17) returnVal = true;
			else returnVal = false;
			
		}  catch (Exception e){
			
		}
		return returnVal;
	}
}
