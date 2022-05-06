/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Diego
 */
public class Conexion {
    private Connection c;
    private Statement st;
    private ResultSet rs;
    
    public Conexion(){
        try{
        String url="jdbc:mysql://localhost/trabajoJava";
        String usuario="root";
        String clave="52047365";
        Class.forName("com.mysql.jdbc.Driver");
        c=DriverManager.getConnection(url,usuario,clave);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        
    }
    
    public boolean validarUsuario(String usuario,String clave){
        boolean existe=false;
        try{
        String sql="select * from usuario where usu_usu='"+usuario+"' and cla_usu='"+clave+"';";
        st=c.createStatement();
        rs=st.executeQuery(sql);
        if(rs.next()){
            existe=true;
        }else{
            JOptionPane.showMessageDialog(null, "Usuario inválido, por favor vuelva a intentarlo.", "Usuario inválido", 1);
        }
        st.close();
        rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        
    return existe;
    }
    
    public void añadirTrabajador(Trabajador tra){
        String sql=("insert into trabajador values('"+tra.getRut()+"','"+tra.getNombre()+"','"+tra.getDireccion()+"','"+tra.getCiudad()+"','"+tra.getSexo()+"','"+tra.getCargo()+"','"+tra.getSueldoBase()+"','"+tra.getSueldoLiquido()+"');");
        try {
            st=c.createStatement();
            st.execute(sql);
            st.close();
            JOptionPane.showMessageDialog(null,"Trabajador guardado existosamente.","Guardar",1);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        
    }
    
    public DefaultTableModel construirTabla(){
        DefaultTableModel borrador=new DefaultTableModel();
        try{
            String encabezados[]={"Rut","Nombre","Dirección","Ciudad","Sexo","Cargo","Sueldo Base","Sueldo líquido"};
            borrador.setColumnIdentifiers(encabezados);
            String datos[]=new String[8];
            String sql=("Select rut_tra,nom_tra,dir_tra,nom_ciu,sex_tra,nom_car,sb_tra,sl_tra from trabajador,ciudad,cargo where trabajador.ciu_tra=id_ciu and trabajador.car_tra=id_car;");
            st=c.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString("rut_tra");
                datos[1]=rs.getString("nom_tra");
                datos[2]=rs.getString("dir_tra");
                datos[3]=rs.getString("nom_ciu");
                datos[4]=rs.getString("sex_tra");
                datos[5]=rs.getString("nom_car");
                datos[6]=rs.getString("sb_tra");
                datos[7]=rs.getString("sl_tra");
                borrador.addRow(datos);
                //si coloco el addRow fuera del while entonces estoy agregando una sola fila, en vez de todas las filas que
                //se puede agregar con el while
            }
            st.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return borrador;
    }
    
    public DefaultComboBoxModel construirComboBox(String tabla,String campo){
        DefaultComboBoxModel borrador=new DefaultComboBoxModel();
        try{
            String sql=("select * from "+tabla+";");
            st=c.createStatement();
            rs=st.executeQuery(sql);
            borrador.addElement("Seleccione");
            while(rs.next()){
                borrador.addElement(rs.getString(campo));
            }
            st.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return borrador;
    }
    
    public void modificarTrabajador(Trabajador tra,String rut){
        try{
            String sql=("update trabajador set nom_tra='"+tra.getNombre()+"',dir_tra='"+tra.getDireccion()+"',ciu_tra='"+tra.getCiudad()+"',sex_tra='"+tra.getSexo()+"' ,car_tra='"+tra.getCargo()+"' ,sb_tra='"+tra.getSueldoBase()+"'  where rut_tra='"+rut+"'; ");
        st=c.createStatement();
        st.execute(sql);
        st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public Trabajador mostrarTrabajador(String rut){
        Trabajador tra=new Trabajador();
        try{
            String sql=("select * from trabajador where rut_tra ='"+rut+"';");
            st=c.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                tra.setNombre(rs.getString("nom_tra"));
                tra.setDireccion(rs.getString("dir_tra"));
                tra.setCiudad(rs.getInt("ciu_tra"));
                tra.setSexo(rs.getString("sex_tra"));
                tra.setCargo(rs.getInt("car_tra"));
                tra.setSueldoBase(rs.getString("sb_tra"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return tra;
    }
    
    public void eliminarTrabajador(String rut){
        String sql=("delete from trabajador where rut_tra='"+rut+"'; ");
        try{
        st=c.createStatement();
        st.execute(sql);
        st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public void agregarUsuario(String usuario,String clave){
        try{
            String sql=("insert into usuario values(' "+usuario+"',' "+clave+" ');");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public void modificarUsuario(String usuario,String clave){
        String sql=("update usuario set cla_usu='"+clave+"' where usu_usu='"+usuario+"'; ");
        try{
        st=c.createStatement();
        st.execute(sql);
        st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public void eliminarUsuario(String usuario){
        String sql=("delete from usuario where usu_usu='"+usuario+"'; ");
        try{
        st=c.createStatement();
        st.execute(sql);
        st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public DefaultTableModel tablaUsuario(String sql){
        DefaultTableModel borrador=new DefaultTableModel();
        try{
            String encabezados[]={"Nombre de usuario","Contraseña"};
            borrador.setColumnIdentifiers(encabezados);
            String datos[]=new String[2];
            st=c.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString("usu_usu");
                datos[1]=rs.getString("cla_usu");
                borrador.addRow(datos);
            }
            
            st.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return borrador;
    }
    public DefaultTableModel tablaCiudades(String sql){
        DefaultTableModel borrador=new DefaultTableModel();
        try{
            String encabezados[]={"ID Ciudad","Nombre de la ciudad"};
            borrador.setColumnIdentifiers(encabezados);
            String datos[]=new String[2];
            st=c.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString("id_ciu");
                datos[1]=rs.getString("nom_ciu");
                borrador.addRow(datos);
            }
            
            st.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return borrador;
    }
    public void eliminarCiudad(int ciudad){
        try{
            String sql=("delete from ciudad where id_ciu="+ciudad+"; ");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    
    public void modificarCiudad(String ciudad,String nuevaCiudad){
        try{
            String sql=("update ciudad set nom_ciu='"+nuevaCiudad+"' where nom_ciu='"+ciudad+"'; ");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    public void agregarCiudad(String ciudad){
        try{
            String sql=("insert into ciudad (nom_ciu) values(' "+ciudad+" ');");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    public DefaultTableModel tablaCargo(){
        DefaultTableModel borrador=new DefaultTableModel();
        try{
            String sql=("Select rut_tra,nom_car from trabajador,cargo;");
            String encabezados[]={"Rut trabajador","Nombre del cargo"};
            borrador.setColumnIdentifiers(encabezados);
            String datos[]=new String[2];
            st=c.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString("rut_tra");
                datos[1]=rs.getString("nom_car");
                borrador.addRow(datos);
            }
            
            st.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
        return borrador;
    }
    
    public void eliminarCargo(String cargo){
        try{
            String sql=("delete from cargo where nom_car='"+cargo+"'; ");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    public void agregarCargo(String cargo){
        String sql=("insert into cargo (nom_car) values('" +cargo+" ')");
        try{
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
    public void modificarCargo(String cargo,String nuevoCargo){
        try{
            String sql=("update cargo set nom_car='"+nuevoCargo+"' where nom_car='"+cargo+"';");
            st=c.createStatement();
            st.execute(sql);
            st.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error SQL en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error Genérico en la conexión: " + e.getMessage(),"Error en la Conexión",0);
            System.exit(0);
        }
    }
}
