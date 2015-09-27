/**
 * 
 */
package practico4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author emi
 *
 */
public class RedNeuronal {

    final List<HiddenSigmoide> capaHidden;
    final List<OutputSigmoide> capaOutput;
    final Double aprendizaje;
    List<Double> capaInput;

    /**
     * Constructor
     *
     * @param cantHidden cuantas neuronas hidden
     * @param cantOutput cuantas neuronas output
     * @param sizeInput  cuantos valores en la neurona input
     */
    public RedNeuronal(int cantHidden, int cantOutput, int sizeInput, Double aprendizaje) {
        this.aprendizaje = aprendizaje;
        int cantNeuronas = 0;
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

        //TODO: aca va un for con las iteraciones?

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
            //lista de errores output
            List<Double> erroresOutput = new ArrayList<>();
            Double terminoOutputParaElError = 0d;
            for (int j = 0; j < capaOutput.size(); j++) {
                //calculo cada error
                erroresOutput.add(j, capaOutput.get(j).getError(salidaOutput.get(j), salidasEsperadas.get(i)));
                //armo el termino para calcular el error de cada hidden.
                for (int k = 0; k < capaOutput.get(j).cantidadEntradas; k++) {
                    terminoOutputParaElError += erroresOutput.get(j) * capaOutput.get(j).pesos.get(k);
                }
            }
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


        }

    }
}
