package option;

import constant.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CounterOptionHandler {
    private FileWriter fileWriter;


    public void handle(CounterOption counterOption, String countMessage) {
        if (counterOption != null) {
            File file = new File(Constant.LOCAL_FILE_NAME);
            initFile(file);
            writeBeginMessage();
            writeCountMessage(countMessage);
            releaseResource();
        }
    }

    private void initFile(File file) {
        if (!file.exists() || existsAndDelete(file)) {
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean existsAndDelete(File file) {
        return file.exists() && file.delete();
    }

    private void writeBeginMessage() {
        writeContent("----------------------\n");
    }

    private void writeCountMessage(String countMessage) {
        writeContent(countMessage);
    }

    private void writeContent(String content) {
        try {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseResource() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
