package logisticequation.ui.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SwingUtilities;

/**
 *
 * @author rafael
 */
public class XNumberEditor extends NumberEditor implements MouseWheelListener, KeyListener {

    public XNumberEditor(JXSpinner spinner, String decimalFormatPattern) {
        super(spinner, decimalFormatPattern);
    }

    public XNumberEditor(JXSpinner spinner) {
        super(spinner);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        JXSpinner jXSpinner = (JXSpinner) super.getSpinner();
        XSpinnerNumberModel numberModel = (XSpinnerNumberModel) jXSpinner.getModel();
        boolean add = (e.getWheelRotation() < 0);
        boolean extender = SwingUtilities.isRightMouseButton(e) || (e.isControlDown());

        Object newValue = null;
        if (extender) {
            newValue = add ? numberModel.getNextExtendedValue() : numberModel.getPreviousExtendedValue();
        } else {
            newValue = add ? numberModel.getNextValue() : numberModel.getPreviousValue();
        }
        super.getSpinner().setValue(newValue);
    }

    public void keyTyped(KeyEvent e) {
//        System.out.println(e);
    }

    public void keyPressed(KeyEvent e) {
        if ((e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)) {
            JXSpinner jXSpinner = (JXSpinner) super.getSpinner();
            XSpinnerNumberModel numberModel = (XSpinnerNumberModel) jXSpinner.getModel();
            Object newValue = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    newValue = numberModel.getNextExtendedValue();
                    break;
                case KeyEvent.VK_PAGE_UP:
                    newValue = numberModel.getNextValue();
                    break;
                case KeyEvent.VK_DOWN:
                    newValue = numberModel.getPreviousExtendedValue();
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    newValue = numberModel.getPreviousValue();
                    break;
                case KeyEvent.VK_HOME:
                    if (numberModel.getMaximum() != null) {
                        newValue = numberModel.getMaximum();
                    }
                    break;
                case KeyEvent.VK_END:
                    if (numberModel.getMinimum() != null) {
                        newValue = numberModel.getMinimum();
                    }
                    break;
            }

            if(newValue != null) {
                super.getSpinner().setValue(newValue);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
//        System.out.println(e);
    }
}
