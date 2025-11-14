package Batalla;

import java.awt.*;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GUI extends JFrame {

    private JTextArea consola;
    private JButton btnGenerar;
    private JButton btnSimular;

    private Ejercito ejercito1;
    private Ejercito ejercito2;
    private Random random = new Random();

    public GUI() {
        setTitle("Batalla Estratégica - GUI");
        setSize(800, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Simulador de Batalla (GUI)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(180, 200, 255));
        add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnGenerar = new JButton("Generar Ejércitos");
        btnSimular = new JButton("Iniciar Batalla");
        panelCentro.add(btnGenerar);
        panelCentro.add(btnSimular);
        add(panelCentro, BorderLayout.CENTER);

        // Consola
        consola = new JTextArea();
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(consola);
        scroll.setPreferredSize(new Dimension(780, 320));
        add(scroll, BorderLayout.SOUTH);

        // Acciones
        btnGenerar.addActionListener(e -> {
            ejercito1 = generarEjercito("Inglaterra");
            ejercito2 = generarEjercito("Francia");
            consola.setText("");
            consola.append("=== EJÉRCITOS GENERADOS ===\n");
            mostrarEjercito(enArray(ejercito1));
            mostrarEjercito(enArray(ejercito2));
            consola.append("\nPulse 'Iniciar Batalla' para simular.\n");
        });

        btnSimular.addActionListener(e -> {
            if (ejercito1 == null || ejercito2 == null) {
                consola.append("Primero genera los ejércitos.\n");
                return;
            }
            // Ejecutar la simulación en un hilo aparte para no bloquear la GUI
            new Thread(this::simularBatalla).start();
        });
    }

    // ---- Métodos auxiliares ----

    // Genera un ejército como en tu Main.generarEjercito (copia de la lógica)
    private Ejercito generarEjercito(String nombre) {
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

    // Para imprimir sin problemas desde cualquier hilo
    private void appendConsole(String texto) {
        SwingUtilities.invokeLater(() -> consola.append(texto));
    }

    private void mostrarEjercito(List<Soldado> lista) {
        if (lista.isEmpty()) return;
        appendConsole("\nEjército: " + lista.get(0).getNombre().split("_")[0] + "\n");
        for (Soldado s : lista) {
            appendConsole(" - " + s.toString() + "\n");
        }
    }

    // convierte Ejercito -> lista de vivos (para usar mostrarEjercito directamente)
    private List<Soldado> enArray(Ejercito e) {
        return e.getSoldadosVivos();
    }

    // Simulación principal (se ejecuta en hilo aparte)
    private void simularBatalla() {
        appendConsole("\n=== INICIANDO BATALLA ===\n");
        // pequeña pausa visual
        sleep(400);

        while (ejercito1.tieneSoldadosVivos() && ejercito2.tieneSoldadosVivos()) {
            realizarTurnoGUI(ejercito1, ejercito2);
            sleep(400);
            if (!ejercito2.tieneSoldadosVivos()) break;
            realizarTurnoGUI(ejercito2, ejercito1);
            sleep(400);
        }

        String ganador = ejercito1.tieneSoldadosVivos() ? ejercito1.getNombre() : ejercito2.getNombre();
        appendConsole("\n=== GANADOR: " + ganador + " ===\n");
    }

    // Realiza un turno usando atacantes/defensores aleatorios y habilidades
    private void realizarTurnoGUI(Ejercito atacante, Ejercito defensor) {
        if (!atacante.tieneSoldadosVivos() || !defensor.tieneSoldadosVivos()) return;

        List<Soldado> vivosAtq = atacante.getSoldadosVivos();
        List<Soldado> vivosDef = defensor.getSoldadosVivos();

        Soldado sAtacante = vivosAtq.get(random.nextInt(vivosAtq.size()));
        Soldado sDefensor = vivosDef.get(random.nextInt(vivosDef.size()));

        appendConsole("\n" + sAtacante.getNombre() + " ataca a " + sDefensor.getNombre() + "\n");

        // Usar habilidades segun tipo
        if (sAtacante instanceof Arquero arq) {
            // disparo si tiene flechas, si no ataca normal
            arq.dispararFlecha(sDefensor);
            appendConsole(" (Arquero dispara). Vida de " + sDefensor.getNombre() + ": " + sDefensor.getPuntosVida() + "\n");
        }
        else if (sAtacante instanceof Caballero cab) {
            cab.envestir(sDefensor);
            appendConsole(" (Caballero envestida). Vida de " + sDefensor.getNombre() + ": " + sDefensor.getPuntosVida() + "\n");
        }
        else if (sAtacante instanceof Lancero lan) {
            lan.schiltrom();                // aumenta defensa propia
            lan.atacar(sDefensor);         // y ataca
            appendConsole(" (Lancero schiltrom+ataque). Vida de " + sDefensor.getNombre() + ": " + sDefensor.getPuntosVida() + "\n");
        }
        else if (sAtacante instanceof Espadachin esp) {
            esp.muroDeEscudos();           // aumento temporal de defensa
            esp.atacar(sDefensor);
            appendConsole(" (Espadachín muro+ataque). Vida de " + sDefensor.getNombre() + ": " + sDefensor.getPuntosVida() + "\n");
        }
        else {
            sAtacante.atacar(sDefensor);
            appendConsole(" Vida de " + sDefensor.getNombre() + ": " + sDefensor.getPuntosVida() + "\n");
        }

        if (!sDefensor.estaVivo()) {
            appendConsole(">> " + sDefensor.getNombre() + " ha caído.\n");
        }
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}