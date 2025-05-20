// Jiachen Si 1085839
package Main.GUI;

public interface EventPublisher {
    void addListener(Listener listener);
    default void notifyListener() {};
}
