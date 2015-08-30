package practico2;

import java.util.List;

public class ID3 {

	public static void calcular(List<Ejemplo> entrenamiento, List<Integer> atributos, int atributoElegido) {
		//Crear raiz
		Subarbol raiz = new Subarbol();		
		// Si todos los ej. tienen el mismo valor → etiquetar con ese valor
		boolean poison = true, edible = true;
		for (Ejemplo ej : entrenamiento) {
			poison = poison && ej.poisonus;
			edible = edible && !(ej.poisonus);
		}
		if (poison) {
			raiz.hoja = true;
			raiz.poisonus = true;
		} else if (edible) {
			raiz.hoja = true;
			raiz.poisonus = false;
		} else {			
			// Si no me quedan atributos→ etiquetar con el valor más común
			if (atributos.isEmpty()) 
		    {
				raiz.poisonus = raiz.nodo.cantPoisonous>=raiz.nodo.cantEdible;
				raiz.hoja = true;
		    }
			else
			{
				// En caso contrario:‣ La raíz pregunte por A, atributo que mejor
				// clasifica los ejemplos‣
				int atributo = clasificarEjemplos();
				// Para cada valor vi de A ๏Genero una rama๏
				for(int i = 0;i<Main.atributos.get(atributo).size();i++)
				{	
				Subarbol rama = new Subarbol();
				
				// Ejemplos vi ={ ejemplos en los cuales A= vi}๏
				
				// Si Ejemplos vi es vacío→ etiquetar con el valor más probable๏

				// En caso contrario→ ID3(Ejemplos vi , Atributos -{A})
			}

		}

	}

}
