package Data;

public class Instance {
    public int num;
    public int Q; // the max capacity of the vehicle
    public int[] x;
    public int[] y;
    public int[] demand;
    public int[] ready_time;
    public int[] due_time;
    public int[] service_time;
    public double[][] cost;
    public double[][] dis;

    public Instance(int num) {
        this.num = num;
        x = new int[num];
        y = new int[num];
        demand = new int[num];
        ready_time = new int[num];
        due_time = new int[num];
        service_time = new int[num];
        cost = new double[num][num];
        dis = new double[num][num];
    }
    
    public void cal_dis() {
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                dis[i][j] = dis[j][i] = Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
            }
        }
    }
}
