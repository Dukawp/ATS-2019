package Controller;

import Exceptions.CarExistsException;
import Exceptions.InvalidCarException;
import Exceptions.InvalidNewRegisterException;
import Exceptions.InvalidNewRentalException;
import Exceptions.InvalidNumberOfArgumentsException;
import Exceptions.InvalidRatingException;
import Exceptions.InvalidTimeIntervalException;
import Exceptions.InvalidUserException;
import Exceptions.NoCarAvaliableException;
import Exceptions.UnknownCompareTypeException;
import Exceptions.UserExistsException;
import Exceptions.WrongPasswordExecption;
import Model.Client;
import Model.Owner;
import Model.Rental;
import Model.UMCarroJa;
import Model.User;
import View.Menu;
import View.ViewModel.*;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private final UMCarroJa model;
    private User user;
    private final Menu menu;

    public Controller(UMCarroJa model) {
        this.menu = new Menu();
        this.model = model;
    }

    private String menuLogin(String error) {
        try {
            NewLogin r = menu.newLogin(error);
            user = model.logIn(r.getUser(), r.getPassword());
            menu.selectOption((user instanceof Client)? Menu.MenuInd.Client : Menu.MenuInd.Owner);
            error = "";
        }
        catch (InvalidUserException e){ error = "Invalid Username"; }
        catch (WrongPasswordExecption e){ error = "Invalid Password"; }
        return error;
    }

    public void run(){
        String error = "";
        while(this.menu.getRun()) {error = getString(error);}
    }

    private String getString(String error) {
        switch (menu.getMenu()) {
                case Login:
                    error = menuLogin(error);
                    break;
                case RegisterClient:
                    error = menuRegisterClient(error);
                    break;
                case RegisterOwner:
                    error = menuRegisterOwner(error);
                    break;
                case Closest:
                    error = menuClosest(error);
                    break;
                case Cheapest:
                    error = menuCheapest(error);
                    break;
                case ReviewRental:
                    error = menuReviewRental(error);
                    break;
                case CheapestNear:
                    error = menuCheapestNear(error);
                    break;

                case Autonomy:
                    error = menuAutonomy(error);
                    break;

                case Specific:
                    error = menuSpecific(error);
                    break;

                case AddCar:
                    error = menuAddCar(error);
                    break;

                case NUses:
                    menuNUses();
                    break;

                case Distance:
                    menuDistance();
                    break;

                case CarOverview:
                    error = menuCarOverView(error);
                    break;

                case Pending:
                    error = menuPending(error);
                    break;
                case HistoryOwner:
                    error = menuHistoryOwner(error);
                    break;

                case HistoryClient:
                    error = menuHistoryClient(error);
                    break;

                default:
                    this.menu.parser();
                    break;
        }return error;
    }

    private String menuHistoryClient(String error) {
        try{
            TimeInterval ti = this.menu.getTimeInterval(error);

            this.menu.rentalHistoryShow(ti,
                    this.model.getRentalListClient((Client) this.user, ti.getInicio(), ti.getFim())
                            .stream()
                            .map(Rental::toParsableUserRentalString)
                            .map(x -> Arrays.asList(x.split("\n")))
                            .collect(Collectors.toList()));

            this.menu.back();
            error = "";
        }
        catch (InvalidTimeIntervalException e){error = "Intervalo Inválido";}
        return error;
    }

    private String menuHistoryOwner(String error) {
        try{
            TimeInterval ti = this.menu.getTimeInterval(error);

            this.menu.rentalHistoryShow(ti,
                    this.model.getRentalListOwner((Owner) this.user, ti.getInicio(), ti.getFim())
                            .stream()
                            .map(Rental::toParsableOwnerRentalString)
                            .map(x -> Arrays.asList(x.split("\n")))
                            .collect(Collectors.toList()));

            this.menu.back();
            error = "";
        }
        catch (InvalidTimeIntervalException e){error = "Intervalo Inválido";}
        return error;
    }

    private String menuPending(String error) {
        try {
            Client cli = (Client) user;
            List<Rental> pR = cli.getPendingRates();

            if (pR.size() == 0) {
                this.menu.back();
                return error;
            }

            RateOwnerCar r = this.menu.pendingRateShow(error, pR.get(0).toString(), pR.size());

            model.rate(cli, pR.get(0), r.getOwnerRate(), r.getCarRate());

            error = "";
        }
        catch (InvalidRatingException e){error = "Parametros Invalidos";}
        return error;
    }

    private String menuCarOverView(String error) {
        Owner ownerCar = (Owner)this.user;
        String action = this.menu.carOverviewShow(error,
                ownerCar.getCars().stream()
                .map(x -> Arrays.asList(x.toString().split("\n")))
                .collect(Collectors.toList()));
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
        return error;
    }

    private void menuDistance() {
        menu.top10ClientsShow(
                this.model.getBestClientsTravel()
                        .stream()
                        .map(x ->
                             Arrays.asList(
                                     x.getKey(),
                                     String.format("%.2f", x.getValue())))
                        .limit(10)
                        .collect(Collectors.toList()),
                "Distância");
        this.menu.back();
    }

    private void menuNUses() {
        menu.top10ClientsShow(
                this.model.getBestClientsTimes()
                        .stream()
                        .map(x ->
                                Arrays.asList(
                                        x.getKey(),
                                        x.getValue().toString()))
                        .limit(10)
                        .collect(Collectors.toList()),
                "Número de Utilizações");
        this.menu.back();
    }

    private String menuAddCar(String error) {
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
        return error;
    }

    private String menuSpecific(String error) {
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
        return error;
    }

    private String menuAutonomy(String error) {
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
        return error;
    }

    private String menuCheapestNear(String error) {
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
        return error;
    }

    private String menuReviewRental(String error) {
        Owner owner = (Owner)this.user;
        ArrayList<Rental> lR = owner.getPending();
        if (lR.size() == 0){
            this.menu.back();
            return error;
        }
        String v = menu.reviewRentShow(
                error,
                owner.getRates(),
                lR.stream()
                        .map(Rental::toParsableUserString)
                        .map(x -> Arrays.asList(x.split("\n")))
                        .collect(Collectors.toList()));

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
        return error;
    }

    private String menuCheapest(String error) {
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
        return error;
    }

    private String menuClosest(String error) {
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
        return error;
    }

    private String menuRegisterOwner(String error) {
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
        return error;
    }

    private String menuRegisterClient(String error) {
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
        return error;
    }


}
