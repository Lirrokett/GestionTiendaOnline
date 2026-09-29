package utilidades;

import java.util.Scanner;

public final class Entrada {
    private static final Scanner SC = new Scanner(System.in);

    private Entrada() {}

    public static String texto(String mensaje) {
        System.out.print(mensaje);
        return SC.nextLine();
    }

    public static int entero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }
}
