package logisticequation.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;

public abstract class QuadraticElement<N extends Number> implements Serializable,
        Iterator<QuadraticElement<N>> {

    /*	public static final QuadraticElement START = new QuadraticElement(
    Integer.MIN_VALUE, Integer.MIN_VALUE, BigDecimal.ZERO,
    BigDecimal.ZERO, QuadraticElement.MAX_R);
    public static final QuadraticElement END = new QuadraticElement(
    Integer.MAX_VALUE, Integer.MAX_VALUE, BigDecimal.ZERO,
    BigDecimal.ZERO, QuadraticElement.MAX_R);
     */
    public static final BigDecimal MAX_R = new BigDecimal(4.0);
    public static final MathContext MATH_CONTEXT = new MathContext(10);
    /**
     *
     */
    private static final long serialVersionUID = -3708367355823083363L;
    private final int maxIteractions;
    private final int i;
    private final N x;
    private final N y;
    private final N r;

    protected QuadraticElement(int n, int i, N x, N y,
            N r) {
        this.maxIteractions = n;
        this.i = i;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getMaxIteractions() {
        return maxIteractions;
    }

    public int getI() {
        return i;
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

    public N getNextY() {
        return this.getNextY(this.y);
    }

    public abstract N getNextY(N _x);
    /*	{
    return null;
    BigDecimal fator1 = r.multiply(_x, MATH_CONTEXT);
    BigDecimal fator2 = BigDecimal.ONE.subtract(_x, MATH_CONTEXT);
    return fator1.multiply(fator2, MATH_CONTEXT);
    }
     */

    @Override
    public boolean hasNext() {
        return (this.i < this.maxIteractions);
    }

    @Override
    public abstract QuadraticElement<N> next();/* {
    N nextY = this.getNextY();
    return new QuadraticElement(this.N, this.i + 1, this.y, nextY, this.r);
    }
     */


    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.format(
                "[i = %05d, x = %1.10f, y = %1.10f, r = %1.10f, N = %05d]",
                this.i, this.x, this.y, this.r, this.maxIteractions);
    }
}
