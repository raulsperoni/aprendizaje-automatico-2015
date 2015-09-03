package practico2;

import java.util.ArrayList;
import java.util.List;

public class ID3 {

    private static boolean LOG = false;

    /**
     * Construyo un arbol a partir del conjunto de entrenamiento.
     *
     * @param entrenamiento
     * @param atributos
     * @return
     */
    public static Subarbol calcular(List<Ejemplo> entrenamiento, List<Integer> atributos) {
        //Crear raiz
        Subarbol raiz = new Subarbol();
        // Si todos los ej. tienen el mismo valor → etiquetar con ese valor        
        boolean poison = true, edible = true;
        for (Ejemplo ej : entrenamiento) {
            poison = poison && ej.poisonus;
            edible = edible && !(ej.poisonus);
            if (ej.poisonus) raiz.cantEjemplosPoisonus++;
            else raiz.cantEjemplosEdiable++;
        }
        if (poison) {
            raiz.hoja = new Hoja();
            raiz.hoja.poisonus = true;
            raiz.atributoDecision = null;
            printTree("TODOS EJEMPLOS SON POISONUS", 0);
            printTree("HOJA: POISONUS", atributos.size());
        } else if (edible) {
            raiz.hoja = new Hoja();
            raiz.hoja.poisonus = false;
            raiz.atributoDecision = null;
            printTree("TODOS EJEMPLOS SON EDIABLE", 0);
            printTree("HOJA: EDIABLE", atributos.size());
        } else {
            // Si no me quedan atributos→ etiquetar con el valor más común
            if (atributos.isEmpty()) {
                raiz.hoja = new Hoja();
                raiz.hoja.poisonus = raiz.cantEjemplosPoisonus >= raiz.cantEjemplosEdiable;
                raiz.atributoDecision = null;
                printTree("ETIQUETO CON VAL MAS COMUN", 0);
                if (raiz.hoja.poisonus) printTree("HOJA: POISONUS", atributos.size());
                else printTree("HOJA: EDIABLE", atributos.size());
            } else {
                // En caso contrario:‣ La raíz pregunte por A, atributo que mejor
                // clasifica los ejemplos‣
                Integer A = buscarAtributoConMejorGanancia(entrenamiento, atributos);
                //Asigno el atributo de decición a la raiz
                raiz.atributoDecision = A;
                //Estoy sacando el objeto Integer A, no el indice.
                atributos.remove(A);
                printTree("ELIJO ATTR CLASIFIQUE MEJOR EJS", 0);
                printTree("NODO: #ATTRS=" + atributos.size() + " A=" + A, atributos.size());

                // Para cada valor vi de A ๏Genero una rama๏
                List<String> valoresPosiblesAtributo = Main.atributos().get(A);
                for (int i = 0; i < valoresPosiblesAtributo.size(); i++) {
                	printTree("FOR: Valor ATRR Padre=" + raiz.atributoDecision, atributos.size());
                	printTree("CANT EJEMPLOS= " + entrenamiento.size(), 0);
                	printTree("PRUEBO CON: "+ valoresPosiblesAtributo.get(i), 0);
                    Subarbol rama = new Subarbol();
                    raiz.hijos.add(rama);

                    rama.valorAtributoDelPadre = valoresPosiblesAtributo.get(i);

                    // Ejemplos vi ={ ejemplos en los cuales A= vi}๏
                    List<Ejemplo> ejemplosConMismoVi = Sv(entrenamiento, A, rama.valorAtributoDelPadre);
                    printTree("RAMA: Valor ATRR Padre=" + rama.valorAtributoDelPadre, atributos.size());
                    printTree("CANT EJEMPLOS MISMO Vi= " + ejemplosConMismoVi.size(), 0);

                    // Si Ejemplos vi es vacío→ etiquetar con el valor más probable๏
                    //Para elegir el valor se toman en cuenta el subconjunto de ejemplos analizados hasta el momento
                    if (ejemplosConMismoVi.isEmpty()) {
                    	printTree("NINGUN EJEMPLO CON MISMO Vi", 0);
	                   	 if (raiz.cantEjemplosPoisonus>raiz.cantEjemplosEdiable)
	                   	 {
	                         rama.hoja = new Hoja();
	                         rama.hoja.poisonus = true;
	                         rama.atributoDecision = null;
	                         printTree("HOJA: POISONUS = Sv Vacio", atributos.size());
	                     } 
                         else
	                   	 {
	                         rama.hoja = new Hoja();
	                         rama.hoja.poisonus = false;
	                         rama.atributoDecision = null;
                             printTree("HOJA: EDIABLE = Sv Vacio", atributos.size());
                         }
                    }
                    // En caso contrario→ ID3(Ejemplos vi , Atributos -{A})
                    else {
                    	printTree("ID3 RECURSIVO", 0);
                        rama.hijos.add(ID3.calcular(ejemplosConMismoVi, atributos));
                    }

                }

            }

        }

        return raiz;

    }

