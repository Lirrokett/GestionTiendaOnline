package main;

import controlador.Controlador;
import controlador.ControladorPedidos;
import controlador.ControladorProductos;
import excepciones.AccesoDatosException;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;
import utilidades.Entrada;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        ControladorProductos productos = new ControladorProductos();
        ControladorPedidos pedidos = new ControladorPedidos();
        File fichero = new File("pedido.dat");

        if (!fichero.exists()) {
            pedidos.crearFichero(fichero);
        }

        boolean salir = false;

        while (!salir) {
            System.out.println();
            System.out.println("===== GESTIÓN TIENDA ONLINE =====");
            System.out.println("1-Registrar cliente");
            System.out.println("2-Consultar pedidos");
            System.out.println("3-Consultar productos disponibles");
            System.out.println("4-Editar stock de un producto");
            System.out.println("5-Realizar pedido de un producto");
            System.out.println("6-Consultar pedidos de un cliente");
            System.out.println("0-Salir");

            int opcion = Utilidades.leerInt();

            try {
                switch (opcion) {
                    case 0:
                        System.out.println("Gracias por usar el programa");
                        salir = true;
                        break;

                    case 1:
                        registrarCliente(controlador);
                        break;

                    case 2:
                        pedidos.listarPedidos(fichero);
                        break;

                    case 3:
                        List<Producto> listaProductos = controlador.consultarProductosDisponibles();
                        if (listaProductos.isEmpty()) {
                            System.out.println("No hay productos disponibles.");
                        } else {
                            for (Producto producto : listaProductos) {
                                System.out.println(producto.toString());
                            }
                        }
                        break;

                    case 4:
                        editarStock(productos);
                        break;

                    case 5:
                        realizarPedido(controlador);
                        break;

                    case 6:
                        consultarPedidosCliente(pedidos, fichero);
                        break;

                    default:
                        System.out.println("Elige una opcion valida");
                        break;
                }
            } catch (SQLException | AccesoDatosException | IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void registrarCliente(Controlador controlador)
            throws AccesoDatosException {

        System.out.println("Introduce el nombre del cliente");
        String nombre = Entrada.texto("Nombre: ");

        System.out.println("Introduce el email del cliente");
        String email = Entrada.texto("Email: ");

        System.out.println("Introduce el telefono del cliente");
        String telefono = Entrada.texto("Telefono: ");

        System.out.println("Introduce la direccion del cliente");
        String direccion = Entrada.texto("Direccion: ");

        Cliente cliente = controlador.registrarCliente(
                nombre, email, telefono, direccion);

        System.out.println("Cliente registrado correctamente.");
        System.out.println("ID asignado: " + cliente.getId());
    }

    private static void realizarPedido(Controlador controlador)
            throws AccesoDatosException {

        System.out.println("Introduce el codigo del cliente");
        int idCliente = Utilidades.leerInt();

        System.out.println("Introduce el codigo del producto");
        int idProducto = Utilidades.leerInt();

        Pedido pedido = controlador.realizarPedido(idCliente, idProducto);

        System.out.println("Pedido registrado correctamente.");
        System.out.println("ID del pedido: " + pedido.getId());
    }

    private static void editarStock(ControladorProductos productos)
            throws SQLException {

        System.out.println("Introduce el codigo del producto a cambiar el stock");

        ArrayList<Producto> listaProductos = productos.listarProductos();
        for (Producto producto : listaProductos) {
            System.out.println(producto.toString());
        }

        int codigo = Utilidades.leerInt();

        System.out.println("Ahora introduce el nuevo stock");
        int stock = Utilidades.leerInt();

        boolean actualizado = productos.editarStock(codigo, stock);

        if (actualizado) {
            System.out.println("Stock actualizado correctamente");
        } else {
            System.out.println("Actualizacion de stock fallida");
        }
    }

    private static void consultarPedidosCliente(ControladorPedidos pedidos, File fichero) {
        System.out.println("Introduce el codigo del usuario de el cual quieras ver los pedidos");
        int clienteId = Utilidades.leerInt();
        pedidos.pedidosPorIdCliente(clienteId, fichero);
    }
}
