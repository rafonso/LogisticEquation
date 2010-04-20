package logisticequation.console;

import java.math.BigDecimal;
import java.math.MathContext;

import org.apache.commons.cli.Option;

public class LogisticOption extends Option {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1401168942984171692L;

	public LogisticOption(String opt, String description)
			throws IllegalArgumentException {
		super(opt, description);
		super.setRequired(true);
	}

	public LogisticOption(String opt, boolean hasArg, String description)
			throws IllegalArgumentException {
		super(opt, hasArg, description);
		super.setRequired(true);
	}

	public LogisticOption(String opt, String longOpt, boolean hasArg,
			String description) throws IllegalArgumentException {
		super(opt, longOpt, hasArg, description);
		super.setRequired(true);
	}

	public int getIntValue() {
		return Integer.parseInt(super.getValue(), 10);
	}

	public double getDoubleValue() {
		return Double.parseDouble(super.getValue());
	}

	public BigDecimal getDecimalValue() {
		return new BigDecimal(super.getValue());
	}

	public BigDecimal getDecimalValue(MathContext mc) {
		return new BigDecimal(super.getValue(), mc);
	}

	public BigDecimal getDecimalValue(int precision) {
		return this.getDecimalValue(new MathContext(precision));
	}

}
