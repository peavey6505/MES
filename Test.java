public class Test {
    public static void main(String args[]){

        testJakobian();
        testCalculateByTime();
    }
    public static void testCalculations(){
        //Testy obliczeń
        Vector v1=new Vector(2);
        Vector v2=new Vector(2);
        v1.setData(0,2);
        v1.setData(1,3);
        v2.setData(0,3);
        v2.setData(1,-4);
        System.out.println("v1:");
        v1.show();
        System.out.println("v2:");
        v2.show();
        SquareMatrix m1= new SquareMatrix(2);
        SquareMatrix m2= new SquareMatrix(2);
        m1.setData(0,0,3);
        m1.setData(0,1,-4);
        m1.setData(1,0,2);
        m1.setData(1,1,3);
        m2.setData(0,0,2);
        m2.setData(0,1,5);
        m2.setData(1,0,1);
        m2.setData(1,1,-2);
        System.out.println("m1:");
        m1.show();
        System.out.println("m2:");
        m2.show();
        Calculation calculation=new Calculation();

        //skalar x wektor
        System.out.println("3 x v1:");
        Vector v1x3=calculation.scalarTimesVector(2,v1);
        v1x3.show();

        //skalar x macierz
        System.out.println("4 x m2:");
        SquareMatrix m2x2=calculation.scalarTimesMatrix(4,m1);
        m2x2.show();

        //dodanie wektorów
        System.out.println("v1 + v2:");
        Vector v1pv2=calculation.addVectors(v1,v2);
        v1pv2.show();

        //dodanie macierzy
        System.out.println("m1 + m2:");
        SquareMatrix m1pm2=calculation.addMatrixes(m1,m2);
        m1pm2.show();

        //macierz x macierz
        System.out.println("m1 x m2:");
        SquareMatrix m1xm2=calculation.matrixTimesMatrix(m1,m2);
        m1xm2.show();

        //wektor x macierz
        System.out.println("v1 x m1:");
        Vector v1xm2=calculation.vectorTimesMatrix(v1,m1);
        v1xm2.show();

        //wektor x wektor
        System.out.println("v1 x v2:");
        SquareMatrix v1xv2=calculation.vectorTimesVectorT(v1,v2);
        v1xv2.show();

    }
    public static void testGrid(){
        Grid grid=new Grid();
        grid.fillGrid();
        grid.drawGrid();
    }
    public static void testJakobian(){
        Grid grid=new Grid();
        grid.fillGrid();
        //grid.drawGrid();
        grid.calculateJakobian();
        grid.showJakobian();
    }
    public static void testCalculateByTime(){
        Grid grid=new Grid();
        grid.fillGrid();
        grid.drawGrid();
        grid.calculateJakobian();
        grid.calculateByTime();
    }
}
