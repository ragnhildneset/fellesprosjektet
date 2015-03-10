package com.gruppe16.main;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import com.gruppe16.admin.AdminPanel;
import com.gruppe16.database.DBConnect;
import com.gruppe16.entities.Appointment;
import com.gruppe16.entities.Employee;

public class AddAppointment implements Initializable {
	@FXML
	private Button sendBtn;
	
	@FXML
	private Button cancelBtn;
	
	@FXML
	private Button findEmployeeBtn;
	
	@FXML
	private Button searchForRoomBtn;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextArea descriptionTextArea;
	
	@FXML
	private TextField attendeesTextField;

	@FXML
	private TextField fromTextField;
	
	@FXML
	private TextField toTextField;
	
	@FXML
	private TextField roomTextField;

	@FXML
	private TextField titleTextField;

	private static Stage stage;
	private static LocalDate startDate = null;
	private static int startTime = 0;
	public static Collection<Employee> cachedEmployees;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cachedEmployees = DBConnect.getEmployees().values();
		if(startDate != null) {
			datePicker.setValue(startDate);
		}
		else {
			datePicker.setValue(LocalDate.now());
		}
		roomTextField.setEditable(true);
		sendBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				boolean valid = true;
				if(titleTextField.getText().isEmpty()) {
					titleTextField.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				LocalDate date = datePicker.getValue();
				if(date == null || date.isBefore(LocalDate.now())) {
					datePicker.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				if(descriptionTextArea.getText().isEmpty()) {
					descriptionTextArea.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				fromTextField.setEffect(null);
				toTextField.setEffect(null);
				
				int fromHour = 0, toHour = 0, fromMin = 0, toMin = 0;
				if(!fromTextField.getText().isEmpty() && !toTextField.getText().isEmpty()) {
					fromHour = Integer.parseInt(fromTextField.getText(0, 2)); toHour = Integer.parseInt(toTextField.getText(0, 2));
					fromMin = Integer.parseInt(fromTextField.getText(3, 5)); toMin = Integer.parseInt(toTextField.getText(3, 5));
				}
				
				if(fromHour > toHour || (fromHour == toHour && fromMin >= toMin)) {
					toTextField.setEffect(new InnerShadow(4.0, Color.RED));
					fromTextField.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				if(roomTextField.getText().isEmpty()) {
					roomTextField.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				if(valid) {
					DBConnect.addAppointment(titleTextField.getText(), descriptionTextArea.getText(), Date.valueOf(datePicker.getValue()), new Time(fromHour, fromMin, 0), new Time(toHour, toMin, 0), Login.getCurrentUser());
					stage.close();
				}
			}
		});
		
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		titleTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) titleTextField.setEffect(null);
			}
		});

		datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) datePicker.setEffect(null);
			}
		});

		descriptionTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) descriptionTextArea.setEffect(null);
			}
		});

		fromTextField.setEditable(false);
		fromTextField.setUserData(new Integer(0));
		if(startTime != 0){
			if(startTime < 10) fromTextField.setText("0"+ startTime + ":00");
			else fromTextField.setText(startTime + ":00");
		}
		fromTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					if(fromTextField.getText().isEmpty()) fromTextField.setText("00:00");
					fromTextField.setUserData(new Integer(0));
					fromTextField.positionCaret(0);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
			}
		});
		
		fromTextField.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getX() < 20) {
					fromTextField.setUserData(new Integer(0));
					fromTextField.positionCaret(0);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
				else {
					fromTextField.setUserData(new Integer(3));
					fromTextField.positionCaret(3);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
			}
		});
		
		fromTextField.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getX() < 20) {
					fromTextField.setUserData(new Integer(0));
					fromTextField.positionCaret(0);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
				else {
					fromTextField.setUserData(new Integer(3));
					fromTextField.positionCaret(3);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
			}
		});

		fromTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event)
			{
				char c = event.getCharacter().charAt(0);
				String beforeText = fromTextField.getText();
				
				Integer textPos = (Integer)fromTextField.getUserData();
				if(textPos < 5) {
					if(c >= '0' && c <= '9') {
						fromTextField.setText(fromTextField.getText(0, textPos) + c + fromTextField.getText(textPos+1, fromTextField.getText().length()));
					}
				}
				
				if(Integer.parseInt(fromTextField.getText(0, 2)) >= 24 || Integer.parseInt(fromTextField.getText(3, 5)) >= 60) {
					fromTextField.setText(beforeText);
				}
				else {
					textPos += 1;
					if(textPos == 2) {
						textPos += 1;
					}
					else if(textPos >= 5) {
						textPos = 0;
						toTextField.requestFocus();
					}
					fromTextField.setUserData(textPos);
				}
				
				if(textPos < 2) {
					fromTextField.positionCaret(0);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
				else {
					fromTextField.positionCaret(3);
					fromTextField.selectForward();
					fromTextField.selectForward();
				}
			}
		});
		
		fromTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) fromTextField.setEffect(null);
			}
		});
		
		toTextField.setEditable(false);
		toTextField.setUserData(new Integer(0));
		if(startTime != 0){
				if(startTime < 10) toTextField.setText("0"+ startTime + ":59");
				else toTextField.setText(startTime + ":59");
		}

		toTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					if(toTextField.getText().isEmpty()) toTextField.setText("00:00");
					toTextField.setUserData(new Integer(0));
					toTextField.positionCaret(0);
					toTextField.selectForward();
					toTextField.selectForward();
				}
			}
		});
		
		toTextField.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getX() < 20) {
					toTextField.setUserData(new Integer(0));
					toTextField.positionCaret(0);
					toTextField.selectForward();
					toTextField.selectForward();
				}
				else {
					toTextField.setUserData(new Integer(3));
					toTextField.positionCaret(3);
					toTextField.selectForward();
					toTextField.selectForward();
				}
			}
		});
		
		toTextField.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getX() < 20) {
					toTextField.setUserData(new Integer(0));
					toTextField.positionCaret(0);
					toTextField.selectForward();
					toTextField.selectForward();
				}
				else {
					toTextField.setUserData(new Integer(3));
					toTextField.positionCaret(3);
					toTextField.selectForward();
					toTextField.selectForward();
				}
			}
		});

		toTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event)
			{
				char c = event.getCharacter().charAt(0);
				String beforeText = toTextField.getText();
				
				Integer textPos = (Integer)toTextField.getUserData();
				if(textPos < 5) {
					if(c >= '0' && c <= '9') {
						toTextField.setText(toTextField.getText(0, textPos) + c + toTextField.getText(textPos+1, toTextField.getText().length()));
					}
				}
				
				if(Integer.parseInt(toTextField.getText(0, 2)) >= 24 || Integer.parseInt(toTextField.getText(3, 5)) >= 60) {
					toTextField.setText(beforeText);
				}
				else {
					textPos += 1;
					if(textPos == 2) {
						textPos += 1;
					}
					toTextField.setUserData(textPos);
				}
				
				if(textPos < 2) {
					toTextField.positionCaret(0);
					toTextField.selectForward();
					toTextField.selectForward();
				}
				else {
					toTextField.positionCaret(3);
					toTextField.selectForward();
					toTextField.selectForward();
				}
			}
		});
		
		toTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) toTextField.setEffect(null);
			}
		});

		roomTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue) roomTextField.setEffect(null);
			}
		});
		
		searchForRoomBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean valid = true;
				
				LocalDate date = datePicker.getValue();
				if(date == null || date.isBefore(LocalDate.now())) {
					datePicker.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				int fromHour = 0, toHour = 0, fromMin = 0, toMin = 0;
				if(!fromTextField.getText().isEmpty() && !toTextField.getText().isEmpty()) {
					fromHour = Integer.parseInt(fromTextField.getText(0, 2)); toHour = Integer.parseInt(toTextField.getText(0, 2));
					fromMin = Integer.parseInt(fromTextField.getText(3, 5)); toMin = Integer.parseInt(toTextField.getText(3, 5));
				}
				
				if(fromHour > toHour || (fromHour == toHour && fromMin >= toMin)) {
					toTextField.setEffect(new InnerShadow(4.0, Color.RED));
					fromTextField.setEffect(new InnerShadow(4.0, Color.RED));
					valid = false;
				}
				
				if(valid){
					try{
						RoomPicker.start(new Stage(), stage.getScene().getWindow(), AddAppointment.this, datePicker.getValue(), LocalTime.of(fromHour, fromMin, 0), LocalTime.of(toHour, toMin, 0));
					}catch (Exception rr){
						rr.printStackTrace();
					}
				}
				
			}
		});
		
		findEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					EmployeePicker.start(new Stage(), stage.getScene().getWindow(), AddAppointment.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setAttendees(Collection<Employee> employees) {
		attendeesTextField.clear();
		int i = 0;
		for(Employee e : employees) {
			if(i++ > 0) {
				attendeesTextField.appendText(", ");
			}
			attendeesTextField.appendText(e.getFirstName() + " " + e.getLastName());
		}
	}
	
	public Collection<Employee> getAttendees() {
		ArrayList<Employee> attendees = new ArrayList<Employee>();
		for(String fullName : attendeesTextField.getText().split(", ")) {
			String[] names = fullName.split(" ");
			for(Employee e : cachedEmployees) {
				if(names[0].equals(e.getFirstName()) && names[1].equals(e.getLastName())) {
					attendees.add(e);
					break;
				}
			}
		}
		return attendees;
	}
	
	public static void start(Stage stage, Window owner, java.util.Date date) throws Exception {
		AddAppointment.stage = stage;
		AddAppointment.startDate = new java.sql.Date(date.getTime()).toLocalDate();
		AddAppointment.startTime = date.getHours();
		Scene scene = new Scene((Parent)FXMLLoader.load(AddAppointment.class.getResource("/com/gruppe16/main/AddAppointment.fxml")));
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		if(owner != null) {
			stage.initOwner(owner);
			stage.initModality(Modality.WINDOW_MODAL);
		}
		stage.setScene(scene);
		stage.show();
	}
}
