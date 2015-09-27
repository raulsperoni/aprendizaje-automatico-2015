/**
 *
 */
package practico4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author emi
 */
public class RedNeuronal {

    final List<HiddenSigmoide> capaHidden;
    final List<OutputSigmoide> capaOutput;
    final Double aprendizaje;
    final int maxIteraciones;
    List<Double> capaInput;

    /**
     * Constructor
     *
     * @param cantHidden cuantas neuronas hidden
     * @param cantOutput cuantas neuronas output
     * @param sizeInput  cuantos valores en la neurona input
     */
    public RedNeuronal(int cantHidden, int cantOutput, int sizeInput, Double aprendizaje, int maxIteraciones) {
        this.aprendizaje = aprendizaje;
        int cantNeuronas = 0;
        this.maxIteraciones = maxIteraciones;
        this.capaHidden = new ArrayList<>(cantHidden);
        for (int i = 0; i < cantHidden; i++) {
            this.capaHidden.add(i, new HiddenSigmoide(cantNeuronas++, sizeInput));
        }
        this.capaOutput = new ArrayList<>(cantOutput);
        for (int i = 0; i < cantOutput; i++) {
            this.capaOutput.add(i, new OutputSigmoide(cantNeuronas++, cantHidden));
        }
    }

    public List<Double> evaluar(List<Double> ejemplo) {
        this.capaInput = ejemplo;
        //Propagar hacia adelante
        //lista de salidas hidden
        List<Double> salidaHidden = new ArrayList<>();
        for (int j = 0; j < capaHidden.size(); j++) {
            salidaHidden.add(j, capaHidden.get(j).getSalida(this.capaInput));
        }
        //lista de salidas output
        List<Double> salidaOutput = new ArrayList<>();
        for (int j = 0; j < capaOutput.size(); j++) {
            salidaOutput.add(j, capaOutput.get(j).getSalida(salidaHidden));
        }
        return salidaOutput;
    }

    public void backpropagation(List<List<Double>> ejemplosEntrenamiento, List<Double> salidasEsperadas) {

        for (int it = 0; it < maxIteraciones; it++) {

            List<Double> E = new ArrayList<>();

            //Para cada ejemplo de entrenamiento
            for (int i = 0; i < ejemplosEntrenamiento.size(); i++) {
                this.capaInput = ejemplosEntrenamiento.get(i);
                //Propagar hacia adelante
                //lista de salidas hidden
                List<Double> salidaHidden = new ArrayList<>();
                for (int j = 0; j < capaHidden.size(); j++) {
                    salidaHidden.add(j, capaHidden.get(j).getSalida(this.capaInput));
                }
                //lista de salidas output
                List<Double> salidaOutput = new ArrayList<>();
                for (int j = 0; j < capaOutput.size(); j++) {
                    salidaOutput.add(j, capaOutput.get(j).getSalida(salidaHidden));
                }

                //Propagar errores hacia atras
                //para calcular error E para graficar
                Double ErrTerm = 0d;
                //lista de errores output
                List<Double> erroresOutput = new ArrayList<>();
                Double terminoOutputParaElError = 0d;
                for (int j = 0; j < capaOutput.size(); j++) {
                    //calculo cada error
                    double valSalidaOutput = salidaOutput.get(j);
                    double valSalidaEsperada = salidasEsperadas.get(i);
                    erroresOutput.add(j, capaOutput.get(j).getError(valSalidaOutput, valSalidaEsperada));
                    //armo el termino para calcular el error de cada hidden.
                    for (int k = 0; k < capaOutput.get(j).cantidadEntradas; k++) {
                        terminoOutputParaElError += erroresOutput.get(j) * capaOutput.get(j).pesos.get(k);
                    }
                    //calculo E
                    ErrTerm += (valSalidaOutput - valSalidaEsperada) * (valSalidaOutput - valSalidaEsperada);

                }
                //error E
                E.add(ErrTerm / 2);
                //lista de errores hidden
                List<Double> erroresHidden = new ArrayList<>();
                for (int j = 0; j < capaHidden.size(); j++) {
                    erroresHidden.add(j, capaHidden.get(j).getError(salidaHidden.get(j), terminoOutputParaElError));
                }

                //Actualizar pesos
                //pesos hidden
                for (int j = 0; j < capaHidden.size(); j++) {
                    capaHidden.get(j).actualizarPesos(capaInput, erroresHidden.get(j), aprendizaje);
                }
                //pesos output
                for (int j = 0; j < capaOutput.size(); j++) {
                    capaOutput.get(j).actualizarPesos(salidaHidden, erroresOutput.get(j), aprendizaje);
                }


                System.out.print("# " + i + " #\t" + ErrTerm + "\t");
            }

            System.out.println("");

        }

    }
}
