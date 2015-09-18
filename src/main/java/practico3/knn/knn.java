package practico3.knn;

import practico3.comun.Ejemplo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.sqrt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author santiago
 */
public class knn {
    
    public Ejemplo comun;
    public int k;
    public List<Ejemplo> ejemplos;
    public HashMap<Integer,Double> pesos = new HashMap<>();   
    public boolean log = false;
    public knn(int k, List<Ejemplo> ejemplos){
        this.k = k;
        this.ejemplos = ejemplos;
        this.comun = comunes();
    }
    
    /***
     * Determina el valor mas comun para cada atributo con los datos 
     * @return 
     */
    public Ejemplo comunes2(){
        Ejemplo aux = new Ejemplo();
        HashMap<Integer, List<String>> atr = Main.atributos();        
        //Para cada atributo encuentro el valor mas comun
        for(int i=1; i<=atr.size(); i++){
            List<String> s = atr.get(i);
            HashMap<String, Integer> cant_atributos = new HashMap<>();
            //Inicializo en 0 la cuenta de cada valor del atributo i
            for(String atributo: s){
                cant_atributos.put(atributo, 0);
            }
            //Cuento la cantidad de veces que aparece cada valor del atributo i en los ejemplos
            for(Ejemplo e: ejemplos)
            {
              /*	for(int j=1;j<s.size();j++) // TODO verificar que falta este for en la funcion de Santiago
            	{
              		System.out.println(e.atributos.get(1));*/
            	//pongo j= i para no borrar mi intento de poner el for y cambiar lo qesue sigue
            	int j = i;
	                if(!e.atributos.get(j).equals("?"))
	                {
	                    cant_atributos.put(e.atributos.get(j), cant_atributos.get(e.atributos.get(j))+1);
	                }
            	//}
            }
            //Encuentro el valor mas frecuente para el atributo i
            int cantidad_maxima = 0;
            String atributo_mas_frecuente = "?";
            for(String atributo: s){
                if(cant_atributos.get(atributo) > cantidad_maxima){
                    cantidad_maxima = cant_atributos.get(atributo);
                    atributo_mas_frecuente = atributo;
                }
            }
            //Seteo el valor mas frecuente
            aux.atributos.put(i, atributo_mas_frecuente);
        } 
        return aux;
    }
    
    /***
     * Determina el valor mas comun para cada atributo con los datos y calcula contadores para pesos
     * @return 
     */
    public Ejemplo comunes(){//ypesos(){
        Ejemplo aux = new Ejemplo();
        HashMap<Integer, List<String>> atr = Main.atributos();
        Integer cantPoisonus = 0;
        Integer cantEdible = 0;
        Double cero = 0.0;
        //Para cada atributo encuentro el valor mas comun
        for(int i=1; i<=atr.size(); i++){
        	pesos.put(i, cero);
            List<String> s = atr.get(i);
            HashMap<String, Integer> cant_atributos = new HashMap<>();
            //contadores por atributo para calculo de pesos
            	//contador para calculo de p(valoratributo,poisonus)
            HashMap<String, Double> aportepoisonus = new HashMap<>();
        		//contador para calculo de p(valoratributo,edible)
            HashMap<String, Double> aporteedible = new HashMap<>();
            //Inicializo en 0 la cuenta de cada valor del atributo i           
            for(String atributo: s){
            	
                cant_atributos.put(atributo,0);
                aportepoisonus.put(atributo, cero);
                aporteedible.put(atributo, cero);
            }
            //Cuento la cantidad de veces que aparece cada valor del atributo i en los ejemplos
            for(Ejemplo e: ejemplos){
            	if (log) System.out.println("11");
	                if(!e.atributos.get(i).equals("?")){
	                	if (log) System.out.println("11");
	                    cant_atributos.put(e.atributos.get(i), cant_atributos.get(e.atributos.get(i))+1);
	                    if (log) System.out.println("12");
	                
	                	//calculo contadores por atributo para calculo de pesos
	                    if(e.poisonus) 
		                {
	                    	if (log) System.out.println("poison");
		                	//calculo cantidad total de poisonus de la muestra
		                	cantPoisonus ++; 
		                	//calculo p(valoratributo,poisonus)
		                	if (log) System.out.println("poison: " + e.atributos.get(i));
		                	aportepoisonus.put(e.atributos.get(i), aportepoisonus.get(e.atributos.get(i))+1);
		                }
		                else 
		                {
		                	if (log) System.out.println("edible");
		                	//calculo cantidad total de edibles de la muestra
		                	cantEdible ++;	
		                	//calculo p(valoratributo,edible)
		                	if (log) System.out.println("edible: " + e.atributos.get(i));
		                	aporteedible.put(e.atributos.get(i), aporteedible.get(e.atributos.get(i))+1);
		                	if (log) System.out.println("15");
		                }
	                }
            	
            }
            //Encuentro el valor mas frecuente para el atributo i
            if (log) System.out.println("14");
            int cantidad_maxima = 0;
            String atributo_mas_frecuente = "?";
            for(String atributo: s){
            	if (log) System.out.println("114");
                if(cant_atributos.get(atributo) > cantidad_maxima){
                    cantidad_maxima = cant_atributos.get(atributo);
                    atributo_mas_frecuente = atributo;
                }
                if (log) System.out.println("141");
                //calculo contadores por atributo para calculo de pesos
                //calculo p(valoratributo,poisonus)
               double pesopoisonus = aportepoisonus.get(atributo)*Math.log(aportepoisonus.get(atributo)/(cant_atributos.get(atributo)*cantPoisonus));// TODO buscar formula logaritmo
                //calculo p(valoratributo,edible)
               if (log) System.out.println("Poison: "+ pesopoisonus);
                double pesoedible = aporteedible.get(atributo)*Math.log(aporteedible.get(atributo)/(cant_atributos.get(atributo)*cantEdible)); // TODO buscar formula logaritmo
                if (log)  System.out.println("Peso Edible "+ pesoedible);
                pesos.put(i,pesos.get(i)+pesopoisonus+pesoedible);
                if (log) System.out.println("Peso atrib: "+ atributo + "peso" + pesos.get(atributo));
            }
            //Seteo el valor mas frecuente
            aux.atributos.put(i, atributo_mas_frecuente);
            //calculo peso del atributo
            
            //pesos(i,());
            
        }
       /* 
        for(int j=0;j<this.pesos.size();j++)
        {
        	
        }*/
        System.out.println("Pesos"+ pesos.toString());
        return aux;
    }
    
