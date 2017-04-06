package application.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingScreenController {

	private DatabaseConnection conn = new DatabaseConnection("student","test");
	private PreparedStatement stmt;
	private Connection myConn;
	
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Label warningLabel;
	
	
	
	@FXML
	private void okbuttonAction (ActionEvent event){
		
		
		if(firstNameTextField.getLength() <1 || lastNameTextField.getLength() <1 || emailTextField.getLength()<1){
			warningLabel.setText("Every field must be filled !");
		}else{
			
		try {
			 conn.addCustomer(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText());
		
		
		
		this.hideAddingScreen(event);
		
		
		
		} catch (SQLException e) {
			warningLabel.setText("Lost connection to the database! ");
		} finally{
			// try { if (result != null) result.close(); } catch (Exception e) {};
			 try { if (stmt != null) stmt.close(); } catch (Exception e) {};
			 try { if (myConn != null) myConn.close(); } catch (Exception e) {};
		}
		
		}
	}
	
	@FXML
	private void cancelbuttonAction (ActionEvent event) throws IOException{
		this.hideAddingScreen(event);
		
	}
	
	private void hideAddingScreen (ActionEvent event){
		Stage addStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
		addStage.hide();
	}
	
	
	
}