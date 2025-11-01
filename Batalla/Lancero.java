package Batalla;

public class Lancero extends Soldado {
    private int longitudLanza;

    public Lancero(String nombre, int vida, int ataque, int defensa, int longitudLanza) {
        super(nombre, vida, ataque, defensa);
        this.longitudLanza = longitudLanza;
    }

    public void schiltrom() {
        defensa += 1;
        System.out.println(nombre + " forma un schiltrom (falange), aumentando su defensa.");
    }
}