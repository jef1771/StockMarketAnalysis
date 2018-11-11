import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;  

public class Helper{

	/**
	 * Helper constructor
	 * 
	 */
	public Helper()
	{
		
	}
	
	/**
	 * Populate the report anaylsis based on the input sma, ema, rsi so
	 */	
	public void writeReportToCSV(
		Boolean isSeq,
		String fileName,
		List<Float> sma,
		List<Float> ema,
		List<Float> rsi,
		List<Float> so,
		Stock s,
		int span
	)
	{
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		
		FileWriter fw = null;
		String dateStr = (isSeq?"Seq Report (":"Smp Report (") + java.time.LocalDate.now().toString() + ")";
		File dateFolder = new File("../output_database/" + dateStr);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dateFolder.mkdirs();
		
		try
		{
			fw = new FileWriter("../output_database/"+ dateStr + "/" + fileName + "_report.csv");
			fw.append("From_Date");
			fw.append(COMMA_DELIMITER);
			fw.append("End_Date");
			fw.append(COMMA_DELIMITER);
			fw.append("SMA");
			fw.append(COMMA_DELIMITER);
			fw.append("EMA");
			fw.append(COMMA_DELIMITER);
			fw.append("RSI");
			fw.append(COMMA_DELIMITER);
			fw.append("SO");
			fw.append(NEW_LINE_SEPARATOR);

			// Need to fix the inconsistent record size
			int record_size = sma.size() - 100;
			for (int i = 0; i < record_size; i++)
			{
				fw.append(df.format(s.data.get(i).date));
				fw.append(COMMA_DELIMITER);
				fw.append(df.format(s.data.get(i+span).date));
				fw.append(COMMA_DELIMITER);
				fw.append(String.valueOf(sma.get(i)));
				fw.append(COMMA_DELIMITER);
				fw.append(String.valueOf(ema.get(i)));
				fw.append(COMMA_DELIMITER);
				fw.append(String.valueOf(rsi.get(i)));
				fw.append(COMMA_DELIMITER);
				fw.append(String.valueOf(so.get(i)));
				fw.append(NEW_LINE_SEPARATOR);
			}
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
			try
			{
				fw.flush();
				fw.close();
			}
			catch(IOException e)
			{
				
			}
		}
	}

	/**
	 * read the input file that contains a list of stock symbols
	 * @param string type file path 
	 */	
	public ArrayList<String> readInputFile(String fileName)
	{
		ArrayList<String> rtnList = new ArrayList<String>();
		
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
					rtnList.add(line);
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
		
		return rtnList;
	}
	
	public boolean NaiveBayesPrediction(
		float stockIR, 		
		List<Float> sma,
		List<Float> ema
	)
	{
		int totalRecords = 0;
		int StockInc = 0;
		int StockNotInc = 0;
		int SMAInc = 0;
		int SMAInc_StockInc = 0;
		int EMAInc = 0;
		int EMAInc_StockInc = 0;
		
		return true;
	}
}