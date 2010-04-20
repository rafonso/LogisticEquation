package logisticequation.console;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticGenerator;
import logisticequation.base.QuadraticElement;

public class LogisticMedia implements Observer {

	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;

		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		final BigDecimal media = element.getMedia();
		final BigDecimal deviation = element.getDeviation(media);
		final BigDecimal percent = deviation.divide(media, QuadraticElement.MATH_CONTEXT);
		System.out.printf("%1.10f | %1.10f | %1.10f | %1.10f | %03d | %03d %n",
				element.getR(), media, deviation, percent, element
						.valueToHistogramPosition(media), element
						.valueToHistogramPosition(deviation));
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
		generator.getObservable().addObserver(new LogisticMedia());
		generator.generate(n, x0, intervals, resolution);
	}

}
