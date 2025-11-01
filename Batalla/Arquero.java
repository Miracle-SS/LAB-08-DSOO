package Batalla;

public class Arquero extends Soldado {
    private int flechas;

    public Arquero(String nombre, int vida, int ataque, int defensa, int flechas) {
        super(nombre, vida, ataque, defensa);
        this.flechas = flechas;
    }
    public void dispararFlecha(Soldado enemigo) {
        if (flechas > 0) {
            flechas--;
            System.out.println(nombre + " dispara una flecha. Flechas restantes: " + flechas);
            atacar(enemigo);
        } else {
            System.out.println(nombre + " no tiene flechas para disparar.");
        }
    }
}