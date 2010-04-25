package logisticequation.ui;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import org.jdesktop.swingx.JXGraph;

/**
 *
 * @author rafael
 */
public class JXGraph1 extends JXGraph {

    private static final SimpleColorMake SIMPLE_COLOR_MAKE = new SimpleColorMake();
    private static final RastroColorMake RASTRO_COLOR_MAKE = new RastroColorMake();
    private LogisticIterator logisticIterator = LogisticIterator.NULL_ITERATOR;
    private int iteracaoExibidaInicial = 0;
    private ColorMake colorMake = null;

    private void drawLine(Graphics2D g2, Point2D.Double p1, Point2D.Double p2) {
        g2.drawLine(
                (int) super.xPositionToPixel(p1.getX()),
                (int) super.yPositionToPixel(p1.getY()),
                (int) super.xPositionToPixel(p2.getX()),
                (int) super.yPositionToPixel(p2.getY()));
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
            if (this.iteracaoExibidaInicial == 0) {
                g2.setColor(this.colorMake.getColor(0, this.logisticIterator.getMaxIteracoes()));
                this.drawLine(g2, p1, p2);
            }
            while (this.logisticIterator.hasNext()) {
                p1 = p2;
                p2 = (p1.getX() == p1.getY())
                        ? new Point2D.Double(p1.getX(), this.logisticIterator.next())
                        : new Point2D.Double(p1.getY(), p1.getY());
                if (this.logisticIterator.getIteracao() > this.iteracaoExibidaInicial) {
                    g2.setColor(this.colorMake.getColor(this.logisticIterator.getIteracao(), this.logisticIterator.getMaxIteracoes()));
                    this.drawLine(g2, p1, p2);
                }
            }
        }
    }

    public void setParameters(Double x0, Double k, int maxIteracoes, boolean exibirRastro, int porcentagemIgnorada) {
        this.logisticIterator = new LogisticIterator(x0, k, maxIteracoes);
        this.iteracaoExibidaInicial = (maxIteracoes * porcentagemIgnorada) / 100;
        this.colorMake = exibirRastro ? RASTRO_COLOR_MAKE : SIMPLE_COLOR_MAKE;
    }
}
