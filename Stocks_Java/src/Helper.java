import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;  

class StockPricePrediction
{
	public float Pro_IncStock_Given_IncEma_IncSMA;
	public float Pro_NotIncStock_Given_IncEma_IncSMA;
	public boolean isStockInc;
	
	public StockPricePrediction(float p1, float p2)
	{
		Pro_IncStock_Given_IncEma_IncSMA = p1;
		Pro_NotIncStock_Given_IncEma_IncSMA = p2;
		isStockInc = (p1 > p2);
	}
}

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
		String dateStr = (isSeq?"Seq Report (":"Smp Report (") + (new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())) + ")";
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
			fw.append(COMMA_DELIMITER);
			fw.append("Prediction on Stock Behavior Given that SMA and EMA are increased");
			fw.append(NEW_LINE_SEPARATOR);

			// Need to fix the inconsistent record size
			int record_size = sma.size();
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
				
				if (i == 0)
				{
					fw.append(COMMA_DELIMITER);
					StockPricePrediction prediction = this.NaiveBayesPrediction(
						(float)0.01, 
						sma, 
						ema,
						s,
						span
					);
					
					String input_pre = (prediction.isStockInc?"Increase":"Not Increase") + 
					"(" + String.valueOf(prediction.Pro_IncStock_Given_IncEma_IncSMA) + ")";
										
					fw.append(input_pre);
				}
				
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
	
	public boolean isHigher(float stockIR, float prePrice, float curPrice)
	{
		float IR = (curPrice - prePrice) / prePrice;
		return (IR >= stockIR);
	}
	
	public StockPricePrediction NaiveBayesPrediction(
		float stockIR, 		
		List<Float> sma,
		List<Float> ema,
		Stock s,
		int span
	)
	{
		float totalRecords = 0;
		float StockInc = 0;
		float StockNotInc = 0;
		
		float SMAInc = 0;
		float SMAInc_StockInc = 0;
		float SMAInc_StockNotInc = 0;
		
		float EMAInc = 0;
		float EMAInc_StockInc = 0;
		float EMAInc_StockNotInc = 0;
		
		for (int i = 0; i < (sma.size() - 1); i++)
		{
			// number if records
			totalRecords++;
			
			if (  
				isHigher(
					stockIR, 
					s.data.get(i).close, 
					s.data.get(i+span).close
				)
			)
			{
				StockInc++;
				
				if (sma.get(i+1) > sma.get(i))
				{
					SMAInc++;
					SMAInc_StockInc++;
				}
				
				if (ema.get(i+1) > ema.get(i))
				{
					EMAInc++;
					EMAInc_StockInc++;
				}
			}
			else
			{
				StockNotInc++;
				
				if (sma.get(i+1) > sma.get(i))
				{
					SMAInc++;
					SMAInc_StockNotInc++;
				}
				
				if (ema.get(i+1) > ema.get(i))
				{
					EMAInc++;
					EMAInc_StockNotInc++;
				}
			}
		}
		
		float Pro_Inc_SMA = SMAInc / totalRecords;
		float Pro_Inc_EMA = EMAInc / totalRecords;
		
		float Pro_Inc_Stock = StockInc / totalRecords;
		float Pro_NotInc_Stock = StockNotInc/ totalRecords;
		
		float Pro_IncSma_IncStock = SMAInc_StockInc / SMAInc;
		float Pro_IncEma_IncStock = EMAInc_StockInc / SMAInc;
		
		float Pro_IncSma_NotIncStock = SMAInc_StockNotInc / SMAInc;
		float Pro_IncEma_NotIncStock = EMAInc_StockNotInc / SMAInc;	
		
		float Pro_IncStock_Given_IncEma_IncSMA = 
			Pro_IncSma_IncStock * Pro_IncEma_IncStock * Pro_Inc_Stock / (Pro_Inc_SMA * Pro_Inc_EMA);
			
		float Pro_NotIncStock_Given_IncEma_IncSMA = 
			Pro_IncSma_NotIncStock * Pro_IncEma_NotIncStock * Pro_NotInc_Stock / (Pro_Inc_SMA * Pro_Inc_EMA);
			
		/*
		System.out.printf(
			"Total Size:%.3f, Stock Inc:%.3f, Stock Not Inc:%.3f\n" + 
			"Total inc SMA:%.3f, inc sma with SI:%.3f, inc sma with SD:%.3f\n" + 
			"Total inc EMA:%.3f, inc ema with SI:%.3f, inc ema with SD:%.3f\n" +
			"Probility of increasing stock given sma and ema is increasing %.3f\n" + 
			"Probility of not increasing stock given sma and ema is increasing %.3f\n",
			totalRecords, StockInc, StockNotInc,
			SMAInc, SMAInc_StockInc, SMAInc_StockNotInc,
			EMAInc, EMAInc_StockInc, EMAInc_StockNotInc,
			Pro_IncStock_Given_IncEma_IncSMA,
			Pro_NotIncStock_Given_IncEma_IncSMA
		); */
		
		StockPricePrediction rtnVal = 
			new StockPricePrediction(Pro_IncStock_Given_IncEma_IncSMA, Pro_NotIncStock_Given_IncEma_IncSMA);
		
		return rtnVal;
	}
}