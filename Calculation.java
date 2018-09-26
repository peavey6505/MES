public class Calculation {
    public Vector scalarTimesVector(double scalar, Vector vector){
        Vector output =  new Vector(vector.getSize());
        output.clear();
        for(int i=0;i<vector.getSize();i++)
        {
            output.setData(i,scalar*vector.getData(i));
        }
        return output;
    }
    public SquareMatrix scalarTimesMatrix(double scalar, SquareMatrix matrix){
        SquareMatrix output=new SquareMatrix(matrix.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++) {
            for (int j = 0; j < output.getSize(); j++) {
                output.setData(i,j,scalar*matrix.getData(i,j));
            }
        }
        return output;
    }
    public SquareMatrix matrixTimesMatrix(SquareMatrix firstMatrix, SquareMatrix secondMatrix){
        if(firstMatrix.getSize()!=secondMatrix.getSize()){return null;}
        SquareMatrix output=new SquareMatrix(firstMatrix.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++) {
            for (int j = 0; j < output.getSize(); j++) {
                double value=0;
                for(int k=0;k< output.getSize();k++)
                {
                    value+=firstMatrix.getData(i,k)*secondMatrix.getData(k,j);
                }
                output.setData(i,j,value);
            }
        }
        return output;
    }
    public Vector vectorTimesMatrix(Vector vector, SquareMatrix matrix){
        if(vector.getSize()!=matrix.getSize()){return null;}
        Vector output =  new Vector(vector.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++) {
            double val=0;
            for (int j = 0; j < output.getSize(); j++){
                    val+=matrix.getData(j,i)*vector.getData(j);
            }
            output.setData(i,val);
        }
        return output;
    }
    public SquareMatrix vectorTimesVectorT(Vector v1, Vector v2){
        if(v1.getSize()!= v2.getSize()){return null;}
        SquareMatrix output=new SquareMatrix(v1.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++)
        {
            for(int j=0;j<output.getSize();j++)
            {
                output.addData(i,j,v1.getData(i)*v2.getData(j));
            }
        }
        return output;
    }
    public Vector addVectors(Vector v1, Vector v2){
        if(v1.getSize()!=v2.getSize()){return null;}
        Vector output =  new Vector(v1.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++)
        {
            output.setData(i,v1.getData(i)+v2.getData(i));
        }
        return output;
    }
    public SquareMatrix addMatrixes(SquareMatrix firstMatrix, SquareMatrix secondMatrix){
        if(firstMatrix.getSize()!=secondMatrix.getSize()){return null;}
        SquareMatrix output=new SquareMatrix(firstMatrix.getSize());
        output.clear();
        for(int i=0;i<output.getSize();i++) {
            for (int j = 0; j < output.getSize(); j++) {
                output.setData(i,j,firstMatrix.getData(i,j)+secondMatrix.getData(i,j));
            }
        }
        return output;
    }

    public Vector GaussExtermiation(Vector v1, SquareMatrix data){
        int n=v1.getSize();
        double e = Math.pow(10, -12);
        double m,s;
        double[][] tab= new double[n][n+1];
        double[] tabResult = new double[n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                tab[i][j]=data.getData(i,j);
            }
        }
        for(int i=0;i<n;i++) {
            tab[i][n]=v1.getData(i);
        }


        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(tab[i][i]) < e) {
                    System.err.println("dzielnik rowny 0");
                    break;
                }

                m = -tab[j][i] / tab[i][i];
                for (int k = 0; k < n + 1; k++) {
                    tab[j][k] += m * tab[i][k];
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            s = tab[i][n];
            for (int j = n - 1; j >= 0; j--) {
                s -= tab[i][j] * tabResult[j];
            }
            if (Math.abs(tab[i][i]) < e) {
                System.err.println("dzielnik rowny 0");
                break;
            }
            tabResult[i] = s / tab[i][i];

        }

        Vector result=new Vector(n);
        for(int i=0;i<n;i++)
        {
            result.setData(i,tabResult[i]);
        }
        return result;
    }
}
