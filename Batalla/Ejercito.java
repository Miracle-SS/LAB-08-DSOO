package Batalla;

import java.util.*;

public class Ejercito {
    private String nombre;
    private List<Soldado> soldados;

    public Ejercito(String nombre) {
        this.nombre = nombre;
        this.soldados = new ArrayList<>();
    }
    public void agregarSoldado(Soldado s) {
        soldados.add(s);
    }

    public boolean tieneSoldadosVivos() {
        return soldados.stream().anyMatch(s -> s.getPuntosVida() > 0);
    }
    public List<Soldado> getSoldadosVivos() {
        List<Soldado> vivos = new ArrayList<>();
        for (Soldado s : soldados) {
            if (s.getPuntosVida() > 0) vivos.add(s);
        }
        return vivos;
    }
    public Soldado getSoldado(int indice) {
        return soldados.get(indice);
    }

    public void mostrarSoldados() {
        System.out.println("Ejército: " + nombre);
        for (int i = 0; i < soldados.size(); i++) {
            Soldado s = soldados.get(i);
            System.out.printf("%d) %s - Vida: %d - Ataque: %d - Defensa: %d\n",
                    i + 1, s.getNombre(), s.getPuntosVida(), s.getAtaque(), s.getDefensa());
        }
    }
    public String getNombre() {
        return nombre;
    }
}