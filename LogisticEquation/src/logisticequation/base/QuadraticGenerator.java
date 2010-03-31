package logisticequation.base;

import java.math.BigDecimal;
import java.util.Observable;

public class QuadraticGenerator {

    private final Observable observable = new Observable() {

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }
    };

    public QuadraticGenerator() {
    }

    public Observable getObservable() {
        return observable;
    }

    private void validateArguments(final int N, final BigDecimal x0,
            final BigDecimal r) throws IllegalArgumentException {
        if (N < 1) {
            throw new IllegalArgumentException("N must be greater than 0, not "
                    + N);
        }
        if ((x0.compareTo(BigDecimal.ZERO) < 0)
                || (x0.compareTo(BigDecimal.ONE) > 0)) {
            throw new IllegalArgumentException(
                    "x0 must be between 0 and 1, not " + x0);
        }
        if ((r.compareTo(BigDecimal.ZERO) < 0)
                || (r.compareTo(QuadraticElement.MAX_R) > 0)) {
            throw new IllegalArgumentException(
                    "r must be between 0 and 4, not " + r);
        }
    }

    public void generate(final int N, final BigDecimal x0, final BigDecimal r) {
        validateArguments(N, x0, r);

        QuadraticElement<BigDecimal> quadraticElement = new QuadraticElementDecimal(N, 0, x0,
                BigDecimal.ZERO, r);
        this.observable.notifyObservers(quadraticElement);

        quadraticElement = new QuadraticElementDecimal(N, 1, x0, (BigDecimal) quadraticElement.getNextY(x0), r);
        this.observable.notifyObservers(quadraticElement);
        while (quadraticElement.hasNext()) {
            quadraticElement = quadraticElement.next();
            this.observable.notifyObservers(quadraticElement);
        }
    }
}
