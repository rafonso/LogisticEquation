/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logisticequation.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import org.jdesktop.swingx.JXGraph;

/**
 *
 * @author rafael
 */
public class JXGraph1 extends JXGraph {

    private LogisticIterator doubleIterator;

    private void drawLine(Graphics2D g2, Point2D.Double p1, Point2D.Double p2) {
        g2.drawLine((int) super.xPositionToPixel(p1.getX()), (int) super.yPositionToPixel(p1.getY()), (int) super.xPositionToPixel(p2.getX()), (int) super.yPositionToPixel(p2.getY()));
    }

    @Override
    protected void paintExtra(Graphics2D g2) {
        super.paintExtra(g2);

        g2.setColor(Color.RED);
        if (this.doubleIterator.getMaxIteracoes() > 2) {
            Point2D.Double p1 = new Point2D.Double(this.doubleIterator.next(), 0.0);        // (x0, 0)
            Point2D.Double p2 = new Point2D.Double(p1.getX(), this.doubleIterator.next());  // (x0, f(x0))
            this.drawLine(g2, p1, p2);
            while (this.doubleIterator.hasNext()) {
                p1 = p2;
                p2 = (p1.getX() == p1.getY())?
                    new Point2D.Double(p1.getX(), this.doubleIterator.next()):
                    new Point2D.Double(p1.getY(), p1.getY());
                this.drawLine(g2, p1, p2);
            }
        }
    }

    public void setLogisticIterator(LogisticIterator doubleIterator) {
        this.doubleIterator = doubleIterator;
    }

}
