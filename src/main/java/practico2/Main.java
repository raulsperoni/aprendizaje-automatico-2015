package practico2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by raul on 28/08/15.
 */
public class Main {


    public static void main(String[] args) {

        List<Ejemplo> ejemplos = AuxLoadData.retreive();

        Collections.shuffle(ejemplos);
        int corte = (ejemplos.size() * 4) / 5;

        List<Ejemplo> entrenamiento_total = ejemplos.subList(0, corte);

        //VALIDACION CRUZADA
        ArrayList<List<Ejemplo>> particiones = new ArrayList<>();
        int ini_particion = 0;
        for (int i = 0; i < 10; i++) {
            particiones.add(entrenamiento_total.subList(ini_particion, ini_particion + 10));
            ini_particion += 10;
        }

        //PARA CADA PARTICION
        for (int i = 0; i < 10; i++) {

            List<Ejemplo> prueba_validacion_cruzada = particiones.get(i);
            List<Ejemplo> entrenamiento_validacion_cruzada = new ArrayList<>();
            //Hago una copia del conjunto de entrenamiento.
            Collections.copy(entrenamiento_validacion_cruzada, entrenamiento_total);
            //Le saco el conjunto de prueba actual.
            entrenamiento_validacion_cruzada.removeAll(prueba_validacion_cruzada);

            //Llamo a ID3 con el conjunto de entrenamiento.

            //Evaluo el conj de prueba con el resultado de ID3

            //Hago algo con los valores resultantes, media, etc.

        }


    }
}
