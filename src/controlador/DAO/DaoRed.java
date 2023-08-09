package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.PosicionException;
import controlador.ed.lista.exception.VacioException;
import java.io.IOException;
import modelo.Red;

/**
 *
 * @author walter
 */

public class DaoRed extends AdaptadorDao<Red> {

    private Red red;

    public DaoRed() {
        super(Red.class);
    }

    public Red getRed() {
        if (red == null) {
            red = new Red();
        }
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public void guardar() throws IOException {
        this.guardar(red);
    }

    public ListaEnlazada<Red> listarCasa(Integer id) throws VacioException, PosicionException {
        ListaEnlazada<Red> lista = new ListaEnlazada<>();
        ListaEnlazada<Red> listado = listar();
        for (int i = 0; i < listado.size(); i++) {
            Red aux = listado.get(i);
            if (aux.getIdCasaOrigen().equals(id)) {
                lista.insertar(aux);
            }
        }
        return lista;
    }
}
