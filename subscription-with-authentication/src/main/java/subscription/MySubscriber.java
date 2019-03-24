package subscription;

import java.util.Random;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MySubscriber implements Subscriber<Integer> {

  private static final Logger log = LoggerFactory.getLogger(MySubscriber.class);

  private static final int DEMAND = 3;
  private static final Random RANDOM = new Random();

  private String name;
  private Subscription subscription;

  private int count;

  public MySubscriber(String name) {
    this.name = name;
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    log.info("Subscribed");
    this.subscription = subscription;

    count = DEMAND;
    requestItems(DEMAND);
  }

  private void requestItems(int n) {
    log.info("Requesting {} new items...", n);
    subscription.request(n);
  }

  @Override
  public void onNext(Integer item) {
    if (item != null) {
      log.info(item.toString());

      synchronized (this) {
        count--;

        if (count == 0) {
          if (RANDOM.nextBoolean()) {
            count = DEMAND;
            requestItems(count);
          } else {
            count = 0;
            log.info("Cancelling subscription...");
            subscription.cancel();
          }
        }
      }
    } else {
      log.info("Null Item!");
    }
  }

  @Override
  public void onComplete() {
    log.info("Complete!");
  }

  @Override
  public void onError(Throwable t) {
    log.info("Subscriber Error >> %s", t);
  }

}
