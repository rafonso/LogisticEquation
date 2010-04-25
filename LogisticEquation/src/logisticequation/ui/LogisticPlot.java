package logisticequation.ui;

import org.jdesktop.swingx.JXGraph;

class LogisticPlot extends JXGraph.Plot {

    private double k;

    public void setK(double k) {
        double oldK = k;
        this.k = k;
        super.firePropertyChange("k", oldK, this.k);
    }

    @Override
    public double compute(double x) {
        return k * x * (1 - x);
    }
}
