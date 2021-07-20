package db;

import calculate.*;
import varianty.Variant;
import varianty.VariantCreator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataController {
    DataDB db;
    Calculate calc = new Calculate();
    Production masterProduction = new Production();
    ProductionList productionList = new ProductionList();
    ArrayList<Production> prodList =new ArrayList<>();
    private ArrayList<Variant> variantArrayList;

    private VariantCreator varCreator = new VariantCreator();

    public static boolean calcDataChanges = true;

    public ArrayList<String> startData(String transport, String type_or_model){
        calcDataChanges = true;
        boolean unique = false;
        if (type_or_model.equals("type")) unique=true;
        type_or_model = transport + "_" + type_or_model;
        ArrayList<String> ar= null;
        try {
            db = DataDB.getInstance();
            ar = db.DB_Read(transport, type_or_model, unique);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка базы данных StartData");
        }
        return ar;
    }

    public ArrayList<String> selectType(String transport, String selectedType){
        calcDataChanges = true;
        ArrayList<String> ar = null;
        String type = transport + "_type";
        String model = transport + "_model";
        try {
            db = DataDB.getInstance();
            ar= db.DB_Read(transport, model, type, selectedType);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка базы данных ModelByType");
        }
        return ar;
    }

    public void selectModel(String transport, String selectedModel){
        calcDataChanges = true;
        String where = transport + "_model";
        String where_who = selectedModel;
        try {
            db = DataDB.getInstance();
            if (transport.equals("truck")) {
                Truck.setTransport(db.DB_Read(transport, Truck.getHash(), where, where_who));
            }
            if (transport.equals("trailer")) {
                Trailer.setTransport(db.DB_Read(transport, Trailer.getHash(), where, where_who));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка базы данных ModelByType");
            if (transport.equals("truck")) {
                Truck.clearTransport();
            }
            if (transport.equals("trailer")) {
                Trailer.clearTransport();
            }
        }
    }

    public double [] loadsEmptyTransport(){
        double[] loads = {0,0,0};
        if (Truck.existTransport() & !Trailer.existTransport()){
            loads[0] = Truck.getTransport_loadT_X1();
            loads[1] = Truck.getTransport_loadT_X2();
        }
        if (!Truck.existTransport() & Trailer.existTransport()){
            loads[2] = Trailer.getTransport_loadPP_TEL();
        }
        if (Truck.existTransport() & Trailer.existTransport()){
            loads[0] = calc.getEmptyAP_X1();
            loads[1] = calc.getEmptyAP_X2();
            loads[2] = Trailer.getTransport_loadPP_TEL();
        }
        return loads;
    }

    public double [] loadsLoadedTransport(Variant variant){
        double[] loads = {0,0,0};
            loads[0] = calc.getLoadedAP_X1(variant);
            loads[1] = calc.getLoadedAP_X2(variant);
            loads[2] = calc.getLoadedAP_X3(variant);
        return loads;
    }

    public Variant getOptVariant (Variant optim, Variant variant){

        Variant check = optim;
        double dX = Math.abs(calc.getNedogruz_X2(optim) - calc.getNedogruz_X3(optim));
                if ((calc.getNedogruz_X2(variant) - calc.getNedogruz_X3(variant)) >= 0 & Math.abs(calc.getNedogruz_X2(variant) - calc.getNedogruz_X3(variant)) < dX) {
                check = variant;
                } else   if ((calc.getNedogruz_X2(variant) - calc.getNedogruz_X3(variant)) < 0 & Math.abs(calc.getNedogruz_X2(variant) - calc.getNedogruz_X3(variant)) < dX) {
                    check = variant;
            }

        return check;
    }

    public double[] getNedogruz(Variant variant){

        double[] nedogruz = {0,0,0};
        nedogruz[0] = calc.getNedogruz_X1(variant);
        nedogruz[1] = calc.getNedogruz_X2(variant);
        nedogruz[2] = calc.getNedogruz_X3(variant);
        return nedogruz;
    }




    public ArrayList<String> setProductionType(String prod_type ){
        calcDataChanges = true;

        ArrayList<String> prod = null;
        try {
            db = DataDB.getInstance();
            prod= db.DB_Read("production", "production_name", "production_type", prod_type);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка базы данных Production_type");
        }
        return prod;
    }

    public void addProduction(String prodName, byte kol){
        calcDataChanges = true;

        HashMap<String,Object> tmp = null;
        try{
            db = DataDB.getInstance();
            tmp = db.DB_Read("production", masterProduction.getProductionHash(),"production_name", prodName);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка базы данных Production_name");
        }
        masterProduction.setProductionHash(tmp);
        ProductionCreator productionCreator = new ProductionCreator(masterProduction);
        productionList.add(productionCreator.createProduction(),kol,(byte)0);
    }

    public ArrayList<Variant> inLimits(ArrayList<Variant> list){
        ArrayList<Variant> lim = new ArrayList<>();
        calc.setLimitX1(8000);
        calc.setLimitX2(10500);
        calc.setLimitX3(22500);
        for (Variant x : list) {
            if ((x.getVariantLength()) < Trailer.getTransport_distPP_POGRUZ_L() &
                    calc.getLoadedAP_X3(x) < calc.getLimitX3() &
                    calc.getLoadedAP_X2(x) < calc.getLimitX2() &
                    calc.getLoadedAP_X1(x) < calc.getLimitX1()) {
                lim.add(x);
            }
        }
        return lim;
    }

    public void createVariants(){
        varCreator.setProductionList(productionList);
        variantArrayList = varCreator.getVarList();
    }

    public ArrayList<Variant> getVariantArrayList() {
        return variantArrayList;
    }

    public ArrayList<Production> getProductionList() {
        return productionList.getProdList();
    }

    public ArrayList<Byte> getKolList() {
        return productionList.getKolList();
    }

    public void clearProduction(){
    if (variantArrayList!=null)       variantArrayList.clear();
     if (productionList!=null)  productionList.clearProdList();
    }

    public boolean existProdList(){
        return productionList.exist();
    }





//
//    //
//    public ArrayList<byte[]> checkCalc (){
//        ArrayList<byte[]> vars = null;
//        if (calcDataChanges){
//            if (Truck.existTransport() & Trailer.existTransport() & prodList != null){
//                calc.setLimitX1(8000);
//                calc.setLimitX2(10500);
//                calc.setLimitX3(22500);
//                for (int i=0; i<prodList.size(); i++){
//                    Production tmp = prodList.get(i);
//                    byte kol_t = (byte) tmp.getKol();
//                }
////                vars = calc.generatedVariants(gr.getKol(),(byte)2);
//                System.out.println("CALCULATE");
//                calcDataChanges=false;
//            }else {
//                System.out.println("No full data for calculations....");
//                String gruz = "false";
//                if (prodList != null) gruz = "true";
//                String alert = "Truck [" + Truck.existTransport() + "], Trailer [" + Trailer.existTransport() + "], Production [" + gruz + "].";
//                System.out.println(alert);
//            }
//
//        }else{
//            System.out.println("No changed data, calculations are not required...");
//        }
//        return vars;
//    }
//

//
//    public double [] loadsLoadedTransport(byte[] current_variant){
//        double[] loads = {  calc.getLoadedAP_X1(current_variant),
//                            calc.getLoadedAP_X2(current_variant),
//                            calc.getLoadedAP_X3(current_variant)};
//        return loads;
//    }



//    public void printArray(Production[][][] array){
//        for (Production[][] z: array) {
//            for (Production[] x : z) {
//                for (Production y : x) {
//                    System.out.print(y.getProductionHash().get("production_name") + "    ");
//                }
//                System.out.println("");
//            }
//            System.out.println("---");
//        }
//        System.out.println("====================");
//    }
//
//
//
//
//
//
//
//
//    public void test (ArrayList<Production> prodList){
//        System.out.println("=====  тыц_ds =====");
//    }
//
//    public void setVariantList(Production[][][] variantList) {
//        this.prodVariantList = variantList;
//    }
//
//    public Production[][][] getProdVariantList() {
//        return prodVariantList;
//    }
}
