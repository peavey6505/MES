public class Element {
    private Grid parent;
    private int ownid;
    private int id[];
    private boolean wall[];
    private Vector dNdX[];
    private Vector dNdY[];
    private Vector detJ;
    private SquareMatrix J[];
    private SquareMatrix revJ[];
    public Element(Grid grid,int ownid){
         id= new int[4];
         wall= new boolean[4];
         dNdX=new Vector[4];
         dNdY=new Vector[4];
            for(int i=0;i<4;i++) {
                dNdX[i] = new Vector(4);
                dNdY[i] = new Vector(4);
            }
         detJ=new Vector(4);
         J=new SquareMatrix[4];
         revJ=new SquareMatrix[4];
         for(int i=0;i<4;i++){
             J[i]=new SquareMatrix(2);
             revJ[i]=new SquareMatrix(2);
         }
         this.ownid=ownid;
         parent=grid;
    }
    public void setParent(Grid g){
        this.parent=g;
    }
    public void setNode(int position, int newId){
        this.id[position]=newId;
    }
    public int getNode(int position){
        return id[position];
    }
    public void setWall(int position, boolean newWall){
        this.wall[position]=newWall;
    }
    public boolean getWall(int position){
        return wall[position];
    }
    public Vector getdNdX(int integrationPoint)
    {
        return dNdX[integrationPoint];
    }
    public Vector getdNdY(int integrationPoint)
    {
        return dNdY[integrationPoint];
    }

    public double getJ(int integrationPoint, int line, int collumn){
        return J[integrationPoint].getData(line,collumn);
    }
    public double getRevJ(int integrationPoint, int line, int collumn)
    {
        return revJ[integrationPoint].getData(line,collumn);
    }
    public double getDetJ(int integrationPoint){
        return detJ.getData(integrationPoint);
    }
    public void calculateJ(UniversalElement uE, Node[] nodes){
        for (int i = 0; i < 4; i++) //dla 4 pkt calkowania
        {
            J[i].setData(0,0,0);
            J[i].setData(0,1,0);
            J[i].setData(1,0,0);
            J[i].setData(1,1,0);

            for(int j=0;j<4;j++)      //+dNj/dEta*xj...
            {
                J[i].addData(0,0,uE.getdNdKsi(i,j)*nodes[id[j]].getX());    //i - który punkt, j - ktora funkcja ksztaltu
                J[i].addData(0,1,uE.getdNdEta(i,j)*nodes[id[j]].getX());
                J[i].addData(1,0,uE.getdNdKsi(i,j)*nodes[id[j]].getY());
                J[i].addData(1,1,uE.getdNdEta(i,j)*nodes[id[j]].getY());

            }
            revJ[i].addData(0,0,J[i].getData(1,1));
            revJ[i].addData(0,1,-J[i].getData(0,1));
            revJ[i].addData(1,0,-J[i].getData(1,0));
            revJ[i].addData(1,1,J[i].getData(0,0));
            detJ.setData(i, J[i].getData(0,0) * J[i].getData(1,1) - J[i].getData(0,1) * J[i].getData(1,0));
        }

    }
    public void calculateN(UniversalElement uE){
        Calculation calc=new Calculation();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
            {
                //getI - wektor dNdKsi, dNdEta
                Vector dNxyi=calc.vectorTimesMatrix(uE.getI(i,j),calc.scalarTimesMatrix((1/detJ.getData(i)),revJ[i]));
                dNdX[i].setData(j,dNxyi.getData(0));
                dNdY[i].setData(j,dNxyi.getData(1));
            }

        }

    }

    public void showJ()
    {
        System.out.println("--Element "+ownid+":  revJ");
        for(int i=0;i<4;i++)
        {
            System.out.println("Punkt całkowania nr "+i+":");
            for(int j=0;j<2;j++)
            {
                for(int k=0;k<2;k++)
                {
                    System.out.print(J[i].getData(j,k)+"  ");
                }
                System.out.println("");
            }
        }
    }

    public void showRevJ(){
        System.out.println("--Element "+ownid+":  revJ");
        for(int i=0;i<4;i++)
        {
            System.out.println("Punkt całkowania nr "+i+":");
            for(int j=0;j<2;j++)
            {
                for(int k=0;k<2;k++)
                {
                    System.out.print(revJ[i].getData(j,k)+"  ");
                }
                System.out.println("");
            }
        }
    }
    public void showDetJ(){
        System.out.println("Element "+ownid+":  detJ");
        for(int i=0;i<4;i++) {
            System.out.println("Punkt całkowania nr "+i+":  "+detJ.getData(i)+"  ");
        }
    }
    public void show(){
        System.out.println("Element "+ownid+":");
        for(int i=0;i<4;i++){
            System.out.println("    Node "+i+": "+id[i]);
        }
    }
    public void draw(){
        System.out.println("Element "+ownid+":");
        System.out.print(id[3]);
        System.out.print(wall[3] ? "----" : "    ");
        System.out.print(id[2]+"\n");
        System.out.print(wall[0] ? "|" : " ");
        System.out.print("     ");
        System.out.print(wall[2] ? "|\n" : " \n");
        System.out.print(id[0]);
        System.out.print(wall[1] ? "----" : "    ");
        System.out.print(id[1]+"\n\n");

    }
}
