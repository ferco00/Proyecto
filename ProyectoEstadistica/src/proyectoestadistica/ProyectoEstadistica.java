package proyectoestadistica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProyectoEstadistica {

    private JFrame interfaz;
    private JTextField textField;
    private JTextArea resultado;

    public ProyectoEstadistica() {
        interfaz = new JFrame("Proyecto Estadistica");
        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setSize(800, 600);
        interfaz.setLayout(new BorderLayout());
        interfaz.setLocationRelativeTo(null);

        // Panel superior con etiqueta, entrada de texto y botón
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel numerosLabel = new JLabel("Ingrese sus datos (separados por espacios): ");
        textField = new JTextField(30);
        JButton calcularButton = new JButton("Calcular");
        calcularButton.setBackground(new Color(50, 150, 50));
        calcularButton.setForeground(Color.WHITE);
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Operaciones();
            }
        });

        inputPanel.add(numerosLabel, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(calcularButton, BorderLayout.SOUTH);

        // Panel central para mostrar resultados con JScrollPane
        resultado = new JTextArea(20, 30);
        resultado.setEditable(false);
        resultado.setFont(new Font("Arial", Font.PLAIN, 16));
        resultado.setBorder(new EmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(resultado);

        // Panel inferior con botón de cierre centrado
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.setBackground(new Color(150, 50, 50));
        cerrarButton.setForeground(Color.WHITE);
        cerrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cerrarButton) {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(cerrarButton, BorderLayout.CENTER);

        // Agregar paneles a la interfaz
        interfaz.add(inputPanel, BorderLayout.NORTH);
        interfaz.add(scrollPane, BorderLayout.CENTER);
        interfaz.add(buttonPanel, BorderLayout.SOUTH);

        interfaz.setVisible(true);
    }

    public void Operaciones() {
        String numerosInput = textField.getText();
        String[] numerosStr = numerosInput.split(" ");
        double[] numeros = new double[numerosStr.length];

        for (int i = 0; i < numerosStr.length; i++) {
            try {
                numeros[i] = Double.parseDouble(numerosStr[i]);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(interfaz, "Por favor, ingrese números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Arrays.sort(numeros);

        double media = calcularMedia(numeros);
        double mediana = calcularMediana(numeros);
        double varianza = calcularVarianza(numeros, media);
        double desviacionEstandar = calcularDesviacionEstandar(varianza);
        double moda = calcularModa(numeros);

        resultado.setText("Medidas de tendencia central\n"+"La media es: " + media + "\n"
                + "La mediana es: " + mediana + "\n" + "NOTA: Si no hay moda mostrara el numero 0\nLa moda es: " + moda + "\n" + "Medidas de dispersion \nLa varianza es: " + varianza + "\n" + "La desviación Estándar es: " + desviacionEstandar );
    }

    public double calcularMedia(double[] numeros) {
        double suma = 0;

        for (double numero : numeros) {
            suma += numero;
        }

        return suma / numeros.length;
    }

    public double calcularMediana(double[] numeros) {
        int n = numeros.length;
        if (n % 2 == 0) {
            // Si hay un número par de elementos, la mediana es el promedio de los dos valores del medio.
            return (numeros[n / 2 - 1] + numeros[n / 2]) / 2.0;
        } else {
            // Si hay un número impar de elementos, la mediana es el valor del medio.
            return numeros[n / 2];
        }
    }

    public double calcularVarianza(double[] numeros, double media) {
        double sumaDiferenciasCuadrado = 0;

        for (double numero : numeros) {
            sumaDiferenciasCuadrado += Math.pow(numero - media, 2);
        }

        return sumaDiferenciasCuadrado / (numeros.length - 1);
    }

    public double calcularDesviacionEstandar(double varianza) {
        return Math.sqrt(varianza);
    }


    public Double calcularModa(double[] numeros) {
        Map<Double, Integer> frecuencias = new HashMap<>();

        for (double numero : numeros) {
            if (frecuencias.containsKey(numero)) {
                frecuencias.put(numero, frecuencias.get(numero) + 1);
            } else {
                frecuencias.put(numero, 1);
            }
        }

        double moda = 0;
        int maxFrecuencia = 0;

        for (Map.Entry<Double, Integer> entry : frecuencias.entrySet()) {
            double numero = entry.getKey();
            int frecuencia = entry.getValue();

            if (frecuencia > maxFrecuencia) {
                moda = numero;
                maxFrecuencia = frecuencia;
            }
        }

        // Verifica si hay una moda clara (frecuencia mayor a 1)
        if (maxFrecuencia > 1) {
            return moda;
        } else {
            double mod=0;
            return mod; // No hay una moda clara, se devuelve null.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProyectoEstadistica();
            }
        });
    }
}
