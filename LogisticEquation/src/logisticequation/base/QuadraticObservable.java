package logisticequation.base;

import java.util.Observable;

public class QuadraticObservable extends Observable {

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
}
