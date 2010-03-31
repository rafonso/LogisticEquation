package logisticequation.base;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

public class LogisticRecorder implements Observer {

    private LogisticElement logisticElement;

    public LogisticRecorder(BigDecimal r, int N, int positions,
            BigDecimal x0) {
        this.logisticElement = new LogisticElement(r, N, positions, x0);
    }

    @Override
    public void update(Observable o, Object object) {
        QuadraticElement quadraticElement = (QuadraticElement) object;
        /*
        if (quadraticElement == QuadraticElement.START
        || quadraticElement == QuadraticElement.END
        || quadraticElement.getI() == 0) {
        return;
        }
         */
        this.logisticElement.addValue((BigDecimal) quadraticElement.getY());
    }

    public LogisticElement getLogisticElement() {
        return logisticElement;
    }
}
