/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nemsicaklik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import NemveSicaklik.model.sensor;
import NemveSicaklik.model.veri;
import java.awt.Toolkit;
import javax.swing.JOptionPane;


/**
 *
 * @author Burcu
 */
public class dbConnection {
    public Connection connection;
    public void baglan() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/veritabani";
        String username = "root";
        String password = "010203";
        //System.out.println("Connecting database...");
        this.connection = DriverManager.getConnection(url, username, password); 
    }
    public boolean girisKontrol(String kadi,String sifre) throws SQLException, ClassNotFoundException{
        baglan();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM veritabani.admin");
        
        while (rs.next()) {
        String k = rs.getString("kadi");
        String p = rs.getString("sifre");
          
        if(k.equals(kadi) && p.equals(sifre)){
            return true;
        }
        }
        return false;
        
        
    }
    public void vt_gelen_kaydet(String nem,String sicaklik) throws SQLException{
        baglan();
      
          PreparedStatement ps = connection.prepareStatement("insert into gelen_sensor(nem, sicaklik ) values(?,?)");
            ps.setString(1, nem);
            ps.setString(2, sicaklik);
            ps.executeUpdate();
        
        
    }
    public List<sensor> sensor_listele() throws SQLException{
        baglan();
        List<sensor> gelen = new ArrayList<>();
     
        sensor veriler ;
        Statement stmt;
        try {
            stmt = connection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM gelen_sensor");
           
            while (rs.next()) {
                System.out.println(rs.getString("nem"));
                System.out.println(rs.getString("sicaklik"));
                veriler = new sensor();
                veriler.setSicaklik(rs.getString("nem"));
                veriler.setNem(rs.getString("sicaklik"));
                
                    gelen.add(veriler);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gelen;
    }
        
    
    public void sensor_alarm() throws SQLException{
        baglan();
        List<sensor> gelen = new ArrayList<>();
        String sicaklik_alarm,nem_alarm;
     
        sensor veriler ;
        Statement stmt;
        try {
            stmt = connection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM gelen_sensor ORDER BY idgelen_sensor DESC LIMIT 1");
           
            while (rs.next()) {
                sicaklik_alarm=rs.getString("sicaklik");
                nem_alarm=rs.getString("nem");
                if(Integer.parseInt(sicaklik_alarm)>28){
                    //Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,"Sicaklik değeri çok yüksek.!","Alarm",JOptionPane.WARNING_MESSAGE);
                }
                if(Integer.parseInt(nem_alarm)>50){
                   // Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,"Nem değeri çok yüksek.!","Alarm",JOptionPane.WARNING_MESSAGE);
                }
            }          
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
    


