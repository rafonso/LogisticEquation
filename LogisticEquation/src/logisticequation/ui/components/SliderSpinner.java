package logisticequation.ui.components;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpinnerModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author rafael
 */
public class SliderSpinner extends JPanel implements PropertyChangeListener {

    public static enum SpinnerPosition {

        BEGIN {

            @Override
            Object getBorderPosition(Orientation orientation) {
                return (orientation == Orientation.HORIZONTAL) ? BorderLayout.WEST : BorderLayout.NORTH;
            }
        },
        END {

            @Override
            Object getBorderPosition(Orientation orientation) {
                return (orientation == Orientation.HORIZONTAL) ? BorderLayout.EAST : BorderLayout.SOUTH;
            }
        };

        abstract Object getBorderPosition(Orientation orientation);
    }

    public static enum Orientation {

        HORIZONTAL(JSlider.HORIZONTAL), VERTICAL(JSlider.VERTICAL);
        private final int sliderOrientation;

        private Orientation(int sliderOrientation) {
            this.sliderOrientation = sliderOrientation;
        }

        int getSliderOrientation() {
            return sliderOrientation;
        }
    }

    private class SliderChangeListener implements ChangeListener {

        private boolean active = true;

        void activate(boolean _active) {
            this.active = _active;
        }

        public void stateChanged(ChangeEvent e) {
            if (this.active) {
                spinner.setValue(SliderSpinner.this.sliderToValue(
                        SliderSpinner.this.getStep().getClass(),
                        SliderSpinner.this.getMinimum(),
                        SliderSpinner.this.getMaximum()));
            }
        }
    }
    //////////
    public static final String PROP_TITULO = "titulo";
    public static final String PROP_ORDER = "order";
    public static final String PROP_SLIDER_POSITION = "sliderPosition";
    public static final String PROP_EXTENDED_STEP = JXSpinner.PROP_EXTENDED_STEP;
    public static final String PROP_MAXIMUM = JXSpinner.PROP_MAXIMUM;
    public static final String PROP_MINIMUM = JXSpinner.PROP_MINIMUM;
    public static final String PROP_PATTERN = JXSpinner.PROP_PATTERN;
    public static final String PROP_STEP = JXSpinner.PROP_STEP;
    public static final String PROP_VALUE = JXSpinner.PROP_VALUE;
    private static final Set<String> JXSPINNER_PROPERTIES = new HashSet<String>(Arrays.asList(PROP_EXTENDED_STEP, PROP_MAXIMUM, PROP_MINIMUM, PROP_PATTERN, PROP_STEP, PROP_VALUE));

    /** Creates new form BeanForm */
    public SliderSpinner() {
        this.sliderPosition = SpinnerPosition.END;
        this.orientation = Orientation.HORIZONTAL;
        this.sliderChangeListener = new SliderChangeListener();
        initComponents();
        this.changeSliderPosition();
    }

    private TitledBorder getTitledBorder() {
        return (TitledBorder) this.getBorder();
    }

    private void changeOrder() {
        this.remove(this.pnlSlider);
        this.add(this.pnlSlider, this.getSliderPosition().getBorderPosition(this.getOrientation()));
    }

    private void changeSliderPosition() {
        this.sliderChangeListener.activate(false);
        this.slider.setValue(this.valueToSlider(this.getValue(), this.getMinimum(), this.getMaximum()));
        this.sliderChangeListener.activate(true);
    }

    private int valueToSlider(Number value, Number minimum, Number maximum) {
        int position;

        if (value.equals(minimum)) {
            position = this.slider.getMinimum();
        } else if (value.equals(maximum)) {
            position = this.slider.getMaximum();
        } else {
            final double numeratorValue = (value.doubleValue() - minimum.doubleValue());
            final double numeratorPosition = this.slider.getMaximum() - this.slider.getMinimum();
            final double denominatorValue = (maximum.doubleValue() - minimum.doubleValue());
            position = (int) (numeratorValue * numeratorPosition / denominatorValue) + this.slider.getMinimum();
        }

        return position;
    }

    private Number sliderToValue(Class stepClass, Number minimum, Number maximum) {
        Number value;

        if (this.slider.getValue() == this.slider.getMaximum()) {
            value = maximum;
        } else if (this.slider.getValue() == this.slider.getMinimum()) {
            value = minimum;
        } else {
            final int numeratorPosition = this.slider.getValue() - this.slider.getMinimum();
            final int denominatorPosition = this.slider.getMaximum() - this.slider.getMinimum();
            if (stepClass == Integer.class) {
                final int numeratorValue = maximum.intValue() - minimum.intValue();
                value = numeratorPosition * numeratorValue / denominatorPosition + minimum.intValue();
            } else if (stepClass == Double.class) {
                final double numeratorValue = maximum.doubleValue() - minimum.doubleValue();
                value = numeratorPosition * numeratorValue / denominatorPosition + minimum.intValue();
            } else if (stepClass == BigDecimal.class) {
                final BigDecimal numeratorValue = ((BigDecimal) maximum).subtract((BigDecimal) minimum);
                value = (new BigDecimal(numeratorPosition)).multiply(numeratorValue).divide(new BigDecimal(denominatorPosition)).add((BigDecimal) minimum);
            } else {
                throw new IllegalStateException("Step class unknown: " + stepClass);
            }
        }

        return value;
    }

