package varianty;

import interfaces.iVariant;
import calculate.Production;

import java.util.ArrayList;

public class Variant  implements iVariant {

    private double variantMass;
    private double variantLength;
    private double variantCT;
    private ArrayList<Production> inVariantProduction;
    private byte[] inVariantQtyProduction;
    private Production[][] raspredProduction;

    public Variant() {
    }

    private Variant(Production[][] raspredProduction) {
        this.raspredProduction = raspredProduction;
        setProdListInVariant();
        setQtyListInVariant();
        setVariantMass();
        setVariantLength();
        setVariantCT();
    }

    @Override
    public Variant create(Production[][] raspredProduction) {
        Variant created = new Variant(raspredProduction);
        return created;
    }

    @Override
    public double getVariantMass() {
        return variantMass;
    }

    @Override
    public double getVariantLength() {
        return variantLength;
    }

    @Override
    public double getVariantCT() {
        return variantCT;
    }

    @Override
    public ArrayList<Production> getInVariantProduction() {
        return inVariantProduction;
    }

    @Override
    public byte[] getInVariantQtyProduction() {
        return inVariantQtyProduction;
    }

    @Override
    public Production[][] getRaspredProduction() {
        return raspredProduction;
    }

    private void setProdListInVariant() {
        this.inVariantProduction = new ArrayList<>();
        for (Production[] x : raspredProduction)
            for (Production p : x) {
                if (!inVariantProduction.contains(p)) {
                    inVariantProduction.add(p);
                }
            }
    }

    private void setQtyListInVariant() {
        inVariantQtyProduction = new byte[inVariantProduction.size()];
        for (Production prod : inVariantProduction) {
            byte p_kol = (byte) 0;
            for (Production[] x : raspredProduction) {
                for (Production p : x) {
                    if (p == prod) p_kol++;
                }
            }
            inVariantQtyProduction[inVariantProduction.indexOf(prod)] = p_kol;
        }
    }

    private void setVariantMass() {
        double mass = 0;
        for (Production[] x : raspredProduction){
            for (Production p : x) {
                mass += (double) p.getProductionHash().get("massG");
            }
        }
        this.variantMass = mass;
    }

    private void setVariantLength(){
        double length = 0;
        for (Production[] x : raspredProduction){
                length += (double) x[0].getProductionHash().get("lengthG");
        }
        this.variantLength = length;
    }

    private void setVariantCT(){
        double ct = 0;
        double moment = 0;
        double start_row = 0;

        for (int i = 0; i< raspredProduction.length; i++){
//            double moment_row = 0;
            double ct_row = 0;
            double mass_row = 0;

            double length_row = (double) raspredProduction[i][0].getProductionHash().get("lengthG");
//            System.out.println("i: " + i + " ; длина ряда: " + length_row + " ; ");
            for (Production p : raspredProduction[i]) {
                mass_row += (double) p.getProductionHash().get("massG");
            }
            ct_row =  start_row + length_row/ (double) 2;
            moment += mass_row * ct_row;
            start_row += length_row;
        }
        ct = Math.round( moment / variantMass);
//        System.out.println(ct);

        this.variantCT =ct;
    }


}



