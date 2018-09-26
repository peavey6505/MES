public class UniversalElement {
    private Vector dNdKsi[];
    private Vector dNdEta[];
    private Vector N[];
    private UniversalSurface[] POW = { //punkty calkowania gaussa dla powierzchni
            new UniversalSurface(new UniversalNode(-1.0, 1.0 / Math.sqrt(3.0)), new UniversalNode(-1.0, -1.0 / Math.sqrt(3.0))),
            new UniversalSurface(new UniversalNode(-1.0 / Math.sqrt(3.0), -1.0), new UniversalNode(1.0 / Math.sqrt(3.0), -1.0)),
            new UniversalSurface(new UniversalNode(1.0, -1.0 / Math.sqrt(3.0)), new UniversalNode(1.0, 1.0 / Math.sqrt(3.0))),
            new UniversalSurface(new UniversalNode(1.0 / Math.sqrt(3.0), 1.0), new UniversalNode(-1.0 / Math.sqrt(3.0), 1.0))
    };
    public UniversalSurface[] getPOW() {
        return POW;
    }
    UniversalElement(){
        double[] ksi = new double[4];
        double[] eta = new double[4];
        dNdKsi=new Vector[4];
        dNdEta=new Vector [4];
        N=new Vector[4];
        for (int i = 0; i < 4; i++) {
            dNdKsi[i]=new Vector(4);
            dNdEta[i]=new Vector(4);
            N[i]=new Vector(4);
            dNdKsi[i].clear();
            dNdEta[i].clear();
            N[i].clear();
        }

        ksi[0] = -0.577;
        ksi[1] = 0.577;
        ksi[2] = 0.577;
        ksi[3] = -0.577;
        eta[0] = -0.577;
        eta[1]= -0.577;
        eta[2]= 0.577;
        eta[3]= 0.577;
        for (int i = 0; i < 4; i++){
            //lokalne
            //Jakobian.pdf strona 1 (Funkcje kszta�tu elementu czterow�z�owego: )
            N[i].setData(0, 0.25*(1 - ksi[i])*(1 - eta[i]));
            N[i].setData(1,0.25*(1 + ksi[i])*(1 - eta[i]));
            N[i].setData(2,0.25*(1 + ksi[i])*(1 + eta[i]));
            N[i].setData(3,0.25*(1 - ksi[i])*(1 + eta[i]));
            //pochodNdKsi po ksi
            //Jakobian.pdf strona 3 (Obliczamy pochodNdKsi funkcji kszta�tu wzgl�dem ksi oraz eta: )
            dNdKsi[i].setData(0,-0.25*(1- eta[i]));
            dNdKsi[i].setData(1,0.25*(1 - eta[i]));
            dNdKsi[i].setData(2,0.25*(1 + eta[i]));
            dNdKsi[i].setData(3,-0.25*(1 + eta[i]));
            //pochodNdKsi po eta
            //Jakobian.pdf strona 3 (Obliczamy pochodNdKsi funkcji kszta�tu wzgl�dem ksi oraz eta: )
            dNdEta[i].setData(0,-0.25*(1 - ksi[i]));
            dNdEta[i].setData(1,-0.25*(1 + ksi[i]));
            dNdEta[i].setData(2,0.25*(1 + ksi[i]));
            dNdEta[i].setData(3,0.25*(1 - ksi[i]));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) { //macierze funkcji ksztaltu dla powierzchni
                POW[i].N[j][0] = N1(POW[i].ND[j].getKsi(), POW[i].ND[j].getEta());
                POW[i].N[j][1] = N2(POW[i].ND[j].getKsi(), POW[i].ND[j].getEta());
                POW[i].N[j][2] = N3(POW[i].ND[j].getKsi(), POW[i].ND[j].getEta());
                POW[i].N[j][3] = N4(POW[i].ND[j].getKsi(), POW[i].ND[j].getEta());
            }
        }
    }
    private double N1(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 - eta);
    }

    private double N2(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 - eta);
    }

    private double N3(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 + eta);
    }

    private double N4(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 + eta);
    }


    public void show(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(N[i].getData(j)+"  ");
            }
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(dNdKsi[i].getData(j)+"  ");
            }
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(dNdEta[i].getData(j)+"  ");
            }
        }
    }
    public double getdNdKsi(int integrationPoint, int position){
        return dNdKsi[integrationPoint].getData(position);
    }
    public double getdNdEta(int integrationPoint, int position){
        return dNdEta[integrationPoint].getData(position);
    }
    public double getN(int integrationPoint, int position){
        return N[integrationPoint].getData(position);
    }
    public Vector getDnDksi(int integrationPoint){
        return dNdKsi[integrationPoint];
    }
    public Vector getDnDeta(int integrationPoint){
        return dNdEta[integrationPoint];
    }
    public Vector getN(int integrationPoint){
        return N[integrationPoint];
    }
    public Vector getI(int integrationPoint, int j){
        Vector v=new Vector(2);
        v.clear();
        v.setData(0,dNdKsi[integrationPoint].getData(j));
        v.setData(1,dNdEta[integrationPoint].getData(j));
        return v;
    }
    public class UniversalSurface {

        public UniversalNode[] ND;

        public double N[][];

        public UniversalSurface(UniversalNode node1, UniversalNode node2) {
            ND = new UniversalNode[2];

            ND[0] = node1;
            ND[1] = node2;

            N = new double[2][4];
        }
    }
    public class UniversalNode {

        private double ksi;
        private double eta;

        public UniversalNode(double ksi, double eta) {
            this.ksi = ksi;
            this.eta = eta;
        }

        public double getKsi() {
            return ksi;
        }

        public double getEta() {
            return eta;
        }

    }
}
