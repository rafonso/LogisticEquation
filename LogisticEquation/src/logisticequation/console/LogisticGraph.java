package logisticequation.console;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticGenerator;

public class LogisticGraph implements Observer {

	private final PrintStream out;
	private final double percent;

	public LogisticGraph(PrintStream out, double percent) {
		this.out = out;
		this.percent = percent;
	}

	public LogisticGraph() {
		this(System.out, 0.0);
	}

	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;

		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		int begin = (int) (this.percent * element.getIteractions());
		int totalIteractions = element.getIteractions() - begin;

		this.out.printf("%1.10f |", element.getR());
		int[] histogram = element.getHistogram(begin);
		for (int frequency : histogram) {
			if (frequency == 0) {
				this.out.print(' ');
			} else if (frequency == 1) {
				this.out.print('.');
			} else {
				int percent = 10 * frequency / totalIteractions;
				this.out.print(Utils.chars[percent]);
			}
		}
//		this.out.printf("| %1.10f (%1.10f)", element.getMedia(), element.getDeviation());
		this.out.println('|');
		
		
	}

	private static PrintStream getOut(int n, BigDecimal x0, int intervals,
			int resolution, String dirName) throws IOException {
		String filename = String.format("%05d-%1.10f-%05d-%05d.txt", n, x0,
				intervals, resolution);
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(dir, filename);
		f.createNewFile();
		return new PrintStream(f);
	}

	private static Options getOptions() {
		Options options = new Options();

		options.addOption(new LogisticOption("n", true,
				"N�mero de Itera��es por r"));
		options.addOption(new LogisticOption("x0", true,
				"X inicial da itera��o"));
		options.addOption(new LogisticOption("int", "intervals", true,
				"N�meros de intervalos do r inicial ao r final"));
		options.addOption(new LogisticOption("res", "resolution", true,
				"Resolu��o de 0 at� 1"));
		options.addOption(new Option("d", "dir", true,
				"Diret�rio onde ser� salvo o arquivo"));
		options.addOption(new Option("p", "percent", true,
				"Apos de qual percentual vou pegar os resultados"));
		options.addOption(new Option("ri", "rinitial", true,
		"Valor inicial de r (default = 0.0)"));
		options.addOption(new Option("rf", "rfinal", true,
		"Valor final de r (default = 4.0)"));

		return options;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final Options options = getOptions();
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine commandLine = parser.parse(options, args);

			BigDecimal x0 = new BigDecimal(commandLine.getOptionValue("x0"));
			int n = Integer.parseInt(commandLine.getOptionValue("n"));
			int intervals = Integer.parseInt(commandLine.getOptionValue("int"));
			int resolution = Integer
					.parseInt(commandLine.getOptionValue("res"));
			PrintStream out = commandLine.hasOption('f') ? getOut(n, x0,
					intervals, resolution, args[4]) : System.out;
			Double percent = new Double(commandLine.getOptionValue("p", "0"));
			BigDecimal rInitial = new BigDecimal(commandLine.getOptionValue("ri", "0.0"));
			BigDecimal rFinal = new BigDecimal(commandLine.getOptionValue("rf", "4.0"));

			LogisticGenerator generator = new LogisticGenerator();
			generator.getObservable().addObserver(new LogisticGraph(out, percent));
			generator.generate(n, x0, intervals, resolution, rInitial, rFinal);
		} catch (ParseException e) {
			Utils.printUsage("LogisticGraph", options, e);
		}
	}

}
