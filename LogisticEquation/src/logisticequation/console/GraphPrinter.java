package logisticequation.console;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import logisticequation.base.QuadraticElement;
import logisticequation.base.QuadraticGenerator;

public class GraphPrinter implements Observer {

	private static final int MAX = 100;
	private final int iSize;

	public GraphPrinter(int n) {
		this.iSize = Utils.getNumberDigits(n);
	}

	private void printColunn(final QuadraticElement element) {
		final int pos = (int) (MAX * element.getY().doubleValue());

		System.out.printf("%0" + iSize + "d |", element.getIteraction());
		if (pos > 0) {
			int espacoInferior = pos - 1;
			int espacoSuperior = MAX - pos;
			System.out.print(Utils.repeatChar(' ', espacoInferior));
			System.out.print('.');
			System.out.print(Utils.repeatChar(' ', espacoSuperior));
		} else {
			System.out.print(Utils.repeatChar(' ', MAX));
		}
		System.out.printf(" | %1.10f%n", element.getY());
	}

	@Override
	public void update(Observable observable, Object object) {
		QuadraticElement element = (QuadraticElement) object;
//		if (element == QuadraticElement.START) {
//		} else if (element == QuadraticElement.END) {
//		} else 
			if (element.getIteraction() > 0) {
			printColunn(element);
		}
	}

	private static Options getOptions() {
		Options options = new Options();

		options.addOption(new LogisticOption("n", true, "N�mero de Itera��es"));
		options.addOption(new LogisticOption("x0", true,"X inicial da itera��o"));
		options.addOption(new LogisticOption("r", true, "Valor de r"));

		return options;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Options options = getOptions();
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine commandLine = parser.parse(options, args);

			int n = Integer.parseInt(commandLine.getOptionValue("n"));
			BigDecimal r = new BigDecimal(commandLine.getOptionValue("r"));
			BigDecimal x0 = new BigDecimal(commandLine.getOptionValue("x0"));
//			BigDecimal x0 = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(r, QuadraticElement.MATH_CONTEXT), QuadraticElement.MATH_CONTEXT);

			QuadraticGenerator generator = QuadraticGenerator.getGenerator(BigDecimal.class);
			generator.getObservable().addObserver(new GraphPrinter(n));
			generator.generate(n, x0, r);
		} catch (ParseException e) {
			Utils.printUsage("GraphPrinter", options, e);
		}
	}

}
