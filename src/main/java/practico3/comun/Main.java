package practico3.comun;

import practico3.knn.knn;
import practico3.naiveBayes.naiveBayes;

import java.util.*;


public class Main {


    public static void main(String[] args) {

        List<Algoritmo> listalgoritmos = new ArrayList<>();
        if (args.length > 3) {
            int algoritmos = Integer.valueOf(args[0]);
            int numeroParticiones = Integer.valueOf(args[1]);
            int numeroEjecucionesIndep = Integer.valueOf(args[2]);
            int k = Integer.valueOf(args[3]);
            System.out.println("Comienzo, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep + " K=" + k + "ALGOS: " + algoritmosAEjecutar(listalgoritmos, algoritmos));
            ejecucion(numeroParticiones, numeroEjecucionesIndep, k, listalgoritmos);
        } else if (args.length > 2) {
            int algoritmos = Integer.valueOf(args[0]);
            int numeroParticiones = Integer.valueOf(args[1]);
            int numeroEjecucionesIndep = Integer.valueOf(args[2]);
            int k = 3;
            System.out.println("Comienzo, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep + " K=" + k + "ALGOS: " + algoritmosAEjecutar(listalgoritmos, algoritmos));
            ejecucion(numeroParticiones, numeroEjecucionesIndep, k, listalgoritmos);
        } else if (args.length > 1) {
            int algoritmos = Integer.valueOf(args[0]);
            int numeroParticiones = Integer.valueOf(args[1]);
            int numeroEjecucionesIndep = 1;
            int k = 3;
            System.out.println("Comienzo, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep + " K=" + k + "ALGOS: " + algoritmosAEjecutar(listalgoritmos, algoritmos));
            ejecucion(numeroParticiones, numeroEjecucionesIndep, k, listalgoritmos);
        } else if (args.length > 0) {
            int algoritmos = Integer.valueOf(args[0]);
            int numeroParticiones = 10;
            int numeroEjecucionesIndep = 1;
            int k = 3;
            System.out.println("Comienzo, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep + " K=" + k + "ALGOS: " + algoritmosAEjecutar(listalgoritmos, algoritmos));
            ejecucion(numeroParticiones, numeroEjecucionesIndep, k, listalgoritmos);
        } else {
            int algoritmos = 4;
            int numeroParticiones = 10;
            int numeroEjecucionesIndep = 1;
            int k = 3;
            System.out.println("Comienzo, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep + " K=" + k + "ALGOS: " + algoritmosAEjecutar(listalgoritmos, algoritmos));
            ejecucion(numeroParticiones, numeroEjecucionesIndep, k, listalgoritmos);
        }


    }

    public static String algoritmosAEjecutar(List<Algoritmo> listalgoritmos, int algoritmos) {
        String nombresalgos = "";
        switch (algoritmos) {
            case 1:
                listalgoritmos.add(Algoritmo.ID3);
                nombresalgos += Algoritmo.ID3.name();
                break;
            case 2:
                listalgoritmos.add(Algoritmo.KNN);
                nombresalgos += Algoritmo.KNN.name();
                break;
            case 3:
                listalgoritmos.add(Algoritmo.NAIVEBAYES);
                nombresalgos += Algoritmo.NAIVEBAYES.name();
                break;
            default:
                listalgoritmos.add(Algoritmo.ID3);
                listalgoritmos.add(Algoritmo.KNN);
                listalgoritmos.add(Algoritmo.NAIVEBAYES);
                nombresalgos += Algoritmo.ID3.name() + " " + Algoritmo.KNN.name() + " " + Algoritmo.NAIVEBAYES.name();
        }
        return nombresalgos;
    }

    public static void ejecucion(int numeroParticiones, int numeroEjecucionesIndep, int k, List<Algoritmo> algoritmosAEvaluar) {
        System.out.println("MAA 2015");
        System.out.println("Ejecutando ...");

        HashMap<Algoritmo, List<Experimento>> experimentosValidacionCruzada;
        HashMap<Algoritmo, Experimento> experimentosEvaluacionTotal;


        for (int i = 0; i < numeroEjecucionesIndep; i++) {
            System.out.println("Iteracion " + i);
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
            experimentosValidacionCruzada = runValidacionCruzada(entrenamiento_total, numeroParticiones, k, algoritmosAEvaluar);

            /**
             * 4/5 vs 1/5
             */
            List<Ejemplo> prueba_total = ejemplos.subList(corte, ejemplos.size());
            experimentosEvaluacionTotal = runAlgoritmoTotal(entrenamiento_total, prueba_total, k, algoritmosAEvaluar);

        }


    }

    /**
     * Ejecucion con Validacion Cruzada
     *
     * @param entrenamiento_total
     * @param itervalcruzada
     * @param k
     */
    public static HashMap<Algoritmo, List<Experimento>> runValidacionCruzada(List<Ejemplo> entrenamiento_total, int itervalcruzada, int k, List<Algoritmo> algoritmosAEvaluar) {

        HashMap<Algoritmo, List<Experimento>> res = new HashMap<>();
        res.put(Algoritmo.KNN, new ArrayList<Experimento>());
        res.put(Algoritmo.NAIVEBAYES, new ArrayList<Experimento>());
        res.put(Algoritmo.ID3, new ArrayList<Experimento>());

        System.out.println("Validacion cruzada tamano " + itervalcruzada);

        //VALIDACION CRUZADA
        ArrayList<List<Ejemplo>> particiones = new ArrayList<>();
        int ini_particion = 0;
        int tam_particion = entrenamiento_total.size() / itervalcruzada;
        for (int i = 0; i < itervalcruzada; i++) {
            particiones.add(entrenamiento_total.subList(ini_particion, ini_particion + tam_particion));
            ini_particion += tam_particion;
        }

        //PARA CADA PARTICION
        for (int i = 0; i < itervalcruzada; i++) {

            //Tomo el conjunto de prueba i
            List<Ejemplo> prueba_validacion_cruzada = particiones.get(i);

            //Hago una copia del conjunto de entrenamiento.
            List<Ejemplo> entrenamiento_validacion_cruzada = new ArrayList<>(entrenamiento_total);
            //Le resto el conjunto de prueba actual al de entrenamiento.
            entrenamiento_validacion_cruzada.removeAll(prueba_validacion_cruzada);

            if (algoritmosAEvaluar.contains(Algoritmo.KNN)) {
                //Evaluo el conj de prueba con el resultado de KNN
                knn KNN = new knn(k, entrenamiento_total);
                Experimento exp = new Experimento("KNN TOTAL", entrenamiento_validacion_cruzada.size(), prueba_validacion_cruzada.size());
                evaluarKNN(exp, prueba_validacion_cruzada, KNN);
                exp.calcularIndicadores();
                //System.out.println(exp.toString());
                res.get(Algoritmo.KNN).add(exp);
            }

            if (algoritmosAEvaluar.contains(Algoritmo.NAIVEBAYES)) {
                //Evaluo el conj de prueba con el resultado de KNN
                Set<Integer> attrs = atributos().keySet();
                List<Integer> attrsList = new ArrayList<>();
                attrsList.addAll(attrs);
                naiveBayes NB = new naiveBayes(entrenamiento_total, atributos());
                Experimento exp = new Experimento("NB TOTAL", entrenamiento_validacion_cruzada.size(), prueba_validacion_cruzada.size());
                evaluarNB(exp, prueba_validacion_cruzada, NB);
                exp.calcularIndicadores();
                //System.out.println(exp.toString());
                res.get(Algoritmo.NAIVEBAYES).add(exp);
            }
        }
        return res;
    }

    /**
     * Evaluo resultado de entrenar con 4/5 con 1/5
     *
     * @param entrenamiento_total
     * @param prueba_total
     */
    public static HashMap<Algoritmo, Experimento> runAlgoritmoTotal(List<Ejemplo> entrenamiento_total, List<Ejemplo> prueba_total, int k, List<Algoritmo> algoritmosAEvaluar) {
        System.out.println("Evaluo resultado de entrenar con 4/5 con 1/5");
        HashMap<Algoritmo, Experimento> res = new HashMap<>();

        if (algoritmosAEvaluar.contains(Algoritmo.KNN)) {
            //Evaluo el conj de prueba con el resultado de KNN
            knn KNN = new knn(k, entrenamiento_total);
            Experimento exp = new Experimento("KNN TOTAL", entrenamiento_total.size(), prueba_total.size());
            evaluarKNN(exp, prueba_total, KNN);
            exp.calcularIndicadores();
            //System.out.println(exp.toString());
            res.put(Algoritmo.KNN, exp);
        }

        if (algoritmosAEvaluar.contains(Algoritmo.NAIVEBAYES)) {
            //Evaluo el conj de prueba con el resultado de KNN
            Set<Integer> attrs = atributos().keySet();
            List<Integer> attrsList = new ArrayList<>();
            attrsList.addAll(attrs);
            naiveBayes NB = new naiveBayes(entrenamiento_total, atributos());
            Experimento exp = new Experimento("NB TOTAL", entrenamiento_total.size(), prueba_total.size());
            evaluarNB(exp, prueba_total, NB);
            exp.calcularIndicadores();
            //System.out.println(exp.toString());
            res.put(Algoritmo.NAIVEBAYES, exp);
        }


        return res;

    }

    private static Experimento calcularMedia(String nombre, List<Experimento> experimentos, int totalEntrenamiento, int totalPrueba) {
        Experimento resultado = new Experimento(nombre, totalEntrenamiento, totalPrueba);
        for (Experimento e : experimentos) {
            resultado.falsosNegativos += e.falsosNegativos;
            resultado.falsosPositivos += e.falsosPositivos;
            resultado.verdaderosNegativos += e.verdaderosNegativos;
            resultado.verdaderosPositivos += e.verdaderosPositivos;
        }
        resultado.falsosNegativos = resultado.falsosNegativos / experimentos.size();
        resultado.falsosNegativos = resultado.falsosPositivos / experimentos.size();
        resultado.falsosNegativos = resultado.verdaderosNegativos / experimentos.size();
        resultado.falsosNegativos = resultado.verdaderosPositivos / experimentos.size();
        return resultado;
    }

    private static void evaluarKNN(Experimento exp, List<Ejemplo> ejemplosAEvaluar, knn KNN) {
        for (Ejemplo e : ejemplosAEvaluar) {
            Experimento.Resultado res = new Experimento.Resultado();
            res.eraPoisonus = e.poisonus;
            try {
                res.seClasificoPoisonus = KNN.evaluar(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            exp.resultados.add(res);
        }
    }

    private static void evaluarNB(Experimento exp, List<Ejemplo> ejemplosAEvaluar, naiveBayes NB) {
        for (Ejemplo e : ejemplosAEvaluar) {
            Experimento.Resultado res = new Experimento.Resultado();
            res.eraPoisonus = e.poisonus;
            try {
                res.seClasificoPoisonus = NB.evaluar(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            exp.resultados.add(res);
        }
    }

    /**
     * Devuelvo los atributos y sus posibles valores segun la documentacion.
     *
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

        List<String> stalckroot = Arrays.asList("b", "c", "u", "e", "z", "r");
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
