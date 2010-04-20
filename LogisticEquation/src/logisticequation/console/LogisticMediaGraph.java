package logisticequation.console;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticGenerator;

public class LogisticMediaGraph implements Observer {

	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;

		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		final BigDecimal media = element.getMedia();
		final BigDecimal deviation = element.getDeviation(media);
		int mediaPosition = element.valueToHistogramPosition(media);
		int intDeviation = element.valueToHistogramPosition(deviation);
		int begin = mediaPosition - intDeviation;
		int end = mediaPosition + intDeviation;

		System.out.printf("%1.5f |", element.getR());
		for (int i = 0; i < element.getPositions(); i++) {
			if(i == mediaPosition) {
				System.out.print('x');
			} else if (i == begin) {
				System.out.print('<');
			} else if (i == end) {
				System.out.print('>');
			} else {
				System.out.print(' ');
			}
		}
		System.out.println("|");
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
		generator.getObservable().addObserver(new LogisticMediaGraph());
		generator.generate(n, x0, intervals, resolution);
	}

}
