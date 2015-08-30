package practico2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ID3 {

    public static Subarbol calcular(List<Ejemplo> entrenamiento, List<Integer> atributos) {
        //Crear raiz
        Subarbol raiz = new Subarbol();
        // Si todos los ej. tienen el mismo valor → etiquetar con ese valor
        boolean poison = true, edible = true;
        for (Ejemplo ej : entrenamiento) {
            poison = poison && ej.poisonus;
            edible = edible && !(ej.poisonus);
        }
        if (poison) {
            raiz.hoja = true;
            raiz.poisonus = true;
        } else if (edible) {
            raiz.hoja = true;
            raiz.poisonus = false;
        } else {
            // Si no me quedan atributos→ etiquetar con el valor más común
            if (atributos.isEmpty()) {
                raiz.poisonus = raiz.nodo.cantPoisonous >= raiz.nodo.cantEdible;
                raiz.hoja = true;
            } else {
                // En caso contrario:‣ La raíz pregunte por A, atributo que mejor
                // clasifica los ejemplos‣
                int A = buscarAtributoConMejorGanancia(entrenamiento);
                //Asigno el atributo de decición a la raiz
                raiz.nodo.atributo = A;

                // Para cada valor vi de A ๏Genero una rama๏
                List<String> valoresPosiblesAtributo = Main.atributos().get(A);
                for (int i = 0; i < valoresPosiblesAtributo.size(); i++) {

                    Subarbol rama = new Subarbol();

                    raiz.hijos.add(rama);


                    String vi = valoresPosiblesAtributo.get(i);

                    // Ejemplos vi ={ ejemplos en los cuales A= vi}๏
                    List<Ejemplo> ejemplosConMismoVi = Sv(entrenamiento, A, vi);


                    // Si Ejemplos vi es vacío→ etiquetar con el valor más probable๏
                    if (ejemplosConMismoVi.isEmpty()) {

                    }
                    // En caso contrario→ ID3(Ejemplos vi , Atributos -{A})
                    else {

                        atributos.remove(A);
                        rama.hijos.add(ID3.calcular(ejemplosConMismoVi, atributos));
                    }

                }

            }

        }

        return raiz;

    }

    private static int buscarAtributoConMejorGanancia(List<Ejemplo> S) {
        int mejorAtributo = -1;
        double mejorGanancia = -1;
        Set<Integer> atributos = Main.atributos().keySet();
        for (Integer A : atributos) {
            double gananciaActual = Gain(S, A);
            if (mejorAtributo == -1 || gananciaActual > mejorGanancia) {
                mejorAtributo = A;
                mejorGanancia = gananciaActual;
            }
        }
        return mejorAtributo;
    }

    private static double Entropy(List<Ejemplo> S) {
        int cantPoisonus = 0;
        int cantEdiable = 0;

        for (Ejemplo e : S) {
            if (e.poisonus) cantPoisonus++;
            else cantEdiable++;
        }

        double Ppoisonus = cantPoisonus / S.size();
        double Pediable = cantEdiable / S.size();

        return -Ppoisonus * Math.log(Ppoisonus) - Pediable * Math.log(Pediable);
    }

    private static double Gain(List<Ejemplo> S, int A) {

        double term = 0;
        List<String> valoresPosiblesAtributo = Main.atributos().get(A);
        for (String v : valoresPosiblesAtributo) {
            List<Ejemplo> Sv = Sv(S, A, v);
            term += (Sv.size() / S.size()) * Entropy(Sv);
        }

        return Entropy(S) - term;

    }

    private static List<Ejemplo> Sv(List<Ejemplo> S, int A, String V) {
        List<Ejemplo> Sv = new ArrayList<>();
        for (Ejemplo e : S) {
            if (e.atributos.get(A).equals(V))
                Sv.add(e);
        }
        return Sv;
    }

}
