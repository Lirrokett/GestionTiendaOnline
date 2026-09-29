package controlador;

import excepciones.AccesoDatosException;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Controlador {

    private final Dao dao;

    public Controlador() {
        dao = new DaoImplementacion();
    }

    public Cliente registrarCliente(String nombre, String email,
                                    String telefono, String direccion)
            throws AccesoDatosException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        nombre = nombre.trim();
        email = email.trim();
        telefono = telefono == null ? null : telefono.trim();
        direccion = direccion == null ? null : direccion.trim();

        if (nombre.length() > 20) {
            throw new IllegalArgumentException("El nombre no puede superar los 20 caracteres.");
        }
        if (email.length() > 30) {
            throw new IllegalArgumentException("El email no puede superar los 30 caracteres.");
        }
        if (telefono != null && telefono.length() > 20) {
            throw new IllegalArgumentException("El teléfono no puede superar los 20 caracteres.");
        }
        if (direccion != null && direccion.length() > 100) {
            throw new IllegalArgumentException("La dirección no puede superar los 100 caracteres.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);

        return dao.registrarCliente(cliente);
    }

    public List<Producto> consultarProductosDisponibles()
            throws AccesoDatosException {
        return dao.consultarProductosDisponibles();
    }

    public Pedido realizarPedido(int idCliente, int idProducto)
            throws AccesoDatosException {

        if (idCliente <= 0) {
            throw new IllegalArgumentException("El código del cliente debe ser mayor que 0.");
        }
        if (idProducto <= 0) {
            throw new IllegalArgumentException("El código del producto debe ser mayor que 0.");
        }

        Cliente cliente = dao.buscarCliente(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe.");
        }

        Producto producto = dao.buscarProducto(idProducto);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        if (producto.getStock() <= 0) {
            throw new IllegalArgumentException("El producto no tiene stock.");
        }

        Pedido pedido = new Pedido();
        pedido.setId(dao.siguienteIdPedido());
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaFin(null);
        pedido.setEntregado(false);
        pedido.setClienteId(idCliente);
        pedido.setProductoId(new ArrayList<>(java.util.Arrays.asList(idProducto)));

        dao.guardarPedido(pedido);

        return pedido;
    }
}