    private void validateNewValue(Object value, String fieldName) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can not be null.");
        }
    }

    protected void fireStateChanged() {
        for (ChangeListener listener : this.changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSlider = new javax.swing.JPanel();
        spinner = new logisticequation.ui.components.JXSpinner();
        slider = new logisticequation.ui.components.JXSlider();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setLayout(new java.awt.BorderLayout(5, 5));

        pnlSlider.add(spinner);
        this.spinner.addPropertyChangeListener(this);

        add(pnlSlider, java.awt.BorderLayout.EAST);

        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        add(slider, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
        this.spinner.setValue(this.sliderToValue(this.getStep().getClass(), this.getMinimum(), this.getMaximum()));
    }//GEN-LAST:event_sliderStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlSlider;
    private logisticequation.ui.components.JXSlider slider;
    private logisticequation.ui.components.JXSpinner spinner;
    // End of variables declaration//GEN-END:variables
    private SpinnerPosition sliderPosition;
    private Orientation orientation;
    private SliderChangeListener sliderChangeListener;
    private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

    public String getTitulo() {
        return this.getTitledBorder().getTitle();
    }

    public void setTitulo(String titulo) {
        String oldValue = this.getTitledBorder().getTitle();
        this.getTitledBorder().setTitle(titulo);
        super.repaint();
        super.firePropertyChange(PROP_TITULO, oldValue, titulo);
    }

    public SpinnerPosition getSliderPosition() {
        return sliderPosition;
    }

    public void setSliderPosition(SpinnerPosition order) {
        this.validateNewValue(order, "Slider Position");

        if (order != this.sliderPosition) {
            Object oldValue = this.sliderPosition;
            this.sliderPosition = order;
            super.firePropertyChange(PROP_ORDER, oldValue, this.sliderPosition);
            this.fireStateChanged();
            this.changeOrder();
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.validateNewValue(orientation, "Orientation");

        if (this.orientation != orientation) {
            Object oldValue = this.orientation;
            this.orientation = orientation;

            this.changeOrder();
            this.slider.changeOrientation(orientation);

            super.firePropertyChange(PROP_SLIDER_POSITION, oldValue, this.orientation);
            this.fireStateChanged();
        }
    }

    public void setStep(Number step) {
        this.validateNewValue(step, "Step");

        Object oldValue = this.getStep();
        spinner.setStep(step);
        super.firePropertyChange(PROP_STEP, oldValue, step);
        this.fireStateChanged();
    }

    public void setPattern(String pattern) {
        this.validateNewValue(pattern, "Pattern");

        Object oldValue = this.getPattern();
        spinner.setPattern(pattern);
        super.firePropertyChange(PROP_PATTERN, oldValue, pattern);
        this.fireStateChanged();
    }

    public void setMinimum(Number minimum) {
        this.validateNewValue(minimum, "Minumum");

        Object oldValue = this.getMinimum();
        spinner.setMinimum(minimum);
        this.changeSliderPosition();
        super.firePropertyChange(PROP_MINIMUM, oldValue, minimum);
        this.fireStateChanged();
    }

    public void setMaximum(Number maximum) {
        this.validateNewValue(maximum, "Maximum");

        Object oldValue = this.getMaximum();
        spinner.setMaximum(maximum);
        this.changeSliderPosition();
        super.firePropertyChange(PROP_MAXIMUM, oldValue, maximum);
        this.fireStateChanged();
    }

    public void setExtendedStep(Number extendedStep) {
        this.validateNewValue(extendedStep, "Extended Step");
        Object oldValue = this.getExtendedStep();
        spinner.setExtendedStep(extendedStep);
        super.firePropertyChange(PROP_EXTENDED_STEP, oldValue, extendedStep);
        this.fireStateChanged();
    }

    public Number getStep() {
        return spinner.getStep();
    }

    public String getPattern() {
        return spinner.getPattern();
    }

    public Number getMinimum() {
        return spinner.getMinimum();
    }

    public Number getMaximum() {
        return spinner.getMaximum();
    }

    public Number getExtendedStep() {
        return spinner.getExtendedStep();
    }

    public void setValue(Number value) {
        spinner.setValue(value);
        this.changeSliderPosition();
        this.fireStateChanged();
    }

    public Number getValue() {
        return (Number) spinner.getValue();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(JXSpinner.PROP_VALUE)) {
            this.changeSliderPosition();
            this.fireStateChanged();
        }
        super.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        super.addPropertyChangeListener(propertyName, listener);
        if (JXSPINNER_PROPERTIES.contains(propertyName)) {
            this.spinner.addPropertyChangeListener(propertyName, listener);
        }
    }

    public void addChangeListener(ChangeListener changeListener) {
        this.changeListeners.add(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener) {
        this.changeListeners.remove(changeListener);
    }
}
