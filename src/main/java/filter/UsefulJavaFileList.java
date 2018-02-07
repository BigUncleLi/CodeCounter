package filter;

import java.util.ArrayList;
import java.util.List;

public class UsefulJavaFileList {
    private static List<String> usefulJavaFileList;

    static {
        usefulJavaFileList = new ArrayList<>();
        usefulJavaFileList.add(".java");
        usefulJavaFileList.add(".groovy");
        usefulJavaFileList.add(".gradle");
    }

    static boolean isUseful(String fileName) {
        return usefulJavaFileList.stream().anyMatch(fileName::endsWith);
    }
}
