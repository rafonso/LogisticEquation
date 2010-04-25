package logisticequation.base;

/**
 *
 * @author rafael
 */
public class QuadraticGeneratorDouble extends QuadraticGenerator<Double> {

    @Override
    protected void validateArguments(int maxIteractions, Double x0, Double r) throws IllegalArgumentException {
        if (maxIteractions < 0) {
            throw new IllegalArgumentException("Max Iteractions must be greater than 0, not "
                    + maxIteractions);
        }
        if ((x0 < 0.0) || (x0 > 1.0)) {
            throw new IllegalArgumentException(
                    "x0 must be between 0 and 1, not " + x0);
        }
        if ((r < 0) || (r > QuadraticElement.MAX_R.doubleValue())) {
            throw new IllegalArgumentException(
                    "r must be between 0 and 4, not " + r);
        }
    }

    @Override
    protected QuadraticElement<Double> getElementFrom(int maxIteractions, int iteraction, Double x, Double y, Double r) {
        return new QuadraticElementDouble(maxIteractions, iteraction, x, y, r);
    }

    @Override
    protected Double getZero() {
        return 0.0;
    }
}
