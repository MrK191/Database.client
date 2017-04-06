package application.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	private DatabaseConnection conn = new DatabaseConnection("student","test");
	private PreparedStatement mySt;
	private Statement st;
	private boolean noConnection;
	
	@FXML
	private Button button;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Label warningLabel;
	
	
	
	@FXML
	private void buttonAction (ActionEvent event) throws IOException, SQLException{
		
		Parent mainPageParent = FXMLLoader.load(getClass().getResource("fxml/DatabaseUi.fxml"));
		Scene mainPageScene = new Scene(mainPageParent);
		
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		if(this.isValidLogin()){
			loginStage.hide();
			loginStage.setScene(mainPageScene);
			loginStage.show();
			
		}else{
			
			username.clear();
			password.clear();
			if(noConnection){
				warningLabel.setText("No connection to the Database");
				
			}else{
				warningLabel.setText("Invalid username or password !");
			}
			
			
			
		}
		
		
	}
	
	private boolean isValidLogin() throws IOException, SQLException {
		
		
		try {
			ResultSet result =conn.verifyLogin(username.getText(), password.getText());
			
			if(result.next()){
				
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e ) {
			this.noConnection=true;
			
		}
		
		return false;
		
		
	}

}
