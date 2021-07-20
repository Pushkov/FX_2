package windowControllers;


import calculate.Trailer;
import calculate.Truck;
import calculate.Production;
import db.DataController;
import varianty.Variant;
import window.Graf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class mainWindowController implements Initializable{
    DataController ds = new DataController();
    Pane selectedPane = null;
    Graf graf;

    Variant opt_var;
    int i_opt_var;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pan_parrent.getStylesheets().add(0, "/fxml/styles/my.css");
        Truck.getInstance();
        Trailer.getInstance();
        initTruck();
        initTrailer();
        initTableProduction();
        initKolProd();
    }

    private void initTruck(){
        ObservableList tt = FXCollections.observableArrayList(ds.startData("truck","type"));
        select_type_t.setValue("Выбор типа тягача");
        select_type_t.setItems(tt);
        ObservableList mm = FXCollections.observableArrayList(ds.startData("truck","model"));
        select_model_t.setValue("Выбор модели тягача");
        select_model_t.setItems(mm);

    }

    private void initTrailer(){
        ObservableList pt = FXCollections.observableArrayList(ds.startData("trailer","type"));
        select_type_pp.setValue("Выбор типа полуприцепа");
        select_type_pp.setItems(pt);
        ObservableList pm = FXCollections.observableArrayList(ds.startData("trailer","model"));
        select_model_pp.setValue("Выбор модели полуприцепа");
        select_model_pp.setItems(pm);
    }

    private void initTableProduction(){

                ObservableList tList = FXCollections.observableArrayList("");
        tableProduction.setItems(tList);
        tableProduction.setPrefHeight(50);
//        tableProduction.autosize();
    }

    private void initKolProd(){
        ObservableList<Byte> tList = FXCollections.observableArrayList();
        for (byte i=1; i<21; i++){
            tList.add(i);
        }
        cb_production_kol.setItems(tList);
        cb_production_kol.setValue((byte) 0);
    }
    @FXML
    public Stage primaryStage;

    @FXML
    public ComboBox<Byte> cb_production_kol;

    @FXML
    public TableView tableProduction;

    @FXML
    public TableColumn<Production,String> tableProductionName, tableProductionQty;

    @FXML
    public Pane pan_gruz,
            pane_report_page , base_pane;
    @FXML
    public ComboBox<String> select_type_t, select_model_t, select_type_pp, select_model_pp, cb_production_name;

    @FXML
    public TextField  load_X1, load_X2, load_X3;

    @FXML
    public TextField name_truck, name_trailer;

    @FXML
    public AnchorPane pan_transport, pan_results, pan_var;

    @FXML
    public ScrollPane sp1;

    @FXML
    public CheckBox autodata;

    @FXML
    public void b_test (){

        System.out.println("тыц == mWC");
    }

    @FXML
    public BorderPane pan_parrent;

    @FXML
    public void panelTransport(ActionEvent event){
        pan_gruz.setVisible(false);
        pan_transport.setVisible(true);
        pan_var.setVisible(false);
    }

    @FXML
    public void panelProduction(ActionEvent event){
        pan_gruz.setVisible(true);
        pan_transport.setVisible(false);
        pan_var.setVisible(false);
    }

    @FXML
    public void panelVariants(ActionEvent event){
        pan_gruz.setVisible(false);
        pan_transport.setVisible(false);
        pan_var.setVisible(true);
        if (autodata.isSelected()){
            ds.clearProduction();
            byte dd = 13;
            ds.selectModel("truck", "m5440");
            printTruck();
            ds.selectModel("trailer", "test999");
            printTrailer();
            ds.setProductionType("m");
            ds.addProduction("MetCord 1",(byte)3);
            ds.addProduction("MetCord2",(byte)2);
            ds.addProduction("MetCord 1",(byte)3);
            tableProduction.setPrefHeight( (3) * 32 );
//            setTableProduction(ds.getProdList());
            autodata.selectedProperty().setValue(false);
        }

        if (ds.existProdList()) {
            ds.createVariants();
            drawVariants(ds.inLimits(ds.getVariantArrayList()));
        }
     }

    private Parent root1;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private PrintPreview pwController;
    private Stage pw;
    private Stage stage_print; //= new Stage ();

    @FXML
    public void panelReport(ActionEvent event){


        if (stage_print==null) {
            try {
                fxmlLoader.setLocation(getClass().getResource("/fxml/printPreview.fxml"));
                stage_print = new Stage();
                root1 = fxmlLoader.load();
                pwController = fxmlLoader.getController();
                stage_print.setTitle("Печать отчета");
                stage_print.setMinWidth(720);
                stage_print.setMinHeight(480);
                stage_print.setResizable(false);
                stage_print.setScene(new Scene(root1));
                stage_print.initModality(Modality.WINDOW_MODAL);
                stage_print.initOwner(((Node) event.getSource()).getScene().getWindow());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage_print.show();
        drawRep(opt_var,i_opt_var);
        

}

    @FXML
    public void cb_transport(ActionEvent event) {
        String eventSource =  ((ComboBox)event.getSource()).getId();
        switch (eventSource){
            case ("truck_type"):
                if (((ComboBox) event.getSource()).isFocused()) {
                    Truck.clearTransport();
                    printTruck();
                    printLoads(ds.loadsEmptyTransport());
                    String selectedType = ((ComboBox) event.getSource()).getValue().toString();
                    String transport = "truck";
                    ObservableList mm = FXCollections.observableArrayList(ds.selectType(transport, selectedType));
                    select_model_t.setItems(mm);
                }

                break;
            case ("truck_model"):
                if (((ComboBox) event.getSource()).isFocused()){
                    Truck.clearTransport();
                    String transport = "truck";
                    String selectedModel =((ComboBox) event.getSource()).getValue().toString();
                    ds.selectModel(transport, selectedModel);
                    printTruck();
                    printLoads(ds.loadsEmptyTransport());
                }
                break;
            case ("trailer_type"):
                if (((ComboBox) event.getSource()).isFocused()) {
                    Trailer.clearTransport();
                    printTrailer();
                    printLoads(ds.loadsEmptyTransport());
                    String selectedType = ((ComboBox) event.getSource()).getValue().toString();
                    String transport = "trailer";
                    ObservableList mmm = FXCollections.observableArrayList(ds.selectType(transport, selectedType));
                    select_model_pp.setItems(mmm);
                }
                break;
            case ("trailer_model"):
                if (((ComboBox) event.getSource()).isFocused()) {
                    Trailer.clearTransport();
                    String transport = "trailer";
                    String selectedModel = ((ComboBox) event.getSource()).getValue().toString();
                    ds.selectModel(transport, selectedModel);
                    printTrailer();
                    printLoads(ds.loadsEmptyTransport());
                }
                break;
        }
    }

    private void printTruck(){
        String nameTruck = Truck.getTransport_NAME();
        name_truck.setText(nameTruck);

    }

    private void printTrailer(){
        String nameTrailer = Trailer.getTransport_NAME();
        name_trailer.setText(nameTrailer);

    }

    public void clearTruck(){
        DataController.calcDataChanges = true;
        Truck.clearTransport();
        printTruck();
        printLoads(ds.loadsEmptyTransport());
        initTruck();
        panelTransport(null);
    }

    public void clearTrailer(){
        DataController.calcDataChanges = true;
        Trailer.clearTransport();
        printTrailer();
        printLoads(ds.loadsEmptyTransport());
        initTrailer();
        panelTransport(null);
    }

    private void printLoads(double[] a){
        load_X1.setText(String.valueOf(a[0]));
        load_X2.setText(String.valueOf(a[1]));
        load_X3.setText(String.valueOf(a[2]));
    }

    @FXML
    public void setProductionType(ActionEvent event){
        String b_name = ((ToggleButton) event.getSource()).getText();
        if (b_name.equals("Метизное производство"))     b_name = "m";
        if (b_name.equals("Прокатное производство"))    b_name = "p";

        ObservableList list = FXCollections.observableArrayList(ds.setProductionType(b_name));
        cb_production_name.setValue("");
        cb_production_name.setItems(list);
    }

    @FXML
    public void addProduction(ActionEvent event){
        byte kol=0;

        ObservableList<Byte> tt = FXCollections.observableArrayList(cb_production_kol.getItems());
        if ( tt.contains(cb_production_kol.getValue())   ){
            kol= cb_production_kol.getValue();
        }
        else {
            System.out.println("ноль груза");
        }

        if (cb_production_name.getValue() != null & kol!=0){
            String productionName = cb_production_name.getValue().toString();
            ds.addProduction(productionName,kol);
            setTableProduction(ds.getProductionList(), ds.getKolList());
            cb_production_kol.setValue((byte) 0);
        }
    }

    private void setTableProduction(ArrayList<Production> prodList, ArrayList<Byte> kol){
        initTableProduction();
        ObservableList tList = FXCollections.observableArrayList();
        for (int i = 0; i<prodList.size(); i++){
            tList.add(prodList.get(i));
        }

        tableProduction.setItems(tList);

        tableProductionName.setCellValueFactory(new PropertyValueFactory<Production,String>("name"));

        tableProductionQty.setCellValueFactory(new PropertyValueFactory<Production,String>("kol"));
        if (tList.size()<7)
            tableProduction.autosize();

    }

    @FXML
    public void clearProduction(){
        DataController.calcDataChanges = true;
        initTableProduction();
        ds.clearProduction();
        cb_production_name.setValue("");
        panelProduction(null);
        if(pan_results.getChildren().size()!=0) clearDraw();
    }


    private void clearDraw(){
            pan_results.getChildren().clear();
         selectedPane.getChildren().clear();
    }



    private void drawVariants (ArrayList<Variant> list){
        double dX = 100;
        Variant optimal_variant = list.get(0);
        graf = new Graf();
        double v0h = 10;
        Pane [] v0 = new Pane[list.size()];
        pan_results.getChildren().clear();
        pan_results.setPrefHeight(v0h + 30);
        for (int i=0; i<list.size();i++){
            v0[i] = new Pane();
            v0[i].setPrefSize(400,50);
            v0[i].setId(String.valueOf(i));
            String msg_id = String.valueOf(i);
            v0[i].getStyleClass().add("variant_pane");
            Variant variant = list.get(i);

            graf.drawing(v0[i], variant, ("Вариант " + String.valueOf(i+1)), 75);
            if (i%2==0) {
                v0[i].setLayoutX(10);
                v0[i].setLayoutY(v0h);
            }
            else {
                v0[i].setLayoutX(280);
                v0[i].setLayoutY(v0h);
                v0h += graf.getV() + 5;
            }
            optimal_variant = ds.getOptVariant(optimal_variant,variant);
            v0[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                opt_var = list.get(Integer.valueOf(msg_id));
                i_opt_var = Integer.valueOf(msg_id);
                mainWindowController.this.drawSelected(list.get(Integer.valueOf(msg_id)), Integer.valueOf(msg_id));
                for (Pane x : v0) {
                    x.getStyleClass().set(0, "variant_pane");
                }
                v0[Integer.valueOf(msg_id)].getStyleClass().set(0, "clicked_pane");

            });

            pan_results.getChildren().add(v0[i]);
            pan_results.setPrefHeight(v0h + graf.getV() + 5);
        }
        // ds.getOptVariant(variants);
        opt_var = optimal_variant;
        i_opt_var = list.indexOf(optimal_variant);
        v0[list.indexOf(optimal_variant)].getStyleClass().set(0,"clicked_pane");
        drawSelected(optimal_variant,list.indexOf(optimal_variant));
    }

    @FXML
    public TextField text_loadX1, text_loadX2, text_loadX3;

    private void drawSelected(Variant variant, int i){
        if (selectedPane!=null)
            pan_var.getChildren().remove(selectedPane);
        selectedPane = new Pane();
        selectedPane.setLayoutX(10);
        selectedPane.setLayoutY(40);
        selectedPane.setId("selectedPane");
        selectedPane.getStyleClass().add("selected_pane");
        selectedPane.setPrefSize(500,100);
        graf.drawing(selectedPane, variant, ("Вариант " + String.valueOf(i+1)), 40);
        pan_var.getChildren().add(selectedPane);
        double[] loads = ds.loadsLoadedTransport(variant);
        setLabels(loads);
        double[] nedogruz = ds.getNedogruz(variant);
//        System.out.println(variant.getVariantCT());
//        System.out.println("x1: " + nedogruz[0] + "%  x2: " + nedogruz[1] + "%  x3: " + nedogruz[2] + "%");
//        System.out.println("");
    }

    private void drawRep(Variant variant, int i){
        Pane reportPane = new Pane();
        reportPane.setLayoutX(50);
        reportPane.setLayoutY(90);
        reportPane.setId("selectedPane");
        reportPane.getStyleClass().add("report_pane");
        reportPane.setPrefSize(500,100);
        graf.drawing(reportPane, variant, ("Вариант " + String.valueOf(i+1)), 40);


//        pan_var.getChildren().add(reportPane);
        double[] loads = ds.loadsLoadedTransport(variant);
        setLabels(loads);
//        System.out.println(variant.getVariantCT());
//        System.out.println("x1: " + nedogruz[0] + "%  x2: " + nedogruz[1] + "%  x3: " + nedogruz[2] + "%");
//        System.out.println("");
        pwController.drawRep(reportPane);

    }


    @FXML
    public void menu(ActionEvent event){
        String eventSource =  ((Button)event.getSource()).getId();
        switch (eventSource) {
            case ("new"):
                clearProduction();
                clearTrailer();
                clearTruck();
                pan_transport.setVisible(false);
                break;
            case ("print"):
//                panelReport(ActionEvent.ACTION ev);
                break;
            case ("exit"):
                System.out.println("exiting....");
                System.exit(0);
                break;
        }
    }

    private void setLabels(double[] loads){
//        loads[0] = Math.random() * 10000 ;
        graf.setLabels(loads);
    }

//    public void menu_mouse_in (MouseEvent event){
//        if (event == MouseEvent.MOUSE_ENTERED){
////
//        }
//    }

}
