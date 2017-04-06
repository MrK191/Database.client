package application.Controllers;
import application.model.Customer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DatabaseUiController {

	private DatabaseConnection conn = new DatabaseConnection("student","test");
	
	private ObservableList<Customer> customers=FXCollections.observableArrayList();
	
	
	@FXML
	private Button searchButton;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button updateButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private TextField searchField;
	
	@FXML
	private TableView<Customer> databaseTable;
	@FXML
	private TableColumn columnId;
	@FXML
	private TableColumn columnFirstName;
	@FXML
	private TableColumn columnLastName;
	@FXML
	private TableColumn columnEmail;
	@FXML 
	private Label warningLabel;
	

	
	@FXML
	private void searchInformation(ActionEvent event) {
		this.tableRefresh();
	}
		
	
	@FXML
	private void addButtonAction(ActionEvent event){
		
		
		try {
			Parent	parentMainAddingScreen = FXMLLoader.load(getClass().getResource("fxml/AddingScreen.fxml"));
			Scene mainAddingScreenScene = new Scene(parentMainAddingScreen);
			Stage addingStage = new Stage();
			addingStage.setTitle("Add customer");
			addingStage.setScene(mainAddingScreenScene);
			addingStage.showAndWait();
			this.tableRefresh();
			
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		}
	@FXML
	private void updateButtonAction(ActionEvent event){
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UpdateScreen.fxml"));
			
			Stage updateStage = new Stage();
			
			updateStage.setScene(
				    new Scene((Pane) loader.load()));
			
			UpdateScreenController controller = loader.getController();
			
			Customer selectedCustomer = databaseTable.getSelectionModel().getSelectedItem();
			controller.setSelectedCustomer(selectedCustomer);
			
			if(selectedCustomer==null){
				warningLabel.setText("You must select the Customer!");
			}else{

				controller.fillTextFields();
				
				updateStage.showAndWait();
				
				this.tableRefresh();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		}
	
	@FXML
	private void deleteEvent(ActionEvent event){
		try {
			Customer selectedItem = databaseTable.getSelectionModel().getSelectedItem();
					
					if(selectedItem !=null){
						
						if(selectedItem.getFirstName() != null) {
						conn.deleteCustomer(selectedItem.getId());
						this.tableRefresh();
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
			
				}
	        }
	
	
	public void tableRefresh() {
			customers.clear();
			
		ResultSet result = null;
		try {
			
			result = conn.searchForAllCustomers();
		
		if(!searchField.getText().isEmpty() ){
			
			while(result.next()){
				if( result.getString(2).toLowerCase().contains((searchField.getText().toLowerCase())) || 
						result.getString(3).toLowerCase().contains((searchField.getText().toLowerCase() ))){
					
					customers.add(new Customer(result.getInt(1), result.getString(2),
							result.getString(3), result.getString(4)));
				}
				
			}
		}else{
			
		while(result.next()){
			customers.add(new Customer(result.getInt(1), result.getString(2),
					result.getString(3), result.getString(4)));
			}
		
		}
		
		if(columnId.getCellValueFactory() ==null){
			columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			
			columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			
			databaseTable.setItems(customers);
		}
		
		
		
		} catch (SQLException e) {
			warningLabel.setText("Lost connection to the database");

		} finally{
			 try { if (result != null) result.close(); } catch (Exception e) {};
		}
	
	}

	
		
}