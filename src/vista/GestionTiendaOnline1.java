/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import controlador.ControladorPedidos;
import controlador.ControladorProductos;
import daos.DaoBaseDeDatos;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import main.Utilidades;
import modelo.Pedido;
import modelo.Producto;

/**
 *
 * @author songo
 */
public class GestionTiendaOnline1 {

    public static void main(String[] args) throws SQLException {
        ControladorProductos produ=new ControladorProductos();
        ControladorPedidos pedi=new ControladorPedidos();
        File fichero=new File("pedido.dat");
        if(!fichero.exists()){
			pedi.crearFichero(fichero);
		}
        int opcion,salir=0;
        
        do{
        System.out.println("2-Consultar pedidos");
        System.out.println("3-Consultar productos disponibles");
        System.out.println("4-Editar stock de un producto");
        System.out.println("6-Consultar pedidos de un cliente");
        opcion=Utilidades.leerInt();
            switch (opcion) {
                case 0:
                    System.out.println("Gracias por usar el programa");
                    salir=1;
                    break;
                case 2:
                    pedi.listarPedidos(fichero);
                    break;
                case 3:
                    ArrayList<Producto> productos1=produ.listarProductos();
                    for(Producto p : productos1){
                       System.out.println(p.toString());
                    }
                    break;
                case 4:
                    int codigo,stock;
                    System.out.println("Introduce el codigo del producto a cambiar el stock");
                    ArrayList<Producto> productos=produ.listarProductos();
                    for(Producto p : productos){
                        System.out.println(p.toString());
                    }
                    codigo=Utilidades.leerInt();
                    System.out.println("Ahora introduce el nuevo stock");
                    stock=Utilidades.leerInt();
                    boolean si=produ.editarStock(codigo, stock);
                    if(si==true){
                        System.out.println("Stock actualizado correctamente");
                    }
                    else{
                        System.out.println("Actualizacion de stock fallida");
                    }
                    break;
                case 6:
                    int clienteid;
                    System.out.println("Introduce el codigo de el usuario de el cual quieras ver los pedidos");
                    clienteid=Utilidades.leerInt();
                    pedi.pedidosPorIdCliente(clienteid, fichero);
                    break;
                default:
                    System.out.println("Elige una opcion valida");
                    break;
            }
        }while(salir==0);
    }
    
    
}
