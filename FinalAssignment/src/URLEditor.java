/*
 * This is a class for editing the URL users entered, i combine this string 
 * values referring to the format of URL of the web site.
 */
public class URLEditor {
	private String realURL;
	
	public URLEditor(String ticketSymbolCode, String startDate, String endDate, String rows) {
		
			String websiteAddress = "http://quotes.wsj.com/";
			String firstAddress = "/historical-prices/download?MOD_VIEW=page&";
			String secondAddress = "num_rows=";
			String thirdAddress = "&startDate=";
			String forthAddress = "&endDate=";
			realURL = websiteAddress + ticketSymbolCode + firstAddress + secondAddress +rows + thirdAddress + startDate+ forthAddress+ endDate ;
	}
	
	public String getOriginalURL() {
		return realURL;
			
		}
}
	