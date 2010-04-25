package logisticequation.ui;

import org.jdesktop.swingx.JXGraph;

class LinePlot extends JXGraph.Plot {

    @Override
    public double compute(double x) {
        return x;
    }
}
