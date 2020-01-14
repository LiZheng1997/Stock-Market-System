import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * this is a class for drawing graphs, and these are components and layout 
 * for this frame.
 */
public class StockMarketDataFrame extends JFrame{

	public StockMarketDataFrame(String aTicketSymbolCode ,ArrayList<Double> aClose, ArrayList<Double> aOpen,ArrayList<String> aDate, ArrayList<Double> aHigh,  ArrayList<Double> aLow,  ArrayList<Double> aVolume,ArrayList<String> aMonth, ArrayList<String> aDay, ArrayList<String> aYear) {
		
		//i use border layout for this frame.
		setTitle("Stock Market Data System");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width*3/5,dim.height);
		setLocation(dim.width/5,0);
		setLayout(new BorderLayout());
		
		/*
		 * These are configurations for the top panel which contains different 
		 * components, like Labels for ticket symbols, high, low, and close stock
		 * values.
		 */
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, this.getWidth(), this.getHeight()/5);
		topPanel.setLayout(new GridLayout(1, 5));
		
		JLabel ticketSymbolLabel = new JLabel(aTicketSymbolCode,SwingConstants.CENTER);
		ticketSymbolLabel.setFont(new Font("Papyrus", Font.BOLD, 30));
		ticketSymbolLabel.setBounds(0, 0,150, 150);
		
		JLabel openDataLabel = new JLabel("Open: "+aOpen.get(aOpen.size()-1).toString()+"$",SwingConstants.CENTER );
		openDataLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
		openDataLabel.setBounds(0, 0,150, 150);
		
		JLabel highDataLabel = new JLabel("High: "+aHigh.get(aHigh.size()-1).toString()+"$",SwingConstants.CENTER );
		highDataLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
		highDataLabel.setBounds(0, 0,150, 150);
		
		JLabel lowDataLabel = new JLabel("Low: "+aLow.get(aLow.size()-1).toString()+"$",SwingConstants.CENTER );
		lowDataLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
		lowDataLabel.setBounds(0, 0,150, 150);
		topPanel.add(ticketSymbolLabel);
		topPanel.add(openDataLabel);
		topPanel.add(highDataLabel);
		topPanel.add(lowDataLabel);
		
		Container contentPanel = this.getContentPane();
		//initiate the stock data panel in this frame, and set the configurations for StockDataPanel class.
		StockDataPanel stockPanel =  new StockDataPanel(aClose, aOpen, aDate, aHigh, aLow, aVolume,aMonth,aDay,aYear);
		stockPanel.setLocation(0, this.getHeight()/5);
		stockPanel.setSize(this.getWidth(), this.getHeight()*4/5);
		Color white = new Color(255,255,255);
		stockPanel.setBackground(white);
		contentPanel.add(stockPanel,BorderLayout.CENTER);
		contentPanel.add(topPanel,BorderLayout.NORTH);
		
	}
		
		
		
}
