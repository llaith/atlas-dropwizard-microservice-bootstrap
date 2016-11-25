package tkt.util;

import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.List;

/**
 *
 */
public class ObservableUtil {

    public static <T> List<T> testSubscriptionOn(final Observable<T> obs) {

        final TestSubscriber<T> testSubscriber = new TestSubscriber<>();

        obs.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();

        return testSubscriber.getOnNextEvents();

    }

    public static <T> T firstFrom(final List<T> list) {

        return list != null ?
                list.get(0) :
                null;

    }

}
