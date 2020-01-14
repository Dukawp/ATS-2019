import Model.UMCarroJa;
import Controller.Controller;
import Model.Parser;
import java.io.IOException;

public class ATS implements Runnable{

    UMCarroJa uc = new UMCarroJa();

    public static void main(String[] args) {
            ATS ats = new ATS();
            ats.run();   
    }

    public ATS(){
        uc = new UMCarroJa();
    }

    public void run(){
        
            uc = new UMCarroJa();
            double[] before = EnergyCheckUtils.getEnergyStats();
            try {
                uc = UMCarroJa.read(".tmp");            
            }
            catch (IOException | ClassNotFoundException e) {
                new Parser("logsPOO_carregamentoInicial.bak", uc);
            }
            double[] after = EnergyCheckUtils.getEnergyStats();
            System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2]));
            EnergyCheckUtils.ProfileDealloc();
            uc.exit();
        
    }

}