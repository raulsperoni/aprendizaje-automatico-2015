package practico2;

import java.util.*;


public class Main {


    public static void main(String[] args) {

        System.out.println("MAA 2015");
        System.out.println("Ejecutando ...");

        //Cargo datos
        List<Ejemplo> ejemplos = AuxLoadData.retreive();
        //Mezclo ejemplos
        Collections.shuffle(ejemplos);
        //Defino corte entrenamiento/prueba
        int corte = (ejemplos.size() * 4) / 5;
        //Conjunto Entrenamiento
        List<Ejemplo> entrenamiento_total = ejemplos.subList(0, corte);

        /**
         * VALIDACION CRUZADA
         */
        run(entrenamiento_total);

        /**
         * 4/5 vs 1/5
         */
        List<Ejemplo> prueba_total = ejemplos.subList(corte, ejemplos.size());
        run(entrenamiento_total, prueba_total);


    }

    /**
     * Ejecucion con Validacion Cruzada
     *
     * @param entrenamiento_total
     */
    public static void run(List<Ejemplo> entrenamiento_total) {
        System.out.println("Validacion cruzada tamano 10");

        //VALIDACION CRUZADA
        ArrayList<List<Ejemplo>> particiones = new ArrayList<>();
        int ini_particion = 0;
        for (int i = 0; i < 10; i++) {
            particiones.add(entrenamiento_total.subList(ini_particion, ini_particion + 10));// :TODO solo 10 ejemplos por particion?
            ini_particion += 10;
        }

        //PARA CADA PARTICION
        for (int i = 0; i < 10; i++) {

            //Tomo el conjunto de prueba i
            List<Ejemplo> prueba_validacion_cruzada = particiones.get(i);
            //Hago una copia del conjunto de entrenamiento.
            List<Ejemplo> entrenamiento_validacion_cruzada = new ArrayList<>(entrenamiento_total);
            //Le resto el conjunto de prueba actual al de entrenamiento.
            entrenamiento_validacion_cruzada.removeAll(prueba_validacion_cruzada);

            //Llamo a ID3 con el conjunto de entrenamiento.
            Set<Integer> attrs = atributos().keySet();
            List<Integer> attrsList = new ArrayList<>();
            attrsList.addAll(attrs);
            Subarbol root = ID3.calcular(entrenamiento_validacion_cruzada, attrsList);

            //Evaluo el conj de prueba con el resultado de ID3
            Experimento exp = new Experimento(i, entrenamiento_validacion_cruzada.size(), prueba_validacion_cruzada.size());
            for (Ejemplo e : prueba_validacion_cruzada) {
                Experimento.Resultado res = new Experimento.Resultado();
                res.eraPoisonus = e.poisonus;
                try {
                    res.seClasificoPoisonus = ID3.evaluar(e, root);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                exp.resultados.add(res);
            }
            exp.calcularIndicadores();
            System.out.println(exp.toString());

            //Hago algo con los valores resultantes, media, etc.
            //TODO: hacer

        }


    }

    /**
     * Evaluo resultado de entrenar con 4/5 con 1/5
     *
     * @param entrenamiento_total
     * @param prueba_total
     */
    public static void run(List<Ejemplo> entrenamiento_total, List<Ejemplo> prueba_total) {
        System.out.println("Evaluo resultado de entrenar con 4/5 con 1/5");

        //Llamo a ID3 con el conjunto de entrenamiento.
        Set<Integer> attrs = atributos().keySet();
        List<Integer> attrsList = new ArrayList<>();
        attrsList.addAll(attrs);
        Subarbol root = ID3.calcular(entrenamiento_total, attrsList);

        //Evaluo el conj de prueba con el resultado de ID3
        Experimento exp = new Experimento(100, entrenamiento_total.size(), prueba_total.size());
        for (Ejemplo e : prueba_total) {
            Experimento.Resultado res = new Experimento.Resultado();
            res.eraPoisonus = e.poisonus;
            try {
                res.seClasificoPoisonus = ID3.evaluar(e, root);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            exp.resultados.add(res);
        }
        exp.calcularIndicadores();
        System.out.println(exp.toString());

        //Hago algo con los valores resultantes, media, etc.
        //TODO: hacer

    }

    /**
     * Devuelvo los atributos y sus posibles valores segun la documentacion.
     * @return
     */
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

        List<String> odor = Arrays.asList("a", "l", "c", "y", "f", "m", "n", "p", "s");
        res.put(5, odor);

        List<String> gillatachment = Arrays.asList("a", "d", "f", "n");
        res.put(6, gillatachment);

        List<String> gillspacing = Arrays.asList("c", "w", "d");
        res.put(7, gillspacing);

        List<String> gillsize = Arrays.asList("b", "n");
        res.put(8, gillsize);

        List<String> gillcolor = Arrays.asList("k", "n", "b", "h", "g", "r", "o", "p", "u", "e", "w", "y");
        res.put(9, gillcolor);

        List<String> stalkshape = Arrays.asList("e", "t");
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

        List<String> ringtype = Arrays.asList("c", "e", "f", "l", "n", "p", "s", "z");
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
