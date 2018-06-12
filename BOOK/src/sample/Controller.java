package sample;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartDataBuilder;
import eu.hansolo.tilesfx.skins.BarChartItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;
import static javafx.scene.layout.Region.layoutInArea;

public class Controller {

    private ObservableList<Book>bookDate= FXCollections.observableArrayList();
    private FilteredList<Book> filteredList;
    private FilteredList<Book> dataOld;
    private FilteredList<Book>data18;
    private FilteredList<Book>data19;
    private FilteredList<Book>data20;

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
    private   ArrayList<Book> barAuthors=new ArrayList<Book>();

    @FXML
    private Pane barChartPane;
    private Pane barChartAuthorPane;
    private Pane radialPercentagePane;

    private Tile barChartTile;
    private Tile barChartTileAuthor;
    private Tile radialTile;

   // private ChartData

    private BarChartItem barChartItemOld;
    private BarChartItem barChartItem18;
    private BarChartItem barChartItem19;
    private BarChartItem barChartItem20;
    private BarChartItem barChartAuthors;

BufferedReader reader=new BufferedReader(new InputStreamReader
        (System.in, StandardCharsets.UTF_8));

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

        barChartItemOld=new BarChartItem("До 18 в.",15, Tile.ORANGE );
        barChartItem18=new BarChartItem("18 в.",25, Tile.BLUE );
        barChartItem19=new BarChartItem("19 в.",35, Tile.GREEN );
        barChartItem20=new BarChartItem("20 в.",45, Tile.PINK );
        barChartAuthors=new BarChartItem("author",0, Tile.BLUE);


        barChartTile=TileBuilder.create()
                .prefSize(barChartPane.getWidth(),barChartPane.getHeight())
                .prefSize(150,150)
                .skinType(Tile.SkinType.BAR_CHART)
                .title("")
                .text("")
                .textColor(Color.BLACK)
                .valueColor(Color.BLACK)
                .backgroundColor(Color.rgb(244,244,244))
                .barChartItems(barChartItemOld, barChartItem18, barChartItem19,barChartItem20)
                .build();
        barChartPane.getChildren().add(barChartTile);

barChartTileAuthor=TileBuilder.create()
        .prefSize(barChartAuthors.getWidth(),barChartAuthors.getHeight())
        .prefSize(150,150)
        .skinType(Tile.SkinType.BAR_CHART)
        .title("")
        .text("")
        .textColor(Color.BLACK)
        .valueColor(Color.BLACK)
        .backgroundColor(Color.rgb(244,244,244))
        .barChartItems()
        .build();

    }
    @FXML
    private void onClick(){
        bookDate.add(new Book(Integer.parseInt(textFieldN.getText()),
                textFieldTitle.getText(),
                textFieldAuthor.getText(),
                Integer.parseInt(textFieldYear.getText())));

//поля не могут быть пустыми
// все исключения перехватывать
//пока не заполнены поля, кнопка не активна
    }


    //оохранение в файле
    @FXML
    private void OnClickOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON","*.json"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        importJSON(selectedFile.getAbsolutePath());
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
        filteredList=new FilteredList<Book>(bookDate, book -> {if(book.getYear()>1800)
            return true;
        else return false;
        });
     //   tblCatalog.setItems(filteredList);
//
        barChartItemOld.setValue(bookDate.stream().filter(book -> book.getYear()<1700).count());
        barChartItem18.setValue(bookDate.stream().filter(book -> book.getYear()>=1700 &&book.getYear()<1800).count());
        barChartItem19.setValue(bookDate.stream().filter(book -> book.getYear()>=1800 && book.getYear()<1900).count());
        barChartItem20.setValue(bookDate.stream().filter(book -> book.getYear()>=1900 && book.getYear()<2000).count());

//Map<String,Long>map=reader.lines().map((String line) -> {
   // return line
        //  .flatMap(Arrays::stream)

}


//        new BarChartItem()
//                .setValue(bookDate.stream()
//                        .filter(book -> book.getAuthor()).count());
//

//         dataOld=new FilteredList<Book>(bookDate,book -> {if(book.getYear()<1700)
//        return true;
//        else return false;});
//        barChartItemOld.setValue(dataOld.size());
//
//        data18=new FilteredList<Book>(bookDate,book -> {if(book.getYear()>=1700&&book.getYear()<1800)
//            return true;
//        else return false;});
//        barChartItem18.setValue(data18.size());
//
//        data19=new FilteredList<Book>(bookDate,book -> {if(book.getYear()>=1800&&book.getYear()<1900)
//            return true;
//        else return false;});
//        barChartItem19.setValue(data19.size());
//
//        data20=new FilteredList<Book>(bookDate,book -> {if(book.getYear()>=1900&&book.getYear()<2000)
//            return true;
//        else return false;});
//        barChartItem20.setValue(data20.size());
//
//        barChartItemOld.setValue(filteredList.size());

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

