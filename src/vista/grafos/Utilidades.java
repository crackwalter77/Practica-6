/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.grafos;

import controlador.DAO.DaoCasa;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.PosicionException;
import controlador.ed.lista.exception.VacioException;
import javax.swing.JComboBox;
import modelo.Casa;

/**
 *
 * @author santiago
 */
public class Utilidades {
    
    public static void cargarCombos(JComboBox cbx){
        
        cbx.removeAllItems();
        
        ListaEnlazada<Casa> casas = new DaoCasa().listar();
        
        for(int i = 0 ; i < casas.size() ; i++){
            try {
                cbx.addItem(casas.get(i).getNumCasa());
            } catch (VacioException | PosicionException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
}
