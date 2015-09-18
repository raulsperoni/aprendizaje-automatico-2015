package practico2;

import java.util.*;


public class Main {


    public static void main(String[] args) {

        if (args.length > 1) {
            int numeroParticiones = Integer.valueOf(args[0]);
            int numeroEjecucionesIndep = Integer.valueOf(args[1]);
            System.out.println("Comienzo ID3, Particiones=" + numeroParticiones + " Ejecuciones=" + numeroEjecucionesIndep);
            ejecucion(numeroParticiones, numeroEjecucionesIndep);

        } else if (args.length > 0) {

            int numeroParticiones = Integer.valueOf(args[0]);
            System.out.println("Comienzo ID3, Particiones=" + numeroParticiones + " Ejecuciones=" + 1);
            ejecucion(numeroParticiones, 1);

        } else {
            System.out.println("Comienzo ID3, Particiones=" + 10 + " Ejecuciones=" + 1);
            ejecucion(10, 1);
        }




    }

    public static void ejecucion(int numeroParticiones, int numeroEjecucionesIndep) {
        System.out.println("MAA 2015");
        System.out.println("Ejecutando ...");
        Experimento valcruzadaTotal = new Experimento(102, 0, 0);
        Experimento entrenamiento2Total = new Experimento(103, 0, 0);
        Experimento valcruzadaparcial = new Experimento(104, 0, 0);
        Experimento entrenamiento2parcial = new Experimento(105, 0, 0);

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
            valcruzadaparcial = run(entrenamiento_total, numeroParticiones);
            //actualizo contadores de entrenamiento general para hacer promedio
            valcruzadaTotal.falsosNegativos += valcruzadaparcial.falsosNegativos;
            valcruzadaTotal.falsosPositivos += valcruzadaparcial.falsosPositivos;
            valcruzadaTotal.verdaderosNegativos += valcruzadaparcial.verdaderosNegativos;
            valcruzadaTotal.verdaderosPositivos += valcruzadaparcial.verdaderosPositivos;
            /**
             * 4/5 vs 1/5
             */
            List<Ejemplo> prueba_total = ejemplos.subList(corte, ejemplos.size());
            entrenamiento2parcial = run(entrenamiento_total, prueba_total);
            //actualizo contadores de entrenamiento general para hacer promedio
            entrenamiento2Total.falsosNegativos += entrenamiento2parcial.falsosNegativos;
            entrenamiento2Total.falsosPositivos += entrenamiento2parcial.falsosPositivos;
            entrenamiento2Total.verdaderosNegativos += entrenamiento2parcial.verdaderosNegativos;
            entrenamiento2Total.verdaderosPositivos += entrenamiento2parcial.verdaderosPositivos;

            //Mezclo ejemplos
            //Collections.shuffle(ejemplos);
        }


        //divido contadores entre cantidad de entrenamientos
        valcruzadaTotal.falsosNegativos = valcruzadaTotal.falsosNegativos / numeroEjecucionesIndep;
        valcruzadaTotal.falsosPositivos = valcruzadaTotal.falsosPositivos / numeroEjecucionesIndep;
        valcruzadaTotal.verdaderosNegativos = valcruzadaTotal.verdaderosNegativos / numeroEjecucionesIndep;
        valcruzadaTotal.verdaderosPositivos = valcruzadaTotal.verdaderosPositivos / numeroEjecucionesIndep;
        //Calculo media de entrenamientos validacion cruzada
        valcruzadaTotal.cantCantEjemplosPrueba = valcruzadaparcial.cantCantEjemplosPrueba;
        valcruzadaTotal.cantEjemplosEntrenamiento = valcruzadaparcial.cantEjemplosEntrenamiento;
        valcruzadaTotal.calcularIndicadores();
        System.out.println("Indicadores Validacion cruzada Total");
        System.out.println(valcruzadaTotal.toString());

