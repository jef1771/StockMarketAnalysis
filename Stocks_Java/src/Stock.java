import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.text.SimpleDateFormat;  


public class Stock {
	
	public String sym;
	public ArrayList<Day_Data> data;
	
	public Stock(String symbol, String fileName) {
		this.data = new ArrayList<Day_Data>();
		this.loadStockDataFromJson(fileName);
	}
	
	public void loadStockDataFromJson(String fileName)
	{
		try {
			
			File f = new File(fileName);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			StringBuffer sb = new StringBuffer();

			while (true) 
			{
				String line = br.readLine();
				if (line == null)
				{
					break;
				}
				else
				{
					String[] tokens = line.split(",");
					data.add(
						new Day_Data(
							new SimpleDateFormat("yyyy-MM-dd").parse(tokens[0]),
							Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3]),
							Float.valueOf(tokens[4]),
							Double.valueOf(tokens[5])
						)
					);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
