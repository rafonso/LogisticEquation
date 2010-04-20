package logisticequation.console;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.QuadraticElement;
import logisticequation.base.QuadraticGenerator;

public class TablePrinter implements Observer {

	
	private final int iSize;
	private final String formatLine;
	final String line;

	public TablePrinter(int n) {
		iSize = Utils.getNumberDigits(n);
		this.formatLine = "| %0" + iSize + "d | %1.10f | %1.10f |%n";
		this.line = Utils.repeatChar('-', 2 + this.iSize + 1 + 1 + 1 + 12
				+ 1 + 1 + 1 + 12 + 1 + 1);
	}

	private void printHeader() {
		System.out.println(this.line);
		int s = this.iSize/2;
		System.out.println("|" + Utils.repeatChar(' ', s + 1) + 'i' + Utils.repeatChar(' ', s) + "|       x      |       y      |");
		System.out.println(this.line);
	}

	@Override
	public void update(Observable observable, Object object) {
		QuadraticElement element = (QuadraticElement) object;
//		if (element == QuadraticElement.START) {
//			this.printHeader();
//		} else if (element == QuadraticElement.END) {
//			System.out.println(this.line);
//		} else {
			System.out.printf(this.formatLine, element.getI(), element.getX(),
					element.getY());
//		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		BigDecimal x0 = new BigDecimal(args[1]);
		BigDecimal r = new BigDecimal(args[2]);

		QuadraticGenerator generator = new QuadraticGenerator();
		generator.getObservable().addObserver(new TablePrinter(n));
		generator.generate(n, x0, r);
	}

}
