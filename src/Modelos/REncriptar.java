/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Visuales.panel;

/**
 *
 * @author Mario A. Yusti
 */
public class REncriptar {

    Automata automataClave;
    Automata automata;
    String alfabeto = " 0123456789abcdefghijklmñnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int[][] espacioaux;
    int ncolumnas;
    int nfilas;
    panel panel;
    int Regla;
    int iteraciones;
    boolean flag = true;
    String clave;

    public REncriptar(int Regla, Automata aut, panel panel, int iteraciones, String clave) {
        this.automata = aut;
        this.ncolumnas = this.automata.espacio[0].length;
        this.nfilas = this.automata.espacio.length;
        this.Regla = Regla;
        this.panel = panel;
        this.iteraciones = iteraciones;
        this.clave = codificarCadena(clave);
        this.automataClave = new Automata(this.nfilas, this.ncolumnas, 2, 0, 0, true, 1, "");
        llenarAutomata();
    }

    public String codificarCadena(String mensaje) {
        String cadena = "";
        for (int i = 0; i < mensaje.length(); i++) {
            cadena += convertirBinario(this.alfabeto.indexOf(mensaje.charAt(i)));
        }
        return cadena;
    }

    public void llenarAutomata() {
        int k = 0;
        for (int i = 0; i < this.nfilas; i++) {
            for (int j = 0; j < this.ncolumnas; j++) {
                if (k == this.clave.length()) {
                    k = 0;
                }
                this.automataClave.espacio[i][j] = Integer.parseInt(this.clave.charAt(k) + "");
                k++;
            }
        }
    }

    public static int getNumero(int regla, int posicion, int base) {
        double numero = (regla - (regla % Math.pow(base, posicion))) / Math.pow(base, posicion + 1);
        return (int) ((numero - ((int) numero)) * base);
    }

    public String convertirBinario(int numero) {
        String retorno = Integer.toString(Integer.parseInt(numero + "", 10), 2);
        int dif = 6 - retorno.length();
        for (int i = 0; i < dif; i++) {
            retorno = "0" + retorno;
        }
        return retorno;
    }

    public void encriptar() {
        int y;
        String aux;
        while (this.flag) {
            this.espacioaux = new int[this.nfilas][this.ncolumnas];
            for (int i = 0; i < this.nfilas; i++) {
                for (int j = 0; j < this.ncolumnas; j++) {
                    aux = "";
                    for (int k = 0; k < 3; k++) {
                        y = this.automataClave.vecindad.get(k)[1];
                        if ((j + y) >= ncolumnas) {
                            y = y - ncolumnas;
                        } else if ((j + y) < 0) {
                            y = y + ncolumnas;
                        }
                        aux += this.automataClave.espacio[i][j + y];
                    }
                    this.espacioaux[i][j] = getNumero(this.Regla, Integer.parseInt(aux, 2), 2);
                }
            }
            this.automataClave.espacio = this.espacioaux;
            this.iteraciones--;
            if (this.iteraciones == 0) {
                this.flag = false;
            }
        }
        for (int i = 0; i < this.nfilas; i++) {
            for (int j = 0; j < this.ncolumnas; j++) {
                this.automata.espacio[i][j] = this.automata.espacio[i][j] ^ this.automataClave.espacio[i][j];
                this.panel.actualizarCelda(i, j, this.automata.espacio[i][j]);
            }
        }
    }
}
