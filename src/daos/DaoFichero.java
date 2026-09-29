/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

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
public class DaoFichero {
    
   public void crearfichero(File fichero) {

	    File aux = new File("reservas.aux");
	    File backup = new File("reservas.backup");
            ArrayList<Integer> productos1 = new ArrayList<>();
            productos1.add(1);
            productos1.add(2);
            productos1.add(3);
            ArrayList<Integer> productos2 = new ArrayList<>();
            productos2.add(2);
            productos2.add(4);
            ArrayList<Integer> productos3 = new ArrayList<>();
            productos3.add(1);
            productos3.add(3);
            productos3.add(5);
            Pedido p1 = new Pedido(1,LocalDate.of(2026, 9, 1),LocalDate.of(2026, 9, 5),true,productos1,1);

            Pedido p2 = new Pedido(2,LocalDate.of(2026, 9, 3),LocalDate.of(2026, 9, 8),true,productos2,2);

            Pedido p3 = new Pedido(3,LocalDate.of(2026, 9, 7),LocalDate.of(2026, 9, 12),true,productos3,3);

            Pedido p4 = new Pedido(4,LocalDate.of(2026, 9, 10),LocalDate.of(2026, 9, 15),true,new ArrayList<>(java.util.Arrays.asList(4, 5)),4);

            Pedido p5 = new Pedido(5,LocalDate.of(2026, 9, 12),null,false,new ArrayList<>(java.util.Arrays.asList(1, 2, 5)),5);
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(aux))) {
	    	oos.writeObject(p1);
                oos.writeObject(p2);
                oos.writeObject(p3);
                oos.writeObject(p4);
                oos.writeObject(p5);
	        }
	    catch (IOException e) {
	        System.out.println("Error al guardar pedidos: " + e.getMessage());
	        return;
	    }

	    if (fichero.exists()) {
	        if (!fichero.renameTo(backup)) {
	            System.out.println("Error al crear el backup");
	            return;
	        }
	    }

	    if (!aux.renameTo(fichero)) {
	        System.out.println("Error al renombrar el fichero auxiliar");
	        if (backup.exists()) {
	            backup.renameTo(fichero);
	        }
	    } else {
	        if (backup.exists()) {
	            backup.delete();
	        }
	    }

	    } 
     
     public void pedidosPorCliente(int id, File fichero){
		int contador=0;
	    try (ObjectInputStream ois =
	         new ObjectInputStream(new FileInputStream(fichero))) {

	        while (true) {
	        	Pedido p = (Pedido) ois.readObject();
	        	if(p.getClienteId()==id){
                            System.out.println(p.toString());
                        }
	        }

	    } catch (EOFException e) {
                e.printStackTrace();
            } catch (Exception e) {
	        System.out.println("Error al leer pedidos");
	    }
	
     }
     
     public void listarPedidos( File fichero){
		int contador=0;
	    try (ObjectInputStream ois =
	         new ObjectInputStream(new FileInputStream(fichero))) {

	        while (true) {
	        	Pedido p = (Pedido) ois.readObject();
                            System.out.println(p.toString());
                        
	        }

	    } catch (EOFException e) {
                e.printStackTrace();
            } catch (Exception e) {
	        System.out.println("Error al leer pedidos");
	    }
	
     }
}