    /**
     * Evaluo el ejemplo con el arbol.
     * @param ejemploAEvaluar
     * @param arbolDecision
     * @return
     * @throws Exception
     */
    public static boolean evaluar(Ejemplo ejemploAEvaluar, Subarbol arbolDecision) throws Exception {
        if (arbolDecision == null)
            throw new Exception("Arbol null");

        //Paso base, llegue a una hoja.
        if (arbolDecision.hoja != null) {
            return arbolDecision.hoja.poisonus;
        } else {
            //Busco el valor de ejemplo en el atributo de este paso del arbol
            String valorDelEjemplo = ejemploAEvaluar.atributos.get(arbolDecision.atributoDecision);
            Subarbol ramaCorrecta = null;
            //Este es el caso en que este valor no fue observado en el entrenamiento para este atributo.
            if (valorDelEjemplo == null) { //: TODO aca me parece si valor es null es porque el ejemplo no tiene el atributo buscado, como se sabe que esa es la rama correcta? no debería evaluar todas las ramas?
                ramaCorrecta = arbolDecision.hijos.get(0);
            }
            //Caso normal
            else {
                for (Subarbol rama : arbolDecision.hijos) {
                    if (rama.valorAtributoDelPadre.equals(valorDelEjemplo)) {
                        ramaCorrecta = rama;
                        break;
                    }
                }
            }
            return evaluar(ejemploAEvaluar, ramaCorrecta);
        }


    }

    /**
     * El mejor atributo de S
     * @param S
     * @return
     */
    private static int buscarAtributoConMejorGanancia(List<Ejemplo> S, List<Integer> atributos) {
        int mejorAtributo = -1;
        double mejorGanancia = -1;
        for (Integer A : atributos) {
            double gananciaActual = Gain(S, A);
            if (mejorAtributo == -1 || gananciaActual > mejorGanancia) {
                mejorAtributo = A;
                mejorGanancia = gananciaActual;
            }
        }
        return mejorAtributo;
    }

    /**
     * Entropia de S
     * @param S
     * @return
     */
    private static double Entropy(List<Ejemplo> S) {

        if (S.size() == 0) return 0.0;

        double cantPoisonus = 0;
        double cantEdiable = 0;

        for (Ejemplo e : S) {
            if (e.poisonus) cantPoisonus++;
            else cantEdiable++;
        }

        double Ppoisonus = (cantPoisonus / S.size());
        double Pediable = (cantEdiable / S.size());

        double res1 = -Ppoisonus * Math.log(Ppoisonus);
        if (Ppoisonus == 0.0) res1 = 0;
        double res2 = -Pediable * Math.log(Pediable);
        if (Pediable == 0.0) res2 = 0;

        return res1 + res2;
    }

    /**
     * Ganancia de S respecto de atributo A
     * @param S
     * @param A
     * @return
     */
    private static double Gain(List<Ejemplo> S, int A) {

        double term = 0;
        List<String> valoresPosiblesAtributo = Main.atributos().get(A);
        for (String v : valoresPosiblesAtributo) {
            List<Ejemplo> Sv = Sv(S, A, v);
            double x = ((double) Sv.size() / S.size()) * Entropy(Sv);
            term += x;
        }

        return Entropy(S) - term;

    }

    /**
     * Devuelvo los ejemplos que para el atributo A tienen valor V
     * @param S
     * @param A
     * @param V
     * @return
     */
    private static List<Ejemplo> Sv(List<Ejemplo> S, int A, String V) {
        List<Ejemplo> Sv = new ArrayList<>();
        for (Ejemplo e : S) {
            if (e.atributos.get(A).equals(V))
                Sv.add(e);
        }
        return Sv;
    }

    /**
     * Imprimir arbol con indentacion
     * @param text
     * @param atributosSize
     */
    private static void printTree(String text, int atributosSize) {
        atributosSize = 22 - atributosSize;
        if (LOG)
            System.out.println(String.format("%" + (2 * atributosSize) + "s", "") + text);

    }

}
