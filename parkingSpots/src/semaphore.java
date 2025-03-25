import java.time.Duration;

public class semaphore {
    protected int value;

    public int getVal() {
        return value;
    }

    protected semaphore() {
        value = 0;
    }

    protected semaphore(int start) {
        value = start;
    }

    public synchronized void P() {
        value--;
        if (value < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("an error occured");
            }
        }
    }

    public synchronized void P(Car car) {
        value--;
        if (value < 0) {
            try {
                car.printWait();
                wait();
            } catch (InterruptedException e) {
                System.out.println("an error occured");
            }
        }
    }

    public synchronized void P1(Car car) {
        value--;
        if (value < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("an error occured");
            }
        }
        System.out.println(
                "Car " + car.carNumber + " left from gate " + car.gate.gateNumber + " after "
                        + car.parkingDurationInMilliseconds / 1000
                        + " units of time. (Parking status: " + car.parking.full.getVal() + " spots occupied)");
    }

    public synchronized void V() {
        value++;
        if (value <= 0) {
            notify();
        }
    }

    public synchronized void V(Car car, Duration waitingTime) {
        value++;
        if (value <= 0) {
            notify();
        }
        if (waitingTime.toSeconds() != 0) {
            System.out
                    .println("Car " + car.carNumber + " from Gate " + car.gate.gateNumber + " parked after waiting for "
                            + waitingTime.toSeconds() + " units of time. " + "(Parking Status: "
                            + car.parking.full.getVal()
                            + " spots occupied)");
        } else {
            System.out
                    .println("Car " + car.carNumber + " from Gate " + car.gate.gateNumber + " parked "
                            + "(Parking Status: " + (car.parking.full.getVal())
                            + " spots occupied)");
        }
    }
}
