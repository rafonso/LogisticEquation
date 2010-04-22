/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logisticequation.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Collections;
import org.jdesktop.swingx.JXGraph;

/**
 *
 * @author rafael
 */
public class JXGraph1 extends JXGraph {

    private LogisticIterator logisticIterator = LogisticIterator.NULL_ITERATOR;
    private boolean exibirRastro = false;

    private void drawLine(Graphics2D g2, Point2D.Double p1, Point2D.Double p2) {
        g2.drawLine((int) super.xPositionToPixel(p1.getX()), (int) super.yPositionToPixel(p1.getY()), (int) super.xPositionToPixel(p2.getX()), (int) super.yPositionToPixel(p2.getY()));
    }

    private Color getColor(int iteracao, int maxIteracoes) {
        Color c = null;

        if (this.exibirRastro) {
            int red = (iteracao * 255) / maxIteracoes;
            int green = 0;
            int blue = ((maxIteracoes - iteracao) * 255) / maxIteracoes;
//            System.out.println("iteracao = " + iteracao + ", red = " + red + ", blue = " + blue);
            c = new Color(red, green, blue);
        } else {
            c = Color.RED;
        }

        return c;
    }

    @Override
    protected void paintExtra(Graphics2D g2) {
        super.paintExtra(g2);

        if (this.logisticIterator == null) {
            return;
        }

        if (this.logisticIterator.getMaxIteracoes() > 2) {
            Point2D.Double p1 = new Point2D.Double(this.logisticIterator.next(), 0.0);        // (x0, 0)
            Point2D.Double p2 = new Point2D.Double(p1.getX(), this.logisticIterator.next());  // (x0, f(x0))
            g2.setColor(this.getColor(0, this.logisticIterator.getMaxIteracoes()));
            this.drawLine(g2, p1, p2);
            while (this.logisticIterator.hasNext()) {
                p1 = p2;
                p2 = (p1.getX() == p1.getY())
                        ? new Point2D.Double(p1.getX(), this.logisticIterator.next())
                        : new Point2D.Double(p1.getY(), p1.getY());
                g2.setColor(this.getColor(this.logisticIterator.getIteracao(), this.logisticIterator.getMaxIteracoes()));
                this.drawLine(g2, p1, p2);
            }
        }
    }

    public void setParameters(Double x0, Double k, int maxIteracoes, boolean exibirRastro) {
        this.logisticIterator = new LogisticIterator(x0, k, maxIteracoes);
        this.exibirRastro = exibirRastro;
    }
}
