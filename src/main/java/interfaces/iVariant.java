package interfaces;


import calculate.Production;
import varianty.Variant;

import java.util.ArrayList;

public interface iVariant {

    Variant create(Production[][] raspredProduction);
    double getVariantMass();
    double getVariantLength();
    double getVariantCT();
    ArrayList<Production> getInVariantProduction();
    byte[] getInVariantQtyProduction();
    Production[][] getRaspredProduction();

}
