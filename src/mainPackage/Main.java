package mainPackage;

import java.util.ArrayList;

public class Main
{
    public static ArrayList<Double> parellelTimeList    = new ArrayList<Double>();
    public static ArrayList<Double> sequentialTimeList  = new ArrayList<Double>();   
    public static ArrayList         dimensionList       = new ArrayList();
    
    private native double nativeParallelMatrixMultiplikation(int n, int m);
    private native double nativeSequentialMatrixMultiplikation(int n, int m);
    
    static
    {
        System.load("/home/eugenej/NetBeansProjects/OMPMatrixMultiplication/c-files/JNIDLforLabThree/dist/libJNIDLforLabThree.so");
    }
    
    public static void main(String[] args) 
    {   
        calculate();
        draw();
    }
    
    public static void calculate()
    {
        for(int i=200;i<=1200;i+=200)
        {
            sequentialTimeList.add(new Main().nativeSequentialMatrixMultiplikation(i,i));
            parellelTimeList.add(new Main().nativeParallelMatrixMultiplikation(i,i));
            dimensionList.add(i);
        }
    }
    
    public static void draw()
    {
        DrawGraphic draw = new DrawGraphic("Chobotar_OMPLabThree");
        draw.pack();
        draw.setVisible(true); 
        parellelTimeList.clear();
        sequentialTimeList.clear();
    }
    
}
