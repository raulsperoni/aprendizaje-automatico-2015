package practico3.knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 28/08/15.
 */
public class AuxLoadData {

    public static List<Ejemplo> retreive() {

        List<Ejemplo> coso = new ArrayList<Ejemplo>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("agaricus-lepiota.data"));

            try {

                String line = br.readLine();
                int id = 1;
                while (line != null) {
                    String[] valores = line.split(",");
                    Ejemplo ejemplo = new Ejemplo();
                    ejemplo.poisonus = valores[0].equals("p");
                    ejemplo.id = id;
                    for (int i = 1; i < valores.length; i++) {
                        ejemplo.atributos.put(i, valores[i]);

                    }
                    coso.add(ejemplo);
                    line = br.readLine();
                    id++;
                }
            } finally {
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coso;
    }
}
