package varianty;

import calculate.GenerateVariants;
import calculate.Production;
import calculate.ProductionList;

import java.util.ArrayList;

public class VariantCreator {

    private ProductionList productionList;
    private Variant masterVariant = new Variant();
    private Production[][][] united;
    private ArrayList<Variant> varList;

    public void setProductionList(ProductionList productionList){
        this.productionList = productionList;
        setByteList();
        setVarList();
    }

    public ArrayList<Variant> getVarList() {
        return varList;
    }

    private void setByteList(){
        Production[][][] plus;

        ArrayList<Production> pList = productionList.getProdList();
        ArrayList<Byte> pKol = productionList.getKolList();
        Production[][] raspredList;
        for (Production x : pList){
            int id = pList.indexOf(x);
            ArrayList<byte[]> tmp = generateByte(pKol.get(id), (byte) 2);
            if (id==0){
                united = productionInArray(tmp,x);
            } else {
                plus = productionInArray(tmp,x);
                united = union(united,plus);
            }
        }
    }

    private void setVarList(){
        varList = new ArrayList<>();
        for (Production[][] x : united){
            varList.add(createVariant(x));
        }
    }

    private Production[][][] productionInArray(ArrayList<byte[]> list, Production production){

        Production[][][] tmp = new Production[list.size()][][];
        for (int x1=0; x1<list.size(); x1++){
            Production[][] p1 = new Production[list.get(x1).length][];
            for( int x2=0; x2<(list.get(x1)).length;x2++){
                int y = (int) (list.get(x1))[x2];
                Production[] p = new Production[ y];
                for (int x3=0; x3<y; x3++){
                    p[x3] = production;
                }
                p1[x2] = p;
            }
            tmp[x1]=p1;
        }
        return tmp;
    }

    private Production[][][] union(Production[][][] first, Production[][][] second){
        Production[][][] united = new Production[(int) (first.length * second.length)][][];
        int id = 0;
        for( int i=0; i<first.length;i++){
            Production[][] m0;
            Production[][] m1 = first[i];
            int m1Len = m1.length;
            for(int j=0; j<second.length;j++){
                Production[][] m2 = second[j];
                int m2Len = m2.length;
                m0=new Production[m1Len+m2Len][];
                System.arraycopy(m1,0,m0,0,m1Len);
                System.arraycopy(m2,0,m0,m1Len,m2Len);
                united[id] = m0;
                id++;
            }
        }
        return united;
    }

    public void clearVarList(){
        varList.clear();
    }

    private ArrayList<byte[]> generateByte(byte kol, byte inRow){
        GenerateVariants gv = new GenerateVariants();
        return gv.genVariants(kol,inRow);
    }

    private Variant createVariant(Production[][] raspredProduction){
        return masterVariant.create(raspredProduction);
    }


}
