package listener;

public interface CodeCounterListener {
    void onCountSuccess(String countMessage);
    void onCountError(String errorMessage);
}
