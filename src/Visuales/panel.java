/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuales;

import Modelos.Automata;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author administrador
 */
public class panel extends javax.swing.JPanel implements MouseListener {

    Automata automata;
    ImageIcon[] imagenes;
    ImageIcon[] imgsEstAct;
    Color[] colores;
    Color ColEstadoActual;
    int anchoCelda;
    int altoCelda;
    int nfilas;
    int ncolumnas;
    GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Creates new form panel
     */
    public panel() {
        initComponents();
        this.imgsEstAct = new ImageIcon[4];
    }

    public void obtenerAutomata(Automata aut) throws IOException {
        this.automata = aut;
        this.nfilas = this.automata.espacio.length;
        this.ncolumnas = this.automata.espacio[0].length;
        this.anchoCelda = (int) Math.round((double) (this.getWidth() / this.ncolumnas));
        this.altoCelda = (int) Math.round((double) (this.getHeight() / this.nfilas));
        if (this.automata.asociacionColor) {
            this.colores = new Color[this.automata.NombreEstados.length];
            for (int i = 0; i < colores.length; i++) {
                this.colores[i] = (Color) this.automata.asociaciones.get(this.automata.NombreEstados[i] + "");
            }
        } else {
            this.imagenes = new ImageIcon[this.automata.NombreEstados.length];
            for (int i = 0; i < imagenes.length; i++) {
                this.imagenes[i] = new ImageIcon(ImageIO.read(new File(this.automata.asociaciones.get(this.automata.NombreEstados[i]) + "")).getScaledInstance(this.anchoCelda, this.altoCelda, Image.SCALE_SMOOTH));
            }
        }
        this.ColEstadoActual = (Color) this.automata.asociaciones.get("Actual");
        if ("".equals(this.automata.VestadoActual)) {
            this.imgsEstAct[0] = new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/hderecha.png")).getScaledInstance(this.anchoCelda, this.altoCelda, java.awt.Image.SCALE_SMOOTH));
            this.imgsEstAct[1] = new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/hatras.png")).getScaledInstance(this.anchoCelda, this.altoCelda, java.awt.Image.SCALE_SMOOTH));
            this.imgsEstAct[2] = new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/hizquierda.png")).getScaledInstance(this.anchoCelda, this.altoCelda, java.awt.Image.SCALE_SMOOTH));
            this.imgsEstAct[3] = new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/hfrente.png")).getScaledInstance(this.anchoCelda, this.altoCelda, java.awt.Image.SCALE_SMOOTH));
        }
    }

    public void limpiar() {
        this.removeAll();
    }

    public void pintarMatriz(boolean manual, boolean bordes) throws IOException {
        setLayout(new GridBagLayout());
        for (int i = 0; i < this.nfilas; i++) {
            for (int j = 0; j < this.ncolumnas; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                crearCelda(this.automata.espacio[i][j], j, i, manual, bordes);
            }
        }
    }

    public void crearCelda(int estado, int x, int y, boolean manual, boolean bordes) throws IOException {
        gbc.gridx = x;
        gbc.gridy = y;
        JLabel jlabel = new JLabel();
        if (bordes && this.automata.asociacionColor) {
            Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
            jlabel.setBorder(border);
        }
        jlabel.setPreferredSize(new Dimension(this.anchoCelda, this.altoCelda));
        if (this.automata.asociacionColor) {
            jlabel.setOpaque(true);
            jlabel.setBackground(this.colores[estado]);
        } else {
            jlabel.setIcon(this.imagenes[estado]);
        }
        if (manual) {
            jlabel.setName(x + "," + y);
            jlabel.addMouseListener(this);
        }
        this.add(jlabel, gbc);
        revalidate();
        repaint();
    }

    public void actualizarCelda(int x, int y, int estado) {
        JLabel aux = (JLabel) this.getComponent(x * this.ncolumnas + y);
        if (this.automata.asociacionColor) {
            aux.setBackground(this.colores[estado]);
        } else {
            aux.setIcon(this.imagenes[estado]);
        }
    }

    public void actualizarCelda2(int x, int y, int estado) {
        JLabel aux = (JLabel) this.getComponent(x * this.ncolumnas + y);
        aux.setText("");
        aux.setIcon(null);
        if (this.automata.asociacionColor) {
            aux.setBackground(this.colores[estado]);
        } else {
            aux.setIcon(this.imagenes[estado]);
        }
    }

    public void actualizarPeligro(int x, int y) {
        try {
            JLabel aux = (JLabel) this.getComponent(x * this.ncolumnas + y);
            aux.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Imagenes/peligro.png")).getScaledInstance(this.anchoCelda, this.altoCelda, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException ex) {
            Logger.getLogger(panel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarActual() {
        JLabel aux = (JLabel) this.getComponent(this.automata.estadoActual[0] * this.ncolumnas + this.automata.estadoActual[1]);
        aux.setBackground(this.ColEstadoActual);
        if ("".equals(this.automata.VestadoActual)) {
            aux.setIcon(this.imgsEstAct[this.automata.direccion / 90]);
        } else {
            aux.setText(this.automata.VestadoActual);
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

        setPreferredSize(new java.awt.Dimension(790, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        String command = source.getName();
        String[] arr = command.split(",");
        int y = Integer.parseInt(arr[0]);
        int x = Integer.parseInt(arr[1]);
        Object opcion = JOptionPane.showInputDialog(null, "Seleccione el estado", "Elegir", JOptionPane.QUESTION_MESSAGE, null, this.automata.NombreEstados, this.automata.NombreEstados[this.automata.espacio[x][y]]);
        this.automata.espacio[x][y] = Arrays.asList(this.automata.NombreEstados).indexOf(opcion + "");
        this.actualizarCelda(x, y, this.automata.espacio[x][y]);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
