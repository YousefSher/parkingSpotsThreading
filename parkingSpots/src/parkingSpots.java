import java.time.Duration;
import java.time.Instant;

public class parkingSpots {
    private int spots = 4;
    private Car[] garage = new Car[spots];
    private boolean[] parkingStatus = new boolean[spots];
    public semaphore empty = new semaphore(spots);
    public semaphore full = new semaphore(0);
    public semaphore mutex = new semaphore(1);
    Reporter reporter;

    public parkingSpots(Reporter reporter){
        this.reporter = reporter;
    }

    public void produce(Car car) {
        empty.P(car);
        reporter.reportCar(car);
        reporter.updateFullValue(full.value);
        int parkingSpot = findParkingSpot();
        garage[parkingSpot] = car;
        car.parkingNumber = parkingSpot;
        parkingStatus[parkingSpot] = true;
        Instant end = Instant.now();
        Duration waitingTime = Duration.between(car.start, end);
        car.waiting.V();
        full.V(car, waitingTime);
    }

    public void consume(int parkingSpot) {
        Car car = garage[parkingSpot];
        full.P1(car);
        reporter.updateFullValue(full.value);
        parkingStatus[parkingSpot] = false;
        empty.V();
    }

    public int findParkingSpot() {
        int parkingSpot = -1;
        for (int i = 0; i < spots; i++) {
            if (parkingStatus[i] == false) {
                parkingSpot = i;
                break;
            }
        }
        return parkingSpot;
    }
}
