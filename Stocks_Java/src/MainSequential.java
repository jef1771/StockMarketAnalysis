import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

public class MainSequential extends Task {
	
	public String Li_Tardis = "/home/stu2/s19/zxl3946/Desktop/StockMarketAnalysis/Stocks_Java/stock_database/A.txt";
	public String James_Tardis = "/home/stu10/s5/jef1771/Courses/CS654/stocks/StockMarketAnalysis/Stocks_Java/stock_database/A.txt";
	public String James_Personal = "/Users/Yamie/Desktop/CS654/Stock_Analytics/StockMarketAnalysis/Stocks_Java/stock_database/A.txt";

	public void main(String[] arg0) throws Exception 
	{				
		String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
		System.out.println("Hello World");
		Stock s = new Stock("Sample", James_Personal);
	}
	
	/**
	 * requires one core for this task.
	 */
	protected static int coresRequired() {
		return 1;
	}
	
}
