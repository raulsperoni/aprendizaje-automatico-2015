package practico1;

import java.util.Random;

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

            //variable jugador;
            Tablero.Marca jugador;

            //Sortear quien empieza,
            Random r = new Random();
            if (r.nextBoolean()) {
                jugador = Tablero.Marca.X;
            } else jugador = Tablero.Marca.O;


            //Movimientos
            boolean juegoFinalizado = false;
            int VEnt = 0;
            double VOp = 0.0;
            while (!juegoFinalizado) {


                //Jugador que le toca
                //2- Calcular posicion para movida probando.

                //Mejor pos.
                double mejorVop = -1;
                int mejori = -1;
                int mejorj = -1;

                for (int i = 0; i < tablero.SIZE; i++) {
                    for (int j = 0; j < tablero.SIZE; i++) {

                        double vop = tablero.setMarca(i, j, jugador, true, coeficientes); //TODO:RAUL
                        if (mejorVop == -1 || vop > mejorVop) {
                            mejorVop = vop;
                            mejori = i;
                            mejorj = j;
                        }
                    }
                }
                //3- Mover
                VOp = tablero.setMarca(mejori, mejorj, jugador, false, coeficientes);
                //imprimir ta-te-ti
                tablero.imprimir();

                //Gano jugador?
                juegoFinalizado = tablero.isFinalizado();
                VEnt = tablero.getVEnt();

                //cambiarJugador
                if (jugador == Tablero.Marca.X) jugador = Tablero.Marca.O;
                else jugador = Tablero.Marca.X;

            }
            cantIteraciones++;

            //actualizo wi's con minimos cuadrados
            coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOp);

            //imprimo datos de partida.
            coeficientes.imprimir();

            //Actualizar MU
            mu -= 0.0001; //TODO: ver esto
        }

    }
}
