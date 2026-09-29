package controlador;

import excepciones.AccesoDatosException;
import fichs.PedidoFicheroDAO;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Implementación del DAO del Reto 0.
 * Cliente y Producto se gestionan mediante MySQL y Pedido mediante pedido.dat.
 *
 * Este DAO se adapta a los modelos y configuración que ya existían en
 * GestionTiendaOnline, sin sustituir sus DAOs ni controladores originales.
 */
public class DaoImplementacion implements Dao {

    private final String urlBD;
    private final String userBD;
    private final String passwordBD;
    private final PedidoFicheroDAO pedidoDAO;

    public DaoImplementacion() {
        ResourceBundle configFile = ResourceBundle.getBundle("configClase");
        this.urlBD = configFile.getString("Conn");
        this.userBD = configFile.getString("DBUser");
        this.passwordBD = configFile.getString("DBPass");
        this.pedidoDAO = new PedidoFicheroDAO();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlBD, userBD, passwordBD);
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws AccesoDatosException {
        String sql = "INSERT INTO CLIENTE (NOMBRE, EMAIL, TELEFONO, DIRECCION) VALUES (?, ?, ?, ?)";

        try (Connection cn = conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }

            return cliente;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo registrar el cliente.", e);
        }
    }

    @Override
    public Cliente buscarCliente(int id) throws AccesoDatosException {
        String sql = "SELECT ID, NOMBRE, EMAIL, TELEFONO, DIRECCION FROM CLIENTE WHERE ID = ?";

        try (Connection cn = conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return new Cliente(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO"),
                        rs.getString("DIRECCION")
                );
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el cliente.", e);
        }
    }

    @Override
    public Producto buscarProducto(int id) throws AccesoDatosException {
        String sql = "SELECT ID, NOMBRE, PRECIO, CATEGORIA, STOCK, RUTA FROM PRODUCTO WHERE ID = ?";

        try (Connection cn = conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return productoDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el producto.", e);
        }
    }

    @Override
    public List<Producto> consultarProductosDisponibles() throws AccesoDatosException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT ID, NOMBRE, PRECIO, CATEGORIA, STOCK, RUTA "
                + "FROM PRODUCTO WHERE STOCK > 0 ORDER BY ID";

        try (Connection cn = conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(productoDesdeResultSet(rs));
            }

            return productos;
        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudieron consultar los productos disponibles.", e);
        }
    }

    private Producto productoDesdeResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("ID"));
        producto.setNombre(rs.getString("NOMBRE"));
        producto.setPrecio(rs.getDouble("PRECIO"));

        String categoria = rs.getString("CATEGORIA");
        if (categoria != null) {
            producto.setCategoria(Categoria.valueOf(categoria));
        }

        producto.setStock(rs.getInt("STOCK"));
        producto.setRuta(rs.getString("RUTA"));
        return producto;
    }

    @Override
    public int siguienteIdPedido() throws AccesoDatosException {
        return pedidoDAO.siguienteId();
    }

    @Override
    public void guardarPedido(Pedido pedido) throws AccesoDatosException {
        pedidoDAO.guardarPedido(pedido);
    }
}
