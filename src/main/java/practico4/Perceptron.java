package practico4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RSperoni on 23/09/2015.
 */
public class Perceptron {

    final float umbral;
    final float tasa_aprendizaje;
    List<Float> pesos;

    public Perceptron(float umbral, float tasa_aprendizaje, int tamano) {
        this.umbral = umbral;
        this.tasa_aprendizaje = tasa_aprendizaje;

        //inicializo pesos.
        this.pesos = new ArrayList<>(tamano);
        for (int i = 0; i < tamano; i++) {
            this.pesos.add(0.0f);
        }
    }

    public void entrenar(List<List<Float>> ejemplos, List<Boolean> resultadosEsperados) throws Exception {
        while (true) {
            int contador_errores = 0;
            for (int i = 0; i < ejemplos.size(); i++) {
                List<Float> ejemplo = ejemplos.get(i);
                double resultado = getProductoInterno(ejemplo) > umbral ? 1 : 0;
                double resultado_esperado = resultadosEsperados.get(i) ? 1 : 0;
                double error = resultado_esperado - resultado;
                if (error != 0) {
                    contador_errores += 1;
                    for (int j = 0; j < ejemplo.size(); j++) {
                        pesos.set(j, pesos.get(j) + (float) error * tasa_aprendizaje * ejemplo.get(j));
                    }
                }
            }
            if (contador_errores == 0) break;
        }
    }

    private float getProductoInterno(List<Float> attr) throws Exception {
        if (attr.size() != pesos.size()) throw new Exception("Error");
        float sum = 0;
        for (int i = 0; i < attr.size(); i++) {
            sum += attr.get(i) * pesos.get(i);
        }
        return sum;
    }

    public boolean evaluar(List<Float> attr) throws Exception {
        return getProductoInterno(attr) > umbral;
    }

    public List<Float> getPesos() {
        return pesos;
    }


}
