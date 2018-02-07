package client;

import counter.CodeCounter;
import filter.JavaFileFilter;
import listener.CodeCounterListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        CodeCounter codeCounter = new CodeCounter();
        codeCounter.init();
        codeCounter.addCodeCountListener(codeCounterListener);
        codeCounter.count(new JavaFileFilter());
    }

    private static CodeCounterListener codeCounterListener = new CodeCounterListener() {
        Logger logger = Logger.getAnonymousLogger();

        @Override
        public void onCountSuccess(String countMessage) {
            logger.log(Level.INFO, countMessage);
        }

        @Override
        public void onCountError(String errorMessage) {
            logger.log(Level.WARNING, errorMessage);
        }
    };
}
