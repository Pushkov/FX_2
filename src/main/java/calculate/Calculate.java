package calculate;

import varianty.Variant;

import java.util.ArrayList;
import java.util.HashMap;

public class Calculate {

    private double limitX1;
    private double limitX2;
    private double limitX3;

    HashMap<String,Object> gruz;
    byte kol;

    public void setGruz(HashMap<String, Object> gruz, byte kol) {
        this.gruz = gruz;
        this.kol = kol;
    }

    public void calc(){
        if (Truck.existTransport() & Trailer.existTransport()){
            System.out.println("Ok");

        } else System.out.println("Bad");
    }



//    public ArrayList<byte[]> generatedVariants (byte kol, byte inRow){             // Пока только для однотипного одинаково расположенного груза
//        GenerateVariants gV = new GenerateVariants();
//        ArrayList<byte []> suitableG = new ArrayList<>();
////        int vc = 0;
//        for (int i = 0; i < gV.genVariants(kol,inRow).size(); i++) {
//            byte[] current_variant = (byte[]) gV.genVariants(kol,inRow).get(i);
//            if (getLoadedAP_X1(current_variant) < getLimitX1() &
//                    getLoadedAP_X2(current_variant) < getLimitX2() &
//                    getLoadedAP_X3(current_variant) < getLimitX3() &
//                    getGruz_VarLength(current_variant) < Trailer.getTransport_distPP_POGRUZ_L()){
//                suitableG.add(current_variant);

//                System.out.print(getLoadedAP_X1(current_variant) + "  **  ");
//                System.out.print(getLoadedAP_X2(current_variant) + "  **  ");
//                System.out.print(getLoadedAP_X3(current_variant));
//                System.out.println("");
//            }
//        }
//        return suitableG;
//    }




    public ArrayList<byte[]> genTest (byte kol, byte inRow){             // Пока только для однотипного одинаково расположенного груза
        GenerateVariants gV = new GenerateVariants();
        ArrayList<byte []> suitableG = new ArrayList<>();
//        int vc = 0;
        for (int i = 0; i < gV.genVariants(kol,inRow).size(); i++) {
            byte[] current_variant = (byte[]) gV.genVariants(kol,inRow).get(i);

                suitableG.add(current_variant);

//                System.out.print(getLoadedAP_X1(current_variant) + "  **  ");
//                System.out.print(getLoadedAP_X2(current_variant) + "  **  ");
//                System.out.print(getLoadedAP_X3(current_variant));
//                System.out.println("");
        }
        return suitableG;
    }





//    public ArrayList<byte[]> seqVariants(ArrayList<byte[]> arrayList){
//
//        ArrayList<byte []> suitableG = new ArrayList<>();
//
//        for (byte[] x: arrayList) {
//            if (getGruz_VarLength(x) < Trailer.getTransport_distPP_POGRUZ_L() &
//                    getLoadedAP_X3(x) < getLimitX3() &
//                    getLoadedAP_X2(x) < getLimitX2() &
//                    getLoadedAP_X1(x) < getLimitX1() ) {
//                suitableG.add(x);
//            }
//        }
//            return suitableG;
//        }




    public double getLimitX1() {
        return limitX1;
    }

    public void setLimitX1(double limitX1) {
        this.limitX1 = limitX1;
    }

    public double getLimitX2() {
        return limitX2;
    }

    public void setLimitX2(double limitX2) {
        this.limitX2 = limitX2;
    }

    public double getLimitX3() {
        return limitX3;
    }

    public void setLimitX3(double limitX3) {
        this.limitX3 = limitX3;
    }


//    public double getGruz_FullMass(){ // Пока для одиночного груза будет так
//        return Math.round((double)gruz.get("massG") * kol);
//    }

    public double getGruz_VarLength(byte[] current_variant){
        double L=0;
        for (byte x: current_variant){
            if (x !=0) L += (double)gruz.get("lengthG");
        }
        return L;
    }

//    public double getGruzCT_var1(byte[] current_variant) {
//        double dist_ct_row;
//        double moment_row = 0;
//        for (int i = 0; i < current_variant.length; i++) {
//            dist_ct_row = (double)gruz.get("lengthG") * i + (double)gruz.get("lengthG") / 2;
//            moment_row = moment_row + (dist_ct_row * (double)gruz.get("massG") * current_variant[i]);
//        }
//        return Math.round(moment_row / getGruz_FullMass());
//    }

