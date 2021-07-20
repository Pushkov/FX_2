package calculate;

import java.util.HashMap;
import java.util.Map;

public class Truck {

    private String truck_model = "";
    private double loadT_X0 = 0;
    private double loadT_X1 = 0;
    private double loadT_X2 = 0;
    private double distT_OSI = 0;
    private double distT_SEDLO = 0;
    private static HashMap<String , Object> truckHash = new HashMap<>();

    private static Truck truck = null;

    public static synchronized Truck getInstance() {
        if (truck==null)
            truck = new Truck();
        return truck;
    }

    private Truck() {
        truckHash.put("truck_model","");
        truckHash.put("loadT_X0",(double) 0);
        truckHash.put("loadT_X1",(double) 0);
        truckHash.put("loadT_X2",(double) 0);
        truckHash.put("distT_OSI",(double) 0);
        truckHash.put("distT_SEDLO",(double) 0);
    }

    private static void setHashItem(String key, Object value){
        truck.truckHash.put(key, value);
    }

    public static Object getHashItem(String key){
        return truckHash.get(key);
    }

    public static HashMap getHash(){
        return truckHash;
    }

    public static String[] getHashKeys(){
        return truckHash.keySet().toArray(new String[0]);
    }

    public static void setTransport(HashMap<String,Object>  truckHash) {
        truck.truck_model = (String) truckHash.get("truck_model");
        truck.loadT_X0 = (double) truckHash.get("loadT_X0");
        truck.loadT_X1 = (double) truckHash.get("loadT_X1");
        truck.loadT_X2 = (double) truckHash.get("loadT_X2");
        truck.distT_OSI = (double) truckHash.get("distT_OSI");
        truck.distT_SEDLO = (double) truckHash.get("distT_SEDLO");
        for (Map.Entry<String,Object> x: truckHash.entrySet()){
            setHashItem(x.getKey(),x.getValue());
        }
    }

    public static String getTransport_NAME() { return truck.truck_model; }

    public static double getTransport_loadT_X0() { return truck.loadT_X0; }

    public static double getTransport_loadT_X1() { return truck.loadT_X1; }

    public static double getTransport_loadT_X2() { return truck.loadT_X2; }

    public static double getTransport_distT_OSI() { return truck.distT_OSI; }

    public static double getTransport_distT_SEDLO() { return truck.distT_SEDLO; }

    public static boolean existTransport(){
        boolean ret;
        if(truck.truck_model == "" |
                truck.loadT_X0 == 0 |
                truck.loadT_X1 == 0 |
                truck.loadT_X2 == 0 |
                truck.distT_OSI == 0 |
                truck.distT_SEDLO == 0){
            ret = false ;
        }
        else ret = true;
        return ret;
    }

    public static void clearTransport() {
        truck.truck_model = "";
        truck.loadT_X0 = 0;
        truck.loadT_X1 = 0;
        truck.loadT_X2 = 0;
        truck.distT_OSI = 0;
        truck.distT_SEDLO = 0;
        truckHash.put("truck_model","");
        truckHash.put("loadT_X0",0);
        truckHash.put("loadT_X1",0);
        truckHash.put("loadT_X2",0);
        truckHash.put("distT_OSI",0);
        truckHash.put("distT_SEDLO",0);
    }
}

