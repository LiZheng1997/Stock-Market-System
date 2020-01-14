import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StockDataPanel extends JPanel {

	private ArrayList<Double> open;
	private ArrayList<Double> high;
	private ArrayList<Double> low;
	private ArrayList<Double> close;
	private ArrayList<Double> volume;
	private ArrayList<String> dateData;
	private ArrayList<String> month;
	private ArrayList<String> day;
	private ArrayList<String> year;	
	
	
	private Graphics2D g2;
	private JLabel tipLabel;
	private Button changeGraphButton;
	private Button ordinaryButton;
	private Boolean advanced;
	int x;
	int y;
	
	
	public  StockDataPanel( ArrayList<Double> aClose, ArrayList<Double> aOpen,ArrayList<String> aDate, ArrayList<Double> aHigh,  ArrayList<Double> aLow,  ArrayList<Double> aVolume,ArrayList<String> aMonth, ArrayList<String> aDay, ArrayList<String> aYear ) {
		
		dateData = new ArrayList<String>();
		close = new ArrayList<Double>();
		open = new ArrayList<Double>();
		high = new ArrayList<Double>();
		low = new ArrayList<Double>();
		volume = new ArrayList<Double>();
		tipLabel = new JLabel("");
		month = new ArrayList<String>();
		day = new ArrayList<String>();
		year = new ArrayList<String>();
		
		close = aClose;
		open = aOpen;
		high = aHigh;
		low = aLow;
		volume = aVolume;		
		dateData = aDate;
		month = aMonth;
		day = aDay;
		year = aYear;
		advanced = false;
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4,10,10));
		this.setLayout(new BorderLayout());
		
		Button sellButton = new Button("Sell");
		sellButton.setFont(new Font("Papyrus", Font.BOLD, 20));
		Button buyButton = new Button("Buy");
		buyButton.setFont(new Font("Papyrus", Font.BOLD, 20));
		ordinaryButton = new Button("Ordinary Charting");
		ordinaryButton.setFont(new Font("Papyrus", Font.BOLD, 20));
		changeGraphButton = new Button("Advanced Charting");
		changeGraphButton.setFont(new Font("Papyrus", Font.BOLD, 20));
		Dimension dimension =new Dimension(80, 40) ;
		changeGraphButton.setPreferredSize(dimension);
		topPanel.add(sellButton);
		topPanel.add(changeGraphButton);
		topPanel.add(ordinaryButton);
		topPanel.add(buyButton);
		this.add(topPanel,BorderLayout.NORTH);
		this.addMouseMotionListener(new PanelMouseListener());
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g2 = (Graphics2D) g ;
		//This is a button for users to change to the ordinary chart.
		ordinaryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				advanced = false;
				changeGraphButton.setVisible(true);
				ordinaryButton.setVisible(false);
			}
			
		});
		// This is a button for users to change to the candlestick chart.
		changeGraphButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				advanced = true;
				changeGraphButton.setVisible(false);
				ordinaryButton.setVisible(true);
			}
		});
		
		/*
		 * At first, X axis is used for presenting time and y axis is used for stock data value.
		 * These values are locations for X axis and Y axis, X axis has two points, one is the start point and
		 * another is the end point, that is the same with Y axis. So, xAxis0 is the start point and xAxisn is 
		 * the end point and yAxis0 is the start point and yAxisn is the end point. After we did these, we can 
		 * assure that all data points will be drawn in these range.
		 */
		int FirstGraphWest = 0;
		int FirstGraphEast = this.getWidth()-20;
		int FirstGraphNorth = 0;
		int FirstGraphSouth = this.getHeight()/2 ;
		
		int SecondGraphWest = 0;
		int SecondGraphEast = this.getWidth()-20;
		int SecondGraphNorth = this.getHeight()/2 ;
		int SecondGraphSouth = this.getHeight();
		
// 		 The aim of using 100, 20, 50 is that i want the graph will not be too close to the side of a panel.
//		this will be helpful for users to use, and avoid misleading about graphs are showed.
		int yAxis0 =  FirstGraphSouth -20;
		int yAxisn =  FirstGraphNorth+50;
		int xAxis0 =  FirstGraphWest + 100;
		int xAxisn =  FirstGraphEast -50;
		
		int SecondYAxis0 = SecondGraphSouth-40;
		int SecondYAxisn = SecondGraphNorth +20;
		int SecondXAxis0 = SecondGraphWest+ 100;
		int SecondXAxisn = SecondGraphEast -50;
		
