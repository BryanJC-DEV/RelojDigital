package relojdigital;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelojDigital extends JFrame {
    // Variables de instancia
    private JLabel etiquetaHora, etiquetaFecha, etiquetaCronometro;
    private JButton botonIniciar, botonDetener, botonReiniciar;
    private javax.swing.Timer temporizadorReloj, temporizadorCronometro;
    private int horas = 0, minutos = 0, segundos = 0, decimos = 0;

    // Constructor
    public RelojDigital() {
        super("Reloj Digital");
        this.setSize(420, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Configurar componentes
        configurarComponentes();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Iniciar el reloj
        iniciarReloj();
    }

    private void configurarComponentes() {
        // Panel del reloj
        JPanel panelClock = new JPanel(new BorderLayout());
        etiquetaHora = new JLabel("", SwingConstants.CENTER);
        etiquetaHora.setFont(new Font("Arial", Font.BOLD, 36));
        etiquetaHora.setOpaque(true);
        etiquetaHora.setBackground(Color.BLACK);
        etiquetaHora.setForeground(Color.WHITE);
        panelClock.add(etiquetaHora, BorderLayout.CENTER);

        // Panel del calendario
        JPanel panelCalendar = new JPanel(new BorderLayout());
        etiquetaFecha = new JLabel("", SwingConstants.CENTER);
        etiquetaFecha.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaFecha.setForeground(Color.BLACK);
        panelCalendar.add(etiquetaFecha, BorderLayout.CENTER);

        // Panel del cronómetro
        JPanel panelCrono = new JPanel(new FlowLayout());
        etiquetaCronometro = new JLabel("00:00:00.0", SwingConstants.CENTER);
        etiquetaCronometro.setFont(new Font("Arial", Font.BOLD, 20));
        etiquetaCronometro.setForeground(Color.BLACK);
        panelCrono.add(etiquetaCronometro);

        botonIniciar = new JButton("Start");
        botonDetener = new JButton("Stop");
        botonReiniciar = new JButton("Reset");
        panelCrono.add(botonIniciar);
        panelCrono.add(botonDetener);
        panelCrono.add(botonReiniciar);

        // Agregar eventos a los botones
        botonIniciar.addActionListener(e -> iniciarCronometro());
        botonDetener.addActionListener(e -> detenerCronometro());
        botonReiniciar.addActionListener(e -> reiniciarCronometro());

        // Añadir los paneles al JFrame
        this.add(panelClock, BorderLayout.NORTH);
        this.add(panelCalendar, BorderLayout.CENTER);
        this.add(panelCrono, BorderLayout.SOUTH);
    }

    private void iniciarReloj() {
        temporizadorReloj = new javax.swing.Timer(1000, e -> actualizarReloj());
        temporizadorReloj.start();
    }

    private void actualizarReloj() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        etiquetaHora.setText(ahora.format(formatoHora));
        etiquetaFecha.setText(ahora.format(formatoFecha));
    }

    private void iniciarCronometro() {
        if (temporizadorCronometro == null) {
            temporizadorCronometro = new javax.swing.Timer(100, e -> actualizarCronometro());
        }
        temporizadorCronometro.start();
        botonIniciar.setEnabled(false);
        botonDetener.setEnabled(true);
        botonReiniciar.setEnabled(true);
    }

    private void detenerCronometro() {
        if (temporizadorCronometro != null) {
            temporizadorCronometro.stop();
        }
        botonIniciar.setEnabled(true);
        botonDetener.setEnabled(false);
    }

    private void reiniciarCronometro() {
        detenerCronometro();
        horas = 0;
        minutos = 0;
        segundos = 0;
        decimos = 0;
        etiquetaCronometro.setText("00:00:00.0");
    }

    private void actualizarCronometro() {
        decimos++;
        if (decimos == 10) {
            decimos = 0;
            segundos++;
        }
        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }
        if (minutos == 60) {
            minutos = 0;
            horas++;
        }
        etiquetaCronometro.setText(String.format("%02d:%02d:%02d.%d", horas, minutos, segundos, decimos));
    }

    // Método principal
    public static void main(String[] args) {
        new RelojDigital();
    }
}
