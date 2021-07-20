package calculate;

import java.util.HashMap;
import java.util.Map;

public class Trailer {

    private String trailer_model = "";
    private double loadPP_X0 = 0;
    private double loadPP_SEDLO = 0;
    private double loadPP_TEL = 0;
    private double distPP_OSI = 0;
    private double distPP_BORT = 0;
    private double distPP_POGRUZ_L = 0;
    private double distPP_POGRUZ_W = 0;
    private static HashMap<String , Object> trailerHash = new HashMap<>();

    private static Trailer trailer;

    public static synchronized Trailer getInstance() {
        if (trailer == null)
            trailer = new Trailer();
        return trailer;
    }

    private Trailer() {
        trailerHash.put("trailer_model","");
        trailerHash.put("loadPP_X0",(double) 0);
        trailerHash.put("loadPP_SEDLO",(double) 0);
        trailerHash.put("loadPP_TEL",(double) 0);
        trailerHash.put("distPP_OSI",(double) 0);
        trailerHash.put("distPP_BORT",(double) 0);
        trailerHash.put("distPP_POGRUZ_L",(double) 0);
        trailerHash.put("distPP_POGRUZ_W",(double) 0);

    }

    private static void setHashItem(String key, Object value){
        trailer.trailerHash.put(key, value);
    }

    public static Object getHashItem(String key){
        return trailerHash.get(key);
    }

    public static HashMap getHash(){
        return trailerHash;
    }
    public static String[] getHashKeys(){
        return trailerHash.keySet().toArray(new String[0]);
    }
    public static void setTransport(HashMap<String,Object>  truckHash) {
        trailer.trailer_model = (String) truckHash.get("trailer_model");
        trailer.loadPP_X0 = (double) truckHash.get("loadPP_X0");
        trailer.loadPP_SEDLO = (double) truckHash.get("loadPP_SEDLO");
        trailer.loadPP_TEL = (double) truckHash.get("loadPP_TEL");
        trailer.distPP_OSI = (double) truckHash.get("distPP_OSI");
        trailer.distPP_BORT = (double) truckHash.get("distPP_BORT");
        trailer.distPP_POGRUZ_L = (double) truckHash.get("distPP_POGRUZ_L");
        trailer.distPP_POGRUZ_W = (double) truckHash.get("distPP_POGRUZ_W");
        for (Map.Entry<String,Object> x: truckHash.entrySet()){
            setHashItem(x.getKey(),x.getValue());
        }
    }

    public static String getTransport_NAME() { return trailer.trailer_model;}

    public static double getTransport_loadPP_X0() { return trailer.loadPP_X0; }

    public static double getTransport_loadPP_SEDLO() { return trailer.loadPP_SEDLO; }

    public static double getTransport_loadPP_TEL() { return trailer.loadPP_TEL; }

    public static double getTransport_distPP_OSI() { return trailer.distPP_OSI; }

    public static double getTransport_distPP_BORT() { return trailer.distPP_BORT; }

    public static double getTransport_distPP_POGRUZ_L() { return trailer.distPP_POGRUZ_L; }

    public static double getTransport_distPP_POGRUZ_W() { return trailer.distPP_POGRUZ_W; }

    public static boolean existTransport (){
        boolean ret;
        if(trailer.trailer_model == "" |
                trailer.loadPP_X0 == 0 |
                trailer.loadPP_SEDLO == 0 |
                trailer.loadPP_TEL == 0 |
                trailer.distPP_OSI== 0 |
                trailer.distPP_BORT== 0 |
                trailer.distPP_POGRUZ_L ==0 |
                trailer.distPP_POGRUZ_W == 0){
            ret = false ;
//            System.out.println("Не задан полуприцеп, либо у полуприцепа заданы не все параметры !!!");
        }
        else ret = true;
        return ret;
    }

    public static void clearTransport () {
        trailer.trailer_model = "";
        trailer.loadPP_X0 = 0;
        trailer.loadPP_SEDLO = 0;
        trailer.loadPP_TEL = 0;
        trailer.distPP_OSI = 0;
        trailer.distPP_BORT = 0;
        trailer.distPP_POGRUZ_L = 0;
        trailer.distPP_POGRUZ_W = 0;
        trailerHash.put("trailer_model","");
        trailerHash.put("loadPP_X0",(double) 0);
        trailerHash.put("loadPP_SEDLO",(double) 0);
        trailerHash.put("loadPP_TEL",(double) 0);
        trailerHash.put("distPP_OSI",(double) 0);
        trailerHash.put("distPP_BORT",(double) 0);
        trailerHash.put("distPP_POGRUZ_L",(double) 0);
        trailerHash.put("distPP_POGRUZ_W",(double) 0);
    }

}

