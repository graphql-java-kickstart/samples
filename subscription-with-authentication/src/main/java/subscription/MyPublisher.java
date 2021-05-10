package subscription;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

class MyPublisher implements Publisher<Integer> {

  private static final Logger log = LoggerFactory.getLogger(MyPublisher.class);
  private final List<MySubscription> subscriptions =
      Collections.synchronizedList(new ArrayList<>());
  private final CompletableFuture<Void> terminated = new CompletableFuture<>();
  private ExecutorService executor = Executors.newFixedThreadPool(4);

  public void waitUntilTerminated() throws InterruptedException {
    try {
      terminated.get();
    } catch (ExecutionException e) {
      System.out.println(e);
    }
  }

  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {
    MySubscription subscription = new MySubscription(subscriber, executor);

    subscriptions.add(subscription);

    subscriber.onSubscribe(subscription);
  }

  private class MySubscription implements Subscription {

    private final ExecutorService executor;
    private final AtomicInteger value;
    private Subscriber<? super Integer> subscriber;
    private AtomicBoolean isCanceled;

    public MySubscription(Subscriber<? super Integer> subscriber, ExecutorService executor) {
      this.subscriber = subscriber;
      this.executor = executor;

      value = new AtomicInteger();
      isCanceled = new AtomicBoolean(false);
    }

    @Override
    public void request(long n) {
      if (isCanceled.get()) {
        return;
      }

      if (n < 0) {
        executor.execute(() -> subscriber.onError(new IllegalArgumentException()));
      } else {
        publishNextItem();
      }
    }

    @Override
    public void cancel() {
      isCanceled.set(true);

      synchronized (subscriptions) {
        subscriptions.remove(this);
        if (subscriptions.size() == 0) {
          shutdown();
        }
      }
    }

    private void publishNextItem() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      executor.execute(
          () -> {
            for (int i = 0; i < 100; i++) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              int v = value.incrementAndGet();
              log.info("publish item: [{}] with principal {}", v, principal);
              subscriber.onNext(v);
            }
          });
    }

    private void shutdown() {
      log.info("Shut down executor...");
      executor.shutdown();
      newSingleThreadExecutor()
          .submit(
              () -> {
                log.info("Shutdown complete.");
                terminated.complete(null);
              });
    }
  }
}
