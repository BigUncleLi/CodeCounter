package counter;

import listener.CodeCounterListener;
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

    protected void notifyCodeCountListenerCountMessage(CodeCounterListener codeCounterListener, String countMessage) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        codeCounterListener.onCountSuccess(countMessage);
    }

    protected void notifyCodeCountListenerErrorMessage(CodeCounterListener codeCounterListener, String errorMessage) {
        ObjectUtils.checkNull(codeCounterListener, "codeCounterListener can't be null !");
        codeCounterListener.onCountSuccess(errorMessage);
    }

    protected void notifyCodeCountListenerCountMessage(String countMessage) {
        codeCounterListeners.forEach(codeCounterListener -> codeCounterListener.onCountSuccess(countMessage));
    }

    protected void notifyCodeCountListenerErrorMessage(String errorMessage) {
        codeCounterListeners.forEach(codeCounterListener -> codeCounterListener.onCountError(errorMessage));
    }
}
