package calculate;

public class ProductionCreator {
    Production production;

    public ProductionCreator(Production production) {
        this.production = production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Production createProduction(){
        return production.create();
    }
}