    public double getEmptyAP_X1() {
        return Math.round(Truck.getTransport_loadT_X1() + (Trailer.getTransport_loadPP_SEDLO() * Truck.getTransport_distT_SEDLO() / Truck.getTransport_distT_OSI()));
    }

    public double getEmptyAP_X2() {
        return Math.round(Truck.getTransport_loadT_X2() + (Trailer.getTransport_loadPP_SEDLO() * (Truck.getTransport_distT_OSI() - Truck.getTransport_distT_SEDLO()) / Truck.getTransport_distT_OSI()));
    }

    public double getEmptyAP_X3() {
        return Math.round(Trailer.getTransport_loadPP_TEL());
    }

    public double getLoadedAP_X1(Variant variant) {
        double distG_BORT = 0;
        double distG_CT_BORT = distG_BORT + variant.getVariantCT() - Trailer.getTransport_distPP_BORT(); //фактическое положение центра тяжести груза на полуприцепе
        double loadG_SEDLO = variant.getVariantMass()*(Trailer.getTransport_distPP_OSI()-distG_CT_BORT)/Trailer.getTransport_distPP_OSI();// нагрузка от груза на седельное устройство
        return Math.round(Truck.getTransport_loadT_X1() + ((Trailer.getTransport_loadPP_SEDLO() + loadG_SEDLO) * Truck.getTransport_distT_SEDLO() / Truck.getTransport_distT_OSI()));
    }

    public double getLoadedAP_X2(Variant variant) {
        double distG_BORT = 0;
        double distG_CT_BORT = distG_BORT + variant.getVariantCT() - Trailer.getTransport_distPP_BORT(); //фактическое положение центра тяжести груза на полуприцепе
        double loadG_SEDLO = Math.round( variant.getVariantMass()*(Trailer.getTransport_distPP_OSI()-distG_CT_BORT)/Trailer.getTransport_distPP_OSI());// нагрузка от груза на седельное устройство
        return Math.round(Truck.getTransport_loadT_X2() + ((Trailer.getTransport_loadPP_SEDLO() + loadG_SEDLO) * (Truck.getTransport_distT_OSI() - Truck.getTransport_distT_SEDLO()) / Truck.getTransport_distT_OSI()));
    }

    public double getLoadedAP_X3(Variant variant) {
        double distG_BORT = 0;
        double distG_CT_BORT = distG_BORT + variant.getVariantCT() - Trailer.getTransport_distPP_BORT(); //фактическое положение центра тяжести груза на полуприцепе
        double loadG_TEL =Math.round(variant.getVariantMass() * (distG_CT_BORT / Trailer.getTransport_distPP_OSI()));// нагрузка от груза на седельное устройство
        return Math.round(Trailer.getTransport_loadPP_TEL() + loadG_TEL);
    }


//    double getGruzCT_optimal (byte[] current_variant){
//        double koef = getLimitX2()/getLimitX3();
//        double q1 = getEmptyAP_X2() + getGruz_FullMass()*((double)1-(Truck.getTransport_distT_SEDLO() / Truck.getTransport_distT_OSI())) - Trailer.getTransport_loadPP_TEL() * koef;
//        double q2 = (getGruz_FullMass() / Trailer.getTransport_distPP_OSI())*(koef+1)
//                - (getGruz_FullMass() * Truck.getTransport_distT_SEDLO()) /(Trailer.getTransport_distPP_OSI() * Truck.getTransport_distT_OSI());
//        double distCT_bort = q1/q2;
////        System.out.println("* " + distCT_bort + " q1 = " + q1 + " q2 = " + q2 );
//        return distCT_bort + Trailer.getTransport_distPP_BORT() - getGruzCT_var1(current_variant);
//    }

    public double getNedogruz_X1 (Variant current_variant){
        return Math.round((1 - getLoadedAP_X1(current_variant) / limitX1)*100);
    }

    public double getNedogruz_X2 (Variant current_variant){
        return Math.round((1 - getLoadedAP_X2(current_variant) / limitX2)*100);
    }

    public double getNedogruz_X3 (Variant current_variant){
        return Math.round((1 - getLoadedAP_X3(current_variant) / limitX3)*100);
    }
}
