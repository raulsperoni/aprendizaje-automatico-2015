package practico3.naiveBayes;

import practico2.Ejemplo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Probabilidades {

    private final HashMap<Integer, List<String>> atributos;
    private final List<Ejemplo> ejemplosTodos;
    private final List<Ejemplo> ejemplosPoisonus;
    private final List<Ejemplo> ejemplosEdible;

    public Probabilidades(HashMap<Integer, List<String>> atributos, List<Ejemplo> ejemplosTodos) {
        this.atributos = atributos;
        this.ejemplosTodos = ejemplosTodos;
        this.ejemplosPoisonus = new ArrayList<>();
        this.ejemplosEdible = new ArrayList<>();
        for (Ejemplo e : this.ejemplosTodos) {
            if (e.poisonus) this.ejemplosPoisonus.add(e);
            else this.ejemplosEdible.add(e);
        }
    }

    /**
     * Devuelve la probabilidad de resultado.
     * P(resultado)
     *
     * @param resultado True si es Poisonus, False si es edible
     * @return
     */
    public float P(boolean resultado) {
        return resultado ? ejemplosPoisonus.size() / ejemplosTodos.size() : ejemplosEdible.size() / ejemplosTodos.size();
    }

    /**
     * Devuelve la probabilidad de que el atributo valga valorAtributo dado dato.
     * P(atributo=valorAtributo/dato)
     *
     * @param atributo      Es el id del atributo que estoy evaluando.
     * @param valorAtributo Es el valor del atributo
     * @param dato          Es el dato que tengo para calcular la probabilidad.
     * @return
     */
    public float P(int atributo, String valorAtributo, boolean dato) {
        List<Ejemplo> ejemplosARecorrer;
        ejemplosARecorrer = dato ? ejemplosPoisonus : ejemplosEdible;
        int cantAtributosConValorAtributo = 0;
        for (Ejemplo e : ejemplosARecorrer) {
            if (e.atributos.get(atributo) != null && e.atributos.get(atributo).equals(valorAtributo))
                cantAtributosConValorAtributo++;
        }
        return cantAtributosConValorAtributo / ejemplosARecorrer.size();
    }


}
