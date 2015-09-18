package practico3.comun;

import java.io.*;
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

    public static void printfile(String nomfile, List<Experimento> experimentos) {
        File file = new File(nomfile + ".csv");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Experimento.getCabecera());
            for (Experimento e : experimentos) {
                fileWriter.write(e.toString2());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
