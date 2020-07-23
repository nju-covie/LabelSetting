package IO;

import Data.Instance;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InstanceLoader {
    public static Instance inst_load(int num, String path, String excel) throws IOException, InvalidFormatException {
        Instance inst = new Instance(num);
        Scanner cin = new Scanner(new BufferedReader(new FileReader(path)));
        for (int i = 0; i < 4; i++) {
            cin.nextLine();
        }
        String line = cin.nextLine();
        String[] split = line.split("\\s+");
        inst.Q = Integer.parseInt(split[2]);
        for (int i = 0; i < 4; i++) {
            cin.nextLine();
        }
        for (int i = 0; i < num; i++) {
            line = cin.nextLine();
            split = line.split("\\s+");
            inst.x[i] = Integer.parseInt(split[2]);
            inst.y[i] = Integer.parseInt(split[3]);
            inst.demand[i] = Integer.parseInt(split[4]);
            inst.ready_time[i] = Integer.parseInt(split[5]);
            inst.due_time[i] = Integer.parseInt(split[6]);
            inst.service_time[i] = Integer.parseInt(split[7]);
        }
        cin.close();

        Workbook workbook = WorkbookFactory.create(new File(excel));
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < num; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < num; j++) {
                Cell cell = row.getCell(j);
                inst.cost[i][j] = cell.getNumericCellValue();
            }
        }
        workbook.close();
        inst.cal_dis();
        return inst;
    }
}
