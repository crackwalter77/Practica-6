/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.DAO.DaoCasa;
import controlador.DAO.DaoRed;
import controlador.ed.grafo.GrafoEtiquetadoD;
import controlador.ed.grafo.exception.GrafoSizeExeption;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.PosicionException;
import controlador.ed.lista.exception.VacioException;
import modelo.Casa;
import modelo.Red;

/**
 *
 * @author walter
 */
public class ControladorGrafo {
    private ListaEnlazada<Casa> casas;
    private GrafoEtiquetadoD<Casa> grafo;
    
    public ControladorGrafo(){
        etiquetar();
    }
    
    public void etiquetar(){
        casas = new DaoCasa().listar();
        grafo = new GrafoEtiquetadoD<>(casas.size());
        for(int i = 1 ; i <= casas.size(); i++){
            try {
                grafo.etiquetarVertice(i, casas.get(i - 1));
            } catch (VacioException | PosicionException ex) {
            }
        }
        
        System.out.println(grafo.toString());
        
        agregarRedes();
    }
    
    public void agregarRedes(){
        DaoCasa dao = new DaoCasa();
        
        ListaEnlazada<Red> redes = new DaoRed().listar();
        
        
        for(int i = 0; i < redes.size(); i++){
            try {
                Red r = redes.get(i);
                System.out.println(r.getIdCasaOrigen() +" -> "+ r.getIdCasaDestino() +" : "+ r.getPeso());
                grafo.insertar(r.getIdCasaOrigen(), r.getIdCasaDestino(), r.getPeso());
            } catch (VacioException | PosicionException | GrafoSizeExeption ex) {
                
            }
        }
        
    }

    public ListaEnlazada<Casa> getCasas() {
        return casas;
    }

    public GrafoEtiquetadoD<Casa> getGrafo() {
        return grafo;
    }
    
    
}
