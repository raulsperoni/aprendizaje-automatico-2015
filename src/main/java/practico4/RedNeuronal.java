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
                //lista de errores hidden
                List<Double> erroresHidden = new ArrayList<>();
                //Double que guarda sumatoria de errores en salida por el peso correspondiente
                Double terminoOutputParaElError = 0d;
                double valSalidaOutput = 0,valSalidaEsperada = salidasEsperadas.get(i);
                //Calcular el error de cada nodo hidden segun neurona elegida.
                for (int h = 0; h < capaHidden.size(); h++) {
                	terminoOutputParaElError = 0d;
                    //Error en salida Sk es el iterador de k de cada unidad de salida 
                	for (int k = 0; k < capaOutput.size(); k++) {
                        //calculo cada error de Salida
                        valSalidaOutput = salidaOutput.get(k);
                        //Calculo Sk segun neurona elegida como salida (T4.3)
                        erroresOutput.add(k, capaOutput.get(k).getError(valSalidaOutput, valSalidaEsperada));	
                        //Calculo sumatoria Wk*Sk
                        terminoOutputParaElError += erroresOutput.get(k) * capaOutput.get(k).pesos.get(k);
                        //calculo E (4.13)
                        ErrTerm += (valSalidaOutput - valSalidaEsperada) * (valSalidaOutput - valSalidaEsperada);
                    }
                	//Calculo error en nodo Hidden h G'*SUM(Wkh*Sk) (T4.4)
                	erroresHidden.add(h, capaHidden.get(h).getError(salidaHidden.get(h), terminoOutputParaElError));
                }
                //error E del ejemplo d indizado por el entero i
                E.add(ErrTerm / 2);
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
