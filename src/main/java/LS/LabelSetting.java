package LS;

import Common.Label;
import Data.Instance;
import IO.InstanceLoader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;

public class LabelSetting {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        int node_num = 102;
        Instance inst = InstanceLoader.inst_load(node_num, "data//r104.txt", "data/102.xlsx");
        ArrayList<Label>[] label_set = new ArrayList[node_num];

        for (int i = 0; i < label_set.length; i++) {
            label_set[i] = new ArrayList<>();
        }

        int[] contain = new int[node_num];
        contain[0] = 1;
        Label init = new Label(0, 0, 0, 0, null, contain);
        label_set[0].add(init);

        ArrayList<Integer> examing = new ArrayList<>(); // the set of the vertices to be examined
        examing.add(0);
        while (!examing.isEmpty()) {
            int index = examing.get(0);
            ArrayList<Label> labels = label_set[index];
            for (int j = 0; j < labels.size(); j++) {
                Label label = labels.get(j);
                if (!label.extend) {
                    for (int k = 0; k < node_num; k++) {
                        ArrayList<Label> labels_exist = label_set[k];
                        if (label.contain[k] == 0) {
                            double t = label.t + inst.service_time[label.v] + inst.dis[label.v][k];
                            if (t > inst.due_time[k]) {
                                continue;
                            }
                            int load = label.load + inst.demand[k];
                            if (load > inst.Q) {
                                continue;
                            }
                            double cost = label.cost + inst.cost[label.v][k];
                            double service_start = Math.max(t, inst.ready_time[k]);
                            Label new_label = label.extend(k, cost, load, service_start);


                            boolean dominate = false;
                            for (int l = 0; l < labels_exist.size(); l++) {
                                if (labels_exist.get(l).dominate(new_label)) {
                                    dominate = true;
                                    break;
                                }
                            }
                            if (!dominate) {
                                labels_exist.add(new_label);
                            }

                            for (int l = 0; l < labels_exist.size(); l++) {
                                if (!labels_exist.get(l).extend) {
                                    examing.add(k);
                                    break;
                                }
                            }
                        }
                    }
                    label.extend = true;
                }
            }
            examing.remove(0);
        }
        ArrayList<Label> target = label_set[101];
        double min_cost = Double.MAX_VALUE;
        Label min_label = null;
        for (int i = 0; i < target.size(); i++) {
            Label l = target.get(i);
            if (l.cost < min_cost) {
                min_cost = l.cost;
                min_label = l;
            }
        }

        ArrayList<Integer> seq = new ArrayList<>();

        while (min_label != null) {
            seq.add(min_label.v);
            min_label = min_label.father;
        }
        System.out.print("The costless path is >>> ");
        for (int i = seq.size() - 1; i >= 0 ; i--) {
            if(i != 0) {
                System.out.print(seq.get(i) + " -> ");
            }
            else {
                System.out.print(0);
            }
        }
        System.out.println();
        System.out.println("The min cost is " + min_cost);
    }
}