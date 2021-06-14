package test;

public class VariableNotificator implements Notificator {
    private boolean isNotified = false;

    @Override
    public boolean isNotified() {
        return isNotified;
    }

    @Override
    public void setNotified() {
        isNotified = true;
    }
}
