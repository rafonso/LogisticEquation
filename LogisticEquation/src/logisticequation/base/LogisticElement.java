package logisticequation.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LogisticElement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1068883596012454251L;
    public static final LogisticElement START = new LogisticElement(BigDecimal.ONE, Integer.MIN_VALUE, 0, BigDecimal.ONE);
    public static final LogisticElement END = new LogisticElement(BigDecimal.ONE, Integer.MAX_VALUE, 0, BigDecimal.ONE);
    public static final double MAX_Y = 1.0;
    private final BigDecimal r;
    private final int iteractions;
    private final int positions;
    private final BigDecimal x0;
    private final List<BigDecimal> sequence;

    public LogisticElement(BigDecimal r, int iteractions, int positions, BigDecimal x0) {
        this.r = r;
        this.iteractions = iteractions;
        this.positions = positions;
        this.x0 = x0;
        this.sequence = new LinkedList<BigDecimal>();
    }

    public BigDecimal getR() {
        return r;
    }

    public int getIteractions() {
        return iteractions;
    }

    public BigDecimal getX0() {
        return x0;
    }

    public int getPositions() {
        return positions;
    }

    public void addValue(BigDecimal y) throws IllegalStateException {
        if (this.sequence.size() > this.iteractions) {
            throw new IllegalStateException();
        }

        this.sequence.add(y);
    }

    public List<BigDecimal> getSequence() {
        return Collections.unmodifiableList(sequence);
    }

    public double getEquivalentY(int position) throws IllegalArgumentException {
        if ((position < 0) || (position > positions)) {
            throw new IllegalArgumentException("Irregular Position: "
                    + position + ". Postion must be positive or lesser than "
                    + this.positions);
        }

        return (double) position / this.positions;
    }

    public int valueToHistogramPosition(Number value) {
        return (int) (value.doubleValue() / MAX_Y * this.positions);
    }

    public int[] getHistogram(final int begin) {
        int[] histogram = new int[this.positions];

        for (Iterator<BigDecimal> itY = this.sequence.listIterator(begin); itY.hasNext();) {
            BigDecimal y = itY.next();
            int pos = (int) (y.doubleValue() * histogram.length);
            histogram[pos]++;
        }

        return histogram;
    }

    public int[] getHistogram() {
        return this.getHistogram(0);
    }

    public BigDecimal getMedia() {
        BigDecimal sum = BigDecimal.ZERO;
        final MathContext MC = QuadraticElement.MATH_CONTEXT;

        for (BigDecimal y : this.getSequence()) {
            sum = sum.add(y, MC);
        }

        return sum.divide(new BigDecimal(this.getIteractions(), MC), MC);
    }

    public BigDecimal getDeviation(BigDecimal media) {
        BigDecimal distanceSum = BigDecimal.ZERO;
        final MathContext MC = QuadraticElement.MATH_CONTEXT;

        for (BigDecimal y : this.getSequence()) {
            distanceSum = distanceSum.add(y.subtract(media, MC).pow(2, MC), MC);
        }

        int divisionFactor = (this.getIteractions() - 1);
        BigDecimal divisionSqrt = new BigDecimal(Math.sqrt(divisionFactor), MC);
        // BigDEcimal does not has sqrt.
        double distanceSqrt = Math.sqrt(distanceSum.doubleValue());

        return new BigDecimal(distanceSqrt, MC).divide(divisionSqrt, MC);
    }

    public BigDecimal getDeviation() {
        return this.getDeviation(this.getMedia());
    }

    @Override
    public String toString() {
        return "[r = " + this.r + ", iteractions = " + this.iteractions
                + ", positions = " + this.positions + ", x0 = " + x0 + "]";
    }
}
