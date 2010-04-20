package logisticequation.console;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticGenerator;

public class LogisticGraphComplete implements Observer {

	private static char[] chars = {'.' , '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	
	private final PrintStream out;
	
	public LogisticGraphComplete(PrintStream out) {
		this.out = out;
	}
	
	public LogisticGraphComplete() {
		this(System.out);
	}
	
	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;
		
		if(element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}
		
		final BigDecimal media = element.getMedia();
		final BigDecimal deviation = element.getDeviation(media);
		int mediaPosition = element.valueToHistogramPosition(media);
		int intDeviation = element.valueToHistogramPosition(deviation);
		int begin = mediaPosition - intDeviation;
		int end = mediaPosition + intDeviation;
		
		this.out.printf("%1.10f |", element.getR());
		int[] histogram = element.getHistogram();
		for(int i = 0; i < histogram.length; i ++) {
			if(i == mediaPosition) {
				System.out.print('x');
			} else if (i == begin) {
				System.out.print('<');
			} else if (i == end) {
				System.out.print('>');
			} else {
				int frequency = histogram[i];
				if(frequency > 0) {
					int percent = 10 * frequency / element.getIteractions();
					this.out.print(chars[percent]);
				} else {
					this.out.print(' ');
				}
			}
		}
		this.out.println("|");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		BigDecimal x0 = new BigDecimal(args[1]);
		int intervals = Integer.parseInt(args[2]);
		int resolution = Integer.parseInt(args[3]);
		
		
		LogisticGenerator generator = new LogisticGenerator();
		generator.getObservable().addObserver(new LogisticGraphComplete());
		generator.generate(n, x0, intervals, resolution);
	}

}
