
package controlador;

import daos.DaoBaseDeDatos;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Producto;


public class ControladorProductos {
    DaoBaseDeDatos base=new DaoBaseDeDatos();
    
    public ArrayList<Producto> listarProductos() throws SQLException{
        return base.recogerProductos();
    }
    
    public boolean editarStock(int cod,int stock) throws SQLException{
       return base.editarStock(cod,stock);
        
    }
}
