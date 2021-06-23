/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuales;

import Modelos.Automata;
import Modelos.REncriptar;
import Modelos.REncriptarMensaje;
import Modelos.RHormigaLangton;
import Modelos.RJuegoVida;
import Modelos.RMayoria;
import Modelos.RSuma;
import Modelos.RTrafico;
import Modelos.ejecutarRegla;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Mario A. Yusti
 */
public class JFrame_Principal extends javax.swing.JFrame {

    Automata automata;
    int vecindad;
    int nestados;
    boolean[] flag = new boolean[2];
    boolean[] eventosTransito = new boolean[2];
    int[] parametros = new int[3];
    JTextArea textarea = new JTextArea("");

    /**
     * Creates new form JFrame_Principal
     */
    public JFrame_Principal() {
        initComponents();
        this.jTextField_Iteraciones.setEnabled(false);
        this.setResizable(false);
        this.setLocationRelativeTo(this);
        this.jButton_pause.setEnabled(false);
        this.jComboBox_ReglasP.setEnabled(false);
        this.jComboBox_tipoSuma.setVisible(false);
        this.jTextField_Num1.setVisible(false);
        this.jTextField_Num2.setVisible(false);
        this.jComboBox_estados.setVisible(false);
        this.jComboBox_Transito.setVisible(false);
        this.textarea.setEditable(true);
    }

