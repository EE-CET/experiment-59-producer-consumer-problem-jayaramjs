class SharedResource {
    int item;
    boolean available = false;

    // Method for the Producer
    synchronized void put(int item) {
        // If an item is already available, wait for the consumer to take it
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        this.item = item;
        available = true;
        System.out.println("Produced: " + item);
        // Wake up the consumer thread
        notify();
    }

    // Method for the Consumer
    synchronized void get() {
        // If no item is available, wait for the producer to make one
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Consumed: " + item);
        available = false;
        // Wake up the producer thread
        notify();
    }
}

class Producer extends Thread {
    SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        // Start both threads
        p.start();
        c.start();
    }
}
