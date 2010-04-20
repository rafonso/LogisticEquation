/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JXSlider.java
 *
 * Created on 15/04/2010, 22:21:16
 */
package logisticequation.ui.components;

import java.awt.Dimension;
import java.math.BigDecimal;
import javax.swing.JSlider;

/**
 *
 * @author rafael
 */
public class JXSlider extends JSlider {

    /** Creates new form BeanForm */
    public JXSlider() {
        initComponents();
    }

    private Dimension invertDimension(Dimension d) {
        return new Dimension(d.height, d.width);
    }

    void changeOrientation(SliderSpinner.Orientation orientation) {
        this.setOrientation(orientation.getSliderOrientation());
        this.setSize(this.invertDimension(this.getSize()));
        this.setMaximumSize(this.invertDimension(this.getMaximumSize()));
        this.setMinimumSize(this.invertDimension(this.getMinimumSize()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPaintLabels(true);
        setPaintTicks(true);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        boolean add = (evt.getWheelRotation() < 0);
        int signal = add ? +1 : -1;
        super.setValue(super.getValue() + signal);
    }//GEN-LAST:event_formMouseWheelMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}