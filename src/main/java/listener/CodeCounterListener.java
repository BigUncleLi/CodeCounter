package listener;

public interface CodeCounterListener {
    void onCountResult(CountResult countResult, String countMessage);
    void onCountError(String errorMessage);
    void onProgress(String progress);
}
