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
public class RMayoria implements Runnable {

    Automata automata;
    int[][] espacioaux;
    int ncolumnas;
    int nfilas;
    panel panel;
    int iteraciones;
    boolean flag[];
    int nvecinos;
    int nestados;
    JLabel jl_iteraciones;

    public RMayoria(Automata aut, panel panel, int iteraciones, boolean[] flag, JLabel ji) {
        this.automata = aut;
        this.ncolumnas = this.automata.espacio[0].length;
        this.nfilas = this.automata.espacio.length;
        this.nvecinos = this.automata.vecindad.size();
        this.panel = panel;
        this.iteraciones = iteraciones;
        this.nestados = this.automata.NombreEstados.length;
        this.flag = flag;
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
        int contar;
        int[] max;
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
                    max = new int[2];
                    for (int k = 0; k < this.nestados; k++) {
                        contar = 0;
                        for (int l = 0; l < aux.length(); l++) {
                            if ((k + "").equals(aux.charAt(l) + "")) {
                                contar++;
                            }
                        }
                        if (max[0] < contar) {
                            max[0] = contar;
                            max[1] = k;
                        }
                    }
                    this.espacioaux[i][j] = max[1];
                    if (this.automata.espacio[i][j] != this.espacioaux[i][j]) {
                        this.panel.actualizarCelda(i, j, this.espacioaux[i][j]);
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
