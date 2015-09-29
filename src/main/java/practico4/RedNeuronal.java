/**
 *
 */
package practico4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author emi
 */
public class RedNeuronal<T extends Neurona> {

    final List<T> capaHidden;
    final List<T> capaOutput;
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
    public RedNeuronal(int cantHidden, int cantOutput, int sizeInput, Double aprendizaje, int maxIteraciones, Class cls) throws Exception {
        /**
         * Generics
         */
        Class[] cArg = new Class[3]; //Our constructor has 3 arguments
        cArg[0] = Integer.class; //First argument is of *object* type Long
        cArg[1] = Integer.class; //Second argument is of *object* type String
        cArg[2] = Neurona.TipoNeurona.class; //Third argument is of *primitive* type int
        /**
         * Generics
         */
        this.aprendizaje = aprendizaje;
        int cantNeuronas = 0;
        this.maxIteraciones = maxIteraciones;
        this.capaHidden = new ArrayList<>(cantHidden);
        for (int i = 0; i < cantHidden; i++) {
            this.capaHidden.add(i, (T) cls.getConstructor(cArg).newInstance(cantNeuronas++, sizeInput, Neurona.TipoNeurona.HIDDEN));
        }
        this.capaOutput = new ArrayList<>(cantOutput);
        for (int i = 0; i < cantOutput; i++) {
            this.capaOutput.add(i, (T) cls.getConstructor(cArg).newInstance(cantNeuronas++, cantHidden, Neurona.TipoNeurona.OUTPUT));
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

    public List<Double> backpropagation(List<List<Double>> ejemplosEntrenamiento, List<Double> salidasEsperadas) {

        List<Double> ErrList = new ArrayList<>();

        for (int it = 0; it < maxIteraciones; it++) {
            //Error de todos los ejemplos de todas las unidades de salida
            Double E = 0d;

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
                double valSalidaOutput = 0,valSalidaEsperada = salidasEsperadas.get(i);
                //lista de errores output
                List<Double> erroresOutput = new ArrayList<>();
                //lista de errores hidden
                List<Double> erroresHidden = new ArrayList<>();
                //Double que guarda sumatoria de errores en salida por el peso correspondiente
                for (int k = 0; k < capaOutput.size(); k++) {
                    salidaOutput.add(k, capaOutput.get(k).getSalida(salidaHidden));
                    //calculo cada error de Salida
                    valSalidaOutput = salidaOutput.get(k);
                    //Calculo Sk segun neurona elegida como salida (T4.3)
                    erroresOutput.add(k, capaOutput.get(k).getError(valSalidaOutput, valSalidaEsperada));
                    //calculo E (4.13)
                    E += (valSalidaOutput - valSalidaEsperada) * (valSalidaOutput - valSalidaEsperada);
                }
                //Propagar errores hacia atras
                Double terminoOutputParaElError = 0d;
                //Calcular el error de cada nodo hidden segun neurona elegida.
                for (int h = 0; h < capaHidden.size(); h++) {
                	terminoOutputParaElError = 0d;
                    //Error en salida Sk es el iterador de k de cada unidad de salida 
                	for (int k = 0; k < capaOutput.size(); k++) {
                        //Calculo sumatoria Wkh*Sk
                        terminoOutputParaElError += erroresOutput.get(k) * capaOutput.get(k).pesos.get(h);
                    }
                	//Calculo error en nodo Hidden h G'*SUM(Wkh*Sk) (T4.4)
                	erroresHidden.add(h, capaHidden.get(h).getError(salidaHidden.get(h), terminoOutputParaElError));
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

            ErrList.add(it, E / 2);
            System.out.println("Err: " + E);
        }
        return ErrList;
    }
}
