package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static javafx.scene.control.cell.TextFieldTableCell.*;

public class Controller {

    private ObservableList<Book>bookDate= FXCollections.observableArrayList();
    private FilteredList<Book> filteredList;


    @FXML
    private Button btnAdd;
    @FXML
    private  Button btnOpen;

    @FXML
    private Button searchByParam;

    @FXML
    private Button btnFilter;

    @FXML
    private TextField textFieldAuthor;

    @FXML
    private TextField textFieldYear;

    @FXML
    private TextField textFieldN;

    @FXML
    private TextField textFieldTitle;

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
//        btnAdd.setDisable(true);
//        tbcId.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcTitle.setCellFactory(forTableColumn());
        tbcAuthor.setCellFactory(forTableColumn());
//        tbcYear.setCellFactory(TextFieldTableCell.forTableColumn());

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
        importJSON(selectedFile.getName());
       // System.out.println(selectedFile.getName().toString());
    }


    public void importJSON(String filename){
        int id=1;
        JSONParser parser = new JSONParser();
        try{
            Object object=parser.parse(new FileReader(filename));
            JSONArray books=(JSONArray)object;
            Iterator bookIterator=books.iterator();
            while (bookIterator.hasNext()){
                JSONObject book=(JSONObject)bookIterator.next();
                String title=book.get("title").toString();
                String author=book.get("author").toString();
                int year=Integer.parseInt(book.get("year").toString());
                bookDate.add(new Book(id++,title,author,year));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onClickSelect(){
        filteredList=new FilteredList<Book>(bookDate, book -> {if(book.getYear()>1900)
            return true;
        else return false;
        });
        tblCatalog.setItems(filteredList);
    }
    @FXML
    private void searchByParam(){
        {
        filteredList=new FilteredList<>(bookDate, book -> {if (
                book.getTitle().contains(textFieldTitle.getText())
                && book.getAuthor().contains(textFieldAuthor.getText())
               && Integer.toString(book.getYear()).contains(textFieldYear.getText())
                )
        return true;
        else
        return false;
        });
        tblCatalog.setItems(filteredList);}
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



}
