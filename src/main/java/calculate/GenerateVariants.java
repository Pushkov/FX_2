package calculate;

import java.util.ArrayList;
import java.util.Arrays;

public class GenerateVariants {

    private byte kol;
    private byte inRow = 2;
    byte [] variant;
    ArrayList <byte[]> varList;

    public ArrayList genVariants(byte kol) {
        this.kol = kol;
        varList = new ArrayList<byte[]>();
        generate(kol,inRow);
        return varList;
    }

    public ArrayList genVariants(byte kol,byte inRow) {
        this.inRow = inRow;
        this.kol = kol;
        varList = new ArrayList<byte[]>();
        generate(kol,inRow);
        return varList;
    }

     private ArrayList<byte[]> generate (byte kol,byte inRow) {
        byte sum = 0;
        byte m = inRow;
        byte n = kol;
        variant = new byte [n];
        boolean yes = true;

        do {
            sum=0;
            for (int x:variant) sum+=x;
            if (sum == n) {
//                for (int x:variant) System.out.print(" "+ x);           // Тестовая пропечатка массива
//                System.out.println("");                                 // Тестовая пропечатка массива
                varList.add(array_cut(array_reverse(Arrays.copyOf(variant,variant.length))));
            }
            int i = n-1;
            while (i>=0 && variant[i]==m) {
                variant[i] = 1;
                i--;
            }
            if (i >= 0) {
                variant[i]++;
                yes = true;
            } else {
                yes = false;
            }
        }while (yes==true);
//         System.out.println("Вариантов " + varList.size());
        return varList;
    }

    private byte[] array_cut(byte[] array){
            int z =0;
            for(byte x:array){
                if (x!=0){
                    z++;
                }
            }
            byte[] tmp = new byte[z];
                System.arraycopy(array,0,tmp,0,z);
        return tmp;
    }

    private byte[] array_reverse(byte[] arr){
        for (int i = 0; i < arr.length / 2; i++) {
            byte temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
        return arr;
    }

}
