package Model;

import Exceptions.CarExistsException;
import Exceptions.InvalidCarException;
import Exceptions.NoCarAvaliableException;
import Exceptions.UnknownCompareTypeException;
import Utils.Point;

import java.io.Serializable;
import java.util.*;

public class Cars implements Serializable {
    private static final long serialVersionUID = 2716582249374370739L;
    private final Map<String, Car> carBase;

    Cars() {
        this.carBase = new HashMap<>();
    }

    private Cars(Cars c) {
        Map<String, Car> map = new HashMap<>();
        for (Car car : c.carBase
                .values()) {
            if (map.put(car.getNumberPlate(), car.clone()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        this.carBase = map;
    }

    /**
     * \brief Adiciona um carro à base de dados
     * @param a Carro a adicionar
     */
    void addCar(Car a) throws CarExistsException {
        if(this.carBase
                .putIfAbsent(a.getNumberPlate(), a)
                != null)
            throw new CarExistsException();
    }

    /**
     * \brief Procura um carro na base de dados
     * @param numberPlate Matricula do carro a procurar
     * @return Clone do carro, Null se não existir
     */
    Car searchCar(String numberPlate) throws InvalidCarException {
        Car car = this.carBase.get(numberPlate);
        if(car == null)
            throw new InvalidCarException();
        return car;
    }

    /**
     * Clona um objeto da classe Model.Cars
     * @return Clone do objeto
     */
    public Cars clone() {
        return new Cars(this);
    }

    /**
     * Obtem a lista de todos os carros no sistema
     * de um determinado tipo
     * @param b Tipo a procurar
     * @return Lista dos carros
     */
    public ArrayList<Car> listOfCarType(Car.CarType b) {
        ArrayList<Car> cars = new ArrayList<>();
        for (Car e : this.carBase
                .values()) {
            if (e.getType().equals(b)) {
                Car clone = e.clone();
                cars.add(clone);
            }
        }
        return cars;
    }

    Car getCar(String compare, Point dest, Point origin, Car.CarType a) throws UnknownCompareTypeException, NoCarAvaliableException {
        try {
            if (compare.equals("MaisPerto")) {
                List<Car> list = new ArrayList<>();
                for (Car car : this.carBase
                        .values()) {
                    if (car.getType().equals(a)
                            && car.hasRange(dest)
                            && car.isAvailable()) {
                        list.add(car);
                    }
                }
                list.sort(Comparator.comparingDouble(e ->
                        e.getPosition()
                                .distanceBetweenPoints(origin)));
                return list
                        .get(0);
            }

            if (compare.equals("MaisBarato")) {
                List<Car> list = new ArrayList<>();
                for (Car car : this.carBase
                        .values()) {
                    if (car.getType().equals(a)
                            && car.hasRange(dest)
                            && car.getPosition().distanceBetweenPoints(dest) != 0
                            && car.isAvailable()) {
                        list.add(car);
                    }
                }
                list.sort(Comparator.comparingDouble(e -> e.getBasePrice() * e.getPosition()
                        .distanceBetweenPoints(dest)));
                return list
                        .get(0);
            }
        }
        catch (IndexOutOfBoundsException ignored) {
            throw new NoCarAvaliableException();
        }
        throw new UnknownCompareTypeException();
    }

    Car getCar(Point dest, Point origin, double range, Car.CarType a) throws NoCarAvaliableException {
        try {
            List<Car> list = new ArrayList<>();
            for (Car car : this.carBase
                    .values()) {
                if (car.getType().equals(a)
                        && car.hasRange(dest)
                        && origin.distanceBetweenPoints(car.getPosition()) <= range
                        && car.isAvailable()) {
                    list.add(car);
                }
            }
            list.sort(Comparator.comparingDouble(e -> e.getBasePrice() * origin.distanceBetweenPoints(dest)));
            return list
                    .get(0);
        }
        catch(IndexOutOfBoundsException ignored) {
            throw new NoCarAvaliableException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        Cars cars = (Cars) o;
        return this.carBase.equals(cars.carBase);
    }

    Car getCar(Point dest, double range, Car.CarType a) throws NoCarAvaliableException {
        try {
            List<Car> list = new ArrayList<>();
            for (Car car : this.carBase
                    .values()) {
                if (car.getType().equals(a)
                        && car.hasRange(dest)
                        && car.getRange() >= range
                        && car.isAvailable()) {
                    list.add(car);
                }
            }
            list.sort(Comparator.comparingDouble(e -> e.getBasePrice() * e.getPosition().distanceBetweenPoints(dest)));
            return list.get(0);
        }
        catch (IndexOutOfBoundsException ignored) {
            throw new NoCarAvaliableException();
        }
    }
}
