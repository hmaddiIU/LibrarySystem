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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        Label label = new Label("Renove book");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox createPatronPage() {
        VBox page = new VBox();
        Label label = new Label("Create Patron.java");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox findPatronPage() {
        VBox page = new VBox();
        Label label = new Label("Find Patron.java");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }

    private VBox editPatronPage() {
        VBox page = new VBox();
        Label label = new Label("Edit Patron.java");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

        return page;
    }
    
    private VBox deletePatronPage() {
        VBox page = new VBox();
        Label label = new Label("Delete Patron.java");
        label.setFont(new Font("Arial", 15));
        label.setTextFill(Color.web("#A9A9A9"));
        label.setPadding(new Insets(10));

        page.getChildren().addAll(label);

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

    public static void main(String[] args) {
        launch(args);
    }

}