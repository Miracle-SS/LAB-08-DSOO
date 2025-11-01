package Batalla;

public class Caballero extends Soldado {
    private boolean montado;
    private String armaActual;
    public Caballero(String nombre, int puntosVida, int ataque, int defensa, boolean montado) {
        super(nombre, puntosVida, ataque, defensa);
        this.montado = montado;
        this.armaActual = montado ? "lanza" : "espada";
    }
    public void montar() {
        if (!montado) {
            montado = true;
            armaActual = "lanza";
            System.out.println(nombre + " se ha montado y cambia su arma a lanza.");
        } else {
            System.out.println(nombre + " ya está montado.");
        }
    }
    public void desmontar() {
        if (montado) {
            montado = false;
            armaActual = "espada";
            System.out.println(nombre + " se ha desmontado y cambia su arma a espada.");
        } else {
            System.out.println(nombre + " ya está desmontado.");
        }
    }
    public void envestir(Soldado enemigo) {
        int golpes = montado ? 3 : 2;
        System.out.println(nombre + " realiza una envestida con su " + armaActual + " (" + golpes + " golpes).");
        for (int i = 0; i < golpes; i++) {
            enemigo.defender(ataque);
        }
    }
    public boolean isMontado() {
        return montado;
    }
    public String getArmaActual() {
        return armaActual;
    }
}