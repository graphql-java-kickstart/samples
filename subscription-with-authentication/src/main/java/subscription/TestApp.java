package subscription;

class TestApp {

  public static void main(String[] args) throws InterruptedException {
    MyPublisher publisher = new MyPublisher();
    MySubscriber subscriberA = new MySubscriber("A");
    MySubscriber subscriberB = new MySubscriber("B");

    publisher.subscribe(subscriberA);
    publisher.subscribe(subscriberB);

    publisher.waitUntilTerminated();
  }

}
