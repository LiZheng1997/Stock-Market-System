import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * This class is aimed at getting the URL after the web site redirects.
 */
public class GetRealURL {

	public String getRealURL (String originalURL) throws Exception, IOException {
		
		HttpURLConnection httpConnect = (HttpURLConnection) new URL(originalURL).openConnection();
		httpConnect.setInstanceFollowRedirects(false);
		httpConnect.setConnectTimeout(3000);
		return httpConnect.getHeaderField("Location");
		
	}
}