    public void iniciar(Automata aut) throws IOException {
        this.automata = aut;
        this.nestados = this.automata.NombreEstados.length;
        this.vecindad = this.automata.vecindad.size();
        this.panel1.obtenerAutomata(this.automata);
        this.panel1.pintarMatriz(false, false);
        this.jButton_pause.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/pause.png")).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        this.jButton_play.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/play.png")).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        this.jButton_next.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/next.png")).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        for (String item : this.automata.NombreEstados) {
            this.jComboBox_estados.addItem(item);
        }
    }

    public int numeroAleatorio(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public void ejecutar(int i) {
        this.flag[0] = true;
        if (this.jRadioButton_RNumero.isSelected()) {
            int regla;
            try {
                regla = Integer.parseInt(this.jTextField_Regla.getText());
                new Thread(new ejecutarRegla(regla, this.automata, this.panel1, i, this.flag, this.jLabel_iteraciones)).start();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Ingrese solo NUMEROS en los campos");
            }
        } else {
            switch (this.jComboBox_ReglasP.getSelectedIndex()) {
                case 0:
                    if (this.automata.NombreEstados.length == 2) {
                        new Thread(new RJuegoVida(this.automata, this.panel1, i, this.flag, this.jLabel_iteraciones)).start();
                    } else {
                        JOptionPane.showMessageDialog(null, "Se requieren tener 2 estados");
                    }
                    break;
                case 1:
                    if (this.automata.NombreEstados.length == 2) {
                        new Thread(new RHormigaLangton(this.automata, this.panel1, i, this.flag, this.jLabel_iteraciones)).start();
                    } else {
                        JOptionPane.showMessageDialog(null, "Se requieren tener 2 estados");
                    }
                    break;
                case 2:
                    new Thread(new RMayoria(this.automata, this.panel1, i, this.flag, this.jLabel_iteraciones)).start();
                    break;
                case 3:
                    try {
                        int num1 = Integer.parseInt(this.jTextField_Num1.getText());
                        int num2 = Integer.parseInt(this.jTextField_Num2.getText());
                        new Thread(new RSuma(this.automata, this.panel1, i, this.flag, num1, num2, this.jComboBox_tipoSuma.getSelectedIndex(), this.jComboBox_estados.getSelectedIndex(), this.jLabel_iteraciones)).start();
                    } catch (Exception e) {
                    }
                    break;
                case 4:
                    switch (this.jComboBox_Transito.getSelectedIndex()) {
                        case 0:
                            this.eventosTransito[0] = false;
                            this.eventosTransito[1] = false;
                            break;
                        case 1:
                            if (this.flag[1]) {
                                this.parametros[0] = numeroAleatorio(0, this.automata.espacio.length - 1);
                                this.eventosTransito[0] = true;
                                this.eventosTransito[1] = false;
                            }
                            break;
                        case 2:
                            if (this.flag[1]) {
                                this.parametros[1] = numeroAleatorio(0, this.automata.espacio.length - 1);
                                this.parametros[2] = numeroAleatorio(0, this.automata.espacio[0].length - 1);
                                this.eventosTransito[1] = true;
                                this.eventosTransito[0] = false;
                            }
                            break;
                        default:
                            break;
                    }
                    try {
                        if (this.automata.NombreEstados.length == 2) {
                            new Thread(new RTrafico(this.automata, this.panel1, i, this.flag, this.eventosTransito, this.parametros, this.jLabel_iteraciones)).start();
                        } else {
                            JOptionPane.showMessageDialog(null, "Se requieren tener 2 estados");
                        }
                    } catch (Exception e) {
                    }
                    break;
                case 5:
                    if (this.jRadioButton_definirIteracciones.isSelected()) {
                        String mensaje = JOptionPane.showInputDialog(null, "Ingrese el mensaje a encriptar \n Alfabeto: [0-9][A-Z][a-z]");
                        String clave = JOptionPane.showInputDialog(null, "Ingrese La clave \n Alfabeto: [0-9][A-Z][a-z]");
                        if (mensaje != null && clave != null && !"".equals(clave) && !"".equals(mensaje)) {
                            REncriptarMensaje res_mensaje = new REncriptarMensaje(mensaje, clave, i);
                            this.textarea.setText(res_mensaje.encriptar());
                            JOptionPane.showMessageDialog(null, this.textarea, "Resultado", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No pueden haber clave o mensaje vacias");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pueden hacer infinitas iteraciones");
                    }
                    break;
                case 6:
                    if (this.automata.NombreEstados.length == 2) {
                        if (this.jRadioButton_definirIteracciones.isSelected()) {
                            String clave = JOptionPane.showInputDialog(null, "Ingrese la clave para encriptar: \n (XXPPPPP..) XX-numero de Regla, PPPP..Palabra Clave \n Alfabeto: [0-9][A-Z][a-z]");
                            if (clave != null && !"".equals(clave)) {
                                try {
                                    int regla = Integer.parseInt(clave.substring(0, 2));
                                    new REncriptar(regla, this.automata, this.panel1, i, clave.substring(2)).encriptar();
                                } catch (Exception e) {
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "No pueden haber claves vacias");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pueden hacer infinitas iteraciones");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Se requieren 2 estados");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_Iteracciones = new javax.swing.ButtonGroup();
        buttonGroup_Reglas = new javax.swing.ButtonGroup();
        panel1 = new Visuales.panel();
        jButton_play = new javax.swing.JButton();
        jButton_next = new javax.swing.JButton();
        jButton_pause = new javax.swing.JButton();
        jTextField_Regla = new javax.swing.JTextField();
        jRadioButton_infinitas = new javax.swing.JRadioButton();
        jRadioButton_definirIteracciones = new javax.swing.JRadioButton();
        jTextField_Iteraciones = new javax.swing.JTextField();
        jRadioButton_RNumero = new javax.swing.JRadioButton();
        jRadioButton_RPersonalizada = new javax.swing.JRadioButton();
        jComboBox_ReglasP = new javax.swing.JComboBox<>();
        jComboBox_tipoSuma = new javax.swing.JComboBox<>();
        jTextField_Num2 = new javax.swing.JTextField();
        jTextField_Num1 = new javax.swing.JTextField();
        jComboBox_estados = new javax.swing.JComboBox<>();
        jComboBox_Transito = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel_iteraciones = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 700));

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setPreferredSize(new java.awt.Dimension(1200, 620));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        jButton_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_playActionPerformed(evt);
            }
        });

        jButton_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_nextActionPerformed(evt);
            }
        });

        jButton_pause.setPreferredSize(new java.awt.Dimension(161, 137));
        jButton_pause.setRolloverEnabled(false);
        jButton_pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pauseActionPerformed(evt);
            }
        });

        jTextField_Regla.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Regla.setText("110");

        buttonGroup_Iteracciones.add(jRadioButton_infinitas);
        jRadioButton_infinitas.setSelected(true);
        jRadioButton_infinitas.setText("Infinitas");
        jRadioButton_infinitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_infinitasActionPerformed(evt);
            }
        });

        buttonGroup_Iteracciones.add(jRadioButton_definirIteracciones);
        jRadioButton_definirIteracciones.setText("Definir: ");
        jRadioButton_definirIteracciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_definirIteraccionesActionPerformed(evt);
            }
        });

        buttonGroup_Reglas.add(jRadioButton_RNumero);
        jRadioButton_RNumero.setSelected(true);
        jRadioButton_RNumero.setText("Regla:");
        jRadioButton_RNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_RNumeroActionPerformed(evt);
            }
        });

        buttonGroup_Reglas.add(jRadioButton_RPersonalizada);
        jRadioButton_RPersonalizada.setText("Reglas Personalizadas:");
        jRadioButton_RPersonalizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_RPersonalizadaActionPerformed(evt);
            }
        });

        jComboBox_ReglasP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Juego de la Vida", "Hormiga de Langton", "Mayoria", "Suma", "Transito", "Encriptar Mensaje", "Encriptar Imagen" }));
        jComboBox_ReglasP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ReglasPActionPerformed(evt);
            }
        });

        jComboBox_tipoSuma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Intervalo:", "Igual:", "Mayor Que:", "Menor Que" }));
        jComboBox_tipoSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_tipoSumaActionPerformed(evt);
            }
        });

        jTextField_Num2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Num2.setText("3");

        jTextField_Num1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Num1.setText("2");

        jComboBox_Transito.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Cuello Botella", "Accidente" }));
        jComboBox_Transito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_TransitoActionPerformed(evt);
            }
        });

        jLabel1.setText("# Pasos:");

        jLabel_iteraciones.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_iteraciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_iteraciones.setText("00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_pause, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_play)
                .addGap(18, 18, 18)
                .addComponent(jButton_next, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_infinitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_definirIteracciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Iteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_iteraciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox_Transito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox_tipoSuma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jTextField_Num1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_Num2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton_RPersonalizada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox_ReglasP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_RNumero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Regla, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton_next, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jButton_play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_pause, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_Regla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton_infinitas)
                        .addComponent(jRadioButton_definirIteracciones)
                        .addComponent(jTextField_Iteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton_RNumero)
                        .addComponent(jRadioButton_RPersonalizada)
                        .addComponent(jComboBox_ReglasP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_tipoSuma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_Num2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_Num1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_Transito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel_iteraciones)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_playActionPerformed
        this.jLabel_iteraciones.setText("000");
        this.jButton_play.setEnabled(false);
        this.jButton_next.setEnabled(false);
        this.jButton_pause.setEnabled(true);
        int i = -1;
        try {
            if (this.jRadioButton_definirIteracciones.isSelected()) {
                this.flag[1] = false;
                i = Integer.parseInt(this.jTextField_Iteraciones.getText());
            } else {
                this.flag[1] = true;
            }
            ejecutar(i);
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
        }
    }//GEN-LAST:event_jButton_playActionPerformed

    private void jButton_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_nextActionPerformed
        this.flag[1] = false;
        ejecutar(1);
    }//GEN-LAST:event_jButton_nextActionPerformed

    private void jButton_pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pauseActionPerformed
        this.flag[0] = false;
        this.jButton_pause.setEnabled(false);
        this.jButton_play.setEnabled(true);
        this.jButton_next.setEnabled(true);
    }//GEN-LAST:event_jButton_pauseActionPerformed

    private void jRadioButton_infinitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_infinitasActionPerformed
        this.jTextField_Iteraciones.setEnabled(false);
        this.jTextField_Iteraciones.setText("");
    }//GEN-LAST:event_jRadioButton_infinitasActionPerformed

    private void jRadioButton_definirIteraccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_definirIteraccionesActionPerformed
        this.jTextField_Iteraciones.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_definirIteraccionesActionPerformed

    private void jRadioButton_RNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_RNumeroActionPerformed
        this.jTextField_Regla.setEnabled(true);
        this.jComboBox_ReglasP.setEnabled(false);
        this.jComboBox_tipoSuma.setVisible(false);
        this.jComboBox_tipoSuma.setSelectedIndex(0);
        this.jTextField_Num1.setVisible(false);
        this.jTextField_Num2.setVisible(false);
        this.jComboBox_estados.setVisible(false);
    }//GEN-LAST:event_jRadioButton_RNumeroActionPerformed

    private void jRadioButton_RPersonalizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_RPersonalizadaActionPerformed
        this.jTextField_Regla.setEnabled(false);
        this.jComboBox_ReglasP.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_RPersonalizadaActionPerformed

    private void jComboBox_ReglasPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ReglasPActionPerformed
        this.jComboBox_tipoSuma.setVisible(false);
        this.jComboBox_tipoSuma.setSelectedIndex(0);
        this.jTextField_Num1.setVisible(false);
        this.jTextField_Num2.setVisible(false);
        this.jComboBox_estados.setVisible(false);
        this.jComboBox_Transito.setVisible(false);
        switch (this.jComboBox_ReglasP.getSelectedIndex()) {
            case 1:
                try {
                    String[] coor;
                    int x;
                    int y;
                    coor = JOptionPane.showInputDialog(null, "Ingrese la coordenada de incio de la hormiga: (X,Y)").split(",");
                    x = Integer.parseInt(coor[0]);
                    y = Integer.parseInt(coor[1]);
                    if (x < this.automata.espacio.length && y < this.automata.espacio[0].length) {
                        this.automata.estadoActual[0] = x;
                        this.automata.estadoActual[1] = y;
                    } else {
                        JOptionPane.showMessageDialog(null, "Coordenada no esta en el espacio");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocurrio un Error: Ingrese Una Coordenada Valida: X,Y", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 3:
                this.jComboBox_tipoSuma.setVisible(true);
                this.jTextField_Num1.setVisible(true);
                this.jTextField_Num2.setVisible(true);
                this.jTextField_Num1.setText("2");
                this.jTextField_Num2.setText("3");
                this.jComboBox_estados.setVisible(true);
                break;
            case 4:
                this.jComboBox_Transito.setVisible(true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jComboBox_ReglasPActionPerformed

    private void jComboBox_tipoSumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_tipoSumaActionPerformed
        if (this.jComboBox_tipoSuma.getSelectedIndex() == 0) {
            this.jTextField_Num2.setVisible(true);
        } else {
            this.jTextField_Num2.setVisible(false);
            this.jTextField_Num2.setText("0");
        }
    }//GEN-LAST:event_jComboBox_tipoSumaActionPerformed

    private void jComboBox_TransitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_TransitoActionPerformed

    }//GEN-LAST:event_jComboBox_TransitoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_Iteracciones;
    private javax.swing.ButtonGroup buttonGroup_Reglas;
    private javax.swing.JButton jButton_next;
    private javax.swing.JButton jButton_pause;
    private javax.swing.JButton jButton_play;
    private javax.swing.JComboBox<String> jComboBox_ReglasP;
    private javax.swing.JComboBox<String> jComboBox_Transito;
    private javax.swing.JComboBox<String> jComboBox_estados;
    private javax.swing.JComboBox<String> jComboBox_tipoSuma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_iteraciones;
    private javax.swing.JRadioButton jRadioButton_RNumero;
    private javax.swing.JRadioButton jRadioButton_RPersonalizada;
    private javax.swing.JRadioButton jRadioButton_definirIteracciones;
    private javax.swing.JRadioButton jRadioButton_infinitas;
    private javax.swing.JTextField jTextField_Iteraciones;
    private javax.swing.JTextField jTextField_Num1;
    private javax.swing.JTextField jTextField_Num2;
    private javax.swing.JTextField jTextField_Regla;
    private Visuales.panel panel1;
    // End of variables declaration//GEN-END:variables
}
