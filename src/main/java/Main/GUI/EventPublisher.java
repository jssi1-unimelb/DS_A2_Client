package Main.GUI;

// Jiachen Si 1085839
public interface EventPublisher {
    void addListener(Listener listener);
    default void notifyListener() {};
}
