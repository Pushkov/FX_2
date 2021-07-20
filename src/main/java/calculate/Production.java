package calculate;

import interfaces.iProduction;

import java.util.HashMap;

public class Production implements iProduction {

    private String name;

    private HashMap<String,Object> productionHash = new HashMap<>();

    public Production() {
        productionHash.put("production_name","");
        productionHash.put("lengthG",(double) 0);
        productionHash.put("wigthG",(double) 0);
        productionHash.put("heigthG",(double) 0);
        productionHash.put("diamG",(double) 0);
        productionHash.put("massG",(double) 0);
    }

    private Production(HashMap<String, Object> productionHash) {
        this.productionHash = productionHash;

        this.name = (String) productionHash.get("production_name");
    }

    public HashMap<String, Object> getProductionHash() {
        return productionHash;
    }

    public String getName() {
        return name;
    }

    public void setProductionHash(HashMap<String, Object> productionHash) {
        this.productionHash = productionHash;
    }

    @Override
    public Production create() {
        Production created = new Production(productionHash);
        return created;
    }
}
