import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
 * This class is aimed at checking and dealing all data, and dividing them into different
 * pieces, then we can get the data separately.
 */
public class DataEditor {
	private ArrayList<String> date;
	private ArrayList<Double> open;
	private ArrayList<Double> high;
	private ArrayList<Double> low;
	private ArrayList<Double> close;
	private ArrayList<Double> volume;
	private ArrayList<String> month;
	private ArrayList<String> day;
	private ArrayList<String> year;
	
	public 	DataEditor () {
		date = new ArrayList<String>();
		open= new ArrayList<Double>();
		high = new ArrayList<Double>();
		low = new ArrayList<Double>();
		close = new ArrayList<Double>();
		volume = new ArrayList<Double>();
		month = new ArrayList<String>();
		day = new ArrayList<String>();
		year = new ArrayList<String>();
	}
	
	public void dataEditor(String dataLine) {
		
		
//		String [] removeTitle = {"Date", "Open", "High", "Low", "Close", "Volume"};
		String [] allData = dataLine.split(", ");
		ArrayList<String> allDataElements = new ArrayList<String>(Arrays.asList(allData));
//		allDataElements.remove(removeTitle);
		Iterator<String> it = allDataElements.iterator();
		
		
		while(it.hasNext()) {
			String dateString = it.next();
			date.add(dateString);
			
			Double openValue = Double.parseDouble(it.next());
			open.add(openValue);
			
			Double highValue = Double.parseDouble(it.next());
			high.add(highValue);
			
			Double lowValue = Double.parseDouble(it.next());
			low.add(lowValue);
			
			Double closeValue = Double.parseDouble(it.next());
			close.add(closeValue);
			
			Double volumeData = Double.parseDouble(it.next());
			volume.add(volumeData);
			
		}
		
		Iterator<String> itDate = date.iterator();
		while(itDate.hasNext()) {
			String [] dateElements = itDate.next().split("/");
			
			String aMonth = dateElements[0];
			month.add(aMonth);
			String aDay =  dateElements[1];
			day.add(aDay);
			String aYear = dateElements[2];
			year.add(aYear);
		}
	}



	public ArrayList<String> getMonth() {
		return month;
	}

	public ArrayList<String> getDay() {
		return day;
	}

	public ArrayList<String> getYear() {
		return year;
	}

	public ArrayList<String> getDate() {
		return date;
	}



	public ArrayList<Double> getOpen() {
		return open;
	}



	public ArrayList<Double> getHigh() {
		return high;
	}



	public ArrayList<Double> getLow() {
		return low;
	}



	public ArrayList<Double> getClose() {
		return close;
	}



	public ArrayList<Double> getVolume() {
		return volume;
	}
	
	public static void main(String[] args) {
		
	
		
		
	}
}
