package concurrency.synchronizers.semophores;

/**
 * Idea:
 Initial state: semaphoreProducer permit=1 | semaphoreConsumer permit=0
 semaphoreProducer created with permit=1. So, that producer can get the permit to produce
 semaphoreConsumer created with permit=0. So, that consumer could wait for permit to consume.

 semaphoreProducer.acquire() is called, Producer has got the permit and it can produce [Now, semaphoreProducer permit=0]
 Produced : 1   [as producer has got permit, it is producing]
 semaphoreConsumer.release() is called, Permit has been released on semaphoreConsumer means consumer can consume [Now, semaphoreConsumer permit=1]

 semaphoreConsumer.acquire() is called, Consumere has got the permit and it can consume [Now, semaphoreConsumer permit=0]
 Consumed : 1 [as consumer has got permit, it is consuming]
 semaphoreProducer.release() is called, Permit has been released on semaphoreProducer means producer can produce [Now, semaphoreProducer permit=1]
 */
public class ProducerConsumerSemaphore {

    public static void main(String[] args) {

        CustomSemaphore producerSemaphore = new CustomSemaphore(1);
        CustomSemaphore consumerSemaphore = new CustomSemaphore(0);

        Thread producer = new Thread(new Producer(producerSemaphore,consumerSemaphore));
        Thread consumer = new Thread(new Consumer(producerSemaphore,consumerSemaphore));

        producer.start(); consumer.start();

    }
}

class Producer implements Runnable{

    CustomSemaphore producerSemaphore;
    CustomSemaphore consumerSemaphore;

    Producer(CustomSemaphore producerSemaphore, CustomSemaphore consumerSemaphore){
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemaphore = producerSemaphore;
    }

    public void run() {
        for(int i = 0; i< 10 ; i++){
            try {
                producerSemaphore.acquire();  // acquire producer permit
                System.out.println("produced: "+i);
                consumerSemaphore.release();  // release consumer permit so they can start consuming.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    CustomSemaphore producerSemaphore;
    CustomSemaphore consumerSemaphore;

    Consumer(CustomSemaphore producerSemaphore, CustomSemaphore consumerSemaphore){
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemaphore = producerSemaphore;
    }

    public void run() {
        for(int i = 0; i< 10 ; i++){
            try {
                consumerSemaphore.acquire(); // acquire consumer permit
                System.out.println("consumed: "+i);
                producerSemaphore.release(); // release producer permit to produce
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
