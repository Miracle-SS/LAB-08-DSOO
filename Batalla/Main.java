package Batalla;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("== Simulador de Batalla por Turnos ==");
        while (true) {
            System.out.println("1) Nueva batalla");
            System.out.println("2) Salir");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();
            if (opcion == 1) ejecutarBatalla();
            else break;
        }
    }
    public static void ejecutarBatalla() {
        Ejercito ejercito1 = generarEjercito("Ejército Rojo");
        Ejercito ejercito2 = generarEjercito("Ejército Azul");

        System.out.println("\n--- COMIENZA LA BATALLA ---");

        while (ejercito1.tieneSoldadosVivos() && ejercito2.tieneSoldadosVivos()) {
            System.out.println("\nTurno de " + ejercito1.getNombre());
            realizarTurno(ejercito1, ejercito2);

            if (!ejercito2.tieneSoldadosVivos()) break;

            System.out.println("\nTurno de " + ejercito2.getNombre());
            realizarTurno(ejercito2, ejercito1);
        }

        if (ejercito1.tieneSoldadosVivos())
            System.out.println("\n" + ejercito1.getNombre() + " ha ganado la batalla!");
        else
            System.out.println("\n" + ejercito2.getNombre() + " ha ganado la batalla!");
    }

    private static void realizarTurno(Ejercito atacante, Ejercito defensor) {
        atacante.mostrarSoldados();
        System.out.print("Elige tu soldado atacante: ");
        int atqIndex = sc.nextInt() - 1;
        defensor.mostrarSoldados();
        System.out.print("Elige al enemigo a atacar: ");
        int defIndex = sc.nextInt() - 1;
        Soldado sAtacante = atacante.getSoldado(atqIndex);
        Soldado sDefensor = defensor.getSoldado(defIndex);

        System.out.println(sAtacante.getNombre() + " ataca a " + sDefensor.getNombre());
        sDefensor.defender(sAtacante.getAtaque());

        if (sDefensor.getPuntosVida() <= 0)
            System.out.println(sDefensor.getNombre() + " ha caído en batalla!");
    }
    private static Ejercito generarEjercito(String nombre) {
        Ejercito ejercito = new Ejercito(nombre);
        int cantidad = random.nextInt(4) + 3; // entre 3 y 6 soldados

        for (int i = 0; i < cantidad; i++) {
            int tipo = random.nextInt(4);
            Soldado s;
            switch (tipo) {
                case 0 -> s = new Espadachin(nombre + "_Espadachin" + i, 3 + random.nextInt(2), 2, 1, 2);
                case 1 -> s = new Arquero(nombre + "_Arquero" + i, 1 + random.nextInt(3), 2, 1, 5);
                case 2 -> s = new Caballero(nombre + "_Caballero" + i, 3 + random.nextInt(3), 3, 2, true);
                default -> s = new Lancero(nombre + "_Lancero" + i, 1 + random.nextInt(2), 2, 1, 3);
            }
            ejercito.agregarSoldado(s);
        }
        return ejercito;
    }
}