import java.util.*;

enum Direction {
    UP, DOWN, IDLE
}

interface ElevatorInterface {
    void addRequest(int floor, Direction dir);
    void run();
}

class Elevator implements ElevatorInterface {
    private int currentFloor = 0;
    private Direction direction = Direction.IDLE;
    private final TreeSet<Integer> upQueue = new TreeSet<>();
    private final TreeSet<Integer> downQueue = new TreeSet<>(Collections.reverseOrder());
    private final Set<Integer> allRequests = new HashSet<>();

    public void addRequest(int floor, Direction dir) {
        if (floor == currentFloor || allRequests.contains(floor)) return;

        if (dir == Direction.UP) {
            upQueue.add(floor);
        } else {
            downQueue.add(floor);
        }
        allRequests.add(floor);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Ask for new requests if idle
            if (upQueue.isEmpty() && downQueue.isEmpty()) {
                System.out.print("\nEnter floor to request (-1 to exit): ");
                int input = scanner.nextInt();
                if (input == -1) break;

                System.out.print("Direction (UP/DOWN): ");
                String dirInput = scanner.next().toUpperCase();

                if (input < 0 || input > 10 || !(dirInput.equals("UP") || dirInput.equals("DOWN"))) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                Direction dir = Direction.valueOf(dirInput);
                addRequest(input, dir);
            }

            processRequests();
        }

        scanner.close();
        System.out.println("Elevator system stopped.");
    }

    private void processRequests() {
        while (!upQueue.isEmpty() || !downQueue.isEmpty()) {
            if (direction == Direction.IDLE) {
                direction = !upQueue.isEmpty() ? Direction.UP : Direction.DOWN;
            }

            if (direction == Direction.UP && !upQueue.isEmpty()) {
                Integer next = upQueue.ceiling(currentFloor);
                if (next != null) {
                    moveToFloor(next);
                    upQueue.remove(next);
                } else {
                    direction = !downQueue.isEmpty() ? Direction.DOWN : Direction.IDLE;
                }
            } else if (direction == Direction.DOWN && !downQueue.isEmpty()) {
                Integer next = downQueue.floor(currentFloor);
                if (next != null) {
                    moveToFloor(next);
                    downQueue.remove(next);
                } else {
                    direction = !upQueue.isEmpty() ? Direction.UP : Direction.IDLE;
                }
            } else {
                direction = Direction.IDLE;
            }
        }
    }

    private void moveToFloor(int targetFloor) {
        System.out.println("\n--> Elevator moving " + direction + " to floor " + targetFloor);
        while (currentFloor != targetFloor) {
            if (targetFloor > currentFloor) {
                currentFloor++;
                direction = Direction.UP;
            } else {
                currentFloor--;
                direction = Direction.DOWN;
            }

            System.out.println("   Reached Floor " + currentFloor);
            waitAtFloor();
        }
        System.out.println(":) Doors opening at Floor " + currentFloor);
        allRequests.remove(currentFloor);
    }

    private void waitAtFloor() {
        try {
            Thread.sleep(2000); //  2-second wait
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

public class ElevatorSystem {
    public static void main(String[] args) {
        ElevatorInterface elevator = new Elevator();
        elevator.run();
    }
}
