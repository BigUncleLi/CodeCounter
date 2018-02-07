package counter;

import filter.FileFilter;
import filter.NullFileFilter;
import option.CounterOption;
import option.CounterOptionHandler;
import utils.ObjectUtils;

import java.io.*;
import java.util.Arrays;

public class CodeCounter extends BaseCodeCounter{
    private File currentFile;
    private StringBuffer mCountMessage;
    private CounterOption mCounterOption;
    private int totalCount = 0;

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
        traverseFile(currentFile, fileFilter);
        addTotalCount(mCountMessage);
        notifyCodeCountListenerCountMessage(mCountMessage.toString());
        handleCounterOption(mCounterOption, mCountMessage.toString());
    }

    private void traverseFile(File file, FileFilter fileFilter) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            ObjectUtils.checkNull(files, "files can't be null !");

            Arrays.stream(files)
                    .forEach(currentFile -> traverseFile(currentFile, fileFilter));
        }
        if (fileFilter.accept(file.getName())) {
            countFile(file);
        }
    }

    private void countFile(File file) {
        mCountMessage.append(file.getName()).append(" : ").append(countLine(file)).append("\n");
    }

    private String countLine(File file) {
        int count = 0;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            while((lineNumberReader.readLine()) != null) {
                count++;
                totalCount++;
            }
        } catch (IOException e) {
            notifyCodeCountListenerErrorMessage(e.getMessage());
        }
        return String.valueOf(count);
    }

    private void addTotalCount(StringBuffer mCountMessage) {
        mCountMessage.append("Total line count : ").append(totalCount);
    }

    private void handleCounterOption(CounterOption counterOption, String countMessage) {
        new CounterOptionHandler().handle(counterOption, countMessage);
    }
}
