/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mario A. Yusti
 */
public class Automata {

    public int[][] espacio;
    public ArrayList<int[]> vecindad = new ArrayList<>();
    public boolean[] frontera = new boolean[3];
    public int vfrontera;
    public int vecinos;
    public String[] NombreEstados;
    public HashMap asociaciones;
    public boolean asociacionColor;
    public int[] estadoActual = new int[2];
    public String VestadoActual;
    public int direccion = 90;

    public Automata(int n, int m, int nes, int f, int vf, boolean tasociacion, int vecinos, String VestAct) {
        this.espacio = new int[n][m];
        this.NombreEstados = new String[nes];
        this.asociaciones = new HashMap();
        this.asociacionColor = tasociacion;
        this.frontera[f] = true;
        this.vfrontera = vf;
        this.vecinos = vecinos;
        this.VestadoActual = VestAct;
        crearVecindad();
    }

    public void crearVecindad() {
        switch (this.vecinos) {
            case 1:
                this.vecindad.add(new int[]{0, -1});
                this.vecindad.add(new int[]{0, 0});
                this.vecindad.add(new int[]{0, 1});
                break;
            case 2:
                this.vecindad.add(new int[]{0, -2});
                this.vecindad.add(new int[]{0, -1});
                this.vecindad.add(new int[]{0, 0});
                this.vecindad.add(new int[]{0, 1});
                this.vecindad.add(new int[]{0, 2});
                break;
            case 3:
                this.vecindad.add(new int[]{-1, -1});
                this.vecindad.add(new int[]{-1, 0});
                this.vecindad.add(new int[]{-1, 1});
                break;
            case 4:
                this.vecindad.add(new int[]{-1, 0});
                this.vecindad.add(new int[]{0, -1});
                this.vecindad.add(new int[]{0, 1});
                this.vecindad.add(new int[]{1, 0});
                break;
            case 8:
                this.vecindad.add(new int[]{-1, +1});
                this.vecindad.add(new int[]{-1, 0});
                this.vecindad.add(new int[]{-1, -1});
                this.vecindad.add(new int[]{0, +1});
                this.vecindad.add(new int[]{0, -1});
                this.vecindad.add(new int[]{1, +1});
                this.vecindad.add(new int[]{1, 0});
                this.vecindad.add(new int[]{1, -1});
                break;
            default:
                break;
        }
    }

    public void llenarAleatorio() {
        int nes = this.NombreEstados.length;
        for (int i = 0; i < this.espacio.length; i++) {
            for (int j = 0; j < this.espacio[0].length; j++) {
                this.espacio[i][j] = (int) (Math.random() * nes);
            }
        }
    }
}
