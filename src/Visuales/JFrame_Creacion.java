/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuales;

import Modelos.Automata;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario A. Yusti
 */
public class JFrame_Creacion extends javax.swing.JFrame {

    /**
     * Creates new form JFrame_Creacion
     */
    Automata automata;
    boolean exito = true;
    String pathImg1;
    String pathImg2;
    String pathImg3;
    String pathImg4;
    String pathImg5;

    public JFrame_Creacion() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(this);
        this.jLabel4.setVisible(false);
        this.jLabel3.setVisible(false);
        this.jTextField_n.setVisible(false);
        this.jTextField_en3.setVisible(false);
        this.jButton_c3.setVisible(false);
        this.jTextField_en4.setVisible(false);
        this.jButton_c4.setVisible(false);
        this.jTextField_en5.setVisible(false);
        this.jButton_c5.setVisible(false);
        this.BtnImagen3.setVisible(false);
        this.BtnImagen4.setVisible(false);
        this.BtnImagen5.setVisible(false);
        this.BtnImagen1.setVisible(false);
        this.BtnImagen2.setVisible(false);
    }

    public void crearAutomata() {
        this.exito = true;
        int m = 0;
        int n = 0;
        int nes = 0;
        int frontera = 0;
        int vfrontera = 0;
        String VestAct = "";
        try {
            m = Integer.parseInt(this.jTextField_n.getText());
            n = Integer.parseInt(this.jTextField_m.getText());
            nes = Integer.parseInt(this.jComboBox_estados.getSelectedItem() + "");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en la variable M o N");
            this.exito = false;
        }
        if (this.exito && this.jRadioButton_ACEstAct.isSelected()) {
            VestAct = this.jTextField_char.getText();
            if (VestAct.length() > 1 || VestAct.equals("")) {
                this.exito = false;
                JOptionPane.showMessageDialog(null, "Caracter invalido");
            }
        }
        if (this.exito && m > 0 && n > 0) {
            if (this.jRadioButton_fabierta.isSelected()) {
                vfrontera = this.jComboBox_VFrontera.getSelectedIndex();
                frontera = 2;
            } else if (this.jRadioButton_freflejada.isSelected()) {
                frontera = 1;
            } else if (this.jRadioButton_fcircular.isSelected()) {
                frontera = 0;
            }
        } else if (this.exito) {
            JOptionPane.showMessageDialog(null, "Las variables M y N no pueden ser cero");
            this.exito = false;
        }
        if (this.exito) {
            if (JoptionColor.isSelected()) {
                this.automata = new Automata(m, n, nes, frontera, vfrontera, true, Integer.parseInt(this.jComboBox_Vecindad.getSelectedItem() + ""), VestAct);
                this.automata.NombreEstados[0] = this.jTextField_en1.getText();
                this.automata.asociaciones.put(jTextField_en1.getText(), this.jButton_c1.getBackground());
                this.automata.NombreEstados[1] = this.jTextField_en2.getText();
                this.automata.asociaciones.put(jTextField_en2.getText(), this.jButton_c2.getBackground());
                if (this.exito && nes > 2) {
                    this.automata.NombreEstados[2] = this.jTextField_en3.getText();
                    this.automata.asociaciones.put(jTextField_en3.getText(), this.jButton_c3.getBackground());
                }
                if (this.exito && nes > 3) {
                    this.automata.NombreEstados[3] = this.jTextField_en4.getText();
                    this.automata.asociaciones.put(jTextField_en4.getText(), this.jButton_c4.getBackground());
                }
                if (this.exito && nes > 4) {
                    this.automata.NombreEstados[4] = this.jTextField_en5.getText();
                    this.automata.asociaciones.put(jTextField_en5.getText(), this.jButton_c5.getBackground());
                }
                this.automata.asociaciones.put("Actual", this.jButton_c6.getBackground());
            } else if (JoptionImg.isSelected()) {
                this.automata = new Automata(m, n, nes, frontera, vfrontera, false, Integer.parseInt(this.jComboBox_Vecindad.getSelectedItem() + ""), VestAct);
                if (this.pathImg1 != null && this.pathImg2 != null) {
                    this.automata.NombreEstados[0] = this.jTextField_en1.getText();
                    this.automata.asociaciones.put(jTextField_en1.getText(), this.pathImg1);
                    this.automata.NombreEstados[1] = this.jTextField_en2.getText();
                    this.automata.asociaciones.put(jTextField_en2.getText(), this.pathImg2);
                } else {
                    this.exito = false;
                }
                if (this.exito && nes > 2) {
                    if (this.pathImg3 != null) {
                        this.automata.NombreEstados[2] = this.jTextField_en3.getText();
                        this.automata.asociaciones.put(jTextField_en3.getText(), this.pathImg3);
                    } else {
                        this.exito = false;
                    }
                }
                if (this.exito && nes > 3) {
                    if (this.pathImg4 != null) {
                        this.automata.NombreEstados[3] = this.jTextField_en4.getText();
                        this.automata.asociaciones.put(jTextField_en4.getText(), this.pathImg4);
                    } else {
                        this.exito = false;
                    }
                }
                if (this.exito && nes > 4) {
                    if (this.pathImg5 != null) {
                        this.automata.NombreEstados[4] = this.jTextField_en5.getText();
                        this.automata.asociaciones.put(jTextField_en5.getText(), this.pathImg5);
                    } else {
                        this.exito = false;
                    }
                }
                this.automata.asociaciones.put("Actual", this.jButton_c6.getBackground());
                if (!this.exito) {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado alguna de las imagenes");
                }
            }
        }
    }

    public void crearAutomataAleatorio() {
        int nes = numeroAleatorio(2, 5);
        int f = numeroAleatorio(0, 2);
        int vf = 0;
        int vec = 0;
        while (vec != 1 && vec != 2 && vec != 3 && vec != 4 && vec != 8) {
            vec = numeroAleatorio(1, 8);
        }
        if (f == 2) {
            vf = numeroAleatorio(0, nes - 1);
        }
        this.automata = new Automata(numeroAleatorio(1, 200), numeroAleatorio(1, 200), nes, f, vf, true, vec, "");
        this.automata.NombreEstados = new String[nes];
        Color col = new Color(255, 255, 255);
        for (int i = 0; i < nes; i++) {
            this.automata.NombreEstados[i] = (i + 1) + "";
            switch (i) {
                case 1:
                    col = this.jButton_c2.getBackground();
                    break;
                case 2:
                    col = this.jButton_c3.getBackground();
                    break;
                case 3:
                    col = this.jButton_c4.getBackground();
                    break;
                case 4:
                    col = this.jButton_c5.getBackground();
                    break;
                default:
                    break;
            }
            this.automata.asociaciones.put((i + 1) + "", col);
        }
        this.automata.asociaciones.put("Actual", this.jButton_c6.getBackground());
        this.automata.llenarAleatorio();
        JFrame_Mostrar jframe = new JFrame_Mostrar();
        try {
            jframe.inicio(this.automata, false);
        } catch (IOException ex) {
            Logger.getLogger(JFrame_Creacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        jframe.setVisible(true);
        this.dispose();
    }

    public int numeroAleatorio(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public void actualizarVFronteras() {
        this.jComboBox_VFrontera.removeAllItems();
        this.jComboBox_VFrontera.addItem(this.jTextField_en1.getText());
        this.jComboBox_VFrontera.addItem(this.jTextField_en2.getText());
        for (int i = 1; i <= this.jComboBox_estados.getSelectedIndex(); i++) {
            switch (i) {
                case 1:
                    this.jComboBox_VFrontera.addItem(this.jTextField_en3.getText());
                    break;
                case 2:
                    this.jComboBox_VFrontera.addItem(this.jTextField_en4.getText());
                    break;
                case 3:
                    this.jComboBox_VFrontera.addItem(this.jTextField_en5.getText());
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

        dimensiones = new javax.swing.ButtonGroup();
        Asociaciones = new javax.swing.ButtonGroup();
        buttonGroup_Frontera = new javax.swing.ButtonGroup();
        buttonGroup_AsociacionEstAct = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField_m = new javax.swing.JTextField();
        jTextField_n = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton_1d = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton_2d = new javax.swing.JRadioButton();
        jComboBox_estados = new javax.swing.JComboBox<>();
        jTextField_en5 = new javax.swing.JTextField();
        jTextField_en4 = new javax.swing.JTextField();
        jTextField_en3 = new javax.swing.JTextField();
        jTextField_en2 = new javax.swing.JTextField();
        jTextField_en1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton_c1 = new javax.swing.JButton();
        jButton_c2 = new javax.swing.JButton();
        jButton_c3 = new javax.swing.JButton();
        jButton_c4 = new javax.swing.JButton();
        jButton_c5 = new javax.swing.JButton();
        jButton_crearAleatorio = new javax.swing.JButton();
        BtnImagen1 = new javax.swing.JButton();
        JoptionColor = new javax.swing.JRadioButton();
        JoptionImg = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        BtnImagen2 = new javax.swing.JButton();
        BtnImagen3 = new javax.swing.JButton();
        BtnImagen5 = new javax.swing.JButton();
        BtnImagen4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton_crearManual = new javax.swing.JButton();
        jComboBox_Vecindad = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jRadioButton_fcircular = new javax.swing.JRadioButton();
        jRadioButton_freflejada = new javax.swing.JRadioButton();
        jRadioButton_fabierta = new javax.swing.JRadioButton();
        jComboBox_VFrontera = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jButton_c6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jRadioButton_AIEstAct = new javax.swing.JRadioButton();
        jRadioButton_ACEstAct = new javax.swing.JRadioButton();
        jTextField_char = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton_aleatorio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Creación del Automata Celular");

        jTextField_m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_m.setText("5");

        jTextField_n.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_n.setText("1");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("M:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Estados:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("N:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("X");

        dimensiones.add(jRadioButton_1d);
        jRadioButton_1d.setSelected(true);
        jRadioButton_1d.setText("1 Dimención");
        jRadioButton_1d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_1dActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Definición del Espacio");

        dimensiones.add(jRadioButton_2d);
        jRadioButton_2d.setText("2 Dimenciones");
        jRadioButton_2d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_2dActionPerformed(evt);
            }
        });

        jComboBox_estados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5" }));
        jComboBox_estados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_estadosActionPerformed(evt);
            }
        });

        jTextField_en5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_en5.setText("5");

        jTextField_en4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_en4.setText("4");

        jTextField_en3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_en3.setText("3");

        jTextField_en2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_en2.setText("2");

        jTextField_en1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_en1.setText("1");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Nombre");

        jButton_c1.setBackground(new java.awt.Color(255, 255, 255));
        jButton_c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c1ActionPerformed(evt);
            }
        });

        jButton_c2.setBackground(new java.awt.Color(0, 0, 0));
        jButton_c2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c2ActionPerformed(evt);
            }
        });

        jButton_c3.setBackground(new java.awt.Color(0, 0, 204));
        jButton_c3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c3ActionPerformed(evt);
            }
        });

        jButton_c4.setBackground(new java.awt.Color(255, 0, 0));
        jButton_c4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c4ActionPerformed(evt);
            }
        });

        jButton_c5.setBackground(new java.awt.Color(255, 255, 0));
        jButton_c5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c5ActionPerformed(evt);
            }
        });

        jButton_crearAleatorio.setText("Aleatorio");
        jButton_crearAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_crearAleatorioActionPerformed(evt);
            }
        });

        BtnImagen1.setText("Imagen 1");
        BtnImagen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagen1ActionPerformed(evt);
            }
        });

        Asociaciones.add(JoptionColor);
        JoptionColor.setSelected(true);
        JoptionColor.setText("Color");
        JoptionColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoptionColorActionPerformed(evt);
            }
        });

        Asociaciones.add(JoptionImg);
        JoptionImg.setText("Imágen");
        JoptionImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoptionImgActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel6.setText("Asociar por:");

        BtnImagen2.setText("Imagen 2");
        BtnImagen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagen2ActionPerformed(evt);
            }
        });

        BtnImagen3.setText("Imagen 3");
        BtnImagen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagen3ActionPerformed(evt);
            }
        });

        BtnImagen5.setText("Imagen 5");
        BtnImagen5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagen5ActionPerformed(evt);
            }
        });

        BtnImagen4.setText("Imagen 4");
        BtnImagen4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagen4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Configuración Inicial");

        jButton_crearManual.setText("Manual");
        jButton_crearManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_crearManualActionPerformed(evt);
            }
        });

        jComboBox_Vecindad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "8" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Vecindad:");

        buttonGroup_Frontera.add(jRadioButton_fcircular);
        jRadioButton_fcircular.setText("Circular");
        jRadioButton_fcircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_fcircularActionPerformed(evt);
            }
        });

        buttonGroup_Frontera.add(jRadioButton_freflejada);
        jRadioButton_freflejada.setSelected(true);
        jRadioButton_freflejada.setText("Reflejada");
        jRadioButton_freflejada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_freflejadaActionPerformed(evt);
            }
        });

        buttonGroup_Frontera.add(jRadioButton_fabierta);
        jRadioButton_fabierta.setText("Abierta");
        jRadioButton_fabierta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_fabiertaActionPerformed(evt);
            }
        });

        jComboBox_VFrontera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        jComboBox_VFrontera.setEnabled(false);
        jComboBox_VFrontera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_VFronteraMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Frontera:");

        jButton_c6.setBackground(new java.awt.Color(0, 102, 102));
        jButton_c6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_c6ActionPerformed(evt);
            }
        });

        jLabel11.setText("Color del Fondo:");

        buttonGroup_AsociacionEstAct.add(jRadioButton_AIEstAct);
        jRadioButton_AIEstAct.setSelected(true);
        jRadioButton_AIEstAct.setText("Imagen");
        jRadioButton_AIEstAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_AIEstActActionPerformed(evt);
            }
        });

        buttonGroup_AsociacionEstAct.add(jRadioButton_ACEstAct);
        jRadioButton_ACEstAct.setText("Caracter:");
        jRadioButton_ACEstAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_ACEstActActionPerformed(evt);
            }
        });

        jTextField_char.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_char.setToolTipText("");
        jTextField_char.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Estado Actual");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(JoptionColor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_en4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_en3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_en2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_en1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_en5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(135, 135, 135)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_c2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_c3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_c4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_c5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_c1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BtnImagen2)
                                .addComponent(BtnImagen1)
                                .addComponent(BtnImagen3)
                                .addComponent(BtnImagen4)
                                .addComponent(BtnImagen5)
                                .addComponent(JoptionImg, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addGap(52, 52, 52))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_crearAleatorio)
                        .addGap(51, 51, 51)
                        .addComponent(jButton_crearManual)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton_1d)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButton_2d))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_m, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_n, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(122, 122, 122))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(179, 179, 179))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton_fcircular)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton_freflejada)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton_fabierta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_VFrontera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_Vecindad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton_AIEstAct)
                                .addGap(28, 28, 28)
                                .addComponent(jRadioButton_ACEstAct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_char, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_c6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_1d)
                    .addComponent(jRadioButton_2d))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_fabierta)
                    .addComponent(jRadioButton_freflejada)
                    .addComponent(jRadioButton_fcircular)
                    .addComponent(jComboBox_VFrontera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_Vecindad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(JoptionColor)
                    .addComponent(JoptionImg))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnImagen1)
                    .addComponent(jButton_c1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_en1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnImagen2)
                    .addComponent(jTextField_en2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_c2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnImagen3)
                    .addComponent(jTextField_en3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_c3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnImagen4)
                    .addComponent(jTextField_en4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_c4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_en5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_c5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnImagen5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton_ACEstAct)
                        .addComponent(jTextField_char, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton_AIEstAct)
                        .addComponent(jLabel11))
                    .addComponent(jButton_c6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_crearAleatorio)
                    .addComponent(jButton_crearManual)))
        );

        jTabbedPane1.addTab("Creación Manual", jPanel1);

        jButton_aleatorio.setText("Creación Aleatoria");
        jButton_aleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aleatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jButton_aleatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jButton_aleatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Aleatorio", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_aleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aleatorioActionPerformed
        this.crearAutomataAleatorio();
    }//GEN-LAST:event_jButton_aleatorioActionPerformed

    private void BtnImagen4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagen4ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showDialog(null, "Aceptar");
        pathImg4 = fc.getSelectedFile().getAbsolutePath();
    }//GEN-LAST:event_BtnImagen4ActionPerformed

    private void BtnImagen5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagen5ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showDialog(null, "Aceptar");
        pathImg5 = fc.getSelectedFile().getAbsolutePath();
    }//GEN-LAST:event_BtnImagen5ActionPerformed

    private void BtnImagen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagen3ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showDialog(null, "Aceptar");
        pathImg3 = fc.getSelectedFile().getAbsolutePath();
    }//GEN-LAST:event_BtnImagen3ActionPerformed

    private void BtnImagen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagen2ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showDialog(null, "Aceptar");
        pathImg2 = fc.getSelectedFile().getAbsolutePath();
    }//GEN-LAST:event_BtnImagen2ActionPerformed

    private void BtnImagen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagen1ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showDialog(null, "Aceptar");
        pathImg1 = fc.getSelectedFile().getAbsolutePath();
    }//GEN-LAST:event_BtnImagen1ActionPerformed

    private void jButton_crearAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_crearAleatorioActionPerformed
        this.crearAutomata();
        if (this.exito) {
            this.automata.llenarAleatorio();
            JFrame_Mostrar jframe = new JFrame_Mostrar();
            try {
                jframe.inicio(this.automata, false);
            } catch (IOException ex) {
                Logger.getLogger(JFrame_Creacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            jframe.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton_crearAleatorioActionPerformed

    private void jButton_c5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c5ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c5.getBackground());
        this.jButton_c5.setBackground(background);
    }//GEN-LAST:event_jButton_c5ActionPerformed

    private void jButton_c4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c4ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c4.getBackground());
        this.jButton_c4.setBackground(background);
    }//GEN-LAST:event_jButton_c4ActionPerformed

    private void jButton_c3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c3ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c3.getBackground());
        this.jButton_c3.setBackground(background);
    }//GEN-LAST:event_jButton_c3ActionPerformed

    private void jButton_c2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c2ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c2.getBackground());
        this.jButton_c2.setBackground(background);
    }//GEN-LAST:event_jButton_c2ActionPerformed

    private void jButton_c1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c1ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c1.getBackground());
        this.jButton_c1.setBackground(background);
    }//GEN-LAST:event_jButton_c1ActionPerformed

    private void jComboBox_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_estadosActionPerformed
        if (this.jRadioButton_fabierta.isEnabled()) {
            this.actualizarVFronteras();
        }
        switch (Integer.parseInt(this.jComboBox_estados.getSelectedItem() + "")) {
            case 2:
                this.jTextField_en3.setVisible(false);
                this.jTextField_en5.setVisible(false);
                this.jTextField_en4.setVisible(false);
                if (this.JoptionColor.isSelected()) {
                    this.jButton_c3.setVisible(false);
                    this.jButton_c4.setVisible(false);
                    this.jButton_c5.setVisible(false);
                } else {
                    this.BtnImagen3.setVisible(false);
                    this.BtnImagen4.setVisible(false);
                    this.BtnImagen5.setVisible(false);
                }
                break;
            case 3:
                this.jTextField_en3.setVisible(true);
                this.jTextField_en5.setVisible(false);
                this.jTextField_en4.setVisible(false);
                if (this.JoptionColor.isSelected()) {
                    this.jButton_c3.setVisible(true);
                    this.jButton_c4.setVisible(false);
                    this.jButton_c5.setVisible(false);
                } else {
                    this.BtnImagen3.setVisible(true);
                    this.BtnImagen4.setVisible(false);
                    this.BtnImagen5.setVisible(false);
                }
                break;
            case 4:
                this.jTextField_en3.setVisible(true);
                this.jTextField_en4.setVisible(true);
                this.jTextField_en5.setVisible(false);
                if (this.JoptionColor.isSelected()) {
                    this.jButton_c3.setVisible(true);
                    this.jButton_c4.setVisible(true);
                    this.jButton_c5.setVisible(false);
                } else {
                    this.BtnImagen4.setVisible(true);
                    this.BtnImagen3.setVisible(true);
                    this.BtnImagen5.setVisible(false);
                }
                break;
            case 5:
                this.jTextField_en3.setVisible(true);
                this.jTextField_en5.setVisible(true);
                this.jTextField_en4.setVisible(true);
                if (this.JoptionColor.isSelected()) {
                    this.jButton_c4.setVisible(true);
                    this.jButton_c5.setVisible(true);
                    this.jButton_c3.setVisible(true);
                } else {
                    this.BtnImagen5.setVisible(true);
                    this.BtnImagen4.setVisible(true);
                    this.BtnImagen3.setVisible(true);
                }
                break;
        }
    }//GEN-LAST:event_jComboBox_estadosActionPerformed

    private void jRadioButton_2dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_2dActionPerformed
        this.jLabel4.setVisible(true);
        this.jLabel3.setVisible(true);
        this.jTextField_n.setVisible(true);
    }//GEN-LAST:event_jRadioButton_2dActionPerformed

    private void jRadioButton_1dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_1dActionPerformed
        this.jLabel4.setVisible(false);
        this.jLabel3.setVisible(false);
        this.jTextField_n.setVisible(false);
        this.jTextField_n.setText("1");
    }//GEN-LAST:event_jRadioButton_1dActionPerformed

    private void JoptionImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JoptionImgActionPerformed
        this.BtnImagen1.setVisible(true);
        this.BtnImagen2.setVisible(true);
        this.jButton_c1.setVisible(false);
        this.jButton_c2.setVisible(false);
        this.jButton_c3.setVisible(false);
        this.jButton_c4.setVisible(false);
        this.jButton_c5.setVisible(false);
        this.jComboBox_estadosActionPerformed(evt);
    }//GEN-LAST:event_JoptionImgActionPerformed

    private void JoptionColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JoptionColorActionPerformed
        this.jButton_c1.setVisible(true);
        this.jButton_c2.setVisible(true);
        this.BtnImagen3.setVisible(false);
        this.BtnImagen4.setVisible(false);
        this.BtnImagen5.setVisible(false);
        this.BtnImagen1.setVisible(false);
        this.BtnImagen2.setVisible(false);
        this.jComboBox_estadosActionPerformed(evt);
    }//GEN-LAST:event_JoptionColorActionPerformed

    private void jButton_crearManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_crearManualActionPerformed
        this.crearAutomata();
        if (this.exito) {
            JFrame_Mostrar jframe = new JFrame_Mostrar();
            try {
                jframe.inicio(this.automata, true);
            } catch (IOException ex) {
                Logger.getLogger(JFrame_Creacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            jframe.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton_crearManualActionPerformed

    private void jRadioButton_fabiertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_fabiertaActionPerformed
        this.jComboBox_VFrontera.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_fabiertaActionPerformed

    private void jComboBox_VFronteraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_VFronteraMouseClicked
        this.actualizarVFronteras();
    }//GEN-LAST:event_jComboBox_VFronteraMouseClicked

    private void jRadioButton_freflejadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_freflejadaActionPerformed
        this.jComboBox_VFrontera.setEnabled(false);
    }//GEN-LAST:event_jRadioButton_freflejadaActionPerformed

    private void jRadioButton_fcircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_fcircularActionPerformed
        this.jComboBox_VFrontera.setEnabled(false);
    }//GEN-LAST:event_jRadioButton_fcircularActionPerformed

    private void jButton_c6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_c6ActionPerformed
        Color background = JColorChooser.showDialog(null, "Elegir color del Estado ", this.jButton_c6.getBackground());
        this.jButton_c6.setBackground(background);
    }//GEN-LAST:event_jButton_c6ActionPerformed

    private void jRadioButton_ACEstActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_ACEstActActionPerformed
        this.jTextField_char.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_ACEstActActionPerformed

    private void jRadioButton_AIEstActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_AIEstActActionPerformed
        this.jTextField_char.setText("");
        this.jTextField_char.setEnabled(false);
    }//GEN-LAST:event_jRadioButton_AIEstActActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_Creacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Creacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Creacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Creacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Creacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Asociaciones;
    private javax.swing.JButton BtnImagen1;
    private javax.swing.JButton BtnImagen2;
    private javax.swing.JButton BtnImagen3;
    private javax.swing.JButton BtnImagen4;
    private javax.swing.JButton BtnImagen5;
    private javax.swing.JRadioButton JoptionColor;
    private javax.swing.JRadioButton JoptionImg;
    private javax.swing.ButtonGroup buttonGroup_AsociacionEstAct;
    private javax.swing.ButtonGroup buttonGroup_Frontera;
    private javax.swing.ButtonGroup dimensiones;
    private javax.swing.JButton jButton_aleatorio;
    private javax.swing.JButton jButton_c1;
    private javax.swing.JButton jButton_c2;
    private javax.swing.JButton jButton_c3;
    private javax.swing.JButton jButton_c4;
    private javax.swing.JButton jButton_c5;
    private javax.swing.JButton jButton_c6;
    private javax.swing.JButton jButton_crearAleatorio;
    private javax.swing.JButton jButton_crearManual;
    private javax.swing.JComboBox<String> jComboBox_VFrontera;
    private javax.swing.JComboBox<String> jComboBox_Vecindad;
    private javax.swing.JComboBox<String> jComboBox_estados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton_1d;
    private javax.swing.JRadioButton jRadioButton_2d;
    private javax.swing.JRadioButton jRadioButton_ACEstAct;
    private javax.swing.JRadioButton jRadioButton_AIEstAct;
    private javax.swing.JRadioButton jRadioButton_fabierta;
    private javax.swing.JRadioButton jRadioButton_fcircular;
    private javax.swing.JRadioButton jRadioButton_freflejada;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField_char;
    private javax.swing.JTextField jTextField_en1;
    private javax.swing.JTextField jTextField_en2;
    private javax.swing.JTextField jTextField_en3;
    private javax.swing.JTextField jTextField_en4;
    private javax.swing.JTextField jTextField_en5;
    private javax.swing.JTextField jTextField_m;
    private javax.swing.JTextField jTextField_n;
    // End of variables declaration//GEN-END:variables
}
