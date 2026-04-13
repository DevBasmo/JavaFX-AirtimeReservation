


import java.security.SecureRandom;
import java.time.LocalDate;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JPanel;


public class AirtimeTravelReservation extends Application {
    private TextField nameField, phoneField, emailField,referenceTextField,searchBookingTextField;
    private DatePicker dateOfTravel,returnDate;
    private ComboBox<String> nationalty, fromBox, toBox, classBox, 
            timeBox,baggageBox,mealBox,seatBox;
    private CheckBox specialAssitanceCheck;
    private Button bookButton,clearButton,exitButton,generateButton,searchTicketButton;
    private int CODE_LENGTH = 6;
    private String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private SecureRandom random = new SecureRandom();
    private ToggleGroup tripTypeGroup;
    private RadioButton roundTrip, oneWayTrip;
   
    private AirtimeTravelDataBase db = new AirtimeTravelDataBase();
    
    
    public static void main(String[] args) {
        launch(args);
        
    }

    @Override
    public void start(Stage stage)  { 
        
        stage.setTitle("Airline Travel Reservation");
        
        
        
        //Headies
        ImageView logo = new ImageView(new Image("airline4.png"));
        logo.setFitHeight(75);
        logo.setFitWidth(105);
        logo.setPreserveRatio(true);
        
        
        Label heading = new Label("Basmo Airways - Online Booking");
        heading.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #003366;");
        
        BorderPane topBanner = new BorderPane();
        topBanner.setLeft(logo);
        topBanner.setCenter(heading);
        BorderPane.setAlignment(logo, Pos.CENTER_LEFT);
        BorderPane.setAlignment(heading, Pos.CENTER);
        topBanner.setPadding(new Insets(10));
        
        //container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(140);
        
        
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);
        
        
        referenceTextField = new TextField(); 
        referenceTextField.setFont(Font.font("Inter", 14));
        referenceTextField.setPrefWidth(20);
        referenceTextField.setPromptText("Click Generate to create ref");
        referenceTextField.setEditable(false);
        referenceTextField.setMaxWidth(Double.MAX_VALUE);
        
        searchBookingTextField = new TextField();
        searchBookingTextField.setFont(Font.font("Inter", 14));
        searchBookingTextField.setPrefWidth(20);
        searchBookingTextField.setPromptText("Enter ref number to search");
        searchBookingTextField.setMaxWidth(Double.MAX_VALUE);
        
        
        nameField = new TextField();
        nameField.setFont(Font.font("Inter",14));
        nameField.setPrefWidth(20);
      
        
        phoneField = new TextField();
        phoneField.setFont(Font.font("Inter", 14));
        phoneField.setAlignment(Pos.BASELINE_RIGHT);
        phoneField.setPrefWidth(20);
        
        emailField = new TextField();
        emailField.setFont(Font.font("Inter", 14));
        emailField.setMaxWidth(Double.MAX_VALUE);
        
        
        //Search Ticket
        Label searchTicket = new Label("Search Booking:");
        searchTicket.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366;");
        
        searchTicketButton = new Button("Search");
        searchTicketButton.setPrefWidth(70);
        searchTicketButton.setOnAction(e -> 
        {
            searchReference();
        });
        
        HBox searchBox = new HBox(8, searchBookingTextField,searchTicketButton);
        HBox.setHgrow(searchBookingTextField, Priority.ALWAYS);
        searchBox.setPadding(new Insets(2));
        grid.add(searchTicket, 0, 0);
        grid.add(searchBox, 1, 0);
        
        
        //Ref Label
        Label refLabel = new Label("Reference Code:");
        refLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366;");
        
        generateButton = new Button("Generate");
        generateButton.setPrefWidth(70);
        generateButton.setOnAction(e -> {
            String refCode = generateReferenceCode();
            referenceTextField.setText(refCode);
        });
        
        HBox refBox = new HBox(8, refLabel, referenceTextField, generateButton);
        HBox.setHgrow(referenceTextField, Priority.ALWAYS);
        refBox.setPadding(new Insets(2));
        
        grid.add(refLabel, 0, 1);
        grid.add(refBox, 1, 1);
       
        
        
        //Name
        Label nameLabel =  new Label("Full Name:");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366;");
        grid.add(nameLabel, 0, 2);
        grid.add(nameField, 1, 2);
        
