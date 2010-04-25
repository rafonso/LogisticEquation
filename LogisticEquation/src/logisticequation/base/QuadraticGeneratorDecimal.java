package logisticequation.base;

import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class QuadraticGeneratorDecimal extends QuadraticGenerator<BigDecimal>{

    @Override
    protected void validateArguments(int maxIteractions, BigDecimal x0, BigDecimal r) throws IllegalArgumentException {
        if (maxIteractions < 1) {
            throw new IllegalArgumentException("Max Iteractions must be greater than 0, not "
                    + maxIteractions);
        }
        if ((x0.compareTo(BigDecimal.ZERO) < 0) || (x0.compareTo(BigDecimal.ONE) > 0)) {
            throw new IllegalArgumentException(
                    "x0 must be between 0 and 1, not " + x0);
        }
        if ((r.compareTo(BigDecimal.ZERO) < 0)
                || (r.compareTo(QuadraticElement.MAX_R) > 0)) {
            throw new IllegalArgumentException(
                    "r must be between 0 and 4, not " + r);
        }
    }

    @Override
    protected QuadraticElement<BigDecimal> getElementFrom(int maxIteractions, int iteraction, BigDecimal x, BigDecimal y, BigDecimal r) {
        return new QuadraticElementDecimal(maxIteractions, iteraction, x, y, r);
    }

    @Override
    protected BigDecimal getZero() {
        return BigDecimal.ZERO;
    }

}
