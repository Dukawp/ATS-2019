package Controller;

import Exceptions.*;
import Model.*;
import View.Menu;
import View.ViewModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {
    private final UMCarroJa model;
    private User user;
    private final Menu menu;

    public Controller(UMCarroJa model) {
        this.menu = new Menu();
        this.model = model;
    }

    public void run(){
        String error = "";
        while(this.menu.getRun()) {
            switch (menu.getMenu()) {
                case Login:
                    try {
                        NewLogin r = menu.newLogin(error);
                        user = model.logIn(r.getUser(), r.getPassword());
                        menu.selectOption((user instanceof Client)? Menu.MenuInd.Client : Menu.MenuInd.Owner);
                        error = "";
                    }
                    catch (InvalidUserException e){ error = "Invalid Username"; }
                    catch (WrongPasswordExecption e){ error = "Invalid Password"; }
                    break;
                case RegisterClient:
                    try {
                        RegisterUser registerUserCli = menu.newRegisterUser(error);
                        Client client = new Client(
                                registerUserCli.getPos(),
                                registerUserCli.getEmail(),
                                registerUserCli.getPasswd(),
                                registerUserCli.getName(),
                                registerUserCli.getAddress(),
                                registerUserCli.getNif()
                        );
                        this.model.addUser(client);
                        menu.back();
                        error = "";
                    }
                    catch (InvalidNewRegisterException e){ error = "Parametros Inválidos"; }
                    catch (UserExistsException e){ error = "Utilizador já existe"; }
                    break;
                case RegisterOwner:
                    try {
                        RegisterUser registerUserProp = menu.newRegisterUser(error);
                        Owner owner = new Owner(
                                registerUserProp.getEmail(),
                                registerUserProp.getName(),
                                registerUserProp.getAddress(),
                                registerUserProp.getNif(),
                                registerUserProp.getPasswd()
                        );
                        this.model.addUser(owner);
                        menu.back();
                        error = "";
                    }
                    catch (InvalidNewRegisterException e){ error = "Parametros Inválidos"; }
                    catch (UserExistsException e){ error = "Utilizador já existe"; }
                    break;
                case Closest:
                    try{
                        RentCarSimple rent = menu.simpleCarRent(error);
                        Rental rental = model.rental(
                                (Client)user,
                                rent.getPoint(),
                                "MaisPerto",
                                rent.getCarType());
                        menu.showString(rental.toString());
                        menu.back();
                        error = "";
                    }
                    catch (UnknownCompareTypeException ignored) {}
                    catch (NoCarAvaliableException e) { error = "No cars availables"; }
                    catch (InvalidNewRentalException e){error = "Novo Rental inválido"; }
                    break;
                case Cheapest:
                    try{
                        RentCarSimple rent = menu.simpleCarRent(error);
                        Rental rental = model.rental(
                                (Client)user,
                                rent.getPoint(),
                                "MaisBarato",
                                rent.getCarType());
                        menu.showString(rental.toString());
                        menu.back();
                        error = "";
                    }
                    catch (UnknownCompareTypeException ignored) {}
                    catch (NoCarAvaliableException e) { error = "No cars availables"; }
                    catch (InvalidNewRentalException e){error = "Novo Rental inválido"; }
                    break;
                case ReviewRental:
                    Owner owner = (Owner)this.user;
                    ArrayList<Rental> lR = owner.getPending();
                    if (lR.size() == 0){
                        this.menu.back();
                        break;
                    }
                    List<List<String>> list = new ArrayList<>();
                    for (Rental rental1 : lR) {
                        String toParsableUserString = rental1.toParsableUserString();
                        List<String> strings = Arrays.asList(toParsableUserString.split("\n"));
                        list.add(strings);
                    }
                    String v = menu.reviewRentShow(
                            error,
                            owner.getRates(),
                            list);

                    try {
                        switch (v.charAt(0)) {
                            case 'a':
                                this.model.rent(lR.get(Integer.parseInt(v.substring(1)) - 1));
                                this.model.rate(
                                        owner,
                                        lR.get(Integer.parseInt(v.substring(1)) - 1),
                                        this.menu.showRentalRate(
                                                lR.get(Integer.parseInt(v.substring(1)) - 1).toFinalString()));
                                break;
                            case 'r':
                                this.model.refuse(owner, lR.get(Integer.parseInt(v.substring(1)) - 1));
                                break;
                            case 'b':
                                this.menu.back();
                                break;
                        }
                        error = "";
                    }
                    catch(NumberFormatException | IndexOutOfBoundsException e){error = "Input Inválido";}
                    break;

                case CheapestNear:
                    try{
                        CheapestNearCar walkCar = menu.walkingDistanceRent(error);

                        Rental rental = model.rental(
                                (Client)user,
                                walkCar.getPoint(),
                                walkCar.getWalkDistance(),
                                walkCar.getType()
                        );

                        this.menu.showString(rental.toString());
                        this.menu.back();
                        error = "";
                    }
                    catch (InvalidNewRentalException e){error = "New rental inválido";}
                    catch (NoCarAvaliableException e)  {error = "No cars availables"; }
                    break;

                case Autonomy:
                    try{
                        AutonomyCar autoCar = menu.autonomyCarRent(error);

                        Rental rental = model.rental(
                                autoCar.getPoint(),
                                autoCar.getAutonomy(),
                                autoCar.getType(),
                                (Client)user);

                        menu.showString(rental.toString());
                        this.menu.back();
                        error = "";
                    }
                    catch (InvalidNewRentalException e){error = "New rental inválido";}
                    catch (NoCarAvaliableException e) { error = "No cars availables"; }
                    break;

                case Specific:
                    try {
                        SpecificCar sc = this.menu.specificCarRent(error);
                        Rental rental = this.model.rental(sc.getPoint(), sc.getNumberPlate(), (Client)user);
                        this.menu.showString(rental.toString());
                        this.menu.back();
                        error = "";
                    }
                    catch (NoCarAvaliableException e) { error = "Carro não está disponível"; }
                    catch (InvalidCarException e) { error = "Carro não existe"; }
                    catch (InvalidNewRentalException e) { error = "Invalid Parameters"; }
                    break;

                case AddCar:
                    try {
                        RegisterCar registerCar = menu.newRegisterCar(error);
                        Owner ownerCar = (Owner)this.user;
                        model.addCar(
                                ownerCar,
                                registerCar.getNumberPlate(),
                                registerCar.getType(),
                                registerCar.getAvgSpeed(),
                                registerCar.getBasePrice(),
                                registerCar.getGasMileage(),
                                registerCar.getRange(),
                                registerCar.getPos(),
                                registerCar.getBrand()
                        );
                        menu.back();
                        error = "";
                    }
                    catch (InvalidNewRegisterException e){ error = "Parametros Inválidos"; }
                    catch (CarExistsException e){ error = "Carro já existe"; }
                    catch (InvalidUserException ignored) {}
                    break;

                case NUses:
                    List<List<String>> result = new ArrayList<>();
                    long limit = 10;
                    for (Map.Entry<String, Integer> stringIntegerEntry : this.model.getBestClientsTimes()) {
                        List<String> strings = Arrays.asList(
                                stringIntegerEntry.getKey(),
                                stringIntegerEntry.getValue().toString());
                        if (limit-- == 0) break;
                        result.add(strings);
                    }
                    menu.top10ClientsShow(
                            result,
                            "Número de Utilizações");
                    this.menu.back();
                    break;

                case Distance:
                    List<List<String>> list1 = new ArrayList<>();
                    long limit1 = 10;
                    for (Map.Entry<String, Double> stringDoubleEntry : this.model.getBestClientsTravel()) {
                        List<String> strings = Arrays.asList(
                                stringDoubleEntry.getKey(),
                                String.format("%.2f", stringDoubleEntry.getValue()));
                        if (limit1-- == 0) break;
                        list1.add(strings);
                    }
                    menu.top10ClientsShow(
                            list1,
                            "Distância");
                    this.menu.back();
                    break;

                case CarOverview:
                    Owner ownerCar = (Owner)this.user;
                    List<List<String>> result1 = new ArrayList<>();
                    for (Car car : ownerCar.getCars()) {
                        List<String> strings = Arrays.asList(car.toString().split("\n"));
                        result1.add(strings);
                    }
                    String action = this.menu.carOverviewShow(error,
                            result1);
                    try {
                        switch (action.charAt(0)) {
                            case ' ':
                                break;
                            case 'r':
                                model.refil(ownerCar, Integer.parseInt(action.substring(1)) - 1);
                                break;
                            case'c':
                                String [] s = action.substring(1).split(" ");
                                if (s.length != 2)
                                    throw new InvalidNumberOfArgumentsException();
                                model.setBasePrice(ownerCar, Integer.parseInt(s[0]) - 1, Double.parseDouble(s[1]));
                                break;
                            case 'd':
                                model.swapState(ownerCar, Integer.parseInt(action.substring(1)) - 1);
                                break;
                            case 't':
                                TimeInterval ti = this.menu.getTimeInterval(error);
                                this.menu.showString("Total faturado: " +
                                        model.getTotalBilledCar(
                                        ownerCar.getCars().get(Integer.parseInt(action.substring(1)) - 1),
                                        ti.getInicio(),
                                        ti.getFim()));
                                break;
                            case 'b':
                                this.menu.back();
                                break;

                                default:
                                    throw new InvalidNumberOfArgumentsException();
                        }
                        error = "";
                    }
                    catch (IndexOutOfBoundsException e){ error = "Valor de carro inválido"; }
                    catch (NumberFormatException e){ error = "Posição inválida"; }
                    catch (InvalidNumberOfArgumentsException e) {error = "Invalid parameters";}
                    catch (InvalidTimeIntervalException e ){error = "Formato Inválido de Data";}
                    break;

                case Pending:
                    try {
                        Client cli = (Client) user;
                        List<Rental> pR = cli.getPendingRates();

                        if (pR.size() == 0) {
                            this.menu.back();
                            break;
                        }

                        RateOwnerCar r = this.menu.pendingRateShow(error, pR.get(0).toString(), pR.size());
                      
                        model.rate(cli, pR.get(0), r.getOwnerRate(), r.getCarRate());

                        error = "";
                    }
                    catch (InvalidRatingException e){error = "Parametros Invalidos";}
                    break;

                case HistoryOwner:
                    try{
                        TimeInterval ti = this.menu.getTimeInterval(error);

                        List<List<String>> list2 = new ArrayList<>();
                        for (Rental rental : this.model.getRentalListOwner((Owner) this.user, ti.getInicio(), ti.getFim())) {
                            String x = rental.toParsableOwnerRentalString();
                            List<String> strings = Arrays.asList(x.split("\n"));
                            list2.add(strings);
                        }
                        this.menu.rentalHistoryShow(ti,
                                list2);

                        this.menu.back();
                        error = "";
                    }
                    catch (InvalidTimeIntervalException e){error = "Intervalo Inválido";}
                    break;

                case HistoryClient:
                    try{
                        TimeInterval ti = this.menu.getTimeInterval(error);

                        List<List<String>> list2 = new ArrayList<>();
                        for (Rental rental : this.model.getRentalListClient((Client) this.user, ti.getInicio(), ti.getFim())) {
                            String x = rental.toParsableUserRentalString();
                            List<String> strings = Arrays.asList(x.split("\n"));
                            list2.add(strings);
                        }
                        this.menu.rentalHistoryShow(ti,
                                list2);

                        this.menu.back();
                        error = "";
                    }
                    catch (InvalidTimeIntervalException e){error = "Intervalo Inválido";}
                    break;

                    default:
                        this.menu.parser();
                        break;
            }
        }
    }
}
