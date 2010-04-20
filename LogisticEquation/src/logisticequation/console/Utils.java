package logisticequation.console;

import java.util.Comparator;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class Utils {

	static char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'*' };

	static String repeatChar(final char c, final int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	static short getNumberDigits(final int n) {
		short size = 0;
		int n1 = n;
		while (n1 > 0) {
			size++;
			n1 /= 10;
		}
		return size;
	}

	public static void printUsage(String commandLine, Options options,
			ParseException pe) {
		if (pe != null) {
			System.err.println(pe);
		}
		
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setOptionComparator(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2) {
				if (o1.isRequired() && !o2.isRequired()) {
					return -1;
				}
				if (!o1.isRequired() && o2.isRequired()) {
					return 1;
				}
				return o1.getOpt().compareToIgnoreCase(o2.getOpt());
			}
		});
		
		helpFormatter.printHelp(commandLine, options, true);
	}
}
