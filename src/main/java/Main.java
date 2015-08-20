import practico1.P1E2;

/**
 * Created by RSperoni on 17/08/2015.
 */
public class Main {

    public static void main(String [ ] args){

        System.out.println("MAA 2015");
        P1E2 p1E2 = new P1E2();

        if (args.length > 0){
            int size = Integer.valueOf(args[0]);
            System.out.println("Comienzo TA-TE-TI, N=" + size);
            try {
                p1E2.run(size);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("Debe pasar como parametro el tamano del tablero");



    }

}
