##Compilacion:

javac -cp practico4/*: practico4/*/*.java


##Ejecucion del Ejercicio 3.

Se puede ejecutar sin parametros:

java -cp practico4/*: practico4.E3.Main

o con parametros:

java -cp practico4/*: practico4.E3.Main FUNCION ITERACIONES CANTIDADHIDDEN

donde:
 
FUNCION es el numero de la funcion a ejecutar:
1 - Identidad
2 - Potencia
3 - Coseno
4 - Todas

ITERACIONES es el numero de iteraciones elejido.

CANTIDADHIDDEN es el numero de unidades hidden elegida.


por ejemplo: 

raul@raul-ThinkPad-T530:~/Escritorio$ java -cp practico4/*: practico4.E3.Main 4 10 1
Comienzo, Iteraciones=10 Unidades Hidden=1 Funcion(es): TODAS
... Entrenando para Identidad ...
... Menor error: 1,335
... Entrenando para Potencia ...
... Menor error: 13,621
... Entrenando para Coseno ...
... Menor error: 31,020
