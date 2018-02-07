package counter;

import filter.FileFilter;
import filter.NullFileFilter;
import utils.ObjectUtils;

import java.io.*;
import java.util.Arrays;

public class CodeCounter extends BaseCodeCounter{
    private File currentFile;
    private StringBuffer countMessage;

    public void init() {
        init(".");
    }

    public void init(String path) {
        super.init();
        currentFile = new File(path);
        countMessage = new StringBuffer();
    }

    public void count() {
        count(new NullFileFilter());
    }

    public void count(FileFilter fileFilter) {
        traverseFile(currentFile, fileFilter);
        notifyCodeCountListenerCountMessage(countMessage.toString());
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
        countMessage.append(file.getName()).append(" : ").append(countLine(file)).append("\n");
    }

    private String countLine(File file) {
        int count = 0;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            while((lineNumberReader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            notifyCodeCountListenerErrorMessage(e.getMessage());
        }
        return String.valueOf(count);
    }
}
