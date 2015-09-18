package practico3.comun;

import java.util.ArrayList;
import java.util.List;


public class Experimento {

    public String nomExperimento;
    public int cantEjemplosEntrenamiento;
    public int cantCantEjemplosPrueba;
    public List<Resultado> resultados = new ArrayList<>();
    public double verdaderosPositivos = 0;
    public double falsosPositivos = 0;
    public double verdaderosNegativos = 0;
    public double falsosNegativos = 0;
    public double precision;
    public double recuperacion;
    public double fallOf;
    public double medidaF;
    public double errors;
    public Confianza intervaloconfianza = new Confianza();
    public long duracion;

    public Experimento(String nomExperimento, int cantEjemplosEntrenamiento, int cantCantEjemplosPrueba) {
        this.nomExperimento = nomExperimento;
        this.cantEjemplosEntrenamiento = cantEjemplosEntrenamiento;
        this.cantCantEjemplosPrueba = cantCantEjemplosPrueba;
    }

    public static String getCabecera() {
        return "nomExperimento" +
                ";duracion" +
                ";cantEjemplosEntrenamiento" +
                ";cantCantEjemplosPrueba" +
                ";verdaderosPositivos" +
                ";falsosPositivos" +
                ";verdaderosNegativos" +
                ";falsosNegativos" +
                ";precision" +
                ";recuperacion" +
                ";medidaF" +
                ";fallOf" +
                ";errors" +
                ";intervaloconfianza\n" ;
    }

    @Override
    public String toString() {
        return "Experimento{" +
                "nomExperimento=" + nomExperimento +
                "; duracion=" + duracion +
                "; cantEjemplosEntrenamiento=" + cantEjemplosEntrenamiento +
                "; cantCantEjemplosPrueba=" + cantCantEjemplosPrueba +
                "; verdaderosPositivos=" + verdaderosPositivos +
                "; falsosPositivos=" + falsosPositivos +
                "; verdaderosNegativos=" + verdaderosNegativos +
                "; falsosNegativos=" + falsosNegativos +
                "; precision=" + precision +
                "; recuperacion=" + recuperacion +
                "; medidaF=" + medidaF +
                "; fallOf=" + fallOf +
                "; errors=" + errors +
                "; intervaloconfianza= [" + intervaloconfianza.x + "," + intervaloconfianza.y + "]" +
                '}';
    }

    
    public String toString2() {
        return nomExperimento +
                ";" + duracion +
                ";" + cantEjemplosEntrenamiento +
                ";" + cantCantEjemplosPrueba +
                ";" + verdaderosPositivos +
                ";" + falsosPositivos +
                ";" + verdaderosNegativos +
                ";" + falsosNegativos +
                ";" + precision +
                ";" + recuperacion +
                ";" + medidaF +
                ";" + fallOf +
                ";" + errors +
                ";[" + intervaloconfianza.x + "," + intervaloconfianza.y + "]\n" ;
    }
    
    public void calcularIndicadores() {
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
        medidaF = 2 * (precision * recuperacion / (precision + recuperacion));
        errors = (falsosPositivos + falsosNegativos)/this.cantEjemplosEntrenamiento;
        intervaloconfianza.x = errors - (1.96) * Math.sqrt((errors * (1 - errors)) / this.cantCantEjemplosPrueba);
        intervaloconfianza.y = errors + (1.96) * Math.sqrt((errors * (1 - errors)) / this.cantCantEjemplosPrueba);

    }

    public static class Resultado {
        public boolean eraPoisonus;
        public boolean seClasificoPoisonus;
    }

    public class Confianza {
        public double x;
        public double y;
    }


}
