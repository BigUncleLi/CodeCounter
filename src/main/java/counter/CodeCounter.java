package counter;

import constant.Constant;
import filter.FileFilter;
import filter.NullFileFilter;
import listener.CountResult;
import option.CounterOption;
import option.CounterOptionHandler;
import utils.ObjectUtils;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CodeCounter extends BaseCodeCounter{
    private File currentFile;
    private StringBuffer mCountMessage;
    private CounterOption mCounterOption;
    private int totalLineCount = 0;
    private double totalFileCount = 0;
    private double currentFileCount = 0;

    public void init() {
        init(".");
    }

    public void init(String path) {
        super.init();
        currentFile = new File(path);
        mCountMessage = new StringBuffer();
    }

    public void setCounterOption(CounterOption counterOption) {
        mCounterOption = counterOption;
    }

    public void count() {
        count(new NullFileFilter());
    }

    public void count(FileFilter fileFilter) {
        traverseFileCalculateCount(currentFile, fileFilter);
        notifyCodeCountListenerCountMessage(CountResult.INIT_DONE, "");
        startProgressThread();
        traverseFile(currentFile, fileFilter);
        addTotalCount(mCountMessage);
        onCounterFinish();
        handleCounterOption(mCounterOption, mCountMessage.toString());
    }


    private void traverseFileCalculateCount(File file, FileFilter fileFilter) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            ObjectUtils.checkNull(files, "files can't be null !");

            Arrays.stream(files)
                    .forEach(currentFile -> traverseFileCalculateCount(currentFile, fileFilter));
        }
        if (!file.isDirectory() && fileFilter.accept(file.getName())) {
            totalFileCount++;
        }
    }


    private ScheduledExecutorService scheduledExecutorService;
    private void startProgressThread() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                ()-> notifyCodeCountListenerProgress(String.format("%.2f", currentFileCount / totalFileCount)),
                500, 500, TimeUnit.MILLISECONDS);
    }

    private void traverseFile(File file, FileFilter fileFilter) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            ObjectUtils.checkNull(files, "files can't be null !");

            Arrays.stream(files)
                    .forEach(currentFile -> traverseFile(currentFile, fileFilter));
        }
        if (!file.isDirectory() && fileFilter.accept(file.getName())) {
            countFile(file);
        }
    }

    private void countFile(File file) {
        mCountMessage.append(file.getAbsolutePath()).append(" : ").append(countLine(file)).append("\n");
    }

    private String countLine(File file) {
        int currentLineCount = 0;
        LineNumberReader lineNumberReader = null;
        try {
            lineNumberReader = new LineNumberReader(new FileReader(file));
            while((lineNumberReader.readLine()) != null) {
                currentLineCount++;
                totalLineCount++;
            }
            currentFileCount++;
        } catch (IOException e) {
            notifyCodeCountListenerErrorMessage(e.getMessage());
        } finally {
            try {
                if (lineNumberReader != null) {
                    lineNumberReader.close();
                }
            } catch (IOException e) {
                notifyCodeCountListenerErrorMessage(e.getMessage());
            }
        }
        return String.valueOf(currentLineCount);
    }

    private void addTotalCount(StringBuffer mCountMessage) {
        mCountMessage.append(Constant.LAST_LINE_PREFIX).append(totalLineCount);
    }

    private void onCounterFinish() {
        scheduledExecutorService.shutdown();
        notifyCodeCountListenerProgress(String.valueOf(1));
        notifyCodeCountListenerCountMessage(CountResult.COUNT_FINISH, mCountMessage.toString());
    }

    private void handleCounterOption(CounterOption counterOption, String countMessage) {
        new CounterOptionHandler().handle(counterOption, countMessage);
    }
}