        //divido contadores entre cantidad de entrenamientos de entrenamiento2
        entrenamiento2Total.falsosNegativos = entrenamiento2Total.falsosNegativos / numeroEjecucionesIndep;
        entrenamiento2Total.falsosPositivos = entrenamiento2Total.falsosPositivos / numeroEjecucionesIndep;
        entrenamiento2Total.verdaderosNegativos = entrenamiento2Total.verdaderosNegativos / numeroEjecucionesIndep;
        entrenamiento2Total.verdaderosPositivos = entrenamiento2Total.verdaderosPositivos / numeroEjecucionesIndep;
        //Calculo media de entrenamientos sin validacion cruzada
        entrenamiento2Total.cantCantEjemplosPrueba = entrenamiento2parcial.cantCantEjemplosPrueba;
        entrenamiento2Total.cantEjemplosEntrenamiento = entrenamiento2parcial.cantEjemplosEntrenamiento;
        entrenamiento2Total.calcularIndicadores();
        System.out.println("Indicadores entrenamiento 2");
        System.out.println(entrenamiento2Total.toString());
    }

    /**
     * Ejecucion con Validacion Cruzada
     *
     * @param entrenamiento_total
     */
    public static Experimento run(List<Ejemplo> entrenamiento_total, int itervalcruzada) {

        System.out.println("Validacion cruzada tamano " + itervalcruzada);

        //VALIDACION CRUZADA
        ArrayList<List<Ejemplo>> particiones = new ArrayList<>();
        int ini_particion = 0;
        int tam_particion = entrenamiento_total.size() / itervalcruzada;
        for (int i = 0; i < itervalcruzada; i++) {
            particiones.add(entrenamiento_total.subList(ini_particion, ini_particion + tam_particion));
            ini_particion += tam_particion;
        }

        Experimento expTotal = new Experimento(101, entrenamiento_total.size() - tam_particion, tam_particion);

        //PARA CADA PARTICION
        for (int i = 0; i < itervalcruzada; i++) {


            //Tomo el conjunto de prueba i
            List<Ejemplo> prueba_validacion_cruzada = particiones.get(i);

            /*
            //GENERAR FALSOS NEGATIVOS EJEMPLO 1.
                int cont = 0;
                for (Ejemplo e: prueba_validacion_cruzada){
                    if (e.poisonus && e.atributos.get(3).equals("w") && e.atributos.get(22).equals("l")){
                        e.poisonus = false;
                        cont++;
                    }
                }

                System.out.println("FALSOS NEGATIVOS: "+cont);

          
            //GENERAR FALSOS NEGATIVOS EJEMPLO 2.
            int cont2 = 0;
            for (Ejemplo e: prueba_validacion_cruzada){
                if (e.poisonus && e.atributos.get(5).equals("c")){
                    e.poisonus = false;
                    cont2++;
                }
            }

            System.out.println("FALSOS NEGATIVOS: "+cont2);         


            // Generar los otros falsos
             
              int cont = 0;
                for (Ejemplo e: prueba_validacion_cruzada){
                    if (!e.poisonus && e.atributos.get(5).equals("a") && e.atributos.get(20).equals("n")){
                        e.poisonus = true;
                        cont++;
                    }
                }

                System.out.println("FALSOS POSITIVOS: "+cont);
            */
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
            //actualizo contadores de experimento general para hacer promedio
            expTotal.falsosNegativos += exp.falsosNegativos;
            expTotal.falsosPositivos += exp.falsosNegativos;
            expTotal.verdaderosNegativos += exp.verdaderosNegativos;
            expTotal.verdaderosPositivos += exp.verdaderosPositivos;
            System.out.println(exp.toString());


        }
        //divido contadores entre cantidad de experimentos
        expTotal.falsosNegativos = expTotal.falsosNegativos / itervalcruzada;
        expTotal.falsosPositivos = expTotal.falsosNegativos / itervalcruzada;
        expTotal.verdaderosNegativos = expTotal.verdaderosNegativos / itervalcruzada;
        expTotal.verdaderosPositivos = expTotal.verdaderosPositivos / itervalcruzada;
        expTotal.calcularIndicadores();
        System.out.println("Promedio de los resultados");
        System.out.println(expTotal.toString());
        return expTotal;

    }

    /**
     * Evaluo resultado de entrenar con 4/5 con 1/5
     *
     * @param entrenamiento_total
     * @param prueba_total
     */
    public static Experimento run(List<Ejemplo> entrenamiento_total, List<Ejemplo> prueba_total) {
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
        return exp;

    }

    /**
     * Devuelvo los atributos y sus posibles valores segun la documentacion.
     *
     * @return
     */
    public static HashMap<Integer, List<String>> atributos() {
        return AuxLoadData.atributos();
    }
}
