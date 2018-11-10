import java.util.*;

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
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
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
	 * averageGainLoss finds the average gain and average loss of a given stock
	 * in a given range of days. This is a utility function for calculating RSI
	 *
	 * @param s the stock to do the calculation with
	 * @param start the index of the day to start at
	 * @param end   the index of the day to end at
	 *
	 * @return A list of float values where the gain is at index 0 and the loss at index 1
	 */
	public static List<Float> averageGainLoss(Stock s, int start, int end){
		float gain = 0;
		float loss = 0;
		for(;start < end; start++){
			float change = s.data.get(start).close - s.data.get(start).open;
			if(change > 0){
				gain += change;
			}
			else{
				loss -= change;
			}
		}
		gain = gain / (end - start);
		loss = loss / (end - start);
		return new ArrayList<Float>(Arrays.asList(gain, loss));
	}

	/**
	 * rsi_range finds the rsi for a given day range
	 *
	 * @param s the stock to do the calculations on 
	 * @param span the day range to do the calculations on
	 *
	 * @return A List of float values representing the RSI for each day.
	 */
	public static List<Float> rsi_range(Stock s, int span){
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		List<List<Float>> gains = new ArrayList<List<Float>>(s.data.size() - span);
		int begin = 0;
		for(int i = span; i < s.data.size(); i++){
			if(begin > 0){
				float change = s.data.get(i).close - s.data.get(i).open;
				float g = change > 0 ? change : 0;
				float l = change < 0 ? change : 0;
				gains.add(begin, averageGainLoss(s, begin, i));
				float smoothedUp = (((gains.get(begin - 1).get(0) * (span - 1)) + g) / span);
				float smoothedDown = (((gains.get(begin - 1).get(1) * (span - 1)) + l) / span);
				float rs = smoothedUp / smoothedDown;
				values.add(begin, (100 - (100/(1 + rs))));
			}
			else{
				List<Float> UD = averageGainLoss(s, begin, i);
				float rs = UD.get(0) / UD.get(1);
				gains.add(begin, UD);
				values.add(begin, (100 - (100/(1 + rs))));
			}
			begin++;

		}
		return values;
	}
	
	/**
	 * rsi returns the default rsi which is calculated with day range of 14 
	 *
	 * @param s the stock to do the calculations on 
	 * @return A List of Float values representing the RSI of each day
	 */
	public static List<Float> rsi(Stock s) {
		return rsi_range(s, 14);
	}
	

	/**
	 * highestHighLowestLow finds the highest high value and lowest low value in a 
	 * given range of days for a given stock
	 *
	 * @param s The stock to do the calculations with
	 * @param start the first day in the range
	 * @param end the last day in the range
	 *
	 * @return a list of floats where index 0 holds the high, and index 1 the low
	 */
	public static List<Float> highestHighLowestLow(Stock s, int start, int end){
		float high = 0;
		float low = 0;
		for(;start < end; start++){
			high = high < s.data.get(start).high ? s.data.get(start).high : high;
			low = low < s.data.get(start).low ? s.data.get(start).low : low;
		}

		return new ArrayList<Float>(Arrays.asList(high, low));
	}

	/**
	 * stochastic oscillator calculation at a given range of days
	 * 
	 * @param s The stock to do the calculations with
	 * @param span the range of days to do the calculation with
	 *
	 * @return The List of float values representing the SO at each day
	 */
	public static List<Float> so_range(Stock s, int span){
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		int begin = 0;
		for(int i = span; i < s.data.size(); i++){
			List<Float> hl = highestHighLowestLow(s, begin, i);
			values.add(begin, (s.data.get(i).close - hl.get(1)) / (hl.get(0) - hl.get(1)) * 100);
			begin++;

		}
		return values;
	}

	/**
	 * stochastic oscillator calculation at default range of days which is 14
	 * 
	 * @param s The stock to do the calculations with
	 *
	 * @return The List of float values representing the SO at each day
	 */
	public static List<Float> so(Stock s) {
		return so_range(s, 14);
	}
}
