package inventario;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.*;

public class Producto {
    private int Codigo;
    private String Nombre;
    private String Marca;
    private String FechaLlegada;
    private double CostoCompra;
    private int Cantidad;
    private String Proveedor;
    private double CostoVenta;
    private double Balance;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getFechaLlegada() {              
        return FechaLlegada;
    }

    public void setFechaLlegada(String FechaLlegada) {
        this.FechaLlegada = FechaLlegada;
    }

    public double getCostoCompra() {
        return CostoCompra;
    }

    public void setCostoCompra(double CostoCompra) {
        this.CostoCompra = CostoCompra;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public double getCostoVenta() {
        return CostoVenta;
    }

    public void setCostoVenta(double CostoVenta) {
        this.CostoVenta = CostoVenta;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    
    
    public void Insertar(JTextField Nombre, JTextField Marca, JTextField PCompra, JTextField Cantidad, 
            JTextField Proveedor, JTextField PVenta){        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date Hoy = new Date();
        String Fecha = sdf.format(Hoy);
        
        setNombre(Nombre.getText());
        setMarca(Marca.getText());
        
        setFechaLlegada(Fecha);
        
        setCostoCompra(Double.parseDouble(PCompra.getText()));
        setCantidad(Integer.parseInt(Cantidad.getText()));
        setProveedor(Proveedor.getText());
        setCostoVenta(Double.parseDouble(PVenta.getText()));
        
        double Balance1 = -getCantidad()*getCostoCompra();
        setBalance(Balance1);
        
        Conexion cnx  = new Conexion();
        String consulta = "insert into productos (nombre, marca, fecha, PCompra, Cantidad, Proveedor, PVenta, Balance) "
                + "values(?,?,?,?,?,?,?,?);";
        
        try{
            CallableStatement cs = cnx.conectar().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setString(2, getMarca());            
            cs.setString(3, getFechaLlegada());
            cs.setDouble(4, getCostoCompra());
            cs.setInt(5, getCantidad());
            cs.setString(6, getProveedor());
            cs.setDouble(7, getCostoVenta());
            cs.setDouble(8, getBalance());
            
            
            cs.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Fallo");
        }
    }
    
    public void Mostrar(JTable table){
        Conexion cnx  = new Conexion();
        
        DefaultTableModel modelo= new DefaultTableModel();
        
        TableRowSorter<TableModel> Ordenar = new TableRowSorter<TableModel>(modelo);
        table.setRowSorter(Ordenar);
        
        String select = "select * from productos\n"+
                        "order by Código desc";
        
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Marca");
        modelo.addColumn("Fecha Llegada");
        modelo.addColumn("PCompra");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Proveedor");
        modelo.addColumn("PVenta");
        modelo.addColumn("Balance");
        
        String[] datos = new String[9];
        
        Statement st;
        
        try{
            st = cnx.conectar().createStatement();
            
            ResultSet rs = st.executeQuery(select);
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                
                modelo.addRow(datos);
            }
            
            table.setModel(modelo);
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Fallo");
        }
    }
    
    public void ProductoModificado(JTable table, int codigo){
        Conexion cnx  = new Conexion();
        
        DefaultTableModel modelo= new DefaultTableModel();
        
        TableRowSorter<TableModel> Ordenar = new TableRowSorter<TableModel>(modelo);
        table.setRowSorter(Ordenar);
        
        String ultimo = "select * from productos\n" +
                        "WHERE Código = "+codigo+" ;";
        
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Marca");
        modelo.addColumn("Fecha Llegada");
        modelo.addColumn("PCompra");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Proveedor");
        modelo.addColumn("PVenta");
        modelo.addColumn("Balance");
        
        Statement st;
        
        String[] datos = new String[9];
        
        try{
            st = cnx.conectar().createStatement();
            
            ResultSet rs = st.executeQuery(ultimo);
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                
                modelo.addRow(datos);
            }
            
            table.setModel(modelo);
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Fallo");
        }
    }
        
    public void EliminarProducto(int codigo){
        Conexion cnx  = new Conexion();

        setCodigo(codigo);

        Conexion objetoConexion = new Conexion();
        
        String consulta = "DELETE FROM productos WHERE productos.Código=?;";
        
        try {
            CallableStatement cs = cnx.conectar().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el Producto");
        }catch (Exception e){
        
            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: "+ e.toString());
        }
    }
    
    public void ModificarProducto(JTextField Codigo, JTextField Nombre, JTextField Marca, JTextField PCompra, JTextField Cantidad, 
            JTextField Proveedor, JTextField PVenta) {

        setCodigo(Integer.parseInt(Codigo.getText()));
        setNombre(Nombre.getText());
        setMarca(Marca.getText());
        setCostoCompra(Double.parseDouble(PCompra.getText()));
        setCantidad(Integer.parseInt(Cantidad.getText()));
        setProveedor(Proveedor.getText());
        setCostoVenta(Double.parseDouble(PVenta.getText()));

        Conexion cnx = new Conexion();

        String consulta = "UPDATE productos SET productos.Nombre =?, productos.Marca =?,productos.PCompra=?,"
                + "productos.Cantidad=?,productos.Proveedor=?,productos.PVenta=? WHERE productos.Código=?;";

        try {
            CallableStatement cs = cnx.conectar().prepareCall(consulta);

            cs.setString(1, getNombre());
            cs.setString(2, getMarca());
            cs.setDouble(3, getCostoCompra());
            cs.setInt(4, getCantidad());
            cs.setString(5, getProveedor());
            cs.setDouble(6, getCostoVenta());
            cs.setInt(7, getCodigo());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se modifico"+e.toString());
        }
    }

    public void SeleccionarProducto(JTable table, JTextField Codigo, JTextField Nombre, JTextField Marca, JTextField PCompra, 
            JTextField Cantidad, JTextField Proveedor, JTextField PVenta) {

        try {
            int fila = table.getSelectedRow();

            if (fila >= 0) {

                Codigo.setText(table.getValueAt(fila, 0).toString());
                Nombre.setText(table.getValueAt(fila, 1).toString());
                Marca.setText(table.getValueAt(fila, 2).toString());
                PCompra.setText(table.getValueAt(fila, 4).toString());
                Cantidad.setText(table.getValueAt(fila, 5).toString());
                Proveedor.setText(table.getValueAt(fila, 6).toString());
                PVenta.setText(table.getValueAt(fila, 7).toString());

            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion" + e.toString());
        }
    }
    
    public void Vender(JTable table, int Codigo, int Cantidad){
        ProductoModificado(table, Codigo);
        double BalanceOg = Double.parseDouble(table.getValueAt(0, 8).toString());
        double PVenta = Double.parseDouble(table.getValueAt(0, 7).toString());
        double Balance1 = BalanceOg + Cantidad*PVenta;
        setBalance(Balance1);        
        
        int NCantidad = Integer.parseInt(table.getValueAt(0, 5).toString())-Cantidad;
        
        String consulta = "UPDATE productos SET productos.Cantidad=?, productos.Balance=? WHERE productos.Código=?;";
        
        Conexion cnx = new Conexion();
        
        try {
            CallableStatement cs = cnx.conectar().prepareCall(consulta);

            cs.setInt(1, NCantidad);
            cs.setDouble(2, getBalance());
            cs.setInt(3, Codigo);
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se modifico."+e.toString());
        }
        
        ProductoModificado(table, Codigo);
    }
}
