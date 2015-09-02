package practico2;

import java.util.ArrayList;
import java.util.List;


public class Experimento {

    public int numExperimento;
    public int cantEjemplosEntrenamiento;
    public int cantCantEjemplosPrueba;
    public List<Resultado> resultados = new ArrayList<>();
    public float precision;
    public float recuperacion;
    public float fallOf;

    public Experimento(int numExperimento, int cantEjemplosEntrenamiento, int cantCantEjemplosPrueba) {
        this.numExperimento = numExperimento;
        this.cantEjemplosEntrenamiento = cantEjemplosEntrenamiento;
        this.cantCantEjemplosPrueba = cantCantEjemplosPrueba;
    }

    @Override
    public String toString() {
        return "Experimento{" +
                "numExperimento=" + numExperimento +
                ", cantEjemplosEntrenamiento=" + cantEjemplosEntrenamiento +
                ", cantCantEjemplosPrueba=" + cantCantEjemplosPrueba +
                ", precision=" + precision +
                ", recuperacion=" + recuperacion +
                ", fallOf=" + fallOf +
                '}';
    }

    public void calcularIndicadores() {
        int verdaderosPositivos = 0;
        int falsosPositivos = 0;
        int verdaderosNegativos = 0;
        int falsosNegativos = 0;
        for (Resultado r : resultados) {
            if (r.eraPoisonus && r.seClasificoPoisonus)
                verdaderosPositivos++;
            else if (r.eraPoisonus && !r.seClasificoPoisonus)
                falsosNegativos++;
            else if (!r.eraPoisonus && r.seClasificoPoisonus)
                falsosPositivos++;
            else if (!r.eraPoisonus && !r.seClasificoPoisonus)
                verdaderosNegativos++;
        }


        precision = verdaderosPositivos / (verdaderosPositivos + falsosPositivos);
        recuperacion = verdaderosPositivos / (verdaderosPositivos + falsosNegativos);
        fallOf = falsosPositivos / (falsosPositivos + verdaderosNegativos);


    }

    public static class Resultado {
        public boolean eraPoisonus;
        public boolean seClasificoPoisonus;
    }


}
