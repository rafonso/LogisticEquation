package logisticequation.base;

import java.math.BigDecimal;
import java.util.Observable;

public abstract class QuadraticGenerator<N extends Number> {

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

    protected abstract void validateArguments(final int maxIteractions, final N x0, final N r) throws IllegalArgumentException;

    protected abstract QuadraticElement<N> getElementFrom(final int maxIteractions, final int iteraction, N x, N y, N r);

    protected abstract N getZero();

    public void generate(final int maxIteractions, final N x0, final N r) {
        validateArguments(maxIteractions, x0, r);

        QuadraticElement quadraticElement = getElementFrom(maxIteractions, 0, x0, this.getZero(), r);
        this.observable.notifyObservers(quadraticElement);

        quadraticElement = getElementFrom(maxIteractions, 1, x0, (N) quadraticElement.getNextY(x0), r);
        this.observable.notifyObservers(quadraticElement);

        while (quadraticElement.hasNext()) {
            quadraticElement = quadraticElement.next();
            this.observable.notifyObservers(quadraticElement);
        }
    }

    public static QuadraticGenerator getGenerator(Class numberClass) {
        if(numberClass == Double.class) {
            return new QuadraticGeneratorDouble();
        }
        if(numberClass == BigDecimal.class) {
            return new QuadraticGeneratorDecimal();
        }

        throw new IllegalArgumentException("Classe descinhecida: " + numberClass);
    }
}
