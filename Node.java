public class Node {
    private double x,y,t;
    private boolean wall;
    private int ownid;
    private Grid parent;
    public Node(double x, double y,double t,boolean status,int ownid, Grid grid){
        this.x=x;
        this.y=y;
        this.t=t;
        this.wall=status;
        this.ownid=ownid;
        this.parent=grid;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isWall(){
        return wall;
    }

    public void show(){
        System.out.println("Node "+ownid+":   X: "+x+"   Y: "+y+"     T: "+t+"    Wall: "+wall);
    }
}
