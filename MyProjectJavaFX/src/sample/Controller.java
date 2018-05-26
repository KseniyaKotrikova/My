package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static javafx.scene.control.cell.TextFieldTableCell.*;

public class Controller {

    private ObservableList<Book>bookDate= FXCollections.observableArrayList(new Book(1,"War and Peace","Tolstoy",1868),new Book(2,"Anna Karenina","Tolstoy",1870),new Book(3,"Childhood","Tolstoy",1886), new Book(4,"Dead Souls","Gogol",1877));

    @FXML
    private TextField textFieldN;

    @FXML
    private TextField textFieldTitle;

    @FXML
    private Button btnAdd;
    @FXML
    private  Button btnOpen;

    @FXML
    private TextField textFieldAuthor;

    @FXML
    private TextField textFieldYear;

    @FXML
    private TableView<Book> tblCatalog;

    @FXML
    private TableColumn<Integer, String> tbcId;
    @FXML
    private TableColumn<Book, String> tbcTitle;
    @FXML
    private TableColumn<Book, String> tbcAuthor;
    @FXML
    private TableColumn<Integer, String> tbcYear;

    @FXML
    private void initialize(){
        tbcId.setCellValueFactory(new PropertyValueFactory<Integer, String>("id"));//id соответствует переменной id
        tbcTitle.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        tbcAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        tbcYear.setCellValueFactory(new PropertyValueFactory<Integer, String>("year"));
        tblCatalog.setItems(bookDate);

        tblCatalog.setEditable(true);
        btnAdd.setDisable(true);
      //  tbcId.setCellFactory(TextFieldTableCell.<Integer>forTableColumn());
        tbcTitle.setCellFactory(forTableColumn());
        tbcAuthor.setCellFactory(forTableColumn());
//        tbcYear.setCellFactory(TextFieldTableCell.<Integer>forTableColumn());

    }
    @FXML
    private void onClick(){
        bookDate.add(new Book(Integer.parseInt(textFieldN.getText()),
                textFieldTitle.getText(),
                textFieldAuthor.getText(),
                Integer.parseInt(textFieldYear.getText())));

//поля не могут быть пустыми
        //все исключения перехватывать
//пока не заполнены поля, кнопка не активна
    }


//оохранение в файле
    @FXML
    private void OnClickOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON","*.json"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        System.out.println(selectedFile.getName().toString());
    }

    @FXML
    public boolean isFilled(){
        return !textFieldN.getText().equals("")&&
                !textFieldTitle.getText().equals("")&&
                !textFieldAuthor.getText().equals("")&&
                !textFieldYear.getText().equals("");
    }
    @FXML
    private void onChange(){
        if(isFilled()){
            btnAdd.setDisable(false);
        }

    }
    @FXML
    private void changeIdCellEvent(TableColumn.CellEditEvent editEvent){
        Book bookSelected=tblCatalog.getSelectionModel().getSelectedItem();
        bookSelected.setId((Integer) editEvent.getNewValue());
    }
    @FXML
    private void changeAuthorCellEvent(TableColumn.CellEditEvent editEvent){
        Book bookSelected=tblCatalog.getSelectionModel().getSelectedItem();
        bookSelected.setAuthor(editEvent.getNewValue().toString());
    }

    @FXML
    private void changeTitleCellEvent(TableColumn.CellEditEvent editEvent){
        Book bookSelected=tblCatalog.getSelectionModel().getSelectedItem();
        bookSelected.setTitle( editEvent.getNewValue().toString());
    }
    @FXML
    private void changeYearCellEvent(TableColumn.CellEditEvent editEvent){
        Book bookSelected=tblCatalog.getSelectionModel().getSelectedItem();
        bookSelected.setYear((Integer) editEvent.getNewValue());
    }

    @FXML
    private void searchByAuthor(Book book){
       {

        }
    }

}
