package windowControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrintPreview implements Initializable{

    PrinterJob printerJob;
    boolean saves=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Printer printer = Printer.getDefaultPrinter();
        PageOrientation orientation = PageOrientation.LANDSCAPE;
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, orientation, 0,0,0,0 );
        printerJob = PrinterJob.createPrinterJob();
        checkSave();
        initPrinters();
    }
    private void checkSave(){ // Проверка на наличие настроек по умолчанию
        saves=false;
    }

    private void initPrinters() {
        ObservableList<String> printerNames = FXCollections.observableArrayList();
        ArrayList<Printer> printersList = new ArrayList<>();
        for (Printer x:Printer.getAllPrinters()){
            printerNames.add(x.getName());
            printersList.add(x);
        }
        cb_printerlist.setItems(printerNames);
        if (saves){
            for (Printer x:printersList) {
                if (x.getName().equals("pdfFactory Pro")) {
                    printerJob.setPrinter(x);
                    cb_printerlist.setValue(x.getName());
                }
            }
        } else {
        cb_printerlist.setValue(Printer.getDefaultPrinter().getName());}
    }

    @FXML
    private ComboBox<String> cb_printerlist;

    @FXML
    private RadioButton portrait, landscape;

    @FXML
    public AnchorPane print_view_panel;

    @FXML
    public void setPrinter(javafx.event.ActionEvent event){
        String selectedPrinter = cb_printerlist.getValue();
        for (Printer x : Printer.getAllPrinters()) {
            if (x.getName().equals(selectedPrinter)) {
              printerJob.setPrinter(x);
            }
        }
    }

    @FXML
    public void but_hide(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    public void drawRep(Pane pane){
        print_view_panel.getChildren().clear();
        print_view_panel.getChildren().add(pane);

    }


}