        //Phone Number
        Label phoneLabel = new  Label("Phone Number:");
        phoneLabel.setStyle(" -fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366; ");
        grid.add(phoneLabel, 0, 3);
        grid.add(phoneField, 1, 3);
        
        
        //Email
        Label mailLabel = new Label("E-mail");
        mailLabel.setStyle("-fx-font-weight: bold; -fx-font-size:  19px; -fx-text-fill: #003366");
        grid.add(mailLabel, 0, 4);
        grid.add(emailField, 1, 4);
        
        
        //Nationalty
        Label nationaltyLabel =  new Label("Nationalty:");
        nationaltyLabel.setStyle("-fx-font-weight: bold; -fx-font-size:  19px; -fx-text-fill: #003366");
        grid.add(nationaltyLabel, 0, 5);
        
        nationalty = new ComboBox<>();
        nationalty.getItems().addAll( "Algeran", "Eritrean", "Eswatini", "Ethiopian", "Gabonese",
    "Gambian", "Ghanaian", "Guinean", "Guinean-Bissauan", "Kenyan", "Lesothoan",
    "Liberian", "Libyan", "Malagasy", "Malawian", "Malian", "Mauritanian",
    "Mauritian", "Moroccan", "Mozambican", "Namibian", "Nigerien", "Nigerian",
    "Rwandan", "São Tomean", "Senegalese", "Seychellois", "Sierra Leonean",
    "Somali", "South African", "South Sudanese", "Sudanese", "Tanzanian", "Togolese",
    "Tunisian", "Ugandan", "Zambian", "Zimbabwean");
        nationalty.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        nationalty.setPromptText("Select Nationality");
        nationalty.setPrefHeight(30);
        nationalty.setPrefWidth(500);
        nationalty.setMaxWidth(Double.MAX_VALUE);
        grid.add(nationalty, 1, 5);
        
        
        //RadioButton for TripType
        Label tripTypeLabel = new Label("Trip Type:");
        tripTypeLabel.setStyle("-fx-font-weight: bold; -fx-font-size:  19px; -fx-text-fill: #003366");
        grid.add(tripTypeLabel, 0, 6);
        
         tripTypeGroup = new ToggleGroup();
        
        oneWayTrip = new RadioButton("One-Way");
        oneWayTrip.setToggleGroup(tripTypeGroup);
       
        
        roundTrip = new RadioButton("Round-Trip");
        roundTrip.setToggleGroup(tripTypeGroup);
        
        HBox tripTypeBox = new HBox(20, oneWayTrip, roundTrip);
        grid.add(tripTypeBox, 1, 6);
        
        //Departure Location
        Label departureLocationLabel = new Label("Departure Location:");
        departureLocationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(departureLocationLabel, 0, 7);
        
        ObservableList<String> states = FXCollections.observableArrayList(
            "Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno",
    "Cross River", "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "Gombe", "Imo",
    "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos",
    "Nasarawa", "Niger", "Ogun", "Ondo", "Osun", "Oyo", "Plateau", "Rivers",
    "Sokoto", "Taraba", "Yobe", "Zamfara", "Federal Capital Territory"
);
  
  
        fromBox  = new ComboBox<>(states);
        fromBox.setPromptText("Select Departure Location");
        fromBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        fromBox.setMaxWidth(Double.MAX_VALUE);
        fromBox.setPrefWidth(500);
        
        grid.add(fromBox, 1, 7);
        
        //Destination
        Label destinationLabel = new Label("Destination Location:");
        destinationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(destinationLabel, 0, 8);
        
        
        ObservableList<String> statesTo = FXCollections.observableArrayList(
            "Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno",
    "Cross River", "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "Gombe", "Imo",
    "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos",
    "Nasarawa", "Niger", "Ogun", "Ondo", "Osun", "Oyo", "Plateau", "Rivers",
    "Sokoto", "Taraba", "Yobe", "Zamfara", "Federal Capital Territory"
);
        
        toBox = new ComboBox<>(statesTo);
        toBox.setPromptText("Select Destination");
        toBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        toBox.setMaxWidth(Double.MAX_VALUE);
        toBox.setPrefWidth(500);
        
        grid.add(toBox, 1, 8);
        
        //Travel Date
        Label travelDate = new Label("Date of Travel:");
        travelDate.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(travelDate, 0, 9);
        
        dateOfTravel = new DatePicker();
        dateOfTravel.setPromptText("Select Date of Travel");
        dateOfTravel.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px; -fx-pref-width: 200px;");
        dateOfTravel.setMaxWidth(Double.MAX_VALUE);
        grid.add(dateOfTravel, 1, 9);
        
