/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Visuales.panel;
import javax.swing.JLabel;

/**
 *
 * @author Mario A. Yusti
 */
public class RSuma implements Runnable {

    int iteraciones;
    Automata automata;
    panel panel;
    int nfilas;
    int ncolumnas;
    boolean[] flag;
    int[][] espacioaux;
    int nvecinos;
    int num1;
    int num2;
    int tipo;
    int poner;
    JLabel jl_iteraciones;

    public RSuma(Automata aut, panel panel, int iteraciones, boolean[] flag, int num1, int num2, int tipo, int poner, JLabel ji) {
        this.automata = aut;
        this.ncolumnas = this.automata.espacio[0].length;
        this.nfilas = this.automata.espacio.length;
        this.nvecinos = this.automata.vecindad.size();
        this.panel = panel;
        this.iteraciones = iteraciones;
        this.flag = flag;
        this.tipo = tipo;
        this.num1 = num1;
        this.num2 = num2;
        this.poner = poner;
        this.jl_iteraciones = ji;
    }

    @Override
    public void run() {
        int it = 0;
        int x;
        int y;
        int contar;
        String aux;
        while (this.flag[0]) {
            this.espacioaux = new int[this.nfilas][this.ncolumnas];
            for (int i = 0; i < this.nfilas; i++) {
                for (int j = 0; j < this.ncolumnas; j++) {
                    aux = "";
                    if (this.automata.frontera[0]) {
                        for (int k = 0; k < this.nvecinos; k++) {
                            x = this.automata.vecindad.get(k)[0];
                            y = this.automata.vecindad.get(k)[1];
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
                        }
                    } else if (this.automata.frontera[1]) {
                        for (int k = 0; k < this.nvecinos; k++) {
                            x = this.automata.vecindad.get(k)[0];
                            y = this.automata.vecindad.get(k)[1];
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
                        }
                    } else {
                        for (int k = 0; k < this.nvecinos; k++) {
                            x = this.automata.vecindad.get(k)[0];
                            y = this.automata.vecindad.get(k)[1];
                            if ((i + x) >= nfilas || (i + x) < 0 || (j + y) >= ncolumnas || (j + y) < 0) {
                                aux += this.automata.vfrontera;
                            } else {
                                aux += this.automata.espacio[i + x][j + y];
                            }
                        }
                    }
                    contar = 0;
                    for (int k = 0; k < aux.length(); k++) {
                        contar += Integer.parseInt(aux.charAt(k) + "");
                    }
                    switch (tipo) {
                        case 0:
                            if (contar >= this.num1 && contar <= this.num2) {
                                this.espacioaux[i][j] = this.poner;
                            }
                            break;
                        case 1:
                            if (contar == this.num1) {
                                this.espacioaux[i][j] = this.poner;
                            }
                            break;
                        case 2:
                            if (contar > this.num1) {
                                this.espacioaux[i][j] = this.poner;
                            }
                            break;
                        case 3:
                            if (contar < this.num1) {
                                this.espacioaux[i][j] = this.poner;
                            }
                            break;
                        default:
                            break;
                    }
                    if (this.automata.espacio[i][j] != this.espacioaux[i][j]) {
                        this.panel.actualizarCelda(i, j, this.poner);
                    }
                }
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
