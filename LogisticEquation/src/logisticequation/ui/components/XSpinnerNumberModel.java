/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logisticequation.ui.components;

import java.math.BigDecimal;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author rafael
 */
public abstract class XSpinnerNumberModel<N extends Number & Comparable> extends SpinnerNumberModel {

    public static class IntXSpinnerNumberModel extends XSpinnerNumberModel<Integer> {

        @Override
        protected Integer getNewValue(Integer step, int dir) {
            return ((Integer) this.getValue()) + dir * step;
        }
    }

    public static class DoubleXSpinnerNumberModel extends XSpinnerNumberModel<Double> {

        @Override
        protected Double getNewValue(Double step, int dir) {
            return ((Double) this.getValue()) + dir * step;
        }
    }

    public static class BigDecimalXSpinnerNumberModel extends XSpinnerNumberModel<BigDecimal> {

        @Override
        protected BigDecimal getNewValue(BigDecimal step, int dir) {
            return ((BigDecimal) this.getValue()).add((dir > 0) ? step : step.negate());
        }
    }

    private N extendedStep;

    public XSpinnerNumberModel() {
        super();
    }

    public XSpinnerNumberModel(N value, N minimum, N maximum, N stepSize, N extendedStep) {
        super(value, minimum, maximum, stepSize);
        this.extendedStep = extendedStep;
    }

    public N getExtendedStep() {
        return extendedStep;
    }

    protected N incrValue(N step, int dir) {
        N newValue = this.getNewValue(step, dir);

        if ((super.getMaximum() != null) && (super.getMaximum().compareTo(newValue) < 0)) {
            return (N) super.getMaximum();
        }
        if ((super.getMinimum() != null) && (super.getMinimum().compareTo(newValue) > 0)) {
            return (N) super.getMinimum();
        }
        return newValue;
    }

    protected abstract N getNewValue(N step, int dir);

    public void setExtendedStep(N extendedStep) {
        boolean changed = ((extendedStep != null) && (this.extendedStep != null) && !this.extendedStep.equals(extendedStep))
                || ((extendedStep == null) && (this.extendedStep != null))
                || ((extendedStep != null) && (this.extendedStep == null));

        if (changed) {
            this.extendedStep = extendedStep;
            super.fireStateChanged();
        }
    }

    public void setMaximum(N maximum) {
        // Validação
        if ((maximum != null) && (super.getMinimum() != null) && (((Number)super.getMinimum()).doubleValue() > maximum.doubleValue())) {
            throw new IllegalArgumentException("New Maximum (" + maximum + ") lesser than Minimum (" + super.getMinimum() + ")");
        }

        super.setMaximum(maximum);
    }

    public void setMinimum(N minimum) {
        // Validação
        if ((minimum != null) && (super.getMaximum() != null) && (((Number)super.getMaximum()).doubleValue() < minimum.doubleValue())) {
            throw new IllegalArgumentException("New Maximum (" + minimum + ") lesser than Minimum (" + super.getMaximum() + ")");
        }

        super.setMinimum(minimum);
    }

    @Override
    public Object getNextValue() {
        return this.incrValue((N) super.getStepSize(), +1);
    }

    @Override
    public Object getPreviousValue() {
        return this.incrValue((N) super.getStepSize(), -1);
    }

    public N getNextExtendedValue() {
        return this.incrValue(this.getExtendedStep(), +1);
    }

    public N getPreviousExtendedValue() {
        return this.incrValue(this.getExtendedStep(), -1);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
//        System.out.println(value);
    }


}
