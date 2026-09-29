package controlador;

import excepciones.AccesoDatosException;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;
import java.util.List;

/**
 * Operaciones de acceso a datos utilizadas por el controlador.
 */
public interface Dao {

    Cliente registrarCliente(Cliente cliente) throws AccesoDatosException;

    Cliente buscarCliente(int id) throws AccesoDatosException;

    Producto buscarProducto(int id) throws AccesoDatosException;

    List<Producto> consultarProductosDisponibles() throws AccesoDatosException;

    int siguienteIdPedido() throws AccesoDatosException;

    void guardarPedido(Pedido pedido) throws AccesoDatosException;
}
