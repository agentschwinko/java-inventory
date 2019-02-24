/*
    SteakInventory.java
    Author: Sandra Krcmar
    Date: April 12 2017

    Description
    This SteakInventory Program allows for simple user transaction, such as 
    inputting details about a Steak Inventory including ID, Name, Quantity On
    Hand, Re-Order Point, Whole Sale Price and Retail Cost. 
    
 */

package krcmar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class SteakInventory extends Application {

    // Labels
    private Label lblItemId = new Label("Item ID");
    private Label lblItemName = new Label("Item Name");
    private Label lblItemQty = new Label("Quantity On Hand");
    private Label lblItemRop = new Label("Re-Order Point");
    private Label lblItemPrice = new Label("Whole Sale Price");
    private Label lblItemCost = new Label("Retail Cost");

    // TextFields
    private TextField txtItemId = new TextField();
    private TextField txtItemName = new TextField();
    private TextField txtItemQty = new TextField();
    private TextField txtItemRop = new TextField();
    private TextField txtItemPrice = new TextField();
    private TextField txtItemCost = new TextField();
    private TextField[] arrTxtFields = {txtItemId, txtItemName, txtItemQty,
        txtItemRop, txtItemPrice, txtItemCost};

    // Button
    private Button btnFirst = new Button("First");
    private Button btnLast = new Button("Last");
    private Button btnPrev = new Button("Prev");
    private Button btnNext = new Button("Next");
    private Button btnAdd = new Button("Add");
    private Button btnDelete = new Button("Delete");
    private Button btnCancel = new Button("Cancel");
    private Button btnUpdate = new Button("Update");
    private Button btnSearch = new Button("Search");
    private Button btnInv = new Button("Show Inventory");

    // Other Global Variables 
    private Boolean addNewRecord = false;
    private int counter = 0; //to check if there is another record in fileList
    private LinkedList<String> fileList = new LinkedList();
    int index;

    /*
    ****************************************************************************
    *                               START                                      *
    ****************************************************************************
    */
    /**
     *Overridden Method representing UI of various Objects.
     * 
     * @param primaryStage The Stage Object
     */
    @Override
    public void start(Stage primaryStage) {

        // VBox
        VBox labels = new VBox(lblItemId, lblItemName, lblItemQty, lblItemRop,
            lblItemPrice, lblItemCost);
        VBox textFields = new VBox(txtItemId, txtItemName, txtItemQty,
            txtItemRop, txtItemPrice, txtItemCost);

        // HBox
        HBox modifyBtns = new HBox(btnAdd, btnDelete, btnUpdate,
            btnCancel, btnSearch);
        HBox recordBtns = new HBox(btnFirst, btnPrev, btnNext, btnLast);
        HBox invButton = new HBox(btnInv);

        // TextArea
        TextArea txaDisplay = new TextArea();
        txaDisplay.setMaxSize(550, 450);
        txaDisplay.setStyle("-fx-font: 12px Monospace");
        txaDisplay.setEditable(false);

        // Spacing
        labels.setSpacing(10);
        textFields.setSpacing(2);
        recordBtns.setSpacing(5);
        modifyBtns.setSpacing(5);

        // GridPane
        GridPane pane = new GridPane();
        pane.setVgap(14);
        pane.setHgap(10);
        pane.setPadding(new Insets(5, 5, 5, 5));

        // Add Nodes
        //pane.add(cmbSteak, 3, 1);
        pane.add(labels, 2, 2);
        pane.add(textFields, 3, 2);
        pane.add(recordBtns, 3, 3);
        pane.add(modifyBtns, 3, 4);
        pane.add(txaDisplay, 3, 5);
        pane.add(invButton, 3, 6);

        /*
         * *********************************************************************
         *                            ON LOAD                                  *
         * *********************************************************************
         *
         * The code below places the sequential file steak2.txt into a linked 
         * list and displays the text in the TextFields
         *
         ***********************************************************************
         */
        File myFile = new File("steak2.txt");
        try {
            if (myFile.exists()) {
                FileReader myFileReader = new FileReader(myFile);
                BufferedReader myBufReader = new BufferedReader(myFileReader);
                //places all the strings from steak2.txt into an array
                for (int sub = 0; sub < 10; sub++) {
                    String line = myBufReader.readLine();
                    if (line != null)    
                    {
                        // Add to Linked List
                        fileList.add(line);
                    }
                }

                String[] onloadResult = fileList.get(counter).split(",");
                for (int x = 0; x < arrTxtFields.length; x++) {
                    arrTxtFields[x].setText(onloadResult[x]);
                }

            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        /*
         * *********************************************************************
         *                        EVENT HANDLERS                               *
         * *********************************************************************
         *
         * The following four event handlers are for recordBtns. Included are
         * exception alert boxes so that the user cannot press btnNext if the
         * last record is displayed and vice versa for btnPrev
         *
         ***********************************************************************
         */
        // Next Button
        btnNext.setOnAction((e) -> {
            if (fileList.get(counter) != fileList.getLast()) {
                counter++;

                String[] nextBtnResult = fileList.get(counter).split(",");
                for (int j = 0; j < arrTxtFields.length; j++) {
                    arrTxtFields[j].setText(nextBtnResult[j]);
                }

            } else {
                // Warning Alert
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Oops!");
                alert.setContentText("There are no more records!");
                alert.showAndWait();
            }
        });

        // Previous Button
        btnPrev.setOnAction((e) -> {
            if (fileList.get(counter) != fileList.getFirst()) {
                counter--;
                String[] prevBtnResult = fileList.get(counter).split(",");
                for (int j = 0; j < arrTxtFields.length; j++) {
                    arrTxtFields[j].setText(prevBtnResult[j]);
                }

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Oops!");
                alert.setContentText("You cannot go back one record!");

                alert.showAndWait();
            }
        });

        // First Button
        btnFirst.setOnAction((e) -> {
            counter = 0;

            String[] firstBtnResult = fileList.get(counter).split(",");
            for (int x = 0; x < arrTxtFields.length; x++) {
                arrTxtFields[x].setText(firstBtnResult[x]);
            }

        });

        // Last Button
        btnLast.setOnAction((e) -> {
            counter = fileList.size();
            counter--;

            String[] lastBtnResult = fileList.get(counter).split(",");
            for (int x = 0; x < arrTxtFields.length; x++) {
                arrTxtFields[x].setText(lastBtnResult[x]);
            }

        });

        /**
         * *********************************************************************
         *                        EVENT HANDLERS                               *
         * *********************************************************************
         *
         * The following event handlers deal will data maintenance buttons: Add
         * Button Update Button Delete Button Cancel Button
         *
         ***********************************************************************
         */
        // Add Button
        btnAdd.setOnAction((e) -> {

            // Sets addNewRec to true so that we know a new 
            // record is being created
            addNewRecord = true;
            for (int i = 0; i < 6; i++) {
                arrTxtFields[i].setText(null);
            }
            // Disables the other buttons while inputing records
            btnUpdate.setDisable(false);
            btnCancel.setDisable(false);
            btnAdd.setDisable(true);
            btnPrev.setDisable(true);
            btnNext.setDisable(true);
            btnFirst.setDisable(true);
            btnLast.setDisable(true);
            btnSearch.setDisable(true);
        });

        // Update Button
        btnUpdate.setOnAction((ActionEvent e) -> {

            if (addNewRecord == false) {
                try {
                    Integer itemId;
                    Integer itemQty;
                    Integer itemRop;
                    Float itemPrice;
                    Float itemCost;
                    String modRec = "";
                    for (int j = 0; j < arrTxtFields.length; j++) {
                        arrTxtFields[j].getText();

                        if (j == 0) {
                            itemId
                                = Integer.parseInt(txtItemId.getText().trim());
                            if (itemId == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemId < 0) {
                                throw new Exception("Please enter a positive "
                                    + "number!");
                            }
                        }

                        if (j == 2) {
                            itemQty
                                = Integer.parseInt(txtItemQty.getText().trim());
                            if (itemQty == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemQty < 0) {
                                throw new Exception("Quantity on Hand must be a "
                                    + "positive number!");
                            }
                        }

                        if (j == 3) {
                            itemRop
                                = Integer.parseInt(txtItemRop.getText().trim());
                            if (itemRop == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemRop < 0) {
                                throw new Exception("Re-Order Point must be a "
                                    + "positive number!");
                            }
                        }

                        if (j == 4) {
                            itemPrice
                                = Float.parseFloat(txtItemPrice.getText().trim());
                            if (itemPrice == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemPrice < 0) {
                                throw new Exception("Whole Sale Price must be a "
                                    + "positive number!");
                            }
                        }

                        if (j == 5) {
                            itemCost
                                = Float.parseFloat(txtItemCost.getText().trim());
                            if (itemCost == 0) {
                                throw new NumberFormatException("Must enter a "
                                    + "number");
                            }
                            if (itemCost < 0) {
                                throw new Exception("Retail Cost must be a "
                                    + "positive number!");
                            }
                        }
                        if (arrTxtFields[j].getText() != null) {
                            modRec += arrTxtFields[j].getText() + ",";
                        }
                    }
                    fileList.add(counter, modRec);
                    counter++;
                    fileList.remove(counter);

                    BufferedWriter bufWrite
                        = new BufferedWriter(new FileWriter("steak2.txt"));
                    for (int i = 0; i < fileList.size(); i++) {
                        bufWrite.write(fileList.get(i) + "\n");
                    }
                    bufWrite.close();

                    Alert msg = new Alert(AlertType.INFORMATION);
                    msg.setTitle("Information");
                    msg.setHeaderText("Success!");
                    msg.setContentText("The record has been modified!");
                    msg.showAndWait();

                } catch (IOException ex) {
                    System.out.println("Array is empty!");
                } catch (NumberFormatException ex) {
                    txtItemId.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemQty.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemRop.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemPrice.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemCost.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Invalid Input");
                    error.setHeaderText("Incorrect Input");
                    error.setContentText("Must enter a number!");
                    error.showAndWait();
                    txtItemId.setStyle(null);
                    txtItemQty.setStyle(null);
                    txtItemRop.setStyle(null);
                    txtItemPrice.setStyle(null);
                    txtItemCost.setStyle(null);
                } catch (Exception ex) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Invalid Input");
                    error.setHeaderText("Incorrect Input");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            }

            if (addNewRecord == true) {
                try {

                    Integer itemId;
                    Integer itemQty;
                    Integer itemRop;
                    Float itemPrice;
                    Float itemCost;
                    String newRecord = new String();
                    for (int i = 0; i < 6; i++) {
                        arrTxtFields[i].getText();

                        if (i == 0) {
                            itemId
                                = Integer.parseInt(txtItemId.getText().trim());
                            if (itemId == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemId < 0) {
                                throw new Exception("Please enter a positive "
                                    + "number!");
                            }
                        }

                        if (i == 2) {
                            itemQty
                                = Integer.parseInt(txtItemQty.getText().trim());
                            if (itemQty == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemQty < 0) {
                                throw new Exception("Quantity on Hand must be a "
                                    + "positive number!");
                            }
                        }

                        if (i == 3) {
                            itemRop
                                = Integer.parseInt(txtItemRop.getText().trim());
                            if (itemRop == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemRop < 0) {
                                throw new Exception("Re-Order Point must be a "
                                    + "positive number!");
                            }
                        }

                        if (i == 4) {
                            itemPrice
                                = Float.parseFloat(txtItemPrice.getText().trim());
                            if (itemPrice == 0) {
                                throw new NumberFormatException("Please enter a "
                                    + "number");
                            }
                            if (itemPrice < 0) {
                                throw new Exception("Whole Sale Price must be a "
                                    + "positive number!");
                            }
                        }

                        if (i == 5) {
                            itemCost
                                = Float.parseFloat(txtItemCost.getText().trim());
                            if (itemCost == 0) {
                                throw new NumberFormatException("Must enter a "
                                    + "number");
                            }
                            if (itemCost < 0) {
                                throw new Exception("Retail Cost must be a "
                                    + "positive number!");
                            }
                        }

                        if (arrTxtFields[i].getText() == null) {
                            throw new IOException("All Fields Require Input!");
                        } else if (arrTxtFields[i].getText() != null) {
                            newRecord += arrTxtFields[i].getText() + ", ";
                        }
                    }

                    //if the input is valid, it is appended to steak2.txt
                    counter++;
                    counter = fileList.size();
                    fileList.add(newRecord);
                    BufferedWriter bufWrite
                        = new BufferedWriter(new FileWriter("steak2.txt", true));
                    bufWrite.write(newRecord + "\n");
                    bufWrite.close();

                    Alert msg = new Alert(AlertType.INFORMATION);
                    msg.setTitle("Information");
                    msg.setHeaderText("Success!");
                    msg.setContentText("A new record has been saved!");
                    msg.showAndWait();

                    btnCancel.setDisable(false);
                    btnAdd.setDisable(false);
                    btnPrev.setDisable(false);
                    btnNext.setDisable(false);
                    btnFirst.setDisable(false);
                    btnLast.setDisable(false);
                    btnSearch.setDisable(false);

                } catch (NumberFormatException ex) {
                    txtItemId.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemQty.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemRop.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemPrice.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    txtItemCost.setStyle("-fx-border-color: red; -fx-border-width: "
                        + "2px;");
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Invalid Input");
                    error.setHeaderText("Incorrect Input");
                    error.setContentText("Must enter a number!");
                    error.showAndWait();
                    txtItemId.setStyle(null);
                    txtItemQty.setStyle(null);
                    txtItemRop.setStyle(null);
                    txtItemPrice.setStyle(null);
                    txtItemCost.setStyle(null);
                } catch (IOException ex) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Invalid Input");
                    error.setHeaderText("Incorrect Input");
                    error.setContentText("You did not input a number!");
                    error.showAndWait();
                } catch (Exception ex) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Invalid Input");
                    error.setHeaderText("Incorrect Input");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            }

        });

        // Delete Button
        btnDelete.setOnAction((e) -> {
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation Dialog");
            confirm.setHeaderText("Confirmation");
            confirm.setContentText("Are you sure you want to delete this record?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                fileList.get(counter);
                fileList.remove(counter);
                counter--;
                if (counter == -1) {
                    counter++;

                    String[] delBtnResult1 = fileList.get(counter).split(",");
                    for (int x = 0; x < arrTxtFields.length; x++) {
                        arrTxtFields[x].setText(delBtnResult1[x]);
                    }

                } else {

                    String[] delBtnResult2 = fileList.get(counter).split(",");
                    for (int x = 0; x < arrTxtFields.length; x++) {
                        arrTxtFields[x].setText(delBtnResult2[x]);
                    }
                }

                try {
                    BufferedWriter bufWrite
                        = new BufferedWriter(new FileWriter("steak2.txt"));
                    for (int x = 0; x < fileList.size(); x++) {
                        bufWrite.write(fileList.get(x) + "\n");
                    }
                    bufWrite.close();
                } catch (IOException ex) {
                    System.out.println("Array is empty!");
                }
            }
        });

        // Button Cancel
        btnCancel.setOnAction((e) -> {
            counter = fileList.size();
            counter--;

            String[] cancelBtnResult = fileList.get(counter).split(",");
            for (int x = 0; x < arrTxtFields.length; x++) {
                arrTxtFields[x].setText(cancelBtnResult[x]);
            }

            btnAdd.setDisable(false);
            btnPrev.setDisable(false);
            btnNext.setDisable(false);
            btnFirst.setDisable(false);
            btnLast.setDisable(false);
            btnSearch.setDisable(false);

        });

        // Search Button
        btnSearch.setOnAction((e) -> {
            ShowStage();
        });
        // Show Inventory Button
        btnInv.setOnAction(e -> {
            txaDisplay.setText(String.format("%-6s%-6s%7s%8s%8s%9s\n",
                "ID", "Name", "QOH", "ROP", "Price", "Cost"));
            txaDisplay.appendText("========================================\n");
            for (int k = 0; k < fileList.size(); k++) {
                String nine = fileList.get(k);
                txaDisplay.appendText(nine + "\n");
            }
        });

        Scene scene = new Scene(pane, 600, 600);

        primaryStage.setTitle("Steak Inventory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
    ****************************************************************************
    *                        SEARCH STAGE                                      *
    ****************************************************************************         
    */
    /**
     * Show Stage Method for implementing search
     */
    private void ShowStage() {
        Stage search = new Stage();
        HBox pane = new HBox();
        Button searchBtn = new Button("Search");
        TextField searchField = new TextField();
        TextArea txaResult = new TextArea();
        txaResult.setEditable(false);
        pane.getChildren().addAll(searchField, searchBtn);

        GridPane grid = new GridPane();
        grid.add(pane, 1, 1);
        grid.add(txaResult, 1, 2);

        searchBtn.setOnAction((e) -> {
            txaResult.setText("");
            boolean matched = false;
            String text = searchField.getText();
            if (text != null && !text.isEmpty()) {
                for (int k = 0; k < fileList.size(); k++) {
                    String nine = fileList.get(k);
                    if (nine.toLowerCase().contains(text.toLowerCase())) {
                        matched = true;
                        txaResult.setText(String.format("%-6s%-6s%7s%8s%8s%9s\n",
                            "ID", "Name", "QOH", "ROP", "Price", "Cost"));
                        txaResult.appendText("=============================\n");
                        txaResult.appendText(nine + "\n");
                    }
                }
            }
            if (!matched) {
                txaResult.setText("NO MATCH FOUND");
            }
        });

        Scene scene = new Scene(grid, 450, 300);
        search.setScene(scene);
        search.setTitle("Search for a record");
        search.show();
    }

    /*
    ****************************************************************************
    *                        MAIN                                              *
    ****************************************************************************         
    */
    /**
     * Main method for launching UI
     * @param args String Argument
     */
    public static void main(String[] args) {
        launch(args);
    }

}
