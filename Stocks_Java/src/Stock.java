import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Stock {
	
	String sym;
	ArrayList<Day_Data> data;
	
	public Stock(String symbol, String fileName) {
		this.loadStockDataFromJson(fileName);
	}
	
	public void loadStockDataFromJson(String fileName)
	{
		try {
			Object obj = new JSONParser().parse(new FileReader("stock_database/A.json"));
			JSONObject jo = (JSONObject) obj; 

			int count = 0;
			
		    // getting address 
	        Map dailyPriceMap = ((Map)jo.get("Time Series (Daily)")); 
	        TreeMap<String, JSONObject> treeMap = new TreeMap<String, JSONObject>(dailyPriceMap); 
	        
	        data = new ArrayList<Day_Data>();
	        
	        // iterating address Map 
	        Iterator<Map.Entry<String, JSONObject>> itr1 = treeMap.entrySet().iterator(); 
	        while (itr1.hasNext()) { 
	            Map.Entry pair = itr1.next(); 
	            JSONObject tmpJson = ((JSONObject)pair.getValue());
	            System.out.println(pair.getKey());
	            data.add(
	            		new Day_Data(
    						Float.parseFloat(tmpJson.get("1. open").toString()),
    						Float.parseFloat(tmpJson.get("2. high").toString()),
    						Float.parseFloat(tmpJson.get("3. low").toString()),
    						Float.parseFloat(tmpJson.get("4. close").toString()),
    						Double.parseDouble(tmpJson.get("5. volume").toString())
	            		)
	            );
	        } 
	        
	        System.out.println(data.size());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// Temp Test main function
	public static void main(String[] args)
	{
		Stock s = new Stock("symbol", "stock_database/A.json");
	}
}
