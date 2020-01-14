import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**@author ZhengLi
 * This class's function is to construct the Frame of the GUI, painting 
 * several main components.
 * 
 */
public class MarketJFrame extends JFrame {
	
	/*
	 * These are global variables for JCOmboBox,
	 * different variables represent for different meanings,
	 * such as, you can see tickSymbolBox stands for a drop down
	 * component for users to select the ticket symbol.
	 */
	private JComboBox<String> ticketSymbolBox;
	private JComboBox<String> startDaySymbolBox;
	private JComboBox<String> startMonthSymbolBox;
	private JComboBox<String> StartYearSymbolBox;
	private JComboBox<String> endDaySymbolBox;
	private JComboBox<String> endMonthSymbolBox;
	private JComboBox<String> endYearSymbolBox;
	private JTextField rowsText;
	
	/*
	 * These are global variables for different Strings which 
	 * are useful for integrating the startDate and endDate and 
	 * provide values for the button actionListener to get the 
	 * value users selected from the JComboBoxs.
	 */
	private String ticketSymbolCode;
	private String startMonth;
	private String startDay;
	private String startYear;
	private String endMonth;
	private String endDay;
	private String endYear;
	private String rows;
	private ArrayList<String> month;
	private ArrayList<String> day;
	private ArrayList<String> year;
	private int index;
	/*
	 * These are global variables for different data Arrays which 
	 * are useful for drawing data graph.
	 */
	private ArrayList<String> date;
	private ArrayList<Double> open;
	private ArrayList<Double> high;
	private ArrayList<Double> low;
	private ArrayList<Double> close;
	private ArrayList<Double> volume;
	
	/*
	 * These are configurations for some components in the main JFrame, do not
	 * change any configurations here. 
	 */
		public MarketJFrame() {
			
			/*
			 * these are some important configurations about the software, 
			 * do not change the settings.
			 */
			setResizable(false);
			setLayout(null);
			setTitle("Stock Market Data System");
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			int frameWidth = dim.width/2+250;
			int	frameHeight =dim.height*4/5; 
			setPreferredSize(new Dimension(frameWidth ,frameHeight));
			setLocation(new Point(dim.width/5, dim.height/10));
			Container contentPanel = this.getContentPane();
			contentPanel.setLayout(new GridLayout(5,1,100,100));
			/*
			 * These are panels for title, start date, end date, rows and
			 * ticket symbols.
			 */
			JPanel titlePanel = new JPanel();
			JPanel startDatePanel = new JPanel(new GridLayout(1,6));
			JPanel EndDatePanel = new JPanel(new GridLayout(1,6));
			JPanel rowsPanel = new JPanel();
			JPanel tsPanel = new JPanel();
			
//			A label telling users to input their rows.
			JLabel rows = new JLabel("Input rows you want",SwingConstants.RIGHT);
			rows.setFont(new Font("Papyrus", Font.BOLD, 25));
			rowsPanel.add(rows);
			rowsText = new JTextField("200");
//			adding a listener for this text field, this can avoid users typing wrong
//			format strings.
			rowsText.addKeyListener((new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char inputChar = e.getKeyChar();
					if(Character.isDigit(inputChar)) {
						return;
					}
					else
						e.consume();
				}
			}));
			Dimension textDimension = new Dimension(100, 50);
			rowsText.setPreferredSize(textDimension);
			rowsText.setFont(new Font("Papyrus", Font.BOLD, 25) );
			rowsText.setToolTipText("Please input numbers.");
			rowsPanel.add(rowsText);
			
//			these are labels and JComboBoxs for ticket symbol and day, month, year.
			JLabel title = new JLabel("Welcome to Stock Market System.", SwingConstants.CENTER);
			title.setFont(new Font("Papyrus", Font.BOLD, 30));
			titlePanel.add(title);
			
			/*
			 * The label is ticket symbol, i set it to right position, two spaces are 
			 * better to have a good look.
			 * using JComBox<String> to store and present the days for users
			 * to select. The model of the ticket is not completed.
			 */
			JLabel ticketLabel = new JLabel("Ticket Symbol  ", SwingConstants.RIGHT );
			ticketLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			tsPanel.add(ticketLabel);
			String [] symbols = new String [10]; 
			CreateTicketSymbols cts = new CreateTicketSymbols();
			ticketSymbolBox= new JComboBox<String>(cts.createTicketSymbols(symbols));
			ticketSymbolBox.setEditable(true);
			ticketSymbolBox.setToolTipText("If you do not find the symbol you want, please type here.");
