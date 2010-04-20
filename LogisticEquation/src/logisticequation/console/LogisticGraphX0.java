package logisticequation.console;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import logisticequation.base.LogisticElement;
import logisticequation.base.LogisticX0Generator;

public class LogisticGraphX0 implements Observer {

	private final PrintStream out;

	public LogisticGraphX0(PrintStream out) {
		this.out = out;
	}

	public LogisticGraphX0() {
		this(System.out);
	}

	private void printLineWithMedia(LogisticElement element) {
		final BigDecimal media = element.getMedia();
		final BigDecimal deviation = element.getDeviation(media);

		final int adjustedMedia = element.valueToHistogramPosition(media);
		final int begin = element.valueToHistogramPosition(media
				.subtract(deviation));
		final int end = element.valueToHistogramPosition(media.add(deviation));

		int[] histogram = element.getHistogram();
		for (int i = 0; i < histogram.length; i++) {

			if (i == adjustedMedia) {
				this.out.print('@');
			} else if (i == begin) {
				this.out.print('<');
			} else if (i == end) {
				this.out.print('>');
			} else {

				int frequency = histogram[i];
				if (frequency == 0) {
					this.out.print(' ');
				} else if (frequency == 1) {
					this.out.print(' ');
				} else {
					int percent = 10 * frequency / element.getIteractions();
					this.out.print(Utils.chars[percent]);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object object) {
		LogisticElement element = (LogisticElement) object;

		if (element == LogisticElement.START || element == LogisticElement.END) {
			return;
		}

		this.out.printf("%1.10f |", element.getX0());
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

		this.out.println('|');
	}

	private static PrintStream getOut(int n, BigDecimal r, int intervals,
			int resolution, String dirName) throws IOException {
		String filename = String.format("%05d-%1.10f-%05d-%05d.txt", n, r,
				intervals, resolution);
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(dir, filename);
		f.createNewFile();
		return new PrintStream(f);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		int n = Integer.parseInt(args[0]);
		BigDecimal r = new BigDecimal(args[1]);
		int intervals = Integer.parseInt(args[2]);
		int resolution = Integer.parseInt(args[3]);
		PrintStream out = (args.length > 4) ? getOut(n, r, intervals,
				resolution, args[4]) : System.out;

		LogisticX0Generator generator = new LogisticX0Generator();
		generator.getObservable().addObserver(new LogisticGraphX0(out));
		generator.generate(n, r, intervals, resolution);
	}

}
