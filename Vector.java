public class Vector {
    private int size;
    private double data[];
    Vector(int size)
    {
        this.size=size;
        data= new double [size];
    }
    public void setData(int position, double value)
    {
        data[position]=value;
    }
    public void addData(int position, double value)
    {
        data[position]+=value;
    }
    public double getData(int position){
        return data[position];
    }
    public void clear(){
        for(int i=0;i<size;i++)
        {
            data[i]=0;
        }
    }
    public int getSize(){
        return size;
    }
    public void show(){
        System.out.print("[ ");
        for(int i=0;i<size;i++)
        {
            System.out.print(data[i]+"  ");
        }
        System.out.print(" ]\n\n");
    }
}
