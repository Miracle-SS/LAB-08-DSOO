package Batalla;

public class Espadachin extends Soldado {
    private int longitudEspada;

    public Espadachin(String nombre, int vida, int ataque, int defensa, int longitudEspada) {
        super(nombre, vida, ataque, defensa);
        this.longitudEspada = longitudEspada;
    }

    public void muroDeEscudos() {
        System.out.println(nombre + " crea un muro de escudos para aumentar la defensa.");
        defensa += 1;
    }
}