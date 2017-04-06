package application.Controllers;


import java.sql.SQLException;

import application.model.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateScreenController {
	private DatabaseConnection conn = new DatabaseConnection("student","test");
	private Customer selectedCustomer;

	
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private Button okButton;
	@FXML
	private Button updateButton;
	@FXML
	private Label warningLabel;
	
	public void setSelectedCustomer(Customer customer){
		this.selectedCustomer=customer;
	}
	
	@FXML
	private void updateButtonAction(ActionEvent event){
		 try {
			 
			 if(firstNameTextField.getLength() <1 || lastNameTextField.getLength() <1 || emailTextField.getLength()<1){
					warningLabel.setText("Every field must be filled !");
			 } else{
		 
			conn.updateCustomer(firstNameTextField.getText(), lastNameTextField.getText(),
					emailTextField.getText(), selectedCustomer.getId());
			
			this.hideUpdateScreen(event);
			}
			
			
		} catch (SQLException e) {
			warningLabel.setText("Lost connection to the database! ");
		}	
			
		
		
	}
	
	@FXML
	private void cancelButtonAction(ActionEvent event){
		this.hideUpdateScreen(event);
		
	}
	
	private void hideUpdateScreen (ActionEvent event){
		Stage updateStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
		updateStage.hide();
	}
	
	public void fillTextFields(){
		firstNameTextField.setText(selectedCustomer.getFirstName());
		lastNameTextField.setText(selectedCustomer.getLastName());
		emailTextField.setText(selectedCustomer.getEmail());
	}
	
}
