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
public class TestQuadraticGenerator1 implements Observer {

    private List<QuadraticElement> doubleValue = new ArrayList<QuadraticElement>();
    private List<QuadraticElement> decimalValue = new ArrayList<QuadraticElement>();
    private boolean isDouble = false;

    public void update(Observable o, Object object) {
        QuadraticElement element = (QuadraticElement) object;
        if(isDouble) {
            doubleValue.add(element);
        } else {
            decimalValue.add(element);
        }
    }

    private void print() {
        System.out.println("iter|   double   | BigDecimal | ");

        Iterator<QuadraticElement> itDouble  = doubleValue.iterator();
        Iterator<QuadraticElement> itDecimal = decimalValue.iterator();

        int i = 0;
        while(itDouble.hasNext() && itDecimal.hasNext()) {
            System.out.printf("%04d| %s | %s | %n", i, itDouble.next(), itDecimal.next());
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

        TestQuadraticGenerator1 testQuadraticGenerator = new TestQuadraticGenerator1();

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