// 		These two values are the lowest close data point and highest data point.
 		double maxHighStockData = Collections.max(high);
 		double minLowStockData = Collections.min(low);
 		double maxVolume = Collections.max(volume);
 		double minVolume = Collections.min(volume);
 	/*
 	 * I defined that the value of the data reference lines which are paralleled with X axis,
 	 * and there are 5 values will be presented by these data lines, so singleShowYValue is the 
 	 * interval for these 5 data reference lines, and stock ValueRange is the stock data range, 
 	 * singleStockValueRange is every movement at Y axis direction, and the purpose of minus 20 
 	 * for  yAxis0 and yAixsn is that the data point will not be too closed to the border of the drawing
 	 * area and users will be easy to see the highest and lowest values. singleTimeRange is also the same
 	 * meaning, it is a single movement at X axis direction.
 	 */
 		int singleTimeRange = (xAxisn-60 - xAxis0-60)/dateData.size();
 		//These are used by the second volume graph
 		double volumeRange = maxVolume - minVolume;
 		double singleYVolume = volumeRange/5;
 		double singleVolumeRange = (SecondYAxis0 -SecondYAxisn)/volumeRange;	
 		//These are used by the first advanced chart
 		double stockValueRange = maxHighStockData - minLowStockData;
 		double singleShowYValue = stockValueRange/10;
 		double singleStockValueRange = 	(yAxis0 -20.00 -yAxisn-20.00)/stockValueRange;	
 		
		//this is a line for y axis
 		Line2D yline = new Line2D.Double(xAxis0, yAxisn, xAxis0, yAxis0);
 		// this is a line for  x axis
 		Line2D xline = new Line2D.Double(xAxis0, yAxis0, xAxisn, yAxis0);
 		//this is a line for y axis of the second graph
 		Line2D SecondYLine = new Line2D.Double(SecondXAxis0, SecondYAxisn, SecondXAxis0, SecondYAxis0);
 		//this is a line for x axis of the second graph
 		Line2D SecondXLine = new Line2D.Double(SecondXAxis0, SecondYAxis0, SecondXAxisn, SecondYAxis0);
 		
