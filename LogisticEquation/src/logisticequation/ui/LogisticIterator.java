/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logisticequation.ui;

import java.util.Iterator;

/**
 *
 * @author rafael
 */
class LogisticIterator implements Iterator<Double> {

    private final Double k;
    private final int maxIteracoes;
    private Double x;
    private int iteracao;

    static LogisticIterator NULL_ITERATOR = new LogisticIterator(0.0, 0.0, 0);

    public LogisticIterator(Double x0, Double k, int maxIteracoes) {
        this.k = k;
        this.maxIteracoes = maxIteracoes;
        this.x = x0;
    }

    private void generateNextX() {
        this.x = this.k * this.x * (1.0 - this.x);
        this.iteracao++;
    }

    public boolean hasNext() {
        return (iteracao < this.maxIteracoes);
    }

    public Double next() {
        Double currentX = this.x;
        this.generateNextX();
        return currentX;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMaxIteracoes() {
        return maxIteracoes;
    }

    public int getIteracao() {
        return iteracao;
    }

}
