import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Attr;


public class ExtractData {
	
	private static int SCORE_BOX = 0;
	private static int PUBLISH_DATE_BOX = 0;
	private static int TITLE_BOX = 0;
	private static int NU_DOWNLOAD_BOX = 0;
	private static int AGE_RANGE_BOX = 0;

	
	public String readPages(String address, int sCORE_BOX, int pUBLISH_DATE_BOX, int tITLE_BOX
			, int nU_DOWNLOAD_BOX, int aGE_RANGE_BOX) throws Exception
	{
		SCORE_BOX = sCORE_BOX;
		PUBLISH_DATE_BOX = pUBLISH_DATE_BOX;
		TITLE_BOX = tITLE_BOX;
		NU_DOWNLOAD_BOX = nU_DOWNLOAD_BOX;
		AGE_RANGE_BOX = aGE_RANGE_BOX;
		
		String result =readFile(address);
		
		return result;
	}
	
	
	public String readFile(String address) throws Exception
	{
		String result = "";
		
		BufferedReader br = null;

		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			org.w3c.dom.Document docxml = docBuilder.newDocument();
			org.w3c.dom.Element rootElement = docxml.createElement("google");
			docxml.appendChild(rootElement);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(address));

			int count = 0;
			while ((sCurrentLine = br.readLine()) != null)
			{
				// item elements
				org.w3c.dom.Element item = docxml.createElement("Item");
				rootElement.appendChild(item);

				// set attribute to staff element
				Attr attr = docxml.createAttribute("id");
				attr.setValue(count+1+"");
				item.setAttributeNode(attr);
				
				readPageData(sCurrentLine,docxml,item);
				System.out.println(sCurrentLine);
				count ++;
				
				if (count > 19 ) break;
			}
			
			result = "Number of Items::"+count;
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(docxml);
			StreamResult resultxml = new StreamResult(new File("D:\\file123.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, resultxml);

			System.out.println("File saved!");
			

		} 
		catch (IOException | ParserConfigurationException e) 
		{
			e.printStackTrace();
			result = "false";
		} 
		finally
		{
			try
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
				result = "false";
			}
		}
		
		return result;
		
	}


	private Map<String,String> readPageData(String url, org.w3c.dom.Document docxml
			, org.w3c.dom.Element item2) {
		
		Document doc;
		Map<String,String> list = new HashMap<String,String>();
		try 
		{
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			doc = Jsoup.connect(url).timeout(5000).get();
			
			
			System.out.println(url);
			//System.out.println(doc);
			String score = doc.select("div.score").first().text();
			org.w3c.dom.Element scorexml = docxml.createElement("score");
			scorexml.appendChild(docxml.createTextNode(doc.select("div.score").first().text()));
			item2.appendChild(scorexml);
			
			String title = doc.select("title").first().text();
			org.w3c.dom.Element titlexml = docxml.createElement("title");
			titlexml.appendChild(docxml.createTextNode(title));
			item2.appendChild(titlexml);
			
			
				Elements items = doc.select("div.content");
				
				for ( Element item: items)
				{
				
					if ( item.select("[itemprop=numDownloads]").text() != null 
							&& !item.select("[itemprop=numDownloads]").text().equals(""))
					{
						String numDownloads =item.select("[itemprop=numDownloads]").text();
						org.w3c.dom.Element numDownloadsxml = docxml.createElement("numDownloads");
						numDownloadsxml.appendChild(docxml.createTextNode(numDownloads));
						item2.appendChild(numDownloadsxml);
						
					}
					
					if ( item.select("[itempropdatePublished]").text() != null 
							&& !item.select("[itemprop=datePublished]").text().equals(""))
					{
						String datePublished=item.select("[itemprop=datePublished]").text();
						org.w3c.dom.Element datePublishedxml = docxml.createElement("datePublished");
						datePublishedxml.appendChild(docxml.createTextNode(datePublished));
						item2.appendChild(datePublishedxml);
						
					}
					
					if ( item.select("[itemprop=ageRange]").text() != null 
							&& !item.select("[itemprop=ageRange]").text().equals(""))
					{
						String ageRange= item.select("[itemprop=ageRange]").text();
						org.w3c.dom.Element ageRangexml = docxml.createElement("ageRange");
						ageRangexml.appendChild(docxml.createTextNode(ageRange));
						item2.appendChild(ageRangexml);
						
					}
					
					if ( item.select("[itemprop=contentRating]").text() != null 
							&& !item.select("[itemprop=contentRating]").text().equals(""))
					{
						String contentRating= item.select("[itemprop=contentRating]").text();
						org.w3c.dom.Element contentRatingxml = docxml.createElement("contentRating");
						contentRatingxml.appendChild(docxml.createTextNode(contentRating));
						item2.appendChild(contentRatingxml);
						
					}
					
				
				}
			}
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
