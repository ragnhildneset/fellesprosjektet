package com.gruppe16.admin;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.gruppe16.database.DBConnect;
import com.gruppe16.entities.Employee;
import com.gruppe16.entities.Room;

/**
 * The Class AdminPanel. A panel used for administrative work.
 * 
 * @author Gruppe 16
 */
public class AdminPanel extends Application implements Initializable {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/** The employee add button. */
	private @FXML Button e_add;
	
	/** The employee ID. */
	private @FXML TextField e_id;
	
	/** The employee first name. */
	private @FXML TextField e_fname;
	
	/** The employee last name. */
	private @FXML TextField e_lname;
	
	/** The employee e-mail. */
	private @FXML TextField e_mail;
	
	/** The employee username. */
	private @FXML TextField e_user;
	
	/** The employee password. */
	private @FXML PasswordField e_pass;
	
	/** The room add button. */
	private @FXML Button r_add;
	
	/** The room ID. */
	private @FXML TextField r_id;
	
	/** The room name. */
	private @FXML TextField r_name;
	
	/** The room's building ID. */
	private @FXML TextField r_bid;
	
	/** The room description. */
	private @FXML TextArea r_desc;
	
	/** The room capacity. */
	private @FXML TextField r_cap;

	/** The building add button. */
	private @FXML Button b_add;
	
	/** The building ID. */
	private @FXML TextField b_id;
	
	/** The building name. */
	private @FXML TextField b_name;
	
	/** The building description. */
	private @FXML TextArea b_desc;
	
	/** The building latitude (because why not). */
	private @FXML TextField b_lat;
	
	/** The building longitude (because why not). */
	private @FXML TextField b_long;
	
    /** The table of rooms in the database. */
    @FXML private TableView<Room> roomlistTable;
    
    /** The room ID column. */
    @FXML private TableColumn<Room, String> roomIDCol;
    
    /** The room capacity column. */
    @FXML private TableColumn<Room, String> capacityCol;
    
    /** The room name column. */
    @FXML private TableColumn<Room, String> roomnameCol;
    
    /** The room description column. */
    @FXML private TableColumn<Room, String> roomdescrCol;
    
    /** The room's building ID column. */
    @FXML private TableColumn<Room, String> roombuildingidCol;
    
    /** The room delete button column. */
    @FXML private TableColumn<Room, Boolean> r_delete;
  
    /** The table of employees in the database */
    @FXML private TableView<Employee> employeelistTable;
    
    /** The employee ID column. */
    @FXML private TableColumn<Employee, String> employeeIDCol;
    
    /** The employee's first name column. */
    @FXML private TableColumn<Employee, String> firstNameCol;
    
    /** The employee's last name column. */
    @FXML private TableColumn<Employee, String> lastNameCol;
    
    /** The employee's username column. */
    @FXML private TableColumn<Employee, String> usernameCol;
    
    /** The employee delete button column. */
    @FXML private TableColumn<Employee, Boolean> e_delete;
    
//    @FXML private TableView<Employee> buildinglistTable;
//    @FXML private TableColumn<Employee, String> buildingIDCol;
//    @FXML private TableColumn<Employee, String> buildingNameCol;
//    @FXML private TableColumn<Employee, String> descriptionNameCol;
//    @FXML private TableColumn<Employee, String> usernameCol;
//    @FXML private TableColumn<Employee, Boolean> e_delete;
    

    /** The room data, from the database. */
private static ObservableList<Room> roomdata = FXCollections.observableArrayList(DBConnect.getRooms().values());
    
    /** The employee data, from the database. */
    private static ObservableList<Employee> employeedata = FXCollections.observableArrayList(DBConnect.getEmployeeList());
    	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		class DeleteRoomCell extends TableCell<Room, Boolean> {
	    	private final Button b = new Button("Delete");
	    	DeleteRoomCell(){
	    		b.setOnMousePressed(new EventHandler<MouseEvent>(){
	    			@Override
	    			public void handle(MouseEvent mouseEvent){
	    				Room r = (Room) DeleteRoomCell.this.getTableView().getItems().get(DeleteRoomCell.this.getIndex());
	    				DBConnect.deleteRoom(r.getID());
	    				roomdata.remove(r);
	    				System.out.println("HELLO");
	    			}
	    		});
	    	}
	    	@Override
	        protected void updateItem(Boolean t, boolean empty) {
	            super.updateItem(t, empty);
	            if(!empty){
	                setGraphic(b);
	            } else {
	            	this.setGraphic(null);
	            }
	        }
	    }
		
		class DeleteEmployeeCell extends TableCell<Employee, Boolean> {
	    	private final Button b = new Button("Delete");
	    	DeleteEmployeeCell(){
	    		b.setOnMousePressed(new EventHandler<MouseEvent>(){
	    			@Override
	    			public void handle(MouseEvent mouseEvent){
	    				Employee r = (Employee) DeleteEmployeeCell.this.getTableView().getItems().get(DeleteEmployeeCell.this.getIndex());
	    				DBConnect.deleteEmployee(r.getID());
	    				roomdata.remove(r);
	    				System.out.println("HELLO");
	    			}
	    		});
	    	}
	    	@Override
	        protected void updateItem(Boolean t, boolean empty) {
	            super.updateItem(t, empty);
	            if(!empty){
	                setGraphic(b);
	            } else {
	            	this.setGraphic(null);
	            }
	        }
	    }
		
