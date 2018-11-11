import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import java.util.ArrayList;

public class MainSequential extends Task {
	
	public String Li_Personal = "../stock_database/";
	public String James_Tardis = "/home/stu10/s5/jef1771/Courses/CS654/stocks/StockMarketAnalysis/Stocks_Java/stock_database/A.txt";
	public String James_Personal = "/Users/Yamie/Desktop/CS654/Stock_Analytics/StockMarketAnalysis/Stocks_Java/stock_database/A.txt";

	public void main(String[] arg0) throws Exception 
	{	
		if (arg0.length < 2)
		{
			terminate(1);
		}
		
		Helper helper = new Helper();
	
		// Argument 0 is the file name
		ArrayList<String> symbolNames = helper.readInputFile(arg0[0]);
		int spanSize = Integer.parseInt(arg0[1]);
	
		for (int i = 0; i < symbolNames.size(); i++)
		{
			Stock s = new Stock(symbolNames.get(i), Li_Personal+symbolNames.get(i));
			if(s.data.size() < 10){
				//System.out.println(s.sym);
				continue;
			}
			helper.writeReportToCSV(
				true,
				symbolNames.get(i),
				Calculations.sma(s, spanSize),
				Calculations.ema(s, spanSize),
				Calculations.so_range(s, spanSize),
				Calculations.rsi_range(s, spanSize),
				s,
				spanSize
			);
			
			if (i == 0)
			{
				helper.NaiveBayesPrediction(
					(float)0.01, 
					Calculations.sma(s, spanSize), 
					Calculations.ema(s, spanSize),
					s,
					spanSize
				);
			}
		}
	}
	
	/**
	 * requires one core for this task.
	 */
	protected static int coresRequired() {
		return 1;
	}
	
}
