package Batalla;

public class Soldado {
    protected String nombre;
    protected int puntosVida;
    protected int ataque;
    protected int defensa;
    protected int fila;
    protected int columna;

    public Soldado(String nombre, int puntosVida, int ataque, int defensa) {
        this.nombre = nombre;
        this.puntosVida = puntosVida;
        this.ataque = ataque;
        this.defensa = defensa;
    }
    public void atacar(Soldado enemigo) {
        System.out.println(nombre + " ataca a " + enemigo.getNombre());
        enemigo.defender(ataque);
    }

    public void defender(int dano) {
        int danoReal = Math.max(0, dano - defensa);
        puntosVida -= danoReal;
        if (puntosVida < 0) puntosVida = 0;
        System.out.println(nombre + " recibe " + danoReal + " de daño. Vida restante: " + puntosVida);
    }
    public boolean estaVivo() {
        return puntosVida > 0;
    }
    public String getNombre() {
        return nombre;
    }
    public int getPuntosVida() {
        return puntosVida;
    }
    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }
    public int getAtaque() {
        return ataque;
    }
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public int getDefensa() {
        return defensa;
    }
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    public int getFila() {
        return fila;
    }
    public void setFila(int fila) {
        this.fila = fila;
    }
    public int getColumna() {
        return columna;
    }
    public void setColumna(int columna) {
        this.columna = columna;
    }
    public String toString() {
        return nombre + " [Vida: " + puntosVida + ", Ataque: " + ataque + ", Defensa: " + defensa + "]";
    }
}