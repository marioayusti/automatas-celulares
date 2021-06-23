/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Visuales.panel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Mario A. Yusti
 */
public class RTrafico implements Runnable {

    Automata automata;
    int[][] espacioaux;
    int ncolumnas;
    int nfilas;
    panel panel;
    int Regla = 184;
    int iteraciones;
    boolean flag[];
    boolean eventos[];
    int parametros[];
    int nvecinos = 3;
    int nestados = 2;
    JLabel jl_iteraciones;

    public RTrafico(Automata aut, panel panel, int iteraciones, boolean[] flag, boolean[] eventos, int[] parms, JLabel ji) {
        this.automata = aut;
        this.ncolumnas = this.automata.espacio[0].length;
        this.nfilas = this.automata.espacio.length;
        this.panel = panel;
        this.iteraciones = iteraciones;
        this.flag = flag;
        this.eventos = eventos;
        this.parametros = parms;
        this.jl_iteraciones = ji;
    }

    public static int getNumero(int regla, int posicion, int base) {
        double numero = (regla - (regla % Math.pow(base, posicion))) / Math.pow(base, posicion + 1);
        return (int) ((numero - ((int) numero)) * base);
    }

    @Override
    public void run() {
        int it = 0;
        int x;
        int y;
        String aux;
        while (this.flag[0]) {
            this.espacioaux = new int[this.nfilas][this.ncolumnas];
            for (int i = 0; i < this.nfilas; i++) {
                for (int j = 0; j < this.ncolumnas; j++) {
                    if (this.eventos[1] && i == this.parametros[1] && j == this.parametros[2]) {
                        this.espacioaux[i][j] = 1;
                        this.panel.actualizarPeligro(i, j);
                    } else if (this.espacioaux[i][j] == 0) {
                        aux = "";
                        for (int k = 0; k < this.nvecinos; k++) {
                            x = this.automata.vecindad.get(k)[0];
                            y = this.automata.vecindad.get(k)[1];
                            if (this.eventos[0] && (j + y) == ncolumnas) {
                                if (i == this.parametros[0]) {
                                    aux += 0;
                                } else {
                                    aux += 1;
                                }
                            } else if (this.automata.frontera[0] && !this.eventos[0]) {
                                if ((i + x) >= nfilas) {
                                    x = x - nfilas;
                                } else if ((i + x) < 0) {
                                    x = x + nfilas;
                                }
                                if ((j + y) >= ncolumnas) {
                                    y = y - ncolumnas;
                                } else if ((j + y) < 0) {
                                    y = y + ncolumnas;
                                }
                                aux += this.automata.espacio[i + x][j + y];
                            } else if (this.automata.frontera[1] && !this.eventos[0]) {
                                if ((i + x) >= nfilas) {
                                    x = -x + 1;
                                } else if ((i + x) < 0) {
                                    x = -x - 1;
                                }
                                if ((j + y) >= ncolumnas) {
                                    y = -y + 1;
                                } else if ((j + y) < 0) {
                                    y = -y - 1;
                                }
                                aux += this.automata.espacio[i + x][j + y];
                            } else {
                                if ((i + x) >= nfilas || (i + x) < 0 || (j + y) >= ncolumnas || (j + y) < 0) {
                                    aux += this.automata.vfrontera;
                                } else {
                                    aux += this.automata.espacio[i + x][j + y];
                                }
                            }
                        }
                        if (aux.charAt(1) == '1' && aux.charAt(2) == '1' && (j + 1 < this.ncolumnas)) {
                            if (i - 1 >= 0 && this.espacioaux[i - 1][j] == 0 && this.espacioaux[i - 1][j + 1] == 0) {
                                this.espacioaux[i - 1][j] = 1;
                                this.panel.actualizarCelda(i - 1, j, 1);
                                this.espacioaux[i][j] = 0;
                            } else if (i + 1 < this.nfilas && this.automata.espacio[i + 1][j] == 0 && this.automata.espacio[i + 1][j + 1] == 0) {
                                this.espacioaux[i + 1][j] = 1;
                                this.automata.espacio[i + 1][j] = 1;
                                this.panel.actualizarCelda(i + 1, j, 1);
                                this.espacioaux[i][j] = 0;
                            } else {
                                this.espacioaux[i][j] = getNumero(this.Regla, Integer.parseInt(aux, this.nestados), this.nestados);
                            }
                        } else {
                            this.espacioaux[i][j] = getNumero(this.Regla, Integer.parseInt(aux, this.nestados), this.nestados);
                        }
                        if (this.automata.espacio[i][j] != this.espacioaux[i][j]) {
                            this.panel.actualizarCelda(i, j, this.espacioaux[i][j]);
                        }
                    }
                }
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(RTrafico.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.automata.espacio = this.espacioaux;
            it++;
            this.jl_iteraciones.setText(it + "");
            if (!this.flag[1]) {
                this.iteraciones--;
                if (this.iteraciones == 0) {
                    this.flag[0] = false;
                }
            }
        }
    }
}
