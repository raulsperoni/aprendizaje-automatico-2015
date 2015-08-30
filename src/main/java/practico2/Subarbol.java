package practico2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 28/08/15.
 */
public class Subarbol {

    public String valorAtributoDelPadre;
    public Integer atributoDecision;
    public List<Subarbol> hijos = new ArrayList<>();
    public Hoja hoja = null;
    public int cantEjemplosPoisonus = 0;
    public int cantEjemplosEdiable = 0;

}
