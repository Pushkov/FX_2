package window;

import calculate.Production;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import varianty.Variant;

import java.util.ArrayList;
import java.util.Random;

public class Graf {
//    Pane parent;

    ArrayList<Color> cArr;
    double osnova;
    double  cab_l = 1500,
            cab_w = 2500,
            pp_l = 13620,
            g_l = 1080,
            g_w = 820;
    double parent_heigth;
    double parent_wigth;

    TextField text_loadX1;
    TextField text_loadX2;
    TextField text_loadX3;
//    TextField text_loadX1 = new TextField();
//    TextField text_loadX2 = new TextField();
//    TextField text_loadX3 = new TextField();

    public void drawing(Pane parent, Variant variant, String zag, double maschtab){

        if (cArr == null) cArr = new ArrayList<>();
        cArr.add(randColor());
        Color cR = cArr.get(0);
        parent.setPrefSize  ((15 + cab_l/maschtab + pp_l/maschtab + 15),((cab_w/maschtab)+20)+20) ;
        osnova =   (cab_w/maschtab)+20;
        Label zagolovok = new Label(zag);
        zagolovok.getStyleClass().add("zagolovok");
        zagolovok.setLayoutX(15);
        zagolovok.setLayoutY(2);

        parent.getChildren().add(zagolovok);

        Rectangle cab = new Rectangle(cab_l/maschtab,cab_w/maschtab);
        cab.setX(10);
        cab.setY(30);
        cab.setFill(Color.DODGERBLUE);
        cab.setStroke(Color.BLACK);
        parent.getChildren().add(cab);
        Rectangle pp = new Rectangle(pp_l/maschtab,cab_w/maschtab);
        pp.setX(10 + cab.getWidth());
        pp.setY(cab.getY());
        pp.setFill(Color.BEIGE);
        pp.setStroke(Color.BLACK);
        parent.getChildren().add(pp);
        parent.setStyle("");
        Production[][] raspred = variant.getRaspredProduction();
        Rectangle [][] gr = new Rectangle[raspred.length][];
        String old_name = "";
        int col = 0;
        for (int i = 0; i<raspred.length; i++){
            gr[i] = new Rectangle[raspred[i].length] ;
            for (int r = 0; r < raspred[i].length; r++) {
                String cur_name = raspred[i][r].getProductionHash().get("production_name").toString();

                if (!cur_name.equals(old_name)){
                    col++;
                    cArr.add(randColor());
                    cR = cArr.get(col);

                    old_name = cur_name;
                }
                gr[i][r]= new Rectangle(g_l/maschtab, g_w/maschtab);
                gr[i][r].setFill(cR);
                gr[i][r].setStroke(Color.BLACK);
                gr[i][r].setX ( pp.getX() + gr[i][r].getWidth() * i );
                gr[i][r].setY( pp.getY() + pp.getHeight() / 2 - gr[i][r].getHeight()*(raspred[i].length-r)  + (gr[i][r].getHeight()*raspred[i].length/2));
                parent.getChildren().add(gr[i][r]);
                parent.setStyle("");




                if (parent.getId() == "selectedPane") {
                     text_loadX1 = new TextField();
                     text_loadX2 = new TextField();
                     text_loadX3 = new TextField();

                    TextField label_loadX1 = new TextField();
                    TextField label_loadX2 = new TextField();
                    TextField label_loadX3 = new TextField();
                    label_loadX1.getStyleClass().add("loads_base_textfield");
                    label_loadX2.getStyleClass().add("loads_base_textfield");
                    label_loadX3.getStyleClass().add("loads_base_textfield");


                    text_loadX1.getStyleClass().add("loads_upper_textfield");
                    text_loadX2.getStyleClass().add("loads_upper_textfield");
                    text_loadX3.getStyleClass().add("loads_upper_textfield");

                    parent_wigth = parent.getPrefWidth();
                    parent_heigth = parent.getPrefHeight();
                    double h = 15;
                    double w = parent_wigth/7+15;
                    label_loadX1.setLayoutX(parent_wigth / 7 - 20);
                    label_loadX2.setLayoutX(parent_wigth * 3 / 7 - 20);
                    label_loadX3.setLayoutX(parent_wigth * 5 / 7 - 20);
                    label_loadX1.setLayoutY(parent_heigth - h/2);
                    label_loadX2.setLayoutY(parent_heigth - h/2);
                    label_loadX3.setLayoutY(parent_heigth - h/2);
                    label_loadX1.setPrefHeight(h);
                    label_loadX2.setPrefHeight(h);
                    label_loadX3.setPrefHeight(h);
                    label_loadX1.setPrefWidth(parent_wigth/7 + 25);
                    label_loadX2.setPrefWidth(parent_wigth/7 + 25);
                    label_loadX3.setPrefWidth(parent_wigth/7 + 25);



                    text_loadX1.setLayoutX(parent_wigth / 7);
                    text_loadX2.setLayoutX(parent_wigth * 3 / 7);
                    text_loadX3.setLayoutX(parent_wigth * 5 / 7);
                    text_loadX1.setLayoutY(parent_heigth - h/2);
                    text_loadX2.setLayoutY(parent_heigth - h/2);
                    text_loadX3.setLayoutY(parent_heigth - h/2);
                    text_loadX1.setPrefHeight(h);
                    text_loadX2.setPrefHeight(h);
                    text_loadX3.setPrefHeight(h);
                    text_loadX1.setPrefWidth(w);
                    text_loadX2.setPrefWidth(w);
                    text_loadX3.setPrefWidth(w);

                    text_loadX1.setText("testX1");
                    text_loadX2.setText("testX2");
                    text_loadX3.setText("testX3");
//                    parent.getChildren().add(label_loadX1);
//                    parent.getChildren().add(label_loadX2);
//                    parent.getChildren().add(label_loadX3);
                    parent.getChildren().add(text_loadX1);
                    parent.getChildren().add(text_loadX2);
                    parent.getChildren().add(text_loadX3);
                }
            }
        }
    }

    public void setLabels(double[] loads){
        text_loadX1.setText(String.valueOf(loads[0]));
        text_loadX2.setText(String.valueOf(loads[1]));
        text_loadX3.setText(String.valueOf(loads[2]));
    }

    public double getV(){
        return osnova + 30;
    }

    public Color randColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat() / 2f;
        float b = rand.nextFloat() / 2f;

        Color randomColor = new Color(r,g,b,1);

        return randomColor;
    }

}

