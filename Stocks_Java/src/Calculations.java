import java.util.ArrayList;
import java.util.List;

public class Calculations {
	
	/**
	 * average	Calculate the average price of the given range
	 * 			this is a helper function for sma and ema
	 * 
	 * @param s		the stock to calculate with
	 * @param start	the start index
	 * @param end	the end index
	 * @return		the average price
	 */
	public static float average(Stock s, int start, int end) {
		float total = 0;
		for(;start < end; start++) {
			total += s.data.get(start).close;
		}
		return total / (end - start);
	}
	
	/**
	 * ema	Calculate the Exponential Moving Average
	 * 		of a given Stock
	 * 
	 * @param s		The stock to calculate with
	 * @param span	The time span in days
	 * @return		The exponential moving average
	 */
	public static List<Float> ema(Stock s, int span) {
		List<Float> values = new ArrayList<Float>();
		int size = s.data.size() - 1;
		
		//EMA multiplier
		float multiplier = (2 / (span + 1));
		
		//Walk through the days starting at the span index
		for(int i = span; i < size - span; i++) {
			if(i == span) {
				values.add(i, average(s, 0, span));
			}
			else {
				values.add(i, ((s.data.get(i).close - values.get(i - 1))
								* multiplier) + values.get(i - 1));
			}
		}
		return values;
	}
	
	
	
	/**
	 * sma	Calculate the Simple Moving Average
	 * 		of a given Stock
	 * 
	 * @param s		The stock to calculate with
	 * @param span	The time span in days
	 * @return		The simple moving average
	 */
	public static List<Float> sma(Stock s, int span) {
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		int size = s.data.size() - 1;
		
		//walk backward through the data and calculate the averages
		for(; size >= span; size--) {
			values.add(size, average(s, size - span, size));
		}
		
		return values;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static float rsi(Stock s) {
		return 0f;
	}
	
	/**
	 * stochastic oscillator
	 * 
	 * @param s
	 * @return
	 */
	public static float so(Stock s) {
		return 0f;
	}
}
