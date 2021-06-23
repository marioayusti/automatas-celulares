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
public class RHormigaLangton implements Runnable {

    int iteraciones;
    Automata automata;
    panel panel;
    int nfilas;
    int ncolumnas;
    boolean[] flag;
    JLabel jl_iteraciones;

    public RHormigaLangton(Automata automata, panel panel, int iteraciones, boolean[] flag, JLabel ji) {
        this.automata = automata;
        this.panel = panel;
        this.nfilas = this.automata.espacio.length;
        this.ncolumnas = this.automata.espacio[0].length;
        this.iteraciones = iteraciones;
        this.flag = flag;
        this.jl_iteraciones = ji;
    }

    @Override
    public void run() {
        int it = 0;
        int[] estAct = this.automata.estadoActual;
        while (this.flag[0]) {
            if (this.automata.espacio[estAct[0]][estAct[1]] == 1) {
                this.automata.espacio[estAct[0]][estAct[1]] = 0;
                this.panel.actualizarCelda2(estAct[0], estAct[1], 0);
                this.automata.direccion -= 90;
            } else {
                this.automata.espacio[estAct[0]][estAct[1]] = 1;
                this.panel.actualizarCelda2(estAct[0], estAct[1], 1);
                this.automata.direccion += 90;
            }
            if (this.automata.direccion < 0) {
                this.automata.direccion += 360;
            } else if (this.automata.direccion == 360) {
                this.automata.direccion = 0;
            }
            switch (this.automata.direccion) {
                case 0:
                    estAct[1] += 1;
                    break;
                case 90:
                    estAct[0] += 1;
                    break;
                case 180:
                    estAct[1] -= 1;
                    break;
                case 270:
                    estAct[0] -= 1;
                    break;
                default:
                    break;
            }
            if (estAct[0] < 0 || estAct[0] == nfilas || estAct[1] < 0 || estAct[1] == this.ncolumnas) {
                flag[0] = false;
                this.automata.estadoActual[0] = 0;
                this.automata.estadoActual[1] = 0;
                System.out.println("Fin");
            } else {
                this.panel.actualizarActual();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(RHormigaLangton.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!this.flag[1]) {
                this.iteraciones--;
                if (this.iteraciones == 0) {
                    this.flag[0] = false;
                }
            }
            if (!this.flag[0]) {
                this.panel.actualizarCelda2(estAct[0], estAct[1], this.automata.espacio[estAct[0]][estAct[1]]);
            }
            it++;
            this.jl_iteraciones.setText(it + "");
        }
    }

}
