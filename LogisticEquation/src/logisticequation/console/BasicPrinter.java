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

public class BasicPrinter implements Observer {

	@Override
	public void update(Observable observable, Object o) {
		QuadraticElement element = (QuadraticElement) o;

		// if(element == QuadraticElement.START || element ==
		// QuadraticElement.END) {
		// return;
		// }

		System.out.println(element);
	}

	private static Options getOptions() {
		Options options = new Options();

		options.addOption(new LogisticOption("n", true, "Número de Iterações"));
		options.addOption(new LogisticOption("x0", true,
				"X inicial da iteração"));
		options.addOption(new LogisticOption("r", true, "Valor de r"));

		return options;
	}

	public static void main(String[] args) {
		final Options options = getOptions();
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine commandLine = parser.parse(options, args);
			int n = Integer.parseInt(commandLine.getOptionValue("n"));
			BigDecimal x0 = new BigDecimal(commandLine.getOptionValue("x0"));
			BigDecimal r = new BigDecimal(commandLine.getOptionValue("r"));

			QuadraticGenerator generator = QuadraticGenerator.getGenerator(BigDecimal.class);
			generator.getObservable().addObserver(new BasicPrinter());
			generator.generate(n, x0, r);
		} catch (ParseException e) {
			Utils.printUsage("GraphPrinter", options, e);
		}
	}
}
