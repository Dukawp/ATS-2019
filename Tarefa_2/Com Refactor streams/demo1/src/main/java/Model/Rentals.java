package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Rentals implements Serializable {
    private static final long serialVersionUID = 1526373866446179937L;
    private final List<Rental> rentalBase;

    static private int id;

    Rentals() {
        this.rentalBase = new ArrayList<>();
        id = -1;
    }

    void addRental(Rental r) {
        id++;
        this.rentalBase.add(r);
    }

    /**
     * Calcula o total faturado por um carro num intervalo de tempo
     * @param car Carro a procurar
     * @param init Data de inicio
     * @param end Data de fim
     * @return Total faturado
     */
    double getTotalBilledCar(Car car, LocalDateTime init, LocalDateTime end) {
        String carID = car.getNumberPlate();
        Double acc = 0.0;
        for (Rental e : this.rentalBase) {
            if (e.getCarID().equals(carID)
                    && e.getDate().isAfter(init)
                    && e.getDate().isBefore(end)) {
                Double price = e.getPrice();
                acc = acc + price;
            }
        }
        return acc;
    }

    double getTotalBilledCar(Car car) {
        String carID = car.getNumberPlate();
        Double acc = 0.0;
        for (Rental e : this.rentalBase) {
            if (e.getCarID().equals(carID)) {
                Double price = e.getPrice();
                acc = acc + price;
            }
        }
        return acc;
    }

    /**
     * Calcula a lista de alugueres que um cliente fez num intervalo de tempo
     * @param c Client a procurar
     * @param init Data de inicio
     * @param end Data de fim
     * @return Lista dos alugueres
     */
    List<Rental> getRentalListClient(Client c, LocalDateTime init, LocalDateTime end) {
        String clientID = c.getEmail();
        List<Rental> list = new ArrayList<>();
        for (Rental e : this.rentalBase) {
            if (e.getClientID().equals(clientID)
                    && e.getDate().isBefore(end)
                    && e.getDate().isAfter(init)) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Calcula a lista de alugueres que um cliente
     * @param clientID Id do cliente
     * @return Lista dos alugueres
     */
    List<Rental> getRentalListClient(String clientID) {
        List<Rental> list = new ArrayList<>();
        for (Rental e : this.rentalBase) {
            if (e.getClientID().equals(clientID)) {
                list.add(e);
            }
        }
        return list;
    }

    List<Rental> getRentalListClient(Client c) {
        String clientID = c.getEmail();
        List<Rental> list = new ArrayList<>();
        for (Rental e : this.rentalBase) {
            if (e.getClientID().equals(clientID)) {
                list.add(e);
            }
        }
        return list;
    }
    /**
     * Calcula a lista de alugueres de um carro num intervalo de tempo
     * @param owner Owner a procurar
     * @param init Data de inicio
     * @param end Data de fim
     * @return Lista de alugueres
     */
    List<Rental> getRentalListOwner(Owner owner, LocalDateTime init, LocalDateTime end) {
        String carID = owner.getEmail();
        List<Rental> list = new ArrayList<>();
        for (Rental e : this.rentalBase) {
            if (e.getOwnerID().equals(carID)
                    && e.getDate().isBefore(end)
                    && e.getDate().isAfter(init)) {
                list.add(e);
            }
        }
        return list;
    }

    List<Rental> getRentalListOwner(Owner owner) {
        String carID = owner.getEmail();
        List<Rental> list = new ArrayList<>();
        for (Rental e : this.rentalBase) {
            if (e.getOwnerID().equals(carID)) {
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        Rentals rentals = (Rentals) o;
        return this.rentalBase.equals(rentals.rentalBase);
    }
}
