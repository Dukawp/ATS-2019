package Model;

import Exceptions.*;
import Utils.Point;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;

public class UMCarroJa implements Serializable {
    private static final long serialVersionUID = 8905150170424120902L;
    private final Cars cars;
    private final Users users;
    private final Rentals rentals;

    public UMCarroJa() {
        this.cars = new Cars();
        this.users = new Users();
        this.rentals = new Rentals();
    }

    public void exit(){
        System.exit(0);
    }

    public List<Entry<String, Double>> getBestClients() {
        Map<String, Double> map = new HashMap<>();
        for (String e : this
                .users
                .getClientIDS()) {
            Double acc = 0.0;
            for (Rental rental : rentals.getRentalListClient(e)) {
                Double distance = rental.getDistance();
                acc = acc + distance;
            }
            if (map.put(e, acc) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        List<Entry<String, Double>> list = new ArrayList<>();
        for (Entry<String, Double> stringDoubleEntry : map
                .entrySet()) {
            list.add(stringDoubleEntry);
        }
        list.sort(Collections
                .reverseOrder(Comparator
                        .comparingDouble(Entry::getValue)));
        return list;
    }

    public List<Entry<String, Integer>> getBestClientsTimes() {
        Map<String, List<Rental>> map = new HashMap<>();
        for (String s : this
                .users
                .getClientIDS()) {
            if (map.put(s, rentals.getRentalListClient(s)) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        List<Entry<String, List<Rental>>> toSort = new ArrayList<>();
        for (Entry<String, List<Rental>> e : map
                .entrySet()) {
            toSort.add(e);
        }
        toSort.sort((o1, o2) -> {
            int r = Integer.compare(o2.getValue().size(), o1.getValue().size());
            if (r == 0) {
                double sum = 0.0;
                for (Rental rental : o2.getValue()) {
                    double distance = rental.getDistance();
                    sum += distance;
                }
                double result = 0.0;
                for (Rental rental : o1.getValue()) {
                    double distance = rental.getDistance();
                    result += distance;
                }
                return Double.compare(sum,
                        result);
            }
            return r;
        });
        List<Entry<String, Integer>> list = new ArrayList<>();
        for (Entry<String, List<Rental>> e : toSort) {
            AbstractMap.SimpleEntry<String, Integer> stringIntegerSimpleEntry = new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().size());
            list.add(stringIntegerSimpleEntry);
        }
        return list;
    }

    public List<Entry<String, Double>> getBestClientsTravel() {
        Map<String, List<Rental>> map = new HashMap<>();
        for (String s : this
                .users
                .getClientIDS()) {
            if (map.put(s, rentals.getRentalListClient(s)) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        List<Entry<String, List<Rental>>> toSort = new ArrayList<>();
        for (Entry<String, List<Rental>> e : map
                .entrySet()) {
            toSort.add(e);
        }
        toSort.sort((o1, o2) -> {
            double sum = 0.0;
            for (Rental rental : o2.getValue()) {
                double distance = rental.getDistance();
                sum += distance;
            }
            double result = 0.0;
            for (Rental rental : o1.getValue()) {
                double distance = rental.getDistance();
                result += distance;
            }
            int r = Double.compare(sum,
                    result);
            if (r == 0) return Integer.compare(o2.getValue()
                            .size(),
                    o1.getValue()
                            .size());
            return r;
        });
        List<Entry<String, Double>> list = new ArrayList<>();
        for (Entry<String, List<Rental>> e : toSort) {
            double sum = 0.0;
            for (Rental rental : e.getValue()) {
                double distance = rental.getDistance();
                sum += distance;
            }
            AbstractMap.SimpleEntry<String, Double> stringDoubleSimpleEntry = new AbstractMap.SimpleEntry<>(e.getKey(), sum);
            list.add(stringDoubleSimpleEntry);
        }
        return list;
    }


    void rental(String username, Point dest, String preference, Car.CarType a)
            throws UnknownCompareTypeException, NoCarAvaliableException, InvalidUserException {
        Client c = (Client) users.getUser(username);
        Car car = cars.getCar(preference, dest, c.getPos(), a);
        Rental r = new Rental(car, c, dest);
        this.rent(r);
    }

    public Rental rental(Point dest, double range, Car.CarType a, Client c)
            throws NoCarAvaliableException {
        Car car = cars.getCar(dest, range, a);
        Rental r = new Rental(car, c, dest);
        car.pendingRental(r);
        return r;
    }

    public Rental rental(Point dest, String numberPlate, Client c) throws InvalidCarException, NoCarAvaliableException {
        Car car = this.cars.searchCar(numberPlate);

        if(car.getPosition().distanceBetweenPoints(dest) > car.getRange())
            throw new NoCarAvaliableException();

        Rental r = new Rental(car, c, dest);
        car.pendingRental(r);
        return r;
    }

    //Range a pe
    public Rental rental(Client c, Point dest, double range, Car.CarType a)
            throws NoCarAvaliableException{
        Car car = cars.getCar(dest, c.getPos(), range, a);
        Rental r = new Rental(car, c, dest);
        car.pendingRental(r);
        return r;
    }

    //MaisPerto e MaisBarato
    public Rental rental(Client c, Point dest, String preference, Car.CarType a)
            throws UnknownCompareTypeException, NoCarAvaliableException {
        Car car = cars.getCar(preference, dest, c.getPos(), a);
        Rental r = new Rental(car, c, dest);
        car.pendingRental(r);
        return r;
    }

    public void rent(Rental r) {
        rentals.addRental(r);
        r.rent();
    }

    public void refuse(Owner a, Rental r) {
        a.refuse(r);
    }

    public void addUser(User a) throws UserExistsException {
        this.users.addUser(a.clone());
    }

    public void refil(Owner ownerCar, int index) {
        ownerCar.getCars().get(index).refil();
    }

    public void setBasePrice(Owner ownerCar, int index, double price) {
        ownerCar.getCars().get(index).setBasePrice(price);
    }

    public void swapState(Owner ownerCar, int index) {
        ownerCar.getCars().get(index).swapState();
    }

    void addCar(String numberPlate, String ownerID, Car.CarType type, double avgSpeed, double basePrice,
                double gasMileage, int range, Point pos, String brand) throws CarExistsException, InvalidUserException {
        Owner o = (Owner) this.users.getUser(ownerID);
        Car a = new Car(numberPlate, o, type, avgSpeed, basePrice, gasMileage, range, pos, brand);
        this.cars.addCar(a);
        o.addCar(a);
    }

    public void addCar(Owner os, String numberPlate, Car.CarType type, double avgSpeed, double basePrice, double gasMileage, int range, Point pos, String brand) throws CarExistsException, InvalidUserException {
        Owner o = (Owner) this.users.getUser(os.getEmail());
        Car a = new Car(numberPlate, o, type, avgSpeed, basePrice, gasMileage, range, pos, brand);
        this.cars.addCar(a);
        o.addCar(a);
    }

    void rate(String s, int rate) throws InvalidUserException, InvalidCarException {
        try {
            this.users
                    .getUser(Integer.parseInt(s) + "@gmail.com")
                    .rate(rate);
        }
        catch(NumberFormatException ignored) {
            this.cars
                    .searchCar(s)
                    .rate(rate);
        }
    }

    public void rate(Client cli, Rental r, int a, int b) {
        cli.rate(r, a, b);
    }

    public void rate(Owner own, Rental r, int a) {
        own.rate(r, a);
    }

    public User logIn(String username, String passwd) throws InvalidUserException, WrongPasswordExecption {
        User c = users.getUser(username);
        if(!c.getPasswd().equals(passwd))
            throw new WrongPasswordExecption();
        return c;
    }

    public double getTotalBilledCar(Car car, LocalDateTime init, LocalDateTime end) {
        return this.rentals.getTotalBilledCar(car, init, end);
    }

    public double getTotalBilledCar(Car car) {
        return this.rentals.getTotalBilledCar(car);
    }

    public List<Rental> getRentalListOwner(Owner owner, LocalDateTime init, LocalDateTime end) {
        return this.rentals.getRentalListOwner(owner, init, end);
    }

    public List<Rental> getRentalListOwner(Owner owner) {
        return this.rentals.getRentalListOwner(owner);
    }

    public List<Rental> getRentalListClient(Client client, LocalDateTime init, LocalDateTime end) {
        return this.rentals.getRentalListClient(client, init, end);
    }

    public List<Rental> getRentalListClient(Client client) {
        return this.rentals.getRentalListClient(client);
    }

    public void save(String fName) throws IOException {
        FileOutputStream a = new FileOutputStream(fName);
        ObjectOutputStream r = new ObjectOutputStream(a);
        r.writeObject(this);
        r.flush();
        r.close();
    }

    public static UMCarroJa read(String fName) throws IOException, ClassNotFoundException {
        FileInputStream r = new FileInputStream(fName);
        ObjectInputStream a = new ObjectInputStream(r);
        UMCarroJa u = (UMCarroJa) a.readObject();
        a.close();
        return u;
    }
}
