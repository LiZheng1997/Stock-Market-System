
/*
 * This is a class for creating the range of day, month, year. 
 * I set the configuration of day to 31, month to 12, and year from 2000 
 * to 2100. Later versions can change these configurations here.
 */
public class CreateTimeNumbers {
	
		public String [] createDayNumbers(String [] s){
			for (int i = 0 ; i < 31 ;i++) {
				String day  = Integer.toString(i+1);
				s[i] = day;
			}
			
			return s;
		}
		
		public String [] createMonthNumbers(String [] s){
			for (int i = 0 ; i < 12 ;i++) {
				String month  = Integer.toString(i+1);
				s[i] = month;
				
			}
			return s;
		}
		
		public String [] createYearNumbers(String [] s){
			
			for (int i = 1999 ; i < 2100 ;i++) {
				String year  = Integer.toString(i+1);
				s[i-1999] = year;
			}
			return s;
		}
		
		
}



	
	

