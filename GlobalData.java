public class GlobalData {
    private double initialT; //temperatura pocz�tkowa
    private double simTime; //czas trwania symulacji
    private double simStep; //krok symulacji
    private double ambientT; //temperatura otoczenia?
    private double alfa; //alfa [W/(m^2*K)]
    private int nH; //ilość węzłów w d�ugo�ci
    private int nB; //ilość węzłów w szeroko�ci
    private int numberOfN; //ilość wszystkich węzłów
    private int numberOfE; //ilość wszystkich element�w
    private double H; //d�ugo�� (pe�ny wymiar)
    private  double B; //szeroko�� (pe�ny wymiar)
    private double heat; //ciep�o w�a�ciwe [J/(kg*K)]
    private double cond; //przewodnictwo [W/(m*K)]
    private double density; //g�sto�� [kg/m^3]

    public GlobalData(){
        initialT=100;
//        simTime=500;
        simTime=300;
        simStep=30;
        ambientT=1100;
        alfa=300;
        ///*
        H=0.0420;
        B=0.040;
        nH=9;
        nB=9;

        heat=553;
        cond=41.87;
        density=7780;
        numberOfN = nH*nB; //wszystkie w�z�y
        numberOfE = (nH - 1)*(nB - 1); // wszystkie elementy
    }


    public double getSimTime() {
        return simTime;
    }

    public double getSimStep() {
        return simStep;
    }

    public double getAmbientT() {
        return ambientT;
    }

    public double getAlfa() {
        return alfa;
    }

    public int getNumberOfN() {
        return numberOfN;
    }

    public void setNumberOfN(int numberOfN) {
        this.numberOfN = numberOfN;
    }

    public int getNumberOfE() {
        return numberOfE;
    }

    public void setNumberOfE(int numberOfE) {
        this.numberOfE = numberOfE;
    }

    public double getH() {
        return H;
    }

    public double getB() {
        return B;
    }

    public double getHeat() {
        return heat;
    }

    public double getCond() {
        return cond;
    }

    public double getDensity() {
        return density;
    }
    public double getInitialT(){
        return initialT;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }
}
