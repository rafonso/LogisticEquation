package logisticequation.base;

/**
 *
 * @author rafael
 */
public class QuadraticElementDouble extends QuadraticElement<Double>{

    public QuadraticElementDouble(int maxIteractions, int iteraction, Double x, Double y, Double r) {
        super(maxIteractions, iteraction, x, y, r);
    }

    @Override
    public Double getNextY(Double _x) {
        return super.getR() * _x * (1 - _x);
    }

    @Override
    public QuadraticElement<Double> next() {
        return new QuadraticElementDouble(super.getMaxIteractions(), super.getIteraction() + 1,
                super.getY(), this.getNextY(super.getY()), super.getR());
    }

}