		roomIDCol.setCellValueFactory(new PropertyValueFactory<Room, String>("ID"));
		capacityCol.setCellValueFactory(new PropertyValueFactory<Room, String>("capacity"));
		roomnameCol.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
		roomdescrCol.setCellValueFactory(new PropertyValueFactory<Room, String>("description"));
		roombuildingidCol.setCellValueFactory(new PropertyValueFactory<Room, String>("buildingID"));
		
		r_delete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Room, Boolean>, 
                ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Room, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
		r_delete.setCellFactory(
                new Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>>() {
 
            @Override
            public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> p) {
                return new DeleteRoomCell();
            }
         
        });
	
		
		employeeIDCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("ID"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
		
		e_delete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Employee, Boolean>, 
                ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Employee, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
		e_delete.setCellFactory(
                new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
 
            @Override
            public TableCell<Employee, Boolean> call(TableColumn<Employee, Boolean> p) {
                return new DeleteEmployeeCell();
            }
         
        });

		employeelistTable.setItems(employeedata);
		
		
		e_add.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					int _e_id = Integer.parseInt(e_id.getText());
					String _e_fname = e_fname.getText();
					String _e_lname = e_lname.getText();
					String _e_mail = e_mail.getText();
					String _e_user = e_user.getText();
					String _e_pass = e_pass.getText();
					AdminPanel.addUser(_e_id, _e_fname, _e_lname, _e_mail, _e_user, _e_pass);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		r_add.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					int _r_id = Integer.parseInt(r_id.getText());
					String _r_name = r_name.getText();
					String _r_desc = r_desc.getText();
					int _r_bin = Integer.parseInt(r_bid.getText());
					int _r_cap = Integer.parseInt(r_cap.getText());
					AdminPanel.addRoom(_r_id, _r_bin, _r_cap, _r_name, _r_desc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		b_add.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					int _b_id = Integer.parseInt(b_id.getText());
					String _b_name = b_name.getText();
					String _b_desc = b_desc.getText();
					float _b_lat = Float.parseFloat(b_lat.getText());
					float _b_long = Float.parseFloat(b_long.getText());
					AdminPanel.addBuilding(_b_id, _b_lat, _b_long, _b_name, _b_desc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		
		try{
			Scene scene = new Scene( (Parent) FXMLLoader.load(getClass().getResource("/com/gruppe16/admin/mainPane.fxml")));
			arg0.setScene(scene);
			arg0.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds an employee to the database.
	 *
	 * @param _e_id the employee ID
	 * @param _e_fname the employee's first name
	 * @param _e_lname the employee's last name
	 * @param _e_mail the employee's e-mail
	 * @param _e_user the employee's username
	 * @param _e_pass the employee's password
	 */
	private static void addUser(int _e_id, String _e_fname, String _e_lname, String _e_mail, String _e_user, String _e_pass){
		try{
			String e_query = "insert into Employee(employeeid, givenName, surname, email) VALUES ( ?, ?, ?, ? )";
			PreparedStatement e = DBConnect.getConnection().prepareStatement(e_query);
			e.setInt(1, _e_id);
			e.setString(2, _e_fname);
			e.setString(3, _e_lname);
			e.setString(4, _e_mail);
			e.execute();
			String u_query = "insert into UserAndID(employeeid, username, password) VALUES ( ?, ?, ? )";
			e = DBConnect.getConnection().prepareStatement(u_query);
			e.setInt(1, _e_id);
			e.setString(2, _e_user);
			e.setString(3, _e_pass);
			e.execute();
			System.out.println("Added new user and employee.");
			employeedata.add(new Employee(_e_id, _e_fname, _e_lname, _e_mail, _e_user));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a room to the database.
	 *
	 * @param _r_id the room ID
	 * @param _r_bin the room's building ID
	 * @param _r_cap the room capacity
	 * @param _r_name the room name
	 * @param _r_desc the room description
	 */
	private static void addRoom(int _r_id, int _r_bin, int _r_cap, String _r_name, String _r_desc){
		String r_query = "insert into Room(roomNumber, buildingID, capacity, roomName, description) VALUES ( ?, ?, ?, ?, ? )";
		PreparedStatement e;
		try {
			e = DBConnect.getConnection().prepareStatement(r_query);
			e.setInt(1, _r_id);
			e.setInt(2, _r_bin);
			e.setInt(3, _r_cap);
			e.setString(4, _r_name);
			e.setString(5, _r_desc);
			e.execute();
			System.out.println("Added new room.");
			roomdata.add(new Room(_r_id, _r_cap, _r_name, _r_desc, _r_bin, "<REPLACE THIS>"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a building to the database.
	 *
	 * @param _b_id the building ID
	 * @param _b_lat the building latitude
	 * @param _b_long the building longitude
	 * @param _b_name the building name
	 * @param _b_desc the building description
	 */
	private static void addBuilding(int _b_id, float _b_lat, float _b_long, String _b_name, String _b_desc){
		try{
			String b_query = "insert into Building(buildingID, latitude, longitude, name, description) VALUES ( ?, ?, ?, ?, ? )";
			PreparedStatement e = DBConnect.getConnection().prepareStatement(b_query);
			e.setInt(1, _b_id);
			e.setFloat(2, _b_lat);
			e.setFloat(3, _b_long);
			e.setString(4, _b_name);
			e.setString(5, _b_desc);
			e.execute();
			System.out.println("Added new building.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
