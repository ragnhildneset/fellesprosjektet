package com.gruppe16.main;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.gruppe16.database.DBConnect;
import com.gruppe16.util.Digest;
import com.mysql.jdbc.ResultSet;

public class Login extends Application implements Initializable {
	
	@FXML Button loginBtn;
	@FXML TextField user;
	@FXML TextField pass;
	
	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					String q = "SELECT Username, Password FROM Employee WHERE Employee.Username=\'" + user.getText()+"\';";
					PreparedStatement s = DBConnect.getConnection().prepareStatement(q);
					ResultSet rs = (ResultSet) s.executeQuery();
					byte[] passString = null;
					while(rs.next()){
						passString = rs.getBytes("Password");
					}
					byte[] inputPass = Digest.getHash(pass.getText());
					boolean t = true;
					for(int i=0;i<inputPass.length;i++){
						if(inputPass[i]!=passString[i]){
							System.out.println(inputPass[i]);
							System.out.println(passString[i]);
							System.out.println();
							t = false;
						}
					}
					if(t){
						System.out.println("Correct password and username.");
					} else {
						System.out.println("Wrong password or username.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Scene scene = new Scene((AnchorPane) FXMLLoader.load(Main.class.getResource("/com/gruppe16/main/login.fxml")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}