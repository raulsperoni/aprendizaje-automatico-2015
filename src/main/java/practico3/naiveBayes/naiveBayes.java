package practico3.naiveBayes;


import practico3.comun.Ejemplo;

import java.util.HashMap;
import java.util.List;

public class naiveBayes {

    final List<Ejemplo> ejemplos;
    final Probabilidades probabilidades;
    final float Ppoisonus;
    final float Pedible;
    final HashMap<Integer, List<String>> atributos;


    public naiveBayes(List<Ejemplo> ejemplos, HashMap<Integer, List<String>> atributos) {
        this.ejemplos = ejemplos;
        this.probabilidades = new Probabilidades(atributos, ejemplos);
        this.atributos = atributos;
        this.Ppoisonus = probabilidades.P(true);
        this.Pedible = probabilidades.P(false);
    }

    /**
     * Estima si es venenoso o no en base a los datos.
     * Busco vj tal que maximize (P(a1/vj)*..*P(an/vj))*P(vj))
     *
     * @return
     */
    public boolean evaluar(Ejemplo ejemplo) {
        float resultadoDadoPoisonus = Ppoisonus;
        float resultadoDadoEdible = Pedible;
        //Para cada atributo
        for (Integer a : atributos.keySet()) {
            resultadoDadoPoisonus *= probabilidades.P(a, ejemplo.atributos.get(a), true);
            resultadoDadoEdible *= probabilidades.P(a, ejemplo.atributos.get(a), false);
        }
        return resultadoDadoPoisonus > resultadoDadoEdible;
    }
}