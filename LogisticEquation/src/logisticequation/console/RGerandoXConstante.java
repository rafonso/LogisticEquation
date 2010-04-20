package logisticequation.console;

import static java.math.BigDecimal.ONE;
import static logisticequation.base.QuadraticElement.MATH_CONTEXT;
import static logisticequation.base.QuadraticElement.MAX_R;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticRecorder;
import logisticequation.base.QuadraticGenerator;

public class RGerandoXConstante implements Observer {

	private PrintStream out = System.out;

	@Override
	public void update(Observable observable, Object object) {
		LogisticElement element = (LogisticElement) object;

		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		this.out.printf("%1.10f |", element.getR());
		int[] histogram = element.getHistogram();
		for (int frequency : histogram) {
			if (frequency == 0) {
				this.out.print(' ');
			} else if (frequency == 1) {
				this.out.print('.');
			} else {
				int percent = 10 * frequency / element.getIteractions();
				this.out.print(Utils.chars[percent]);
			}
		}
		this.out.println("|");
	}

	private void gerarLinha(LogisticElement element) {
		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		this.out.printf("%1.10f |", element.getR());
		int[] histogram = element.getHistogram();
		for (int frequency : histogram) {
			if (frequency == 0) {
				this.out.print(' ');
			} else if (frequency == 1) {
				this.out.print('.');
			} else {
				int percent = 10 * frequency / element.getIteractions();
				this.out.print(Utils.chars[percent]);
			}
		}
		this.out.println("|");
	}

	private static Options getOptions() {
		Options options = new Options();

		options.addOption(new LogisticOption("n", true,
				"N�mero de Itera��es por r"));
		options.addOption(new LogisticOption("int", "intervals", true,
				"N�meros de intervalos do r inicial ao r final"));
		options.addOption(new LogisticOption("res", "resolution", true,
				"Resolu��o de 0 at� 1"));
		/*
		 * options.addOption(new Option("d", "dir", true,
		 * "Diret�rio onde ser� salvo o arquivo")); options.addOption(new
		 * Option("p", "percent", true,
		 * "Apos de qual percentual vou pegar os resultados"));
		 * options.addOption(new Option("ri", "rinitial", true,
		 * "Valor inicial de r (default = 0.0)")); options.addOption(new
		 * Option("rf", "rfinal", true, "Valor final de r (default = 4.0)"));
		 */

		return options;
	}

	public void execute(final int n, final int intervals, int resolution) {
		BigDecimal r = ONE;
		BigDecimal step = MAX_R.subtract(ONE).divide(new BigDecimal(intervals),
				MATH_CONTEXT);

		QuadraticGenerator quadraticGenerator = new QuadraticGenerator();
		for (int i = 0; i < intervals; i++) {
			BigDecimal x0 = r.subtract(ONE, MATH_CONTEXT).divide(r,
					MATH_CONTEXT);
			quadraticGenerator.getObservable().deleteObservers();
			LogisticRecorder logisticRecorder = new LogisticRecorder(r, n,
					resolution, x0);
			quadraticGenerator.getObservable().addObserver(logisticRecorder);
			quadraticGenerator.generate(n, x0, r);

			this.gerarLinha(logisticRecorder.getLogisticElement());

			r = r.add(step, MATH_CONTEXT);
		}
	}

	public static void main(String[] args) {
		final Options options = getOptions();
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine commandLine = parser.parse(options, args);

			int n = Integer.parseInt(commandLine.getOptionValue("n"));
			int intervals = Integer.parseInt(commandLine.getOptionValue("int"));
			int resolution = Integer
					.parseInt(commandLine.getOptionValue("res"));

			RGerandoXConstante gerandoXConstante = new RGerandoXConstante();
			gerandoXConstante.execute(n, intervals, resolution);
		} catch (ParseException e) {
			Utils.printUsage("LogisticGraph", options, e);
		}

	}
}
