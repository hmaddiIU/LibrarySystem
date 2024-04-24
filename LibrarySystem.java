/*
Project     : Library System
Author      : Hamid Maddi
Created on  : 04/01/2024
Updated by  : Hamid Mddi
Updated on  : 04/21/24
Description : This is the Patron Class for the Library System.
*/

import java.io.Serializable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleStringProperty;
import java.text.DecimalFormat;

public class LibrarySystem extends Application {

    private Stage primaryStage;
    private BorderPane root;

     // patrons, books and transactions lists
    private ArrayList<Patron> patrons = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // patrons, books and transactions databases
    private String patronsDatabasePath = "patrons.ser";
    private String booksDatabasePath = "books.ser";
    private String transactionsDatabasePath = "transactions.ser";

    @Override
    public void start(Stage primaryStage) {
        // attempting to load data here
        // ---> load the data from the database here <----

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library System");
        
        root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(landingPage());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Patrons menu
        Menu patronsMenu = new Menu("Patrons");
        MenuItem createPatronItem = new MenuItem("Create");
        createPatronItem.setOnAction(event -> {
            root.setCenter(createPatronPage());
        });
        MenuItem findPatronItem = new MenuItem("Find");
        findPatronItem.setOnAction(event -> {
            root.setCenter(findPatronPage());
        });
        MenuItem editPatronItem = new MenuItem("Edit");
        editPatronItem.setOnAction(event -> {
            root.setCenter(editPatronPage());
        });
        MenuItem deletePatronItem = new MenuItem("Delete");
        deletePatronItem.setOnAction(event -> {
            root.setCenter(deletePatronPage());
        });
        
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        patronsMenu.getItems().addAll(createPatronItem, findPatronItem, editPatronItem, deletePatronItem,
                new SeparatorMenuItem(), exitMenuItem);
        // exit action
        exitMenuItem.setOnAction(actionEvent -> {
            // save data to local serializer before exit
            System.out.println("Attempting to save data...");

            // only save if user made changes
            if (!patrons.isEmpty())
                saveRecords(patronsDatabasePath, patrons);

            if (!books.isEmpty())
                saveRecords(booksDatabasePath, books);

            if (!patrons.isEmpty())
                saveRecords(transactionsDatabasePath, transactions);

            Platform.exit();
        });

        // Catalog menu
        Menu catalogMenu = new Menu("Catalog");
        MenuItem searchBookItem = new MenuItem("Search book");
        searchBookItem.setOnAction(event -> {
            root.setCenter(searchBookPage());
        });
        MenuItem addBookItem = new MenuItem("Add book");
        addBookItem.setOnAction(event -> {
            root.setCenter(addBookPage());
        });
        MenuItem removeBookItem = new MenuItem("Remove book");
        removeBookItem.setOnAction(event -> {
            root.setCenter(removeBookPage());
        });
        catalogMenu.getItems().addAll(searchBookItem, addBookItem, removeBookItem);

        // Reports menu
        Menu reportsMenu = new Menu("Reports");
        MenuItem checkedOutBooksReportItem = new MenuItem("Checked out books");
        checkedOutBooksReportItem.setOnAction(event -> {
            root.setCenter(checkedOutBooksReportPage());
        });
        MenuItem patronsReportItem = new MenuItem("Patrons");
        patronsReportItem.setOnAction(event -> {
            root.setCenter(patronsReportPage());
        });
        reportsMenu.getItems().addAll(checkedOutBooksReportItem, patronsReportItem);

        // Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(event -> {
            root.setCenter(aboutPage());
        });
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(patronsMenu, catalogMenu, reportsMenu, helpMenu);

        return menuBar;
    }

    private VBox landingPage() {
        VBox page = new VBox();
        Label label = new Label("Library System");
        label.setFont(new Font("Arial", 25));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10, 10, 10, 170));
        
        page.getChildren().add(label);
        return page;
    }

    private VBox patronsReportPage() {
        VBox page = new VBox();
        Label label = new Label("Patrons");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox checkedOutBooksReportPage() {
        VBox page = new VBox();
        Label label = new Label("Checked out books");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox searchBookPage() {
        VBox page = new VBox();
        Label label = new Label("Search book");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox addBookPage() {
        VBox page = new VBox();
        Label label = new Label("Add book");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox removeBookPage() {
        VBox page = new VBox();
        Label label = new Label("Remove book");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox createPatronPage() {
        VBox page = new VBox();
        Label label = new Label("Create Patron");
        label.setFont(new Font("Arial", 22));
        label.setPadding(new Insets(20,20,0,20));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Labels
        Label nameLabel = new Label("Name");
        GridPane.setConstraints(nameLabel, 0, 0);

        Label phoneLabel = new Label("Phone");
        GridPane.setConstraints(phoneLabel, 0, 1);

        Label emailLabel = new Label("Email");
        GridPane.setConstraints(emailLabel, 0, 2);

        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        GridPane.setColumnSpan(errorLabel, 2);
        GridPane.setConstraints(errorLabel, 0, 3);

        // TextFields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter the name");
        nameField.setPrefWidth(250);

        GridPane.setConstraints(nameField, 1, 0);
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter the phone number");
        GridPane.setConstraints(phoneField, 1, 1);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter the email");
        GridPane.setConstraints(emailField, 1, 2);

        // Buttons
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 0, 4);
        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 4);

        // Event handling for Create button
        createButton.setOnAction(e -> {
            // clear error
            errorLabel.setText("");
            if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
                errorLabel.setText("All fields are mandatory");
            } else if (!isValidPhone(phoneField.getText())) {
                errorLabel.setText("Invalid phone number, expected 10 digits!");
            } else if (!isValidEmail(emailField.getText())) {
                errorLabel.setText("Invalid email format");
            } else {
                // Handle form submission here
                System.out.println("Name: " + nameField.getText());
                System.out.println("Phone: " + phoneField.getText());
                System.out.println("Email: " + emailField.getText());
            }
        });

        // Add all elements to grid
        grid.getChildren().addAll(nameLabel, phoneLabel, emailLabel, errorLabel, nameField, phoneField, emailField, cancelButton, createButton);
        page.getChildren().addAll(label, grid);

        return page;
    }

    private VBox findPatronPage() {
        VBox page = new VBox();

        Label label = new Label("Find Patron");
        label.setFont(new Font("Arial", 22));
        label.setPadding(new Insets(20,20,0,20));

        // Create dropdown to select patrons
        ComboBox<String> patronsDropdown = new ComboBox<>();
        patronsDropdown.setPrefWidth(210);
        patronsDropdown.getItems().addAll("Patron 1", "Patron 2", "Patron 3");

        // patron comboBox title
        Label patronComboBoxLabel = new Label("Select a patron");

        // Create an HBox to hold the title and the ComboBox
        HBox hbox = new HBox(10); // 10 pixels spacing
        hbox.getChildren().addAll(patronComboBoxLabel, patronsDropdown);
        hbox.setPadding(new javafx.geometry.Insets(20));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Create line separator
        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);

        // Labels
        Label nameLabel = new Label("Name");
        GridPane.setConstraints(nameLabel, 0, 0);
        Label phoneLabel = new Label("Phone");
        GridPane.setConstraints(phoneLabel, 0, 1);
        Label emailLabel = new Label("Email");
        GridPane.setConstraints(emailLabel, 0, 2);

        // TextFields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setPrefWidth(250);
        GridPane.setConstraints(nameField, 1, 0);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        GridPane.setConstraints(phoneField, 1, 1);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        GridPane.setConstraints(emailField, 1, 2);

        // Add action to ComboBox
        patronsDropdown.setOnAction(event -> {
            String selectedOption = patronsDropdown.getSelectionModel().getSelectedItem();
            nameField.setText("name " + selectedOption);
            phoneField.setText("phone " + selectedOption);
            emailField.setText("email " + selectedOption);
        });

        page.setMargin(patronsDropdown, new Insets(20));
        // Add all elements to grid
        grid.getChildren().addAll(nameLabel, phoneLabel, emailLabel, nameField, phoneField, emailField);
        page.getChildren().addAll(label, hbox, separator, grid);

        return page;
    }

    private VBox editPatronPage() {
        VBox page = new VBox();

        Label label = new Label("Edit Patron");
        label.setFont(new Font("Arial", 22));
        label.setPadding(new Insets(20,20,0,20));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // error label
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        GridPane.setColumnSpan(errorLabel, 2);
        GridPane.setConstraints(errorLabel, 0, 3);

        // Create dropdown to select patrons
        ComboBox<String> patronsDropdown = new ComboBox<>();
        patronsDropdown.setPrefWidth(210);
        patronsDropdown.getItems().addAll("Patron 1", "Patron 2", "Patron 3");
        // Add action to ComboBox
        patronsDropdown.setOnAction(event -> {
            String selectedOption = patronsDropdown.getSelectionModel().getSelectedItem();
            System.out.println("Selected option: " + selectedOption);
        });

        // patron comboBox title
        Label patronComboBoxLabel = new Label("Select a patron");

        // Create an HBox to hold the title and the ComboBox
        HBox hbox = new HBox(10); // 10 pixels spacing
        hbox.getChildren().addAll(patronComboBoxLabel, patronsDropdown);
        hbox.setPadding(new javafx.geometry.Insets(20));

        // Create line separator
        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);

        // Labels
        Label nameLabel = new Label("Name");
        GridPane.setConstraints(nameLabel, 0, 0);
        Label phoneLabel = new Label("Phone");
        GridPane.setConstraints(phoneLabel, 0, 1);
        Label emailLabel = new Label("Email");
        GridPane.setConstraints(emailLabel, 0, 2);

        // TextFields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setPrefWidth(250);
        GridPane.setConstraints(nameField, 1, 0);
        
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        GridPane.setConstraints(phoneField, 1, 1);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        GridPane.setConstraints(emailField, 1, 2);

        // Buttons
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 0, 4);

        // Event handling for Create button
        saveButton.setOnAction(e -> {
            // clear error
            errorLabel.setText("");

            // validate that the fields have data before saving
            if (patronsDropdown.getValue() == null || patronsDropdown.getValue().isEmpty()) {
                errorLabel.setText("Select the patron you want to update!");
            } else if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
                errorLabel.setText("All fields are mandatory");
            } else if (!isValidPhone(phoneField.getText())) {
                errorLabel.setText("Invalid phone number, expected 10 digits!");
            } else if (!isValidEmail(emailField.getText())) {
                errorLabel.setText("Invalid email format");
            } else {
                // Handle form submission here
                System.out.println("Name: " + nameField.getText());
                System.out.println("Phone: " + phoneField.getText());
                System.out.println("Email: " + emailField.getText());
            }
        });

      
        page.setMargin(patronsDropdown, new Insets(20));
        // Add all elements to grid
        grid.getChildren().addAll(nameLabel, phoneLabel, emailLabel, nameField, phoneField, emailField, errorLabel, saveButton);
        page.getChildren().addAll(label, hbox, separator, grid);

        return page;
    }
    
    private VBox deletePatronPage() {
        VBox page = new VBox();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label label = new Label("Delete Patron");
        label.setFont(new Font("Arial", 22));
        label.setPadding(new Insets(20,20,0,20));

        // Create label and combobox
        Label comboBoxLabel = new Label("Select a patron");
        ComboBox<String> patronsDropdown = new ComboBox<>();
        patronsDropdown.getItems().addAll("Option 1", "Option 2", "Option 3");
        patronsDropdown.setPrefWidth(210);
        
        // Create HBox for label and combobox
        HBox labelComboBoxHBox = new HBox(20); // 10 is spacing between label and combobox
        labelComboBoxHBox.getChildren().addAll(comboBoxLabel, patronsDropdown);
        GridPane.setConstraints(labelComboBoxHBox, 0, 0);

        // error label
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        // GridPane.setColumnSpan(errorLabel, 2);
        GridPane.setConstraints(errorLabel, 0, 1);

        // Create button
        Button deleteButton = new Button("Delete");
        GridPane.setConstraints(deleteButton, 0, 2);
        
        // Event handling for delete button
        deleteButton.setOnAction(e -> {
            // clear error
            errorLabel.setText("");
            // validate that patron is selected before deleting
            if (patronsDropdown.getValue() == null || patronsDropdown.getValue().isEmpty()) {
                errorLabel.setText("Select the patron to delete!");
            } else {
                // Show confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Are you sure you want to remove the account " + patronsDropdown.getValue() + " ?");

                // Adding Yes and No buttons to the confirmation alert
                confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // Show the confirmation alert and wait for a button press response
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                
                // Check the user's response
                if (result.isPresent() && result.get() == ButtonType.YES) {
                       System.out.println("perform delete");
    
                }
            }
        });

        page.setMargin(patronsDropdown, new Insets(20));
        grid.getChildren().addAll(labelComboBoxHBox, errorLabel, deleteButton);
        page.getChildren().addAll(label, grid);

        return page;
    }

    private VBox aboutPage() {
        VBox page = new VBox();
        Label label = new Label("About");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        Label about = new Label("This application provides basic functionality to run a library");
        about.setFont(new Font("Arial", 15));
        about.setPadding(new Insets(10));

        page.getChildren().addAll(label, about);

        return page;
    }

    // save the records into the database
    // this method is using the generic type T to accomodate the different classes we have (Patron, Book and transaction)
    @SuppressWarnings("unchecked")
    private <T> void saveRecords(String dbPath, ArrayList<T> records) {
        int recordCount = 0;
        try (FileOutputStream fileOut = new FileOutputStream(dbPath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            for (T record : records) {
                objectOut.writeObject(record);
                recordCount++;
            }
            System.out.println("Data saved successfully to " + dbPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // email format validation
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    // phone validation method
    private boolean isValidPhone(String phone) {
        // 10 digit phone format validation
        return phone.matches("\\d{10}");
    }

    public static void main(String[] args) {
        launch(args);
    }

}