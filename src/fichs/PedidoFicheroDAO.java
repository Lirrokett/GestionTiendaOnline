package fichs;

import excepciones.AccesoDatosException;
import modelo.Pedido;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de pedidos del Reto 0.
 *
 * Mantiene el mismo formato de serialización que ya utiliza
 * GestionTiendaOnline: objetos Pedido escritos de forma consecutiva.
 */
public class PedidoFicheroDAO {

    private final File fichero;

    public PedidoFicheroDAO() {
        fichero = new File("pedido.dat");
    }

    public synchronized void guardarPedido(Pedido pedido) throws AccesoDatosException {
        List<Pedido> pedidos = leerPedidos();
        pedidos.add(pedido);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fichero))) {
            for (Pedido p : pedidos) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            throw new AccesoDatosException("No se pudo guardar el pedido.", e);
        }
    }

    public synchronized List<Pedido> leerPedidos() throws AccesoDatosException {
        List<Pedido> pedidos = new ArrayList<>();

        if (!fichero.exists() || fichero.length() == 0) {
            return pedidos;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichero))) {

            while (true) {
                pedidos.add((Pedido) ois.readObject());
            }

        } catch (EOFException e) {
            // Fin normal del fichero.
            return pedidos;
        } catch (IOException | ClassNotFoundException e) {
            throw new AccesoDatosException("No se pudo leer pedido.dat.", e);
        }
    }

    public int siguienteId() throws AccesoDatosException {
        int max = 0;

        for (Pedido pedido : leerPedidos()) {
            if (pedido.getId() > max) {
                max = pedido.getId();
            }
        }

        return max + 1;
    }
}
