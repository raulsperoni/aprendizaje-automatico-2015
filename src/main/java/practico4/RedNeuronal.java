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
    List<Double> capaInput;

    /**
     * Constructor
     *
     * @param cantHidden cuantas neuronas hidden
     * @param cantOutput cuantas neuronas output
     * @param sizeInput  cuantos valores en la neurona input
     */
    public RedNeuronal(int cantHidden, int cantOutput, int sizeInput) {
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

    public void backpropagation(List<List<Double>> ejemplosEntrenamiento) {

        //Para cada ejemplo de entrenamiento
        for (int i = 0; i < ejemplosEntrenamiento.size(); i++) {
            this.capaInput = ejemplosEntrenamiento.get(i);
            //Propagar hacia adelante
            //hidden
            List<Double> salidaHidden = new ArrayList<>();
            for (int j = 0; j < capaHidden.size(); j++) {
                salidaHidden.add(j, capaHidden.get(j).getSalida(this.capaInput));
            }
            //output
            List<Double> salidaOutput = new ArrayList<>();
            for (int j = 0; j < capaOutput.size(); j++) {
                salidaOutput.add(j, capaOutput.get(j).getSalida(salidaHidden));
            }

            //Propagar errores hacia atras
            //output
            List<Double> erroresOutput = new ArrayList<>();
            for (int j = 0; j < capaOutput.size(); j++) {
                erroresOutput.add(j, capaOutput.get(j).getError());
            }
            //hidden
            List<Double> erroresHidden = new ArrayList<>();
            for (int j = 0; j < capaHidden.size(); j++) {
                erroresHidden.add(j, capaHidden.get(j).getError(erroresOutput));
            }

            //Actualizar pesos
            //TODO: aca no me queda claro esto:
            // La entrada de la unidad i hacia la j se denota xji, y el peso de i a j se denota wji.


        }

    }
}
