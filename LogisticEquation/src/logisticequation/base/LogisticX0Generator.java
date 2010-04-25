package logisticequation.base;

import java.math.BigDecimal;
import java.util.Observable;

public class LogisticX0Generator {

    private final Observable observable = new QuadraticObservable();

    public Observable getObservable() {
        return observable;
    }

    private void validateArguments(final int N, final BigDecimal r,
            final int intervals, final int resolution)
            throws IllegalArgumentException {
        if (N < 1) {
            throw new IllegalArgumentException("N irregular: " + N
                    + ". It must be greater than 0.");
        }
        if ((r.compareTo(BigDecimal.ZERO) < 0)
                || (r.compareTo(QuadraticElement.MAX_R) > 0)) {
            throw new IllegalArgumentException("r irregular: " + r
                    + ".It must be between 0 and 1.");
        }
        if (intervals < 0) {
            throw new IllegalArgumentException(
                    "Quantity of intervals irregular: " + intervals
                    + ". It must be greater than 0.");
        }
        if (intervals < 0) {
            throw new IllegalArgumentException("Resolution irregular: "
                    + resolution + ". It must be greater than 0.");
        }
    }

    public void generate(final int N, final BigDecimal r, final int intervals,
            final int resolution) {
        this.validateArguments(N, r, intervals, resolution);

        QuadraticGenerator quadraticGenerator = QuadraticGenerator.getGenerator(BigDecimal.class);
        final BigDecimal pass = BigDecimal.ONE.divide(new BigDecimal(
                intervals), QuadraticElement.MATH_CONTEXT);
        BigDecimal x0 = BigDecimal.ZERO;

        this.observable.notifyObservers(LogisticElement.START);
        for (int i = 1; i <= intervals; i++) {
            quadraticGenerator.getObservable().deleteObservers();
            LogisticRecorder logisticRecorder = new LogisticRecorder(r, N,
                    resolution, x0);
            quadraticGenerator.getObservable().addObserver(logisticRecorder);
            quadraticGenerator.generate(N, x0, r);

            this.observable.notifyObservers(logisticRecorder.getLogisticElement());

            x0 = x0.add(pass);
        }

        this.observable.notifyObservers(LogisticElement.END);
    }
}
