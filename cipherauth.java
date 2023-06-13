/*r
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project;

//for db connectivity
import java.sql.*;

import javafx.application.Application;
import javafx.event.*;
import javafx.event.EventHandler;
//for grid pane
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author aksha
 */
public class Project extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //title of form
        primaryStage.setTitle("Login Portal");
        
        //creating grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        Text scenetitle = new Text("Enter Login Credentials");
        
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label l1 = new Label("User Name:");
        grid.add(l1, 0, 1);

        TextField tf = new TextField();
        grid.add(tf, 1, 1);

        Label l2 = new Label("Password:");
        grid.add(l2, 0, 2);

        Label message = new Label();
        
        PasswordField pf = new PasswordField();
        grid.add(pf, 1, 2);
        
        Button btn1 = new Button("Sign in");
        Button btn2 = new Button("Check");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn1);
        hbBtn.getChildren().add(btn2);
        grid.add(hbBtn, 1, 4);
       
                    
       EventHandler<ActionEvent> ev1 = new EventHandler<ActionEvent>()
       {
       public void handle(ActionEvent e) 
       {
        //ENCRYPTING CODE
       String passe = pf.getText();
       int shift = 2;
       int k = shift % 26;
       char ch;
       StringBuffer epass= new StringBuffer();
       
       for (int i=0;i<passe.length();i++)
       {
       // alphabet
       if(Character.isLetter(passe.charAt(i)))
       {
       if(Character.isLowerCase(passe.charAt(i)))
       {
       // lowercase
       ch = (char)(((int)passe.charAt(i) + k - 97) % 26 + 97);
       epass.append(ch);
       }
       else
       {
       // uppercase
       ch = (char)(((int)passe.charAt(i) + k - 65) % 26 + 65);
       epass.append(ch);
       }}
       else
       {
       // for number
       ch = (char)((int)passe.charAt(i) + k);
       epass.append(ch);
       }}
       
       int flag;
       //PART 1: ENCYRPTING USER PASS AND ENTERING INTO DB
       //inputting username from user
       String username = tf.getText();
       
       try
       {
       Class.forName("com.mysql.cj.jdbc.Driver");
       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/univdb","root","mysql");
       
       String password = epass.toString();
       //entering the username and encrypted password into database
       
       PreparedStatement st1 = conn.prepareStatement("insert into login values('"+username+"','"+password+"')");
       flag = st1.executeUpdate();
       
       if(flag==1)
       {
       message.setText("Password Encrypted!");
       System.out.println("Password Encrypted into Database!");
       }
       else
       {
       message.setText("Fail");
       }
       }
       catch(Exception ex)
       {
       System.out.println(ex);
       }
       }
       };
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
       EventHandler<ActionEvent> ev2 = new EventHandler<ActionEvent>()
       {
       public void handle(ActionEvent e) 
       {
        //ENCRYPTING CODE
       String passe = pf.getText();
       int shift = 2;
       int k = shift % 26;
       char ch;
       StringBuffer epass= new StringBuffer();
       
       for (int i=0;i<passe.length();i++)
       {
       // alphabet
       if(Character.isLetter(passe.charAt(i)))
       {
       if(Character.isLowerCase(passe.charAt(i)))
       {
       // lowercase
       ch = (char)(((int)passe.charAt(i) + k - 97) % 26 + 97);
       epass.append(ch);
       }
       else
       {
       // uppercase
       ch = (char)(((int)passe.charAt(i) + k - 65) % 26 + 65);
       epass.append(ch);
       }
       }
       else
       {
       // for number
       ch = (char)((int)passe.charAt(i) + k);
       epass.append(ch);
       }
       }
       
       //PART 2: VERIFYING USER CREDENTIALS AND EXTRACTING FROM DATABASE 
       try
       {
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/univdb","root","mysql");
       
       //inputting username from user
       String username = tf.getText();
       String password = epass.toString();  
       
       PreparedStatement st2 = con.prepareStatement("select pass from login where user='"+username+"' and pass='"+password+"';");
       ResultSet rs2 = st2.executeQuery();
       
       while(rs2.next())
       {
       String passd = rs2.getString("pass");
       //DECRYPTING CODE
       StringBuffer dpass= new StringBuffer();
       char d;
       shift = shift % 26;
       
       for (int i=0;i<passd.length();i++)
       {
       // alphabet
       if(Character.isLetter(passd.charAt(i)))
       {
       if(Character.isLowerCase(passd.charAt(i)))
       {
       // lowercase
       d = (char)(((int)passd.charAt(i) - k - 97) % 26 + 97);
       dpass.append(d);
       }
       else
       {
       // uppercase
       d = (char)(((int)passd.charAt(i) - k - 65) % 26 + 65);
       dpass.append(d);
       }
       }
       else
       {
       // for number
       d = (char)((int)passd.charAt(i) - k);
       dpass.append(d);
       }
       }
       
       String pass;
       pass = dpass.toString();
       
       message.setText("Password Decrypted!");
       System.out.println("Password: "+passd+" Decrypted From Database: "+pass);
       }
       }
       catch(Exception ex)
       {
       System.out.println(ex);
       }
       }
       };
       
    btn1.setOnAction(ev1);
    btn2.setOnAction(ev2);
    grid.add(message, 2, 2);
    primaryStage.setScene(scene);
    primaryStage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    launch(args);
    }
    
}
