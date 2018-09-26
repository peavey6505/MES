public class SquareMatrix {
    private int size;
    private double data[][];
    SquareMatrix(int size){
        this.size=size;
        data= new double[size][size];
    }
    public void clear(){
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++){
                data[i][j]=0;
            }
        }
    }
    public void setData(int line,int column, double value)
    {
        data[line][column]=value;
    }
    public void addData(int line,int column, double value)
    {
        data[line][column]+=value;
    }
    public double getData(int line,int column){
        return data[line][column];
    }
    public int getSize(){
        return size;
    }
    public void show(){
        for(int i=0;i<size;i++) {
            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                System.out.print(data[i][j] + "  ");
            }
            System.out.print(" |\n");
        }
        System.out.print("\n");
    }
}