    /*****
     * Calcula la distancia entre 2 ejemplos
     * @param a
     * @param b
     * @return 
     */
    public double distancia(Ejemplo a, Ejemplo b){
        double dist = 0;
        for (int i=1; i<=a.atributos.size(); i++){
            String atributo_a = a.atributos.get(i);
            String atributo_b = b.atributos.get(i);
            if(atributo_a.equals("?")){
                atributo_a = comun.atributos.get(i);
            }
            if(atributo_b.equals("?")){
                atributo_b = comun.atributos.get(i);
            }
            if(!atributo_b.equals(atributo_a)){
                dist = dist+1;
            }
        }
        return sqrt(dist); //: TODO Vefiricar si es raiz o hay que elevar al cuadrado
    }
    
    /*****
     * Calcula la distancia entre 2 ejemplos teniendo en cuenta pesos
     * @param a
     * @param b
     * @return 
     */
    public double distanciapesos(Ejemplo a, Ejemplo b){
        double dist = 0;
        for (int i=1; i<=a.atributos.size(); i++){
            String atributo_a = a.atributos.get(i);
            String atributo_b = b.atributos.get(i);
            if(atributo_a.equals("?")){
                atributo_a = comun.atributos.get(i);
            }
            if(atributo_b.equals("?")){
                atributo_b = comun.atributos.get(i);
            }
            if(!atributo_b.equals(atributo_a)){
                dist = dist+(1*pesos.get(a.atributos.get(i)));
            }
        }
        return sqrt(dist);
    }
    
    
    /***
     * Encuentra los k ejemplos mas cercanos a x
     * @param x
     * @return 
     */
    public Map<Double, Ejemplo> k_cercanos(Ejemplo x){
        Map<Double, Ejemplo> cercanos = new HashMap<>(); // :TODO pueden haber 2 distancias iguales? me parece que no es lo mejor la distancia como clave
        double distancia_maxima = distancia(ejemplos.get(0), x);
        for(int i = 1; i<k; i++){
            cercanos.put(distancia(ejemplos.get(i), x), ejemplos.get(i));
            if(distancia(ejemplos.get(i), x) > distancia_maxima){
                distancia_maxima = distancia(ejemplos.get(i), x);
            }
        }
        for(int i = k; i<ejemplos.size(); i++){
            if(distancia(ejemplos.get(i), x) < distancia_maxima){
                cercanos.remove(distancia_maxima);
                cercanos.put(distancia(ejemplos.get(i), x), ejemplos.get(i));
                Set<Double> s = cercanos.keySet();
                double nuevo_maximo = 0;
                for(Double n: s){
                    if(nuevo_maximo < n){
                        nuevo_maximo = n;
                    }
                }
                distancia_maxima = nuevo_maximo;
            }
        }
        return cercanos;
    }
    
    
    /****
     * Estima si x es venenoso o no en base a los datos
     * @param x
     * @return
     */
    public boolean evaluar(Ejemplo x){
        Map<Double, Ejemplo> k_cercanos = this.k_cercanos(x);
        double peso_venenoso = 0;
        double peso_comestible = 0;
        Set<Double> s = k_cercanos.keySet(); 
        for(Double n: s){
            if(n == 0){
                return k_cercanos.get(n).poisonus;
            }
            else if(k_cercanos.get(n).poisonus){
                peso_venenoso = peso_venenoso + (1/(n*n));
            }else{
                peso_comestible = peso_comestible + (1/(n*n));
            }
        }
        return peso_venenoso >= peso_comestible;
    }
    
}
