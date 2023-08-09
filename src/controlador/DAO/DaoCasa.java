package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.PosicionException;
import controlador.ed.lista.exception.VacioException;
import java.io.IOException;
import java.util.Objects;
import modelo.Casa;

/**
 *
 * @author walter
 */
public class DaoCasa extends AdaptadorDao<Casa> {

    private Casa casa;

    public DaoCasa() {
        super(Casa.class);
    }

    public Casa getCasa() {
        if (casa == null) {
            casa = new Casa();
        }
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public void guardar() throws IOException {
        casa.setId(generateID());
        this.guardar(casa);
    }

    public Casa buscarId(Integer id) throws VacioException, PosicionException {
        Casa c = null;
        ListaEnlazada<Casa> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            Casa aux = lista.get(i);
            if (Objects.equals(aux.getId(), id)) {
                c = aux;
                break;
            }
        }
        return c;
    }

}
