/**
 * 
 */
package practico4;

import java.util.HashMap;
import java.util.Random;

/**
 * @author emi
 *
 */
public class Sigmoide {
	Integer id;
	HashMap<Integer,Double> pesos = new HashMap<>();
	public Sigmoide()
	{		
	}
	
	public Sigmoide(Integer id,Integer cantEntradas)
	{
		this.id=id;
		Random r = new Random();
		for(int i=0;i<cantEntradas;i++)
		{	        
			pesos.put(i,r.nextDouble());
		}
	}
	public double salida(HashMap<Integer,Double> entradas)
	{
		Double resultado=0.0; 
		return resultado;
	}
}
