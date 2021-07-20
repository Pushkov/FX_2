package calculate;

import java.util.ArrayList;

public class ProductionList {


    private ArrayList<Production> prodList = new ArrayList<>();
    private ArrayList<Byte> kolList = new ArrayList<>();
    private ArrayList<Byte> priorityList = new ArrayList<>();

    private boolean checkPriority(byte priority){
        boolean checked = false;
        if (priorityList.size()==0) checked=true;
        else if (samePriority(priority)){
            checked = true;
        }
    return checked;
    }
    public boolean exist(){
        boolean ex = false;
        if (prodList.size()!=0) ex = true;

        return ex;
    }

    public ArrayList<Production> getProdList() {
        return prodList;
    }

    public ArrayList<Byte> getKolList() {
        return kolList;
    }

    public ArrayList<Byte> getPriorityList() {
        return priorityList;
    }

    private boolean samePriority(byte priority){
        boolean same = false;
        boolean check = true;
        for (Byte x : priorityList){
            if (priority!=x) check = false;
        }
        if (check) same=true;
        return same;
    }

    public void add(Production production, byte kol, byte priority){
        if (priorityList==null){
            prodList = new ArrayList<>();
            kolList = new ArrayList<>();
            priorityList = new ArrayList<>();
        }

        if (checkPriority(priority)){
                prodList.add(production);
                kolList.add(kol);
                priorityList.add(priority);

        } else {
            prodList.add(production);
            kolList.add(kol);
            priorityList.add(priority);
        }
    }

    public void clearProdList(){
        prodList.clear();
        kolList.clear();
        priorityList.clear();
    }

}
