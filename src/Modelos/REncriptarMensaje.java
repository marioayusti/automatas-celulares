/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author Mario A. Yusti
 */
public class REncriptarMensaje {

    Automata automata;
    int[][] espacioaux;
    String alfabeto = " 0123456789abcdefghijklmñnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String mensaje;
    String clave;
    int iteraciones;
    int ncolumnas;
    boolean flag = true;

    public REncriptarMensaje(String mensaje, String clave, int iteraciones) {
        this.mensaje = codificarCadena(mensaje);
        this.clave = codificarCadena(clave);
        if (this.clave.length() < this.mensaje.length()) {
            corregirClave();
        }
        this.iteraciones = iteraciones;
        this.ncolumnas = this.clave.length();
        this.automata = new Automata(1, this.ncolumnas, 1, 0, 0, true, 2, "");
        llenarAutomata();
    }

    public String codificarCadena(String mensaje) {
        String cadena = "";
        for (int i = 0; i < mensaje.length(); i++) {
            cadena += convertirBinario(this.alfabeto.indexOf(mensaje.charAt(i)));
        }
        return cadena;
    }

    public void corregirClave() {
        int aux = this.mensaje.length() - this.clave.length();
        for (int i = 0; i < aux; i++) {
            this.clave = "0" + this.clave;
        }
    }

    public String obtenerMensaje(String aux) {
        String cadena = "";
        String aux2 = "";
        for (int i = 0; i < aux.length(); i++) {
            aux2 += aux.charAt(i) + "";
            if (aux2.length() == 6) {
                cadena += this.alfabeto.charAt(Integer.parseInt(aux2, 2));
                aux2 = "";
            }
        }
        return cadena;
    }

    public void llenarAutomata() {
        for (int i = 0; i < this.automata.espacio[0].length; i++) {
            this.automata.espacio[0][i] = Integer.parseInt(this.clave.charAt(i) + "");
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

    public String encriptar() {
        int y;
        String aux;
        while (this.flag) {
            this.espacioaux = new int[1][this.ncolumnas];
            for (int j = 0; j < this.ncolumnas; j++) {
                aux = "";
                for (int k = 0; k < 3; k++) {
                    y = this.automata.vecindad.get(k)[1];
                    if ((j + y) >= ncolumnas) {
                        y = y - ncolumnas;
                    } else if ((j + y) < 0) {
                        y = y + ncolumnas;
                    }
                    aux += this.automata.espacio[0][j + y];
                }
                this.espacioaux[0][j] = getNumero(30, Integer.parseInt(aux, 2), 2);
            }
            this.automata.espacio = this.espacioaux;
            this.iteraciones--;
            if (this.iteraciones == 0) {
                this.flag = false;
            }
        }
        aux = "";
        int l = this.ncolumnas - this.mensaje.length();
        for (int i = 0; i < this.mensaje.length(); i++) {
            aux += (this.automata.espacio[0][i + l] + "").charAt(0) ^ this.mensaje.charAt(i);
        }
        return obtenerMensaje(aux);
    }
}
