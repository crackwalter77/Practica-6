/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ed.grafo;

import controlador.ed.grafo.exception.GrafoSizeExeption;
import controlador.ed.lista.ListaEnlazada;
import java.util.Objects;
import javax.swing.JOptionPane;
import modelo.Red;

/**
 *
 * @author darkangel
 */
public class GrafoD extends Grafo {

    protected Integer numV;
    protected Integer numA;
    protected ListaEnlazada<Adycencia> listaAdycencia[];

    public GrafoD(Integer nroVertices) {
        numV = nroVertices;
        numA = 0;
        listaAdycencia = new ListaEnlazada[nroVertices + 1];
        for (int i = 1; i <= nroVertices; i++) {
            listaAdycencia[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public Integer numVertices() {
        return numV;
    }

    @Override
    public Integer numAristas() {
        return numA;
    }

    @Override
    public Boolean existeArista(Integer i, Integer j) throws GrafoSizeExeption {
        Boolean esta = false;
        if (i.intValue() <= numV && j.intValue() <= numV) {
            ListaEnlazada<Adycencia> lista = listaAdycencia[i];
            for (int k = 0; k < lista.size(); k++) {
                try {
                    Adycencia aux = lista.get(k);
                    if (aux.getDestino().intValue() == j.intValue()) {
                        esta = true;
                        break;
                    }
                } catch (Exception e) {
                }
            }
        } else {
            throw new GrafoSizeExeption();
        }
        return esta;
    }

    @Override
    public Double pesoArista(Integer i, Integer j) throws GrafoSizeExeption {
        Double esta = Double.NaN;
        if (i.intValue() <= numV && j.intValue() <= numV) {
            ListaEnlazada<Adycencia> lista = listaAdycencia[i];
            for (int k = 0; k < lista.size(); k++) {
                try {
                    Adycencia aux = lista.get(k);
                    if (aux.getDestino().intValue() == j.intValue()) {
                        esta = aux.getPeso();
                        break;
                    }
                } catch (Exception e) {
                }
            }
        } else {
            throw new GrafoSizeExeption();
        }
        return esta;
    }

    @Override
    public void insertar(Integer i, Integer j) throws GrafoSizeExeption {
        insertar(i, j, Double.NaN);
    }

    @Override
    public void insertar(Integer i, Integer j, Double peso) throws GrafoSizeExeption {
        if (i.intValue() <= numV
                && j.intValue() <= numV) {
            if (!existeArista(i, j)) {
                listaAdycencia[i].insertar(new Adycencia(j, peso));
                numA++;
            }
        } else {
            throw new GrafoSizeExeption();
        }
    }

    @Override
    public ListaEnlazada<Adycencia> adycentes(Integer i) {
        return listaAdycencia[i];
    }

    private Red[] pesos() {

        Red[] pesos = new Red[numA];

        Integer contador = 0;

        for (int i = 1; i <= numVertices(); i++) {
            contador = agregar(i, contador, pesos);
        }

        return pesos;
    }

    public Double[][] adyacencia() throws Exception {
        Double[][] adj = new Double[numV + 1][numV + 1];
        for (int i = 1; i <= numV; i++) {
            for (int j = 1; j <= numV; j++) {
                if (i == j) {
                    adj[i][j] = 0.0;
                } else {
                    if (!existeArista(i, j)) {
                        adj[i][j] = Double.MAX_VALUE;
                    } else {
                        adj[i][j] = pesoArista(i, j);
                    }
                }
            }
        }
        return adj;
    }

    private int agregar(int origen, int destino, Red[] pesos) {

        if (listaAdycencia[origen].isEmpty()) {
            return destino;
        }

        Adycencia[] adyacencias = listaAdycencia[origen].toArray();

        for (Adycencia ady : adyacencias) {
            pesos[destino] = new Red(origen, ady.getDestino(), ady.getPeso());
            destino++;
        }

        return destino;
    }

    public ListaEnlazada<Integer> floyd(Integer origen, Integer destino) throws Exception {
        ListaEnlazada<Integer> respuesta = new ListaEnlazada<>();

        Double[][] adj = adyacencia();

        Integer[][] ruta = new Integer[numV + 1][numV + 1];
        Integer[][] camino = new Integer[numV + 1][numV + 1];

        for (int i = 1; i <= numV; i++) {
            for (int j = 1; j <= numV; j++) {
                if (i == j) {
                    camino[i][j] = 0;
                    ruta[i][j] = 0;
                } else {
                    camino[i][j] = j;
                    ruta[i][j] = i;
                }
            }
        }

        for (int k = 1; k <= numV; k++) {
            for (int i = 1; i <= numV; i++) {
                for (int j = 1; j <= numV; j++) {
                    if (adj[i][k] + adj[k][j] < adj[i][j]) {
                        adj[i][j] = adj[i][k] + adj[k][j];
                        camino[i][j] = camino[i][k]; 
                    }
                }
            }
        }

        Integer i = origen;
        Integer j = destino;

        if (camino[i][j] == j) {
            //JOptionPane.showMessageDialog(null, "No hay camino");
            System.out.println("No hay camino");
            return respuesta;
        }

        while (i != j) {
            respuesta.insertar(i);
            i = camino[i][j];
        }

        respuesta.insertar(i);

        return respuesta;
    }

    public ListaEnlazada<Integer> bellmanFord(Integer origen, Integer destino) {
        Red[] pesos = pesos();
        ListaEnlazada<Integer> respuesta = new ListaEnlazada<>();
        double[] distancias = new double[numV];
        String[] camino = new String[numV];

        for (int i = 0; i < numV; ++i) {
            distancias[i] = Double.MAX_VALUE;
            camino[i] = "Inf";
        }

        distancias[origen - 1] = 0;
        camino[origen - 1] = String.valueOf(origen);

        for (int i = 1; i < numV; ++i) {
            for (int j = 0; j < numA; ++j) {
                Integer u = pesos[j].getIdCasaOrigen() - 1;
                Integer v = pesos[j].getIdCasaDestino() - 1;
                Double peso = pesos[j].getPeso();
                if (distancias[u] != Double.MAX_VALUE && distancias[u] + peso < distancias[v]) {
                    distancias[v] = distancias[u] + peso;
                    camino[v] = String.valueOf(u + 1);
                }
            }
        }

        for (int j = 0; j < numA; ++j) {
            Integer u = pesos[j].getIdCasaOrigen() - 1;
            Integer v = pesos[j].getIdCasaDestino() - 1;
            Double peso = pesos[j].getPeso();
            if (distancias[u] != Double.MAX_VALUE && distancias[u] + peso < distancias[v]) {
                System.out.println("El grafo tiene un ciclo");
                return null;
            }
        }

        boolean encontradoCamino = false;
        if (camino[destino - 1].equals("Inf")) {
            //JOptionPane.showMessageDialog(null, "No hay camino");
            System.out.println("No hay Camino");
        } else {
            encontradoCamino = true;
            respuesta.insertar(destino);
            do {
                destino = Integer.valueOf(camino[destino - 1]);
                respuesta.insertarInicio(destino);
            } while (!Objects.equals(destino, origen));
        }
        return respuesta;
    }
}