//			ticketSymbolBox.setSize(150, 150);
			ticketSymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25)  );
			tsPanel.add(ticketSymbolBox);
			
			/*
			 * Using JButton to create a button for the users to click it for 
			 * finishing their choices and after this button is clicked, it will
			 * lead to another GUI which will show users the picture of the data
			 * they selected.
			 */
			JButton clickToSearch = new JButton(" Search");
			clickToSearch.setFont(new Font("Papyrus", Font.BOLD, 25));
			clickToSearch.addActionListener(new SearchButtonAction());
			tsPanel.add(clickToSearch);
			
			/*
			 * The label is the start day, i set it to right position, two spaces are 
			 * better to have a good look.
			 * using JComBox<String> to store and present the days for users
			 * to select.
			 */
			String day [] = new String [31];
			CreateTimeNumbers ctnDay = new CreateTimeNumbers();
			JLabel startDayLabel = new JLabel("Start Day  ", SwingConstants.RIGHT);
			startDayLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(startDayLabel);
			startDaySymbolBox= new JComboBox<String>(ctnDay.createDayNumbers(day));
			startDaySymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(startDaySymbolBox);
			
			/*
			 * The label is the start month, i set it to right position, two spaces are 
			 * better to have a good look.
			 *  using JComBox<String> to store and present the months for users
			 * to select.
			 */
			String month [] = new String [12];
			CreateTimeNumbers ctnMonth = new CreateTimeNumbers();
			JLabel startMonthLabel = new JLabel("Start Month  ", SwingConstants.RIGHT);
			startMonthLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(startMonthLabel);
			startMonthSymbolBox= new JComboBox<String>(ctnMonth.createMonthNumbers(month));
			startMonthSymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(startMonthSymbolBox);
			
			
			/*
			 * The label is start year, i set it to right position, two spaces are 
			 * better to have a good look.
			 *  using JComBox<String> to store and present the months for users
			 * to select
			 */
			String year [] = new String [101];
			CreateTimeNumbers ctnYear = new CreateTimeNumbers();
			JLabel startYearLabel = new JLabel("Start Year  ", SwingConstants.RIGHT);
			startYearLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(startYearLabel);
			StartYearSymbolBox= new JComboBox<String>(ctnYear.createYearNumbers(year));
			StartYearSymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			startDatePanel.add(StartYearSymbolBox);
			
			
			/*
			 * The label is the end day, i set it to right position, two spaces are 
			 * better to have a good look.
			 * using JComBox<String> to store and present the days for users
			 * to select.
			 */
			JLabel endDayLabel = new JLabel("End Day  ", SwingConstants.RIGHT);
			endDayLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endDayLabel);
			endDaySymbolBox= new JComboBox<String>(ctnDay.createDayNumbers(day));
			endDaySymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endDaySymbolBox);
			
			/*
			 * The label is the end month, i set it to right position, two spaces are 
			 * better to have a good look.
			 *  using JComBox<String> to store and present the months for users
			 * to select.
			 */
			JLabel endMonthLabel = new JLabel("End Month  ", SwingConstants.RIGHT);
			endMonthLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endMonthLabel);
			endMonthSymbolBox= new JComboBox<String>(ctnMonth.createMonthNumbers(month));
			endMonthSymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endMonthSymbolBox);
			
			
			/*
			 * The label is the end year, i set it to right position, two spaces are 
			 * better to have a good look.
			 *  using JComBox<String> to store and present the months for users
			 * to select
			 */
			JLabel endYearLabel = new JLabel("End Year  ", SwingConstants.RIGHT);
			endYearLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endYearLabel);
			endYearSymbolBox= new JComboBox<String>(ctnYear.createYearNumbers(year));
			endYearSymbolBox.setFont(new Font("Papyrus", Font.BOLD, 25));
			EndDatePanel.add(endYearSymbolBox);
			
			//Setting the background for all panels.
			Color bgColor = new Color(176,196,222);
			contentPanel.setBackground(bgColor);
			startDatePanel.setBackground(bgColor);
			EndDatePanel.setBackground(bgColor);
			rowsPanel.setBackground(bgColor);
			tsPanel.setBackground(bgColor);
			titlePanel.setBackground(bgColor);
			//adding all panels to the contentPanel.
			contentPanel.add(titlePanel);
			contentPanel.add(startDatePanel);
			contentPanel.add(EndDatePanel);
			contentPanel.add(rowsPanel);
			contentPanel.add(tsPanel);
			pack();
			
		}
		
		/*
		 * This is a class for the search button, i set a action listener for
		 * this search button, there will collecting all data to another class for 
		 * drawing graphs.
		 */
		private class SearchButtonAction implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*
				 * These following codes are getting the selected item after users 
				 * choose one item for month, day, year.
				 */
				String choosedTS = (String) ticketSymbolBox.getSelectedItem();
				ticketSymbolCode = choosedTS;
				
				String choosedStartDay = (String) startDaySymbolBox.getSelectedItem();
				startDay = choosedStartDay;
				
				String choosedStartMonth = (String) startMonthSymbolBox.getSelectedItem();
				startMonth = choosedStartMonth;
				
				String choosedStartYear = (String) StartYearSymbolBox.getSelectedItem();
				startYear = choosedStartYear;
				
				String choosedEndDay = (String) endDaySymbolBox.getSelectedItem();
				endDay = choosedEndDay;
				
				String choosedEndMonth = (String) endMonthSymbolBox.getSelectedItem();
				endMonth = choosedEndMonth;
				
				String choosedEndYear = (String) endYearSymbolBox.getSelectedItem();
				endYear = choosedEndYear;
				
				String choosedRows = (String)rowsText.getText();
				rows = choosedRows;
				
				//This judgment is aimed at judging whether users' choice of date is abnormal.
				if(!rows.equals("")){
					if( 0<Integer.parseInt(rows)&&Integer.parseInt(rows)<= 36*20) {
						if(Integer.parseInt(startYear) > Integer.parseInt(endYear)) {
							JOptionPane.showMessageDialog(null, "This date is abnormal!", "Hints", JOptionPane.INFORMATION_MESSAGE);	
						}
						else if(Integer.parseInt(startYear) == Integer.parseInt(endYear)){ 
							
							if( Integer.parseInt(startMonth) > Integer.parseInt(endMonth)  ) {
								JOptionPane.showMessageDialog(null, "This date is abnormal!", "Hints", JOptionPane.INFORMATION_MESSAGE);			
							}
							else if(Integer.parseInt(startMonth) == Integer.parseInt(endMonth)){
								
								if(Integer.parseInt(startDay) >= Integer.parseInt(endDay)) {
									JOptionPane.showMessageDialog(null, "This date is abnormal!", "Hints", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									this.turnToPanel();
								}
							}
							else {
								this.turnToPanel();
							}
						}
						else {
							this.turnToPanel();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You would better choose rows bwtween 1 and 720!", "Hints", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You would better input Numbers!", "Hints", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			/*
			 * This function is for getting users' choice and then use this choice to combine into
			 * a URL, because of redirecting problem, i write a GetRealURL class to get the URL after
			 * web site redirects, and URLEditor is aimed at integrating the users' choice of date, 
			 * because every URL is made of several strings, so i write this class to combine the users'
			 * date, then we can use the real URL to get data from the web. At last, in this function, 
			 * if data are download successfully, it will turn to another windows for drawing data graph.
			 */
			public void turnToPanel() {
				/*
				 * Integrating  the start date elements and end date elements which users have selected, and the 
				 * dates are divided by the symbol of "/". 
				 */
				String startDate = startMonth+"/"+startDay + "/"+ startYear; 
				String endDate =   endMonth+ "/" + endDay + "/" + endYear ;
				
				String originalURL = new URLEditor(ticketSymbolCode, startDate, endDate,rows).getOriginalURL();
				System.out.println(originalURL);
				
				
				/*
				 * This part is to get the redirecting URL, using the getRealURL method 
				 * in the GetRealURL class. 
				 * 
				 */
				GetRealURL gru = new GetRealURL();
				
					String objectiveURL = null;
					try {
						objectiveURL = gru.getRealURL(originalURL);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					URL original = null;
					try {
						original = new URL(objectiveURL);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					URLConnection connection = null;
					try {
						connection = original.openConnection();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					InputStream inStream = null;
					try {
						inStream = connection.getInputStream();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Scanner in = new Scanner(inStream);
					in.nextLine();//this step is to jump over the title line.
					
					/*
					 * This block is to initiate some variables for Storing the value 
					 * of various data.
					 */
					DataEditor de = new DataEditor();
					close = new ArrayList<Double>();
					open = new ArrayList<Double>();
					date = new ArrayList<String>();
					high = new ArrayList<Double>();
					low = new ArrayList<Double>();
					volume = new ArrayList<Double>();
					month = new ArrayList<String>();
					day = new ArrayList<String>();
					year = new ArrayList<String>();
					//reading all lines of the CSV format data.
					while(in.hasNextLine()) {
						String inputLine = in.nextLine();
						de.dataEditor(inputLine);
					}
					in.close();
					//using DataEditor's method to get all the data which are checked.
					close = de.getClose();
					open = de.getOpen();
					date = de.getDate();
					high = de.getHigh();
					low = de.getLow();
					volume = de.getVolume();
					month = de.getMonth();
					day = de.getDay();
					year = de.getYear();
					//judging whether the data size is zero, if it is ,this means there is no data
					//for users to view, so we will show a hint for users to know.
					if(close.size()==0) {
						JOptionPane.showMessageDialog(null, "There is no data for this date range or" +ticketSymbolCode+ "symbol!", "Hints", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
							Collections.reverse(date);
							Collections.reverse(open);
							Collections.reverse(high);
							Collections.reverse(low);
							Collections.reverse(close);
							Collections.reverse(volume);
							Collections.reverse(month);
							Collections.reverse(day);
							Collections.reverse(year);
							JFrame stockDataFrame = new StockMarketDataFrame(ticketSymbolCode,close, open, date, high, low, volume,month,day,year);
							stockDataFrame.setVisible(true);
					}
					
					
//					this is another way to get the context from the URL
//				try {
//					
//					
//					String inputLine;
//					URL url = new URL(originalURL);
//					System.out.println(url.getContent()); 
////					System.out.println(url);
//					
//					
//					BufferedReader in = new BufferedReader(
//							new InputStreamReader(url.openStream()) );
//					while((inputLine = in.readLine()) != null) {
//						System.out.println(inputLine);
//					}
//					in.close();
//					
//				} catch (MalformedURLException e1) {
//					System.out.println("The url is not correct!");
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					System.out.println("The IO step failed!");
//					e1.printStackTrace();
//				}
//				
	//
				}

		}
		
		
	}