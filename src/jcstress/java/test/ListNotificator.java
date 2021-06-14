package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListNotificator implements Notificator {
    private final List<Boolean> isNotified = new ArrayList<>(Collections.singletonList(false));


    @Override
    public boolean isNotified() {
        return isNotified.get(0);
    }

    @Override
    public void setNotified() {
        isNotified.set(0, true);
    }
}