        //Return Date
        Label returnDatee = new Label("Return Date:");
        returnDatee.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(returnDatee, 0, 10);
        
        
        returnDate = new DatePicker();
        returnDate.setPromptText("Select Return Date");
        returnDate.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px; -fx-pref-width: 200px;");
        returnDate.setMaxWidth(Double.MAX_VALUE);
        
        returnDate.setDisable(true);
        returnDate.setOpacity(0.5);
        
        grid.add(returnDate, 1, 10);
        
        tripTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            
        boolean isRoundTrip = roundTrip.isSelected();
        returnDate.setDisable(!isRoundTrip);
        returnDate.setOpacity(isRoundTrip ? 1.0 : 0.5);
        });
        
        
        
        
        
        //Departure Time
        Label departureTime = new Label("Prefered Departure Time:");
        departureTime.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(departureTime, 0, 11);
        
        
        timeBox = new ComboBox<>();
        timeBox.getItems().addAll("Anytime","06:00 Am", "09:00 AM", "12:00 PM", "02:00 PM", "04:00 PM", "06:00 PM", "08:00 PM", "10:00PM");
        timeBox.setPrefWidth(500);
        timeBox.setMaxWidth(Double.MAX_VALUE);
        timeBox.setPromptText("Set Prefered Time of Travel");
        timeBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        grid.add(timeBox, 1, 11);
        
        
        //Seat Class
        Label seatClass = new Label("Seat Class:");
        seatClass.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(seatClass, 0, 12);
        
        classBox = new ComboBox<>();
        classBox.getItems().addAll("Economy","Bussiness", "First Class");
        classBox.setPrefWidth(500);
        classBox.setMaxWidth(Double.MAX_VALUE);
        classBox.setPromptText("Select Seat Class");
        classBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        
        grid.add(classBox, 1, 12);
        
        //Baggage Options
        Label baggageLabel = new Label("Baggage Option:");
        baggageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(baggageLabel, 0, 13);
        
        baggageBox = new ComboBox<>();
        baggageBox.getItems().addAll("No baggage", "5kg", "10kg", "15kg", "20kg", "25kg", "30kg");
        baggageBox.setPrefWidth(500);
        baggageBox.setMaxWidth(Double.MAX_VALUE);
        baggageBox.setPromptText("Select baggage weight");
        baggageBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        grid.add(baggageBox, 1, 13);
        
        
        //Meal Preference
        Label mealLabel = new Label("Meal Preferences:");
        mealLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(mealLabel, 0, 14);
        
        mealBox = new ComboBox<>();
        mealBox.getItems().addAll("No Preference", "Vegetarian", "Vegan", "Gluten-Free", "Halal", "Kosher", "Low Sodium",
                "Diabetic", "Child Meal"
                );
        mealBox.setPromptText("Select Meal Preference");
        mealBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        mealBox.setPrefWidth(500);
        mealBox.setMaxWidth(Double.MAX_VALUE);
        grid.add(mealBox, 1, 14);
        
        
        //SeatNumber
        Label seatNumberLabel = new Label("Seat Selection:");
        seatNumberLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(seatNumberLabel, 0, 15);
        
        seatBox = new ComboBox<>();
        for(char row = 'A'; row <= 'F'; row++){
            for(int num = 1; num <= 5; num++){
                seatBox.getItems().add(row + String.valueOf(num));
            }
        }
        seatBox.setPrefWidth(500);
        seatBox.setPromptText("Select Seat Number");
        seatBox.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        seatBox.setMaxWidth(Double.MAX_VALUE);
        
        grid.add(seatBox, 1, 15);
        
        
        //Special Assistance
        Label specialAsstLabel = new Label("Special Assistance:");
        specialAsstLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #003366");
        grid.add(specialAsstLabel, 0, 16);
        
        specialAssitanceCheck = new CheckBox("Request Special Assistance");
        specialAssitanceCheck.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px;");
        grid.add(specialAssitanceCheck, 1, 16);
        
        
        
       
        
        
       Button bookButton = new Button("Book Now");
       bookButton.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 16px;");
       bookButton.setPrefWidth(100);
       bookButton.setOnAction(e -> 
       {
           bookTrip();
       });
       
       clearButton = new Button("Clear");
       clearButton.setStyle("-fx-background-color: #CCCCCC; -fx-text-fill: black; -fx-font-size: 16px;");
       clearButton.setPrefWidth(100);
       
       exitButton = new Button("Exit");
       exitButton.setStyle("-fx-background-color: #FF4C4C; -fx-text-fill: WHITE; -fx-font-size: 16px;");
       exitButton.setPrefWidth(100);
       
       HBox buttonBox = new HBox(20, bookButton, clearButton, exitButton);
       buttonBox.setAlignment(Pos.CENTER);
       buttonBox.setPadding(new Insets(20, 0, 0, 0));
       
        
        
        
        
       //Actions for the Button
       exitButton.setOnAction(e -> stage.close());
       bookButton.setOnAction(e -> {
           bookTrip();
           
               
           
       });
       
       //Clear Button Method
       clearButton.setOnAction(e -> {
           
           referenceTextField.clear();
           nameField.clear();
           emailField.clear();
           phoneField.clear();
           
           
           nationalty.getSelectionModel().clearSelection();
           nationalty.setValue(null);
           nationalty.getEditor().clear();
           nationalty.setButtonCell(new ListCell<>() {
               
               @Override
               protected void updateItem(String item, boolean empty)
               {
                   super.updateItem(item, empty);
                   setText(empty || item == null ? nationalty.getPromptText() : item);
               }
           });
           tripTypeGroup.selectToggle(null);
           fromBox.setValue(null);
           toBox.setValue(null);
           dateOfTravel.setValue(null);
           returnDate.setValue(null);
           timeBox.setValue(null);
           classBox.setValue(null);
           baggageBox.setValue(null);
           mealBox.setValue(null);
           seatBox.setValue(null);
           specialAssitanceCheck.setSelected(false);
           
           
       });
        
        
        
       
      
        
        
        
      
        
        
        VBox root = new VBox(5, topBanner,grid, buttonBox);
        root.setPadding(new Insets(20));
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
       // scrollPane.setPrefHeight(200);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Scene scene = new Scene (scrollPane, 850, 650);
        stage.setScene(scene);
        stage.show();
        
        
        grid.requestFocus();
    
        
    }
    
    //method to generate reference code
    private String generateReferenceCode()
    {
        StringBuilder sb = new StringBuilder("REF-");
        for(int i =  0; i < CODE_LENGTH; i++)
        {
            int index = random.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }
    
    
    //Display message for invalid input
    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    //Validate input method
    private boolean validateInputs()
    {
        StringBuilder errors = new StringBuilder();
        String name = nameField.getText().trim();
        
        if(name == null || !name.matches("^[A-Za-z][A-Za-z\\s'\\-]{1,49}$"))
        {
            errors.append("\u2022 Full name is required.\n");
            nameField.setStyle("-fx-border-color: red;");
            
        }
        else{
            nameField.setStyle("");
            
        }
        
        
        
        String email = emailField.getText();
    if(email == null || !email.matches("^\\S+@\\S+\\.\\S+$")){
        errors.append("\u2022 Enter a valid email address.\n");
        emailField.setStyle("-fx-border-color: red");
    }else {
        emailField.setStyle("");
    }
    
    
    String ref = referenceTextField.getText();
    if(ref == null || ref.trim().isEmpty())
    {
        errors.append("\u2022 Generate a reference code.\n");
        referenceTextField.setStyle("-fx-border-color: red");
    }
    else{
        referenceTextField.setStyle("");
    }
    
    
    String phone = phoneField.getText();
    if( phone == null || !phone.matches("\\d{10,15}"))
    {
        errors.append("\u2022 Phone number must be 10-15 digit.\n");
        phoneField.setStyle("-fx-border-color: red");
    }
    else{
        phoneField.setStyle("");
    }
    
    
    
    if (nationalty.getValue()  == null || nationalty.getValue().trim().isEmpty()){
        errors.append("\u2022 Please select Nationalty.\n");
        nationalty.setStyle("-fx-border-color: red;");
    }else{
        nationalty.setStyle("");
    }
    
    
    if(tripTypeGroup.getSelectedToggle() == null){
        errors.append("\u2022 Please select a trip type.\n");
        oneWayTrip.setStyle("-fx-border-color: red;");
        roundTrip.setStyle("-fx-border-color: red;");
    }else{
        oneWayTrip.setStyle("");
        roundTrip.setStyle("");
    }
    
    if(fromBox.getValue() == null || fromBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Please Select Departure Location.\n");
        fromBox.setStyle("-fx-border-color: red;");
    }else{
        fromBox.setStyle("");
    }
    
    if (toBox.getValue() == null || toBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Please Select Destination.\n");
        toBox.setStyle("-fx-border-color: red;");
    }else{
        toBox.setStyle("");
    }
    
    LocalDate selectedDate = dateOfTravel.getValue();
    LocalDate today = LocalDate.now();
    if( selectedDate == null || selectedDate.isBefore(today)){
        errors.append("\u2022 Please select a valid date.\n");
        dateOfTravel.setStyle("-fx-border-color: red;");
    }else{
        dateOfTravel.setStyle("");
    }
    
    LocalDate returnSelectedDate = returnDate.getValue();
    if (roundTrip.isSelected()){
         if (returnSelectedDate == null || returnSelectedDate.isBefore(today)){
        errors.append("\u2022 Please select valide return date.\n");
        returnDate.setStyle("-fx-border-color: red;");
    }
   
        
    }
    
    
    if( timeBox.getValue() == null || timeBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Please Select A Preffered Travel Time.\n");
        timeBox.setStyle("-fx-border-color: red;");
    }else{
        timeBox.setStyle("");
    }
    
    
    if( classBox.getValue() == null || classBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Please Select Class Seat.\n");
        classBox.setStyle("-fx-border-color: red;");
    }else{
        classBox.setStyle("");
    }
    
    if( baggageBox.getValue() == null || baggageBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Select a correct Baggage Weight.\n");
        baggageBox.setStyle("-fx-border-color: red;");
    }else{
        baggageBox.setStyle("");
    }
    
    
    if(mealBox.getValue() == null || mealBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Select a meal preference.\n ");
        mealBox.setStyle("-fx-border-color: red;");
    }else{
        mealBox.setStyle("");
    }
    
    
    if( seatBox.getValue() == null || seatBox.getValue().trim().isEmpty()){
        errors.append("\u2022 Select a seat.\n");
        seatBox.setStyle("-fx-border-color: red;");
    }else
    {
        seatBox.setStyle("");
    }
    
    
    
    
    
    
    
    
   if(errors.length() > 0)
   {
       showAlert("Input Validation Error", errors.toString());
       return false;
   }
        return true;
    }
    
    

    
    private void bookTrip()
    {
        if(!validateInputs())
        {
            return;
        }
        String referenceDB = referenceTextField.getText().trim();
        
        if ( db.exists(referenceDB))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText("Ticket already existed!");
            alert.showAndWait();
            
           referenceTextField.clear();
           nameField.clear();
           emailField.clear();
           phoneField.clear();
           nationalty.setValue(null);
           tripTypeGroup.selectToggle(null);
           fromBox.setValue(null);
           toBox.setValue(null);
           dateOfTravel.setValue(null);
           returnDate.setValue(null);
           timeBox.setValue(null);
           classBox.setValue(null);
           baggageBox.setValue(null);
           mealBox.setValue(null);
           seatBox.setValue(null);
           specialAssitanceCheck.setSelected(false);
          
           return;
            
        }
        
        referenceDB = referenceTextField.getText().trim();
        String nameDB = nameField.getText().trim();
        String phoneDB = phoneField.getText().trim();
        String emailDB = emailField.getText().trim();
        String nationaltyDB = nationalty.getValue();
        Toggle selectedToggle = tripTypeGroup.getSelectedToggle();
        String tripTypeDB = "";
        if ( selectedToggle != null)
        {
            tripTypeDB = ((RadioButton) selectedToggle).getText();
        }
        String fromDB = fromBox.getValue();
        String toDB = toBox.getValue();
        
        LocalDate selectedDate = dateOfTravel.getValue();
        String dateOfTravelDB = (selectedDate != null) ? selectedDate.toString() : "";
        
        LocalDate returnDatee = returnDate.getValue();
        String returnDateDB = (returnDatee != null) ? returnDatee.toString() : "";
        
        String timeDB = timeBox.getValue();
        String classDB = classBox.getValue();
        String baggageDB = baggageBox.getValue();
        String mealDB = mealBox.getValue();
        String seatDB = seatBox.getValue();
        String specialAsstDB = specialAssitanceCheck.isSelected() ? "Yes" : "No";
        
        
        
        
        
        
        
            AirtimeTravelStore travel = new AirtimeTravelStore(referenceDB, nameDB, phoneDB, emailDB, nationaltyDB,
            tripTypeDB, fromDB, toDB, dateOfTravelDB,returnDateDB,
            timeDB, classDB, baggageDB, mealDB, seatDB, specialAsstDB
    );
            db.registerTicket(travel);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText("Ticket Booked");
            alert.showAndWait();
            showTicket(travel);
            
            referenceTextField.clear();
           nameField.clear();
           emailField.clear();
           phoneField.clear();
           nationalty.setValue(null);
           tripTypeGroup.selectToggle(null);
           fromBox.setValue(null);
           toBox.setValue(null);
           dateOfTravel.setValue(null);
           returnDate.setValue(null);
           timeBox.setValue(null);
           classBox.setValue(null);
           baggageBox.setValue(null);
           mealBox.setValue(null);
           seatBox.setValue(null);
           specialAssitanceCheck.setSelected(false);
          
            
        
        
    }
       
    
    
    
    private void searchReference()
    {
        
        
        String searchTicketFieldd = searchBookingTextField.getText().trim();
        
        AirtimeTravelStore travel = db.findTicket(searchTicketFieldd);
        
        if (travel == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText("Ticket not found.");
            alert.showAndWait();
            searchBookingTextField.clear();
        }
        
        referenceTextField.setText(travel.referenceDB);
        nameField.setText(travel.nameDB);
        phoneField.setText(travel.phoneDB);
        emailField.setText(travel.emailDB);
        nationalty.setValue(travel.nationaltyDB);
        if (travel.tripTypeDB.equalsIgnoreCase("One-Way"))
        {
            tripTypeGroup.selectToggle(oneWayTrip);
        }else if (travel.tripTypeDB.equalsIgnoreCase("Round-Trip"))
        {
            tripTypeGroup.selectToggle(roundTrip);
            
        }
               
        fromBox.setValue(travel.fromDB);
        toBox.setValue(travel.toDB);
        dateOfTravel.setValue(LocalDate.parse(travel.dateOfTravelDB));
        if ( travel.returnDateDB != null && !travel.returnDateDB.isEmpty())
        {
        returnDate.setValue(LocalDate.parse(travel.returnDateDB));
        }else{
            returnDate.setValue(null);
        }
        timeBox.setValue(travel.timeDB);
        classBox.setValue(travel.classDB);
        baggageBox.setValue(travel.baggageDB);
        mealBox.setValue(travel.mealDB);
        seatBox.setValue(travel.seatDB);
        if (travel.specialAsstDB.equals("Yes")){
            specialAssitanceCheck.setSelected(true); 
        }else{
            specialAssitanceCheck.setSelected(false);
        }
      
    }
        public void showTicket(AirtimeTravelStore travel)
        {
            String ticket = "=================== TRAVEL TICKET ==================\n\n"+
                    "Reference Code:     " + travel.referenceDB + "\n" +
                    "Passenger Name:     " + travel.nameDB + "\n" +
                    "Nationalty:         " + travel.nationaltyDB + "\n" +
                    "Trip Type:          " + travel.tripTypeDB + "\n\n" +
                    "From:               " + travel.fromDB +  "\n"   +
                    "To:                 " + travel.toDB   +  "\n"   +
                    "Date of Travel:     " + travel.dateOfTravelDB + "\n"  +
                    "Return Date:        " + travel.returnDateDB  + "\n"   +
                    "Time:               " + travel.timeDB +  "\n" +
                    "Class:              " + travel.classDB + "\n"   +
                    "Seat Number:        " + travel.seatDB +  "\n"  +
                    "Baggage:            " + travel.baggageDB +  "\n" +
                    "Meal Preference:    " + travel.mealDB + "\n"  +
                    "Special Assistance: " + travel.specialAsstDB + "\n\n"+
                    "Contact Info: \n"     +
                    "Phone:              " + travel.phoneDB + "\n" +
                    "Email:              " + travel.emailDB + "\n\n"+
                    "=================================================";
            
            TextArea ticketArea = new TextArea(ticket);
            ticketArea.setEditable(false);
            ticketArea.setPrefSize(500, 500);
            ticketArea.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
            
            
            Button printButton = new Button("Print Ticket");
            printButton.setOnAction( e->{
                
               
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText(null);
                alert.setContentText("Printing Ticket...");
                alert.showAndWait();
                
            });
            
            VBox layout = new VBox(10, ticketArea, printButton);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.CENTER);
            
            Scene scene = new Scene(layout, 500, 600);
            Stage ticketStage  = new Stage();
            ticketStage.setWidth(500);
            ticketStage.setHeight(600);
            ticketStage.setTitle("Your Travel Ticket");
            ticketStage.setScene(scene);
            ticketStage.show();
           
            
        }
        
        
    

    
}
