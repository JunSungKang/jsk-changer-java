package jsk.changer.common;

public enum ReaderType {
    PATH("path", 0),
    FILE("file", 1),
    STRING("string", 2);

    private String name = "";
    private int signal = -1;

    ReaderType(String name, int signal) {
        this.name = name;
        this.signal = signal;
    }

    public String getName() {
        return name;
    }

    public int getSignal() {
        return signal;
    }
}
