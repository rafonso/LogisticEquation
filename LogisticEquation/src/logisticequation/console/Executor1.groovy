/**
 * 
 */
package quadratica.console



/**
 * @author Rafael
 *
 */
public class Executor1{
    
    
    /**
     * @param args
     */
    public static void main(def args){
        def n = 500
        def x0s = [
0, 
0.0000000001, 
0.000000001, 
0.00000001, 
0.0000001, 
0.000001, 
0.00001, 
0.0001, 
0.001, 
0.01, 
0.10,
0.15,
0.20,
0.25,
0.30,
0.35,
0.40,
0.45,
0.50,
0.55,
0.60,
0.65,
0.70,
0.75,
0.80,
0.85,
0.90,
0.95,
0.99,
0.999,
0.9999,
0.99999,
0.999999,
0.9999999,
0.99999999,
0.999999999,
0.9999999999,
1
]
        def intervals = '1000'
        def resolution = '1000'
        def dir = 'logistic/13'
        
        x0s.each { x0 ->
        	print "Gerando para " + x0 + ".\t\t\t"
        	def t0 = System.currentTimeMillis()
        	LogisticGraph.main(n.toString(), x0.toString(), intervals, resolution, dir)
        	def t = System.currentTimeMillis() - t0 
        	println "Tempo total: " + t + "."
        }
    }
    
}