// 		drawing the different lines.
 		g2.setColor(Color.gray);
 		g2.draw(yline);
		g2.draw(xline);
		g2.draw(SecondXLine);
		g2.draw(SecondYLine);
 		
 		/*
 		 * These are logic block to draw the line which is aimed at telling users the round
 		 * time, these lines will be showed at the X Axis, different range of time users selected
 		 * will decide how many lines to be drawn. And addDataComponents function is aimed at drawing 
 		 * a viewing data assistance tool, this tool can help users to view the data point with a moving
 		 * tool, and this tool contains a tool tip for users to know all the data value for one point.
 		 */
 		this.addTimeRefLine (xAxis0,yAxis0, yAxisn,singleTimeRange);
 		this.addTimeRefLine (SecondXAxis0,SecondYAxis0, SecondYAxisn,singleTimeRange);
 		this.addDataComponents(x, y,xAxis0,yAxisn,yAxis0, minLowStockData, singleStockValueRange,singleTimeRange);
 		//these two strings are for telling the format of the time line at the x axis.
 		g2.setFont(new Font("Papyrus", Font.BOLD, 12));
 		g2.drawString("month-day-year", 4, (yAxis0-12));
 		g2.setFont(new Font("Papyrus", Font.BOLD, 12));
 		g2.drawString("month-day-year", 4, (SecondYAxis0-12));
 		/*
 		 * yTick is a single movement at Y axis direction, this will help us to locate every reference line.
 		 * And the for loop is to draw the stock data value and reference line.
 		 */
 		int yTick = (yAxis0 - yAxisn -40 )/10 ;
 		for(int i = 0 ; i < 11 ; i++) {
 			g2.setColor(Color.gray);
 			g2.setStroke(new BasicStroke(1.0f));
 			g2.drawLine(xAxis0, yAxis0-20 - yTick*i , xAxisn,  yAxis0-20 - yTick*i);
 			double lineData = (minLowStockData + singleShowYValue*i);
 			String YLineData=String.format("%.2f",lineData);
// 			These 40 ,3 and 20 numbers are aimed at locating a good position for showing the stock data value 
 			g2.setColor(Color.black);
 			g2.setStroke(new BasicStroke(1.0f));
 			g2.setFont(new Font("Papyrus", Font.BOLD, 20));
 			g2.drawString(YLineData+"$", (xAxisn-60), (yAxis0-20 - yTick*i)-3);
 		}
 		
 		/*
 		 * This is a function which is almost the same with last tick function, it is also drawing a line and 
 		 * string values of reference volumes.
 		 */
 		int VolumeYTcik = (SecondYAxis0 -SecondYAxisn)/5;
 		for(int i = 1 ; i < 6 ; i++) {
 			g2.setColor(Color.BLACK);
// 			float[] dash = {5,5};
// 			BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
// 			g2.setStroke(bs);
 			g2.setStroke(new BasicStroke(1.0f));
 			g2.drawLine(SecondXAxis0, (SecondYAxis0 - VolumeYTcik*i) , SecondXAxisn,  (SecondYAxis0- VolumeYTcik*i));
 			g2.drawLine(SecondXAxis0, (SecondYAxis0 - VolumeYTcik*i) , SecondXAxis0-5,  (SecondYAxis0- VolumeYTcik*i));
 			double lineData = (minVolume + singleYVolume*i);
 			String YLineData=String.format("%.2f",lineData);
// 			These 40 ,3 and 20 numbers are aimed at locating a good position for showing the stock data value 
 			g2.setColor(Color.black);
 			g2.setStroke(new BasicStroke(2.0f));
 			g2.setFont(new Font("Papyrus", Font.BOLD, 12));
 			g2.drawString(YLineData, SecondXAxis0-85, (SecondYAxis0- VolumeYTcik*i)+5);
 		}
 		

		
		/*
		 * This function is aimed at drawing the newest close value for users to use, and it 
		 * also will tell users the range and whether or not the stock grows or drops compared with
		 * the day before the final day which is chose by users when they select the date previously.
		 */
 		if(close.size()==1) {
 			
 		}
 		else {
	 			double range = ((high.get(high.size()-1) - low.get(low.size()-1))/close.get(close.size()-2))*100;
	 			String rangeString = String.format("%.2f",range);
	 			
	 			if(open.get(open.size()-1)> close.get(close.size()-1)) {
	 				this.addNewestData(rangeString);
	 				
	 			}
	 			else if(open.get(open.size()-1)< close.get(close.size()-1)) {
	 				this.addNewestData(rangeString);
	 			}
	 			else {
	 				this.addNewestData(rangeString);
	 			}
 		}
		
		
		/*
		 * this loop is aimed at drawing the 3DRectangle  for the second graph, different
		 * conditions(grow or drop) will decide the color of rectangle, this can help users
		 * to judge whether this stock' price at one day drops or not. 
		 */
		for(int i= 0 ; i< volume.size(); i++ ) {
 			
			double yNextMovement =  ( volume.get(i) - minVolume)* singleVolumeRange ;
			int x = (int) (SecondXAxis0 + (i+1)*singleTimeRange);
			int y = (int) (SecondYAxis0 - yNextMovement);
	 		if(open.get(i)> close.get(i)) {
	 			g2.setColor(Color.GREEN);
	 			g2.fill3DRect((int)(x - singleTimeRange/4), y, (int)singleTimeRange/2, (int)yNextMovement, true);
	 		}
	 		else if(open.get(i)< close.get(i)){
		 		g2.setColor(Color.RED);
		 		g2.fill3DRect((int)(x - singleTimeRange/4), y, (int)singleTimeRange/2, (int)yNextMovement, true);
	 		}
	 		else {
	 			g2.setColor(Color.GRAY);
	 			g2.fill3DRect((int)(x - singleTimeRange/4), y, (int)singleTimeRange/2, (int)yNextMovement, true);
	 		}
	 		
	 	}
		
		/*
		 * This block is aimed at drawing a ordinary chart for users to use, the graph is line
		 * chart. Only when users click on the ordinary charting button, will this block run.
		 * So, users can have more interactive experience, and they can choose what they want to
		 * see. 
		 */
 		if(advanced != true) {
 			double movement = ( close.get(0) - minLowStockData)* singleStockValueRange ;
 			Point fristPoint = new Point((int)(xAxis0 + singleTimeRange), (int)(yAxis0 -20 - movement) );
 	 		//this loop aims at drawing the various line segments 
 			for(int i= 1 ; i< close.size(); i++ ) {
 		 			
 		 			double yNextMovement = ( close.get(i) - minLowStockData)* singleStockValueRange ;
 		 	 		Point secondPoint = new Point((int)(fristPoint.x + singleTimeRange), (int)(yAxis0 -20 - yNextMovement) );
 		 	 		Line2D nextLine = new Line2D.Double(fristPoint,secondPoint );
 		// 	 		LineBorder aLineBorder = new LineBorder(Color.blue);
 		 	 		g2.setColor(new Color(135,206,250));
 		 	 		g2.setStroke(new BasicStroke(2.5f));
 		 	 		g2.draw(nextLine);
 		 	 		fristPoint = secondPoint;
 		 		}
 		}
 		//This is advanced chart which is a candlestick graph.
 		else {
 			for(int i= 0 ; i< close.size(); i++ ) {
 				
 				double x = xAxis0 + (i+1)*singleTimeRange;
 				double closeNextMovement = ( close.get(i) - minLowStockData)* singleStockValueRange ;
 				double highNextMovement = ( high.get(i) - minLowStockData)* singleStockValueRange ;
 				double lowNextMovement = ( low.get(i) - minLowStockData)* singleStockValueRange ;
 				double openNextMovement = ( open.get(i) - minLowStockData)* singleStockValueRange ;
 				double yClose = yAxis0-20 - closeNextMovement;
 				double yOpen = yAxis0-20 - openNextMovement;
 				double yHigh = yAxis0-20 - highNextMovement;
 				double yLow = yAxis0-20 - lowNextMovement;
 				
 				//this judgment is aimed at judging whether the close value is larger than the open value. 
 				if(open.get(i)> close.get(i)) {
 					double yNextMovement =  yClose - yOpen  ;
 					g2.setColor(Color.BLACK);
 					Rectangle2D candle = new Rectangle2D.Double(x - singleTimeRange/4-1, yOpen-1, singleTimeRange/2+1, yNextMovement+1);
 					g2.draw(candle);
 					g2.setColor(Color.GREEN);
 					g2.fill3DRect((int)(x - singleTimeRange/4), (int)yOpen, (int)singleTimeRange/2, (int)yNextMovement,true);
 					g2.setColor(Color.BLACK);
 					Rectangle2D line1 = new Rectangle2D.Double(x-1, yOpen-1, 2, yHigh -yOpen+1);
 					g2.draw(line1);
 					Rectangle2D line2 = new Rectangle2D.Double(x-1, yClose-1, 2, yClose - yLow+1);
 					g2.draw(line2);
 					g2.setStroke(new BasicStroke(2.0f));
 					g2.drawLine((int)x, (int)yOpen, (int)x, (int)yHigh);
 					g2.drawLine((int)x, (int)yClose, (int)x, (int)yLow);
 				}
 				else if(open.get(i)< close.get(i)){
 					double yNextMovement =  yOpen -yClose ;
 					g2.setColor(Color.BLACK);
 					Rectangle2D candle = new Rectangle2D.Double(x - singleTimeRange/4-1, yClose-1, singleTimeRange/2+1, yNextMovement+1);
 					g2.draw(candle);
 					g2.setColor(Color.RED);
 					g2.fill3DRect((int)(x - singleTimeRange/4), (int)yClose, (int)singleTimeRange/2, (int)yNextMovement,true);
 					Rectangle2D line1 = new Rectangle2D.Double(x-1, yOpen-1, 2, yHigh -yClose+1);
 					g2.draw(line1);
 					Rectangle2D line2 = new Rectangle2D.Double(x-1, yClose-1, 2, yOpen - yLow+1);
 					g2.draw(line2);
 					g2.setStroke(new BasicStroke(2.0f));
 					g2.drawLine((int)x, (int)yClose, (int)x, (int)yHigh);
 					g2.drawLine((int)x, (int)yOpen, (int)x, (int)yLow);
 				}
 				else {
 					double yNextMovement =  yOpen -yClose ;
 					g2.setColor(Color.gray);
 					g2.fill3DRect((int)(x - singleTimeRange/4), (int)yOpen, (int)singleTimeRange/2, (int)yNextMovement,true);
 					g2.setStroke(new BasicStroke(2.0f));
 					g2.drawLine((int)x, (int)yClose, (int)x, (int)yHigh);
 					g2.drawLine((int)x, (int)yOpen, (int)x, (int)yLow);
 				}
 			}
 		}
 		
}
	
	
	
	/*
	 * this function is aimed at drawing the final date's close value, show the range of this value compared
	 * with the close value which is one day before the final date, and show a triangle for indicating that 
	 * whether this stock drops or grows.
	 */
	public void addNewestData (String rangeString) {
		
		
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Papyrus", Font.BOLD, 25));
		g2.drawString("Close ",5,60 );
		g2.drawString(rangeString+"%", 5, 120);
		if(open.get(open.size()-1)> close.get(close.size()-1)) {
			g2.setColor(Color.GREEN);
			g2.setFont(new Font("Papyrus", Font.BOLD, 25));
			g2.drawString(close.get(close.size()-1).toString()+"$", 5, 90);
			g2.fillPolygon(new int[] {80,70,90}, new int[] {120,100,100}, 3);
		}
		else if(open.get(open.size()-1)< close.get(close.size()-1)) {
			g2.setColor(Color.RED);
			g2.setFont(new Font("Papyrus", Font.BOLD, 25));
			g2.drawString(close.get(close.size()-1).toString()+"$", 5, 90);
			g2.fillPolygon(new int[] {80,90,70}, new int[] {100,120,120}, 3);
		}
		else {
			g2.setColor(Color.gray);
			g2.setFont(new Font("Papyrus", Font.BOLD, 25));
			g2.drawString(close.get(close.size()-1).toString()+"$", 5, 90);
		}
		
	}
	
	/*
	 * This function is for drawing the moving circle and line when users move their mouse on the graph, 
	 * i set the mouseMotionListener for this Panel, and only when users move mouse on the data point range
	 * which are a label from top to the bottom around a data point, can it shows tips for users. More Explanations
	 * about the range around the data point and the tips: the range is a JLabel which set the width and height as 
	 * current graph's 0.25 * width and 0.25 * height, so, whenever users' mouse is over or below one data point, this action can
	 * invoke a tip for users, and the tip contains information about open, close, high, low, date and volume. Hence,
	 * users can browse all data they want, once they move their mouse around a data point. It is more convenient and 
	 * accurate than  adding a tip just on each data point. users have to hold the muse around one data point for a short
	 * time, then the tool tip can be showed.
	 *  
	 */
	public void addDataComponents(int x, int y, int xAxis0, int yAxisn, int yAxis0, double minLowStockData, double singleStockValueRange, int singleTimeRange) {
		
		if (xAxis0 + singleTimeRange <x &&  x<= xAxis0 + dateData.size()*singleTimeRange + 0.25*singleTimeRange ) {
					
			int dataIndex = (x - xAxis0)/singleTimeRange -1;
			double yNextMovement = ( close.get(dataIndex) - minLowStockData)* singleStockValueRange ;
 	 		Point dataPoint = new Point((xAxis0 + (dataIndex+1)*singleTimeRange), (int)(yAxis0 -20 - yNextMovement) );
 	 		
				if(dataPoint.x - 0.25*singleTimeRange < x && x <dataPoint.x + 0.25*singleTimeRange  ) {
		//			draw the data point circle
					g2.setColor(new Color(0,191	,255));
		 	 		g2.fillOval(dataPoint.x - 6, dataPoint.y -6 , 12, 12);
		 	 		g2.setColor(new Color(119,136,153));
		 	 		g2.setStroke(new BasicStroke(1.5f));
		 	 		g2.drawLine(dataPoint.x ,yAxisn , dataPoint.x, yAxis0 );
		 	 		
		 	 		String dataInfo = " Close:  "+ close.get(dataIndex).toString()+ "  Open:  "+open.get(dataIndex).toString()+"  Date:  "+dateData.get(dataIndex).toString()+"  High: "+ high.get(dataIndex).toString()+"  Low:  "+low.get(dataIndex).toString()+"  Volume: "+ volume.get(dataIndex).toString();
					tipLabel.setBounds((int)(dataPoint.x - 0.25*singleTimeRange) ,yAxisn , (int)(0.5*singleTimeRange),(yAxis0 - yAxisn));
					tipLabel.setToolTipText(dataInfo);
//					tipLabel.add(dataTips);
					this.add(tipLabel);
		 	 		
	//	 	 		JToolTip dataTips = new JToolTip();
	//	 	 		dataTips.setDefaultLocale(dataPoint.x, dataPoint.y);
	//	 	 		dataTips.setTipText(dataInfo);
	//	 	 		dataTips.setBounds(dataPoint.x, dataPoint.y,50, 30);
	//	 	 		dataTips.setBorder(new LineBorder(Color.blue));
				}
				
				else if( tipLabel.isShowing()== true &&  ((dataPoint.x - 0.5*singleTimeRange) <x && x <(dataPoint.x - 0.25*singleTimeRange)) || ((dataPoint.x + 0.25*singleTimeRange) <x && x< (dataPoint.x + 0.5*singleTimeRange ))) {
	//				remove(dataTips);
	//				updateUI();
	//				dataTips.setEnabled(false);
//					tipLabel.setVisible(false);
//					tipLabel.setEnabled(false);
					remove(tipLabel);
//					
				}
			}
	}
	
	/*
	 * This is a action listener for mouse, if the mouse move on the stock panel, it will
	 * get the coordinate value and set this value to x and y, we will use x and y in drawing data components. 
	 */
	public class PanelMouseListener implements MouseMotionListener{
			
	
		@Override
		public void mouseDragged(MouseEvent e) {
		}
	
		@Override
		public void mouseMoved(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			repaint();
		}
	}	

	/*
	 * This function is for showing a time reference line for users, and this function can draw 
	 * some dot lines and time for users. 
	 */
	public void drawTimeRefLine(int xAxis0,int yAxis0, int yAxisn,int singleTimeRange,int xTickRange) {
		int xTimeTick = (dateData.size())/xTickRange;
		for(int i =0 ; i <xTimeTick; i++) {
			g2.setColor(Color.gray);
			float[] dash = {5,5};
 			BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
 			g2.setStroke(bs);
 			g2.drawLine((xAxis0+ singleTimeRange + i*xTickRange *singleTimeRange) , yAxis0, (xAxis0+ singleTimeRange) + (i*xTickRange *singleTimeRange), yAxisn );
			g2.drawLine((xAxis0+ singleTimeRange + i*xTickRange *singleTimeRange) , yAxis0, (xAxis0+ singleTimeRange + i*xTickRange *singleTimeRange), (yAxis0+10) );
			String tickDate = month.get((int)(i*xTickRange))+"-"+ day.get((int)(i*xTickRange)) +"-"+ year.get((int)(i*xTickRange));
			g2.setFont(new Font("Papyrus", Font.BOLD, 15));
			g2.drawString(tickDate, (xAxis0+ singleTimeRange + i*xTickRange *singleTimeRange- 25) , (yAxis0+20));
		}
	}
	
	/*
	 * This function is to draw time reference line with judging how long the date is chose and 
	 * implement different time intervals, when the date is really long, the interval will be longer
	 * than short date.
	 */
	public void addTimeRefLine(int xAxis0,int yAxis0, int yAxisn,int singleTimeRange) {
		
		if( 24*20<=dateData.size() && dateData.size()<= 36*20 ) {
			int xTickRange = 4*20;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		else if(  12*20<=dateData.size() && dateData.size()< 24*20 ) {
			int xTickRange = 60;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
			
		}
 		
 		else if(  8*20 <=dateData.size() && dateData.size()< 12*20 ) {
			int xTickRange = 40;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
 		
 		else if(   6*20<= dateData.size()&& dateData.size()< 8*20 ) {
			int xTickRange = 20;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		
		else if ( 3*20 <= dateData.size()&& dateData.size()< 6*20) {
			int xTickRange = 14;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		
		else if ( 2*20 <= dateData.size()&& dateData.size()< 3*20) {
			int xTickRange = 7;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		
		else if( 20<= dateData.size() && dateData.size()< 2*20 ){
			int xTickRange = 5;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		
		else if( 8<= dateData.size() && dateData.size()< 20){
			int xTickRange = 2;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
		
		else {
			int xTickRange = 1;
			this.drawTimeRefLine(xAxis0,yAxis0,yAxisn,singleTimeRange,xTickRange);
		}
	}
	
}
