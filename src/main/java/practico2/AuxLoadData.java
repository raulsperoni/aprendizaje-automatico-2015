package practico2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    /**
     * Devuelvo los atributos y sus posibles valores segun la documentacion.
     *
     * @return
     */
    public static HashMap<Integer, List<String>> atributos() {
        HashMap<Integer, List<String>> res = new HashMap<Integer, List<String>>();
        List<String> capshape = Arrays.asList("b", "c", "x", "f", "k", "s");
        res.put(1, capshape);

        List<String> capsurface = Arrays.asList("f", "g", "y", "s");
        res.put(2, capsurface);

        List<String> capcolor = Arrays.asList("n", "b", "c", "g", "r", "p", "u", "e", "w", "y");
        res.put(3, capcolor);

        List<String> bruises = Arrays.asList("t", "f");
        res.put(4, bruises);

        List<String> odor = Arrays.asList("a", "l", "c", "y", "f", "m", "n", "p", "s");
        res.put(5, odor);

        List<String> gillatachment = Arrays.asList("a", "d", "f", "n");
        res.put(6, gillatachment);

        List<String> gillspacing = Arrays.asList("c", "w", "d");
        res.put(7, gillspacing);

        List<String> gillsize = Arrays.asList("b", "n");
        res.put(8, gillsize);

        List<String> gillcolor = Arrays.asList("k", "n", "b", "h", "g", "r", "o", "p", "u", "e", "w", "y");
        res.put(9, gillcolor);

        List<String> stalkshape = Arrays.asList("e", "t");
        res.put(10, stalkshape);

        List<String> stalckroot = Arrays.asList("b", "c", "u", "e", "z", "r");
        res.put(11, stalckroot);

        List<String> stacksurfaceabovering = Arrays.asList("f", "y", "k", "s");
        res.put(12, stacksurfaceabovering);

        List<String> stacksurfacebelowring = Arrays.asList("f", "y", "k", "s");
        res.put(13, stacksurfacebelowring);

        List<String> stackcolorabovering = Arrays.asList("n", "b", "c", "g", "o", "p", "e", "w", "y");
        res.put(14, stackcolorabovering);

        List<String> stackcolorbelowring = Arrays.asList("n", "b", "c", "g", "o", "p", "e", "w", "y");
        res.put(15, stackcolorbelowring);

        List<String> veiltype = Arrays.asList("p", "u");
        res.put(16, veiltype);

        List<String> vailcolor = Arrays.asList("n", "o", "w", "y");
        res.put(17, vailcolor);

        List<String> ringnumber = Arrays.asList("n", "o", "t");
        res.put(18, ringnumber);

        List<String> ringtype = Arrays.asList("c", "e", "f", "l", "n", "p", "s", "z");
        res.put(19, ringtype);

        List<String> sporeprintcolor = Arrays.asList("k", "n", "b", "h", "r", "o", "u", "w", "y");
        res.put(20, sporeprintcolor);

        List<String> population = Arrays.asList("a", "c", "n", "s", "v", "y");
        res.put(21, population);

        List<String> habitat = Arrays.asList("g", "l", "m", "p", "u", "w", "d");
        res.put(22, habitat);

        return res;
    }
}
