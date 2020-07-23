package Common;

public class Label implements Comparable<Object>{
    public int v; // the last vertex
    public double cost; // the reduced cost of the current partial path
    public int load; // accumulative load along the current partial path
    public double t; // the earliest service start time
    public boolean extend;
    public Label father;
    public int[] contain;

    public Label(int v, double cost, int load, double t, Label father, int[] contain) {
        this.v = v;
        this.cost = cost;
        this.load = load;
        this.t = t;
        this.father = father;
        this.contain = contain;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public boolean dominate(Label label) {
        if( v != label.v)
            return false;
        if (cost > label.cost)
            return false;
        if (load > label.load)
            return false;
        return !(t > label.t);
    }

    public Label extend(int i, double cost, int load, double t) {
        int[] ctn = this.contain.clone();
        ctn[i] = 1;
        return new Label(i, cost, load, t, this, ctn);
    }

}
