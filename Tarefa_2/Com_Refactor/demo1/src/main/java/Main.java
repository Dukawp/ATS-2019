import Controller.Controller;
import Model.Parser;
import Model.UMCarroJa;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        double[] before = EnergyCheckUtils.getEnergyStats();
        UMCarroJa model = new UMCarroJa();
        try {
            model = UMCarroJa.read(".tmp");            
        }
        catch (IOException | ClassNotFoundException e) {
            new Parser("logsTestLarge.bak", model);
        }
        double[] after = EnergyCheckUtils.getEnergyStats();
        System.out.println("Energy consumption of dram: " + (after[0] - before[0])+ " Energy consumption of cpu: " + (after[1] - before[1])+ " Energy consumption of package: " + (after[2] - before[2]));
 		EnergyCheckUtils.ProfileDealloc();
        // try { Thread.sleep(10000);} catch (Exception e) {}
        // new Controller(model).run();
        // try {
        //     model.save(".tmp");
        // }
        // catch (IOException ignored) {}
    }
}
