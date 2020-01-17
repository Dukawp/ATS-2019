import Model.UMCarroJa;
import Controller.Controller;
import Model.Parser;
import java.io.IOException;
import Model.Owner;
import Utils.Point;
import static Model.Car.CarType.gas;
import Exceptions.*;
import View.*;

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
            long start = System.currentTimeMillis();
            double[] before = EnergyCheckUtils.getEnergyStats();
            try {
                uc = UMCarroJa.read(".tmp");            
            }
            catch (IOException | ClassNotFoundException e) {
                new Parser("logsTestMini.bak", uc);
            }
            double[] after = EnergyCheckUtils.getEnergyStats();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);
            
            //GET BEST CLIENTS
            start = System.currentTimeMillis();
            before = EnergyCheckUtils.getEnergyStats();
            uc.getBestClients();
            after = EnergyCheckUtils.getEnergyStats();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);

            //ADDUSER
            try{
                start = System.currentTimeMillis();
                before = EnergyCheckUtils.getEnergyStats();
                Owner u = new Owner("ats@gmail.com", "ats", "UM", 999999999, "ats@gmail.com");  
                uc.addUser(u);
                after = EnergyCheckUtils.getEnergyStats();
                finish = System.currentTimeMillis();
                timeElapsed = finish - start;
                System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);
            } catch(UserExistsException e){
                e.printStackTrace();
            }
            //ADD CAR
            try {
                start = System.currentTimeMillis();
                before = EnergyCheckUtils.getEnergyStats();
                Owner u = new Owner("ats@gmail.com", "ats", "UM", 999999999, "ats@gmail.com");  
                Point p = new Point(1.0,2.1);
                uc.addCar(u, "01-ATS-00", gas, 100.0, 1.50, 9.6, 120, p, "Aston");
                after = EnergyCheckUtils.getEnergyStats();
                finish = System.currentTimeMillis();
                timeElapsed = finish - start;
                System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed); 
            } catch (InvalidUserException | CarExistsException e) {
                e.printStackTrace();
            }

            //GET LOGIN
            try{
                start = System.currentTimeMillis();
                before = EnergyCheckUtils.getEnergyStats();
                uc.logIn("ats@gmail.com", "ats@gmail.com");
                after = EnergyCheckUtils.getEnergyStats();
                finish = System.currentTimeMillis();
                timeElapsed = finish - start;
                System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);
            } catch (WrongPasswordExecption | InvalidUserException e){
                e.printStackTrace();
            }


            //GET BEST CLIENTS TIMES
            start = System.currentTimeMillis();
            before = EnergyCheckUtils.getEnergyStats();
            uc.getBestClientsTimes();
            after = EnergyCheckUtils.getEnergyStats();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);


            //GET BEST CLIENTS TRAVEL
            start = System.currentTimeMillis();
            before = EnergyCheckUtils.getEnergyStats();
            uc.getBestClientsTravel();
            after = EnergyCheckUtils.getEnergyStats();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            System.out.println((after[0] - before[0])+ ","+(after[1] - before[1])+","+(after[2] - before[2])+ "," + timeElapsed);


            EnergyCheckUtils.ProfileDealloc();
            uc.exit();
        
    }

}