import java.util.ArrayList;
import java.util.List;

public class Grid {
    private  Node nodes[];
    private  Element elements[];
    private GlobalData globalData;
    private UniversalElement universalElement;
    public UniversalElement getUniversalElement(){ return universalElement;}
    private SquareMatrix matrixH;
    private SquareMatrix matrixC;
    private SquareMatrix matrixS;
    private SquareMatrix matrixHC;
    private Vector vectorP;

    public Grid(){

        globalData=new GlobalData();
        nodes=new Node[globalData.getNumberOfN()];
        elements= new Element[globalData.getNumberOfE()];
        universalElement=new UniversalElement();
        matrixC= new SquareMatrix(globalData.getNumberOfN());
        matrixH= new SquareMatrix(globalData.getNumberOfN());
        matrixS= new SquareMatrix(globalData.getNumberOfN());
        matrixHC= new SquareMatrix(globalData.getNumberOfN());
        vectorP= new Vector(globalData.getNumberOfN());
        matrixC.clear();
        matrixS.clear();
        matrixH.clear();
        matrixHC.clear();
        vectorP.clear();
    }


    public void fillGrid(){
        double dH=globalData.getH()/(globalData.getnH()-1);
        double dB=globalData.getB()/(globalData.getnB()-1);
        //Węzły
        for (int i = 0; i < globalData.getnB(); i++) //id�c po szeroko�ci
        {
            for (int j = 0; j < globalData.getnH(); j++) //id�c po d�ugo�ci
            {
                int n = i*globalData.getnB() + j; //tworzymy element numer n
                boolean wall=false;
                if (i==0 || i==globalData.getnB()-1 || j==0 || j==globalData.getnH()-1)  //sprawdzamy czy na �cianie
                {
                    wall=true;
                }
                nodes[n]=new Node(i*dB,j*dH,globalData.getInitialT(),wall,n,this);
            }
        }
        //Elementy
        for (int i = 0; i < globalData.getnB()-1; i++)//idziemy po szeroko�ci -1 (tyle element�w si� mie�ci na szeroko��)
        {
            for (int j = 0; j < globalData.getnH()-1; j++)//idziemy po d�ugo�ci -1 (tyle element�w si� mie�ci na d�ugo��)
            {
                int n = i*(globalData.getnB()-1) + j; //tworzymy element numer N
                elements[n] = new Element(this,n);
                //ustawiamy odpowiednie w�z�y - najlepiej narysowa� sobie na kartce jak powinno to i��
                elements[n].setNode(0, n + i);
                elements[n].setNode(3, n + i + 1);
                elements[n].setNode(1, n + i + globalData.getnH());
                elements[n].setNode(2, n + i + globalData.getnH() + 1);
                if(nodes[n+i].isWall() &&nodes[n+i+1].isWall() ){//ściana 3
                    elements[n].setWall(0,true);
                }else{
                    elements[n].setWall(0,false);}
                if(nodes[n+i].isWall() &&nodes[n + i + globalData.getnH()].isWall() ){//ściana 0
                    elements[n].setWall(1,true);
                }else {
                    elements[n].setWall(1,false);}
                if(nodes[n + i + globalData.getnH()].isWall()&&nodes[n + i + globalData.getnH()+1].isWall() ){
                    elements[n].setWall(2,true);
                }else{
                    elements[n].setWall(2,false);}
                if(nodes[n+i+1].isWall() &&nodes[n + i + globalData.getnH()+1].isWall() ){//ściana 2
                    elements[n].setWall(3,true);
                }else{
                    elements[n].setWall(3,false);}

            }
        }
    }
    public void showNodes(){
        for(int i=0;i<globalData.getNumberOfN();i++){
            nodes[i].show();
        }
    }
    public void showElements(){
        for(int i=0;i<globalData.getNumberOfE();i++){
            elements[i].show();
        }
    }
    public void drawGrid(){
        for(int i=0;i<globalData.getNumberOfE();i++){
            elements[i].draw();
        }
    }

    public void calculateJakobian(){
        for(int i=0;i<globalData.getNumberOfE();i++)
        {
            elements[i].calculateJ(universalElement, nodes);
            elements[i].calculateN(universalElement);
            elements[i].showJ();
        }
    }
    public void showJakobian(){
        for(int i=0;i<globalData.getNumberOfE();i++)
        {
            elements[i].showJ();
            elements[i].showRevJ();
            elements[i].showDetJ();
        }
    }

    public Vector getTasVector(){
        Vector output=new Vector(globalData.getNumberOfN());
        output.clear();
        for(int i=0;i<globalData.getNumberOfN();i++)
        {
            output.setData(i,nodes[i].getT());
        }
        return output;
    }

    public void showMatrixH(){
        System.out.println("----    Matrix H    ----");
        matrixH.show();
    }
    public void showMatrixC(){
        System.out.println("----    Matrix C    ----");
        matrixC.show();
    }
    public void showMatrixHC(){
        System.out.println("----    Matrix HC    ----");
        matrixHC.show();
    }
    public void showVectorP(){
        System.out.println("----    Vector P    ----");
        vectorP.show();
    }

    public void clear(){
        matrixHC.clear();
        matrixH.clear();
        matrixC.clear();
        matrixS.clear();
        vectorP.clear();
    }
    public void calculateByTime(){
        Calculation calculation=new Calculation();
        double time=0;
        double iterations=globalData.getSimTime()/globalData.getSimStep();
        // double iterations=1.5;
        for(int step=0;step<=iterations;step++) //pętla po czasie
        {
            clear();
            SquareMatrix localH=new SquareMatrix(4);
            SquareMatrix localSurface=new SquareMatrix(4);
            SquareMatrix localC=new SquareMatrix(4);
            Vector localP=new Vector(4);

            for(int element=0;element<globalData.getNumberOfE();element++){// pętla po elementach
                localH.clear();
                localC.clear();
                localP.clear();
                localSurface.clear();
                for(int pkt=0;pkt<4;pkt++)//po punktach całkowania
                {
                    double t0p=0;
                    for (int j = 0; j < 4; j++)  // 4 - liczba wezlow w wykorzystywanym elemencie skonczonym
                    {
                        t0p+=nodes[elements[element].getNode(j)].getT() * getUniversalElement().getN(pkt,j);//interpolacja temperatury w punkcie całkowania
                    }
                    //-------------Liczenie C lokalne - pdf Kustra: FEM 2, wzór 6.11
                    SquareMatrix tempC=calculation.vectorTimesVectorT(universalElement.getN(pkt),universalElement.getN(pkt));//NxNt
                    tempC=calculation.scalarTimesMatrix(elements[element].getDetJ(pkt),tempC);//* detJ
                    tempC=calculation.scalarTimesMatrix(globalData.getHeat(),tempC);//* c
                    tempC=calculation.scalarTimesMatrix(globalData.getDensity(),tempC);//* ro


                    SquareMatrix dNdxt=calculation.vectorTimesVectorT(elements[element].getdNdX(pkt),elements[element].getdNdX(pkt));//macierz dnxt x dndt transponowane
                    SquareMatrix dNdyt=calculation.vectorTimesVectorT(elements[element].getdNdY(pkt),elements[element].getdNdY(pkt));//macierz dnxy x dndy transponowane

                    //k* detj *(dNdx * dNdxt + dNdy * dNdyt) - pdf Kustra FEM 2, wzór 6.9 , pierwsza całka
                    SquareMatrix tempH=calculation.scalarTimesMatrix(elements[element].getDetJ(pkt),calculation.scalarTimesMatrix(globalData.getCond(),calculation.addMatrixes(dNdxt,dNdyt)));

                    //zapis do macierzy elementu
                    localH=calculation.addMatrixes(localH,tempH);
                    localC=calculation.addMatrixes(localC,tempC);

                }

                for(int sciana=0;sciana<4;sciana++)
                {
                    double detj=0;
                    if(elements[element].getWall(sciana)){
                        switch(sciana){
                            case 0:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(3)].getX()-nodes[elements[element].getNode(0)].getX(),2)+
                                               Math.pow(nodes[elements[element].getNode(3)].getY()-nodes[elements[element].getNode(0)].getY(),2))/2.0;
                                break;
                            case 1:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(0)].getX()-nodes[elements[element].getNode(1)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(0)].getY()-nodes[elements[element].getNode(1)].getY(),2))/2.0;
                                break;
                            case 2:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(1)].getX()-nodes[elements[element].getNode(2)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(1)].getY()-nodes[elements[element].getNode(2)].getY(),2))/2.0;
                                break;
                            case 3:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(2)].getX()-nodes[elements[element].getNode(3)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(2)].getY()-nodes[elements[element].getNode(3)].getY(),2))/2.0;
                                break;
                        }
                        for (int p = 0; p < 2; p++)
                        {
                            for (int n = 0; n < 4; n++)
                            {
                                for (int i = 0; i < 4; i++)
                                {
                                    //H - Kustra FEM 2, wzór 6.9, całka druga
                                    double x=globalData.getAlfa() * universalElement.getPOW()[sciana].N[p][n] * universalElement.getPOW()[sciana].N[p][i] * detj;
                                    localSurface.addData(n,i,x);
                                }
                                //P - Kustra FEM 2, wzór 6.10
                                double x=globalData.getAlfa() * globalData.getAmbientT() * universalElement.getPOW()[sciana].N[p][n] * detj;
                               localP.addData(n, x);
                            }
                        }

                    }
                }
                //zapis do globalnej macierzy
                for (int a = 0; a < 4; a++) {
                    for (int b = 0; b < 4; b++) {
                        matrixH.addData(elements[element].getNode(a),elements[element].getNode(b),localH.getData(a,b));
                        matrixS.addData(elements[element].getNode(a),elements[element].getNode(b),localSurface.getData(a,b));
                        matrixC.addData(elements[element].getNode(a),elements[element].getNode(b),localC.getData(a,b));

                    }
                    vectorP.addData(elements[element].getNode(a),localP.getData(a));
                }


            }
            Vector t0=getTasVector();
            System.out.println("-------- Iteration "+step+" --------");
            //[P]^ = [P] + [C]/dT * {T0}
            vectorP=calculation.addVectors(vectorP,calculation.vectorTimesMatrix(t0,calculation.scalarTimesMatrix((1/globalData.getSimStep()),matrixC)));
            if(step==0) {
      //          showMatrixC();
     //           showMatrixH();
            }

            //[H]^ = [H] + [C]/dT
            matrixHC=calculation.addMatrixes(calculation.scalarTimesMatrix((1/globalData.getSimStep()),matrixC),matrixH);
            //dodanie warunków brzegowych
            matrixHC=calculation.addMatrixes(matrixHC,matrixS);
        //    showMatrixHC();
        //    showVectorP();
            Vector t1=calculation.GaussExtermiation(vectorP,matrixHC);

            for(int q=0;q<globalData.getNumberOfN();q++){
                nodes[q].setT(t1.getData(q));
            }
            System.out.println("Vector T1:");
            t1.show();
            System.out.println("----------------------------------------------");
            time+=globalData.getSimStep();//kolejny krok czasowy



        }

    }
}
