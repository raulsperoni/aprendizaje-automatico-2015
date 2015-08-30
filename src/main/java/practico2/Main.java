package practico2;

import java.util.*;

/**
 * Created by raul on 28/08/15.
 */
public class Main {


    public static void main(String[] args) {

        List<Ejemplo> ejemplos = AuxLoadData.retreive();

        Collections.shuffle(ejemplos);
        int corte = (ejemplos.size() * 4) / 5;

        List<Ejemplo> entrenamiento_total = ejemplos.subList(0, corte);

        //VALIDACION CRUZADA
        ArrayList<List<Ejemplo>> particiones = new ArrayList<>();
        int ini_particion = 0;
        for (int i = 0; i < 10; i++) {
            particiones.add(entrenamiento_total.subList(ini_particion, ini_particion + 10));
            ini_particion += 10;
        }

        //PARA CADA PARTICION
        for (int i = 0; i < 10; i++) {

            List<Ejemplo> prueba_validacion_cruzada = particiones.get(i);
            List<Ejemplo> entrenamiento_validacion_cruzada = new ArrayList<>();
            //Hago una copia del conjunto de entrenamiento.
            Collections.copy(entrenamiento_validacion_cruzada, entrenamiento_total);
            //Le saco el conjunto de prueba actual.
            entrenamiento_validacion_cruzada.removeAll(prueba_validacion_cruzada);

            //Llamo a ID3 con el conjunto de entrenamiento.

            //Evaluo el conj de prueba con el resultado de ID3

            //Hago algo con los valores resultantes, media, etc.

        }


    }


    public static HashMap<Integer, List<String>> atributos() {
        HashMap<Integer, List<String>> res = new HashMap<Integer, List<String>>();
        List<String> capshape = Arrays.asList("b", "c", "x", "f", "k", "s");
        res.put(1, capshape);

        List<String> capsurface = Arrays.asList("f", "g", "y", "s");
        res.put(2, capsurface);

        List<String> capcolor = Arrays.asList("n", "b", "c", "g", "r", "p", "u", "e", "w", "y");
        res.put(3, capcolor);

        List<String> bruises = Arrays.asList("t", "f");
        res.put(4, bruises);

        List<String> odor = Arrays.asList("a", "l", "c", "y", "f", "m", "n", "p", "c");
        res.put(5, odor);

        List<String> gillatachment = Arrays.asList("a", "d", "f", "n");
        res.put(6, gillatachment);

        List<String> gillspacing = Arrays.asList("c", "w", "d");
        res.put(7, gillspacing);

        List<String> gillsize = Arrays.asList("b", "n");
        res.put(8, gillsize);

        List<String> gillcolor = Arrays.asList("k", "n", "b", "h", "g", "r", "o", "p", "u", "e");
        res.put(9, gillcolor);

        List<String> stalkshape = Arrays.asList("n", "b", "c", "g", "r", "p", "u", "e", "w", "y");
        res.put(10, stalkshape);

        List<String> stalckroot = Arrays.asList("b", "c", "u", "e", "z", "r", "?"); //TODO: ver esto
        res.put(11, stalckroot);

        List<String> stacksurfaceabovering = Arrays.asList("f", "y", "k", "s");
        res.put(12, stacksurfaceabovering);

        List<String> stacksurfacebelowring = Arrays.asList("f", "y", "k", "s");
        res.put(13, stacksurfacebelowring);

        List<String> stackcolorabovering = Arrays.asList("n", "b", "c", "g", "o", "p", "e", "w", "y");
        res.put(14, stackcolorabovering);

        List<String> stackcolorbelowring = Arrays.asList("n", "b", "c", "g", "o", "p", "e", "w", "y");
        res.put(15, stackcolorbelowring);

        List<String> veiltype = Arrays.asList("p", "u");
        res.put(16, veiltype);

        List<String> vailcolor = Arrays.asList("n", "o", "w", "y");
        res.put(17, vailcolor);

        List<String> ringnumber = Arrays.asList("n", "o", "t");
        res.put(18, ringnumber);

        List<String> ringtype = Arrays.asList("p", "u");
        res.put(19, ringtype);

        List<String> sporeprintcolor = Arrays.asList("k", "n", "b", "h", "r", "o", "u", "w", "y");
        res.put(20, sporeprintcolor);

        List<String> population = Arrays.asList("a", "c", "n", "s", "v", "y");
        res.put(21, population);

        List<String> habitat = Arrays.asList("g", "l", "m", "p", "u", "w", "d");
        res.put(22, habitat);

        return res;
    }
}
