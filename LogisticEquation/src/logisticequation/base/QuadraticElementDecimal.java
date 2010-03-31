package logisticequation.base;

import java.math.BigDecimal;
import java.math.MathContext;

public class QuadraticElementDecimal extends QuadraticElement<BigDecimal> {

    /**
     *
     */
    private static final long serialVersionUID = 7300028542324659901L;
    public static final MathContext BASIC_MATH_CONTEXT = new MathContext(10);
    private final MathContext mathContext;

    public QuadraticElementDecimal(int n, int i, BigDecimal x, BigDecimal y,
            BigDecimal r) {
        this(n, i, x, y, r, BASIC_MATH_CONTEXT);
    }

    public QuadraticElementDecimal(int n, int i, BigDecimal x, BigDecimal y,
            BigDecimal r, MathContext mathContext) {
        super(n, i, x, y, r);
        this.mathContext = mathContext;
    }

    @Override
    public BigDecimal getNextY(BigDecimal _x) {
        BigDecimal fator1 = super.getR().multiply(_x, this.mathContext);
        BigDecimal fator2 = BigDecimal.ONE.subtract(_x, this.mathContext);
        return fator1.multiply(fator2, this.mathContext);
    }

    @Override
    public QuadraticElement<BigDecimal> next() {
        return new QuadraticElementDecimal(super.getN(), super.getI() + 1,
                super.getY(), super.getNextY(), super.getR(), this.mathContext);
    }
}
