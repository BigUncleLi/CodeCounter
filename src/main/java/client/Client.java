package client;

import counter.CodeCounter;
import filter.JavaFileFilter;
import listener.CodeCounterListener;
import listener.CountResult;
import option.CounterOption;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        CodeCounter codeCounter = new CodeCounter();
        codeCounter.init(".");
        codeCounter.setCounterOption(counterOption());
        codeCounter.addCodeCountListener(codeCounterListener);
        Logger.getAnonymousLogger().log(Level.INFO, "start");
        codeCounter.count(new JavaFileFilter());
    }

    private static CounterOption counterOption() {
        CounterOption counterOption = new CounterOption();
        counterOption.setLocalCountFile(true);
        return counterOption;
    }

    private static CodeCounterListener codeCounterListener = new CodeCounterListener() {
        Logger logger = Logger.getAnonymousLogger();

        @Override
        public void onCountResult(CountResult countResult, String countMessage) {
            logger.log(Level.INFO, "onCountResult : " + countResult);
        }

        @Override
        public void onCountError(String errorMessage) {
            logger.log(Level.WARNING, errorMessage);
        }

        @Override
        public void onProgress(String progress) {
            logger.log(Level.WARNING, "progress : " + progress);
        }
    };

}
