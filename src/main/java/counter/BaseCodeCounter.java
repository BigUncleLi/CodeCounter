package counter;

import listener.CodeCounterListener;
import listener.CountResult;
import utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseCodeCounter {
    private List<CodeCounterListener> codeCounterListeners;

    public void init() {
        codeCounterListeners = new ArrayList<>();
    }

    public void addCodeCountListener(CodeCounterListener codeCounterListener) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        if (!codeCounterListeners.contains(codeCounterListener)) {
            codeCounterListeners.add(codeCounterListener);
        }
    }

    public void removeCodeCountListener(CodeCounterListener codeCounterListener) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        if (codeCounterListeners.contains(codeCounterListener)) {
            codeCounterListeners.remove(codeCounterListener);
        }
    }

    public void removeAllCodeCountListener() {
        codeCounterListeners.clear();
    }

    protected void notifyCodeCountListenerCountMessage(CodeCounterListener codeCounterListener,
                                                       CountResult countResult, String countMessage) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        codeCounterListener.onCountResult(countResult, countMessage);
    }

    protected void notifyCodeCountListenerErrorMessage(CodeCounterListener codeCounterListener,
                                                       CountResult countResult, String errorMessage) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        codeCounterListener.onCountResult(countResult, errorMessage);
    }

    protected void notifyCodeCountListenerCountMessage(CountResult countResult, String countMessage) {
        codeCounterListeners.forEach(codeCounterListener -> codeCounterListener.onCountResult(countResult, countMessage));
    }

    protected void notifyCodeCountListenerErrorMessage(String errorMessage) {
        codeCounterListeners.forEach(codeCounterListener -> codeCounterListener.onCountError(errorMessage));
    }

    protected void notifyCodeCountListenerProgress(CodeCounterListener codeCounterListener, String progress) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        codeCounterListener.onProgress(progress);
    }

    protected void notifyCodeCountListenerProgress(String progress) {
        codeCounterListeners.forEach(codeCounterListener -> codeCounterListener.onProgress(progress));
    }
}
