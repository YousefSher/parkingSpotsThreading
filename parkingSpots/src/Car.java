import java.time.Instant;

public class Car implements Runnable {
    public int arriveTimeInMilliseconds;
    public Gate gate;
    public int carNumber;
    public int parkingDurationInMilliseconds;
    public int parkingNumber;
    public Instant start;
    public boolean printed = false;
    semaphore waiting = new semaphore();
    parkingSpots parking;

    public Car(int carNumber, int arriveTime, Gate gate, int parkingDuration, parkingSpots parking) {
        this.carNumber = carNumber;
        this.arriveTimeInMilliseconds = arriveTime * 1000;
        this.gate = gate;
        this.parkingDurationInMilliseconds = parkingDuration * 1000;
        this.parking = parking;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arriveTimeInMilliseconds);
            System.out.println("Car " + carNumber + " from Gate " + gate.gateNumber + " arrived at time "
                    + arriveTimeInMilliseconds / 1000);
        } catch (InterruptedException e) {
            System.out.println("an error occured");
        }
        getToGate();
        try {
            Thread.sleep(parkingDurationInMilliseconds);
        } catch (InterruptedException e) {
            System.out.println("an error occured");
        }
        parking.consume(parkingNumber);

    }

    public void printWait() {
        if (!printed) {
            System.out.println(
                    "Car " + this.carNumber + " from Gate " + this.gate.gateNumber + " is waiting for a spot");
            printed = true;
        }
    }

    public void getToGate() {
        start = Instant.now();
        gate.reset();
        if (gate.queueOfCars.size() > 0) {
            printWait();
        }
        gate.queueOfCars.add(this);
        waiting.P();
    }
}
