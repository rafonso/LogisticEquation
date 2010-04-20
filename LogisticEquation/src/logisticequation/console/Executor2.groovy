/**
 * 
 */
package quadratica.console



/**
 * @author rafael
 *
 */
public class Executor2 {
    
    public static void main(def args){
        def n = 500
        def range = (38280..38360)
        def intervals = 1000
        def resolution = 1000
        def dir = 'logistic/15'
        
        range.each{r1 ->
            def r = r1 / 10000
            print "Gerando para " + r + "\t\t\t"
            def t0 = System.currentTimeMillis()
            LogisticGraphX0.main(n.toString(), r.toString(), intervals.toString(), resolution.toString(), dir)
            def t = System.currentTimeMillis() - t0 
            println "Tempo: " + t + " ms"
        }
    }
    
}
