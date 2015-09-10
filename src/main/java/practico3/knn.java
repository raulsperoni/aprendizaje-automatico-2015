package practico3;

import static java.lang.Math.sqrt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
    public knn(int k, List<Ejemplo> ejemplos){
        this.k = k;
        this.ejemplos = ejemplos;
        this.comun = comunes();
    }
    
    /***
     * Determina el valor mas comun para cada atributo con los datos 
     * @return 
     */
    public Ejemplo comunes(){
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
            for(Ejemplo e: ejemplos){
                if(!e.atributos.get(i).equals("?")){
                    cant_atributos.put(e.atributos.get(i), cant_atributos.get(e.atributos.get(i))+1);
                }
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
            if(atributo_b.equals(atributo_a)){
                dist = dist+1;
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
        Map<Double, Ejemplo> cercanos = new HashMap<>();
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
            if(k_cercanos.get(n).poisonus){
                peso_venenoso = peso_venenoso + (1/(n*n));
            }else{
                peso_comestible = peso_comestible + (1/(n*n));
            }
        }
        return peso_venenoso >= peso_comestible;
    }
    
}
