package logisticequation.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;

public abstract class QuadraticElement<N extends Number> implements Serializable,
        Iterator<QuadraticElement<N>> {

    public static final BigDecimal MAX_R = new BigDecimal(4.0);
    public static final MathContext MATH_CONTEXT = new MathContext(10);
    /**
     *
     */
    private static final long serialVersionUID = -3708367355823083363L;
    private final int maxIteractions;
    private final int iteraction;
    private final N x;
    private final N y;
    private final N r;

    protected QuadraticElement(int maxIteractions, int iteraction, N x, N y,
            N r) {
        this.maxIteractions = maxIteractions;
        this.iteraction = iteraction;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getMaxIteractions() {
        return maxIteractions;
    }

    public int getIteraction() {
        return iteraction;
    }

    public N getX() {
        return x;
    }

    public N getY() {
        return y;
    }

    public N getR() {
        return r;
    }

    protected N getNextY() {
        return this.getNextY(this.getX());
    }

    public abstract N getNextY(N _x);

    @Override
    public boolean hasNext() {
        return (this.iteraction < this.maxIteractions);
    }

    @Override
    public abstract QuadraticElement<N> next();

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean isFirstIteraction() {
        return (this.getIteraction() == 0);
    }

    public boolean isLastIteraction() {
        return (this.getIteraction() == this.getMaxIteractions());
    }

    @Override
    public String toString() {
        return String.format(
                "[i = %05d, x = %1.10f, y = %1.10f, r = %1.10f, N = %05d]",
                this.iteraction, this.x, this.y, this.r, this.maxIteractions);
    }
}
