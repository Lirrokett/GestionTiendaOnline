/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import modelo.Categoria;
import modelo.Producto;
/**
 *
 * @author songo
 */
public class DaoBaseDeDatos {
    // Los siguientes atributos se utilizan para recoger los valores del fich de
		// configuracion
		private ResourceBundle configFile;
		private String urlBD;
		private String userBD;
		private String passwordBD;

		// Sentencias SQL		
		final String RECOGERPRODUCTOS = "SELECT * FROM producto";
                final String sql = "UPDATE PRODUCTO SET stock = ? WHERE ID = ?";
		// Para la conexi n utilizamos un fichero de configuaraci n, config que
		// guardamos en el paquete control:
		public DaoBaseDeDatos() {
			this.configFile = ResourceBundle.getBundle("configClase");
			this.urlBD = this.configFile.getString("Conn");
			this.userBD = this.configFile.getString("DBUser");
			this.passwordBD = this.configFile.getString("DBPass");
		}

                
		public ArrayList<Producto> recogerProductos() throws SQLException{
                    ArrayList<Producto> productos=new ArrayList<>();
		    try (Connection con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
		    		PreparedStatement stmt = con.prepareStatement(RECOGERPRODUCTOS)){
	        	try (ResultSet rs = stmt.executeQuery())
	        	{	        
			        while (rs.next()) {
			            Producto p=new Producto();
                                    p.setId(rs.getInt("ID"));
                                    p.setNombre(rs.getString("NOMBRE"));
                                    p.setPrecio(rs.getDouble("PRECIO"));
			            p.setCategoria(Categoria.valueOf(rs.getString("CATEGORIA")));
                                    p.setStock(rs.getInt("STOCK"));
                                    p.setRuta(rs.getString("RUTA"));
                                    productos.add(p);
			        }
	        	}
	
		    } 

		    return productos;
		}
                    
                public boolean editarStock(int idProducto, int stocknuevo) throws SQLException {
                    try (Connection con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
                        PreparedStatement stmt = con.prepareStatement(sql)) {

                            stmt.setInt(1, stocknuevo);
                            stmt.setInt(2, idProducto);

                            int filasModificadas = stmt.executeUpdate();

                            return filasModificadas>0;
                        }
                    }
}