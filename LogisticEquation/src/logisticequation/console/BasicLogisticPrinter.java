package logisticequation.console;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticGenerator;

public class BasicLogisticPrinter implements Observer {

	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;
		
		if(element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}
		
		System.out.printf("%1.10f => ", element.getR());
		System.out.println(Arrays.toString(element.getHistogram()));
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
		generator.getObservable().addObserver(new BasicLogisticPrinter());
		generator.generate(n, x0, intervals, resolution);
	}

}
