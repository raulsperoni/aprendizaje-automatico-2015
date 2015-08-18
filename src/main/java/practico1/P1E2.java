package practico1;

/**
 * Created by RSperoni on 17/08/2015.
 * L�gica para TA-TE-TI
 */
public class P1E2 {

    static float MIN_MU = 0.1f;
    static int MAX_IT = 500;
    static int SIZE = 5;

    public void run() {

        //declaro wi's
        Coeficientes coeficientes = new Coeficientes();

        //Partidas
        float mu = 0.0f;
        int cantIteraciones = 0;
        while (mu < MIN_MU || cantIteraciones > MAX_IT) {

            //Inicializo
            Tablero tablero = new Tablero(SIZE);

            //Sortear quien empieza,
            //variable jugador;

            //Movimientos
            boolean juegoFinalizado = false;
            while (!juegoFinalizado) {


                //Jugador que le toca
                //1- ProbarMarcar, evaluar tablero.
                //2- Calcular posicion para movida
                //3- Mover

                //imprimir ta-te-ti

                //evaluar juego finalizado

                //cambiarJugador

            }
            cantIteraciones++;

            //actualizo wi's con minimos cuadrados
            //imprimo datos de partida.
        }

    }
}
