/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import daos.DaoFichero;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.Pedido;

/**
 *
 * @author songo
 */
public class ControladorPedidos {
    
    DaoFichero dao=new DaoFichero();
    
     public void crearFichero(File fichero){
         dao.crearfichero(fichero);
     }
     
     public void pedidosPorIdCliente(int id, File fichero){
         dao.pedidosPorCliente(id, fichero);
     }

    public void listarPedidos(File fichero) {
        dao.listarPedidos(fichero);
    }
}
