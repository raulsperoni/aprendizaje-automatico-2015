package practico2;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import practico1.Ejemplo;
import practico1.Subarbol;

/**
 * Created by raul on 28/08/15.
 */
public class Main {
    public static HashMap<Integer,List<String>> atributos= new HashMap<>();    		
    public static void main(String[] args) {
 
        List<Ejemplo> ejemplos = AuxLoadData.retreive();

        //ESTO ES SIN REF CRUZADA.
        Collections.shuffle(ejemplos);
        int corte = (ejemplos.size() * 4) / 5;

        List<Ejemplo> entrenamiento = ejemplos.subList(0, corte);
        
        //// ID3 ////
        
     
}
