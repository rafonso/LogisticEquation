package logisticequation.base;

import java.math.BigDecimal;
import java.util.Observable;

public class LogisticGenerator {

    private final Observable observable = new QuadraticObservable();

    public Observable getObservable() {
        return observable;
    }

    private void validateArguments(final int N, final BigDecimal x0,
            final int intervals, final int resolution,
            final BigDecimal rInitial, final BigDecimal rFinal)
            throws IllegalArgumentException {
        if (N < 1) {
            throw new IllegalArgumentException("N irregular: " + N
                    + ". It must be greater than 0.");
        }
        if ((x0.compareTo(BigDecimal.ZERO) < 0)
                || (x0.compareTo(BigDecimal.ONE) > 0)) {
            throw new IllegalArgumentException("x0 irregular: " + x0
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
        if (rInitial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("r Initial irregular: "
                    + rInitial + ". It must be greater than 0.");
        }
        if (rFinal.compareTo(QuadraticElement.MAX_R) > 0) {
            throw new IllegalArgumentException("r Final irregular: " + rFinal
                    + ". It must be lesser than 4.");
        }
        if (rInitial.compareTo(rFinal) > 0) {
            throw new IllegalArgumentException("r Initial (" + rInitial
                    + ") must be lesser r Final (" + rFinal + ")");
        }
    }

    public void generate(final int N, final BigDecimal x0, final int intervals,
            final int resolution) {
        this.generate(N, x0, intervals, resolution, BigDecimal.ZERO,
                QuadraticElement.MAX_R);
    }

    public void generate(final int N, final BigDecimal x0, final int intervals,
            final int resolution, final BigDecimal rInitial,
            final BigDecimal rFinal) {
        this.validateArguments(N, x0, intervals, resolution, rInitial, rFinal);

        QuadraticGenerator quadraticGenerator = new QuadraticGenerator();
        final BigDecimal step = (rFinal.subtract(rInitial,
                QuadraticElement.MATH_CONTEXT)).divide(
                new BigDecimal(intervals), QuadraticElement.MATH_CONTEXT);
        BigDecimal r = rInitial;

        this.observable.notifyObservers(LogisticElement.START);
        for (int i = 1; i <= intervals; i++) {
            quadraticGenerator.getObservable().deleteObservers();
            LogisticRecorder logisticRecorder = new LogisticRecorder(r, N,
                    resolution, x0);
            quadraticGenerator.getObservable().addObserver(logisticRecorder);
            quadraticGenerator.generate(N, x0, r);

            this.observable.notifyObservers(logisticRecorder.getLogisticElement());

            r = r.add(step);
        }
        this.observable.notifyObservers(LogisticElement.END);
    }
}
