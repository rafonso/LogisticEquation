package logisticequation.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author rafael
 */
public class TestQuadraticGenerator implements Observer {

    private List<Double> doubleValue = new ArrayList<Double>();
    private List<BigDecimal> decimalValue = new ArrayList<BigDecimal>();
    private boolean isDouble = false;

    public void update(Observable o, Object object) {
        QuadraticElement element = (QuadraticElement) object;
        if(isDouble) {
            doubleValue.add(element.getY().doubleValue());
        } else {
            decimalValue.add((BigDecimal) element.getY());
        }
    }

    private void print() {
        System.out.println("iter|   double   | BigDecimal | Difference |");

        Iterator<Double> itDouble  = doubleValue.iterator();
        Iterator<BigDecimal> itDecimal = decimalValue.iterator();

        int i = 0;
        while(itDouble.hasNext() && itDecimal.hasNext()) {
            final Double dbl = itDouble.next();
            final BigDecimal dec = itDecimal.next();
            final double diff = dbl - dec.doubleValue();
            System.out.printf("%04d| %,15f | %,15f | %,15f |%n", i, dbl, dec, diff);
            i ++;
        }
    }

    public static void main(String[] args) {
        System.out.print("Entre os valores de X0, r e iterações: ");
        Scanner sc = new Scanner(System.in);
        BigDecimal x0 = sc.nextBigDecimal();
        BigDecimal r = sc.nextBigDecimal();
        int iteracoes = sc.nextInt();

        System.out.println("x0 = " + x0);
        System.out.println("r = " + r);
        System.out.println("iterações = " + iteracoes);

        TestQuadraticGenerator testQuadraticGenerator = new TestQuadraticGenerator();

        QuadraticGenerator generator = QuadraticGenerator.getGenerator(Double.class);
        testQuadraticGenerator.isDouble = true;
        generator.getObservable().addObserver(testQuadraticGenerator);
        generator.generate(iteracoes, x0.doubleValue(), r.doubleValue());

        generator = QuadraticGenerator.getGenerator(BigDecimal.class);
        testQuadraticGenerator.isDouble = false;
        generator.getObservable().addObserver(testQuadraticGenerator);
        generator.generate(iteracoes, x0, r);

        testQuadraticGenerator.print();
    }

}
