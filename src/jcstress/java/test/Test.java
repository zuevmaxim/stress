package test;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;

@JCStressTest
@Outcome(id = "1, 2", expect = ACCEPTABLE, desc = "Correct order")
@State
public class Test {
    private final Notificator notificator = new ListNotificator();
    private final Object mutex = new Object();
    private final List<Integer> result = Collections.synchronizedList(new ArrayList<>());

    @Actor
    public void actor1() {
        result.add(1);
        synchronized (mutex) {
            notificator.setNotified();
            mutex.notify();
        }
    }

    @Actor
    public void actor2() {
        synchronized (mutex) {
            while (!notificator.isNotified()) {
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        result.add(2);
    }

    @Arbiter
    public void arbiter(II_Result r) {
        r.r1 = result.get(0);
        r.r2 = result.get(1);
    }
}
