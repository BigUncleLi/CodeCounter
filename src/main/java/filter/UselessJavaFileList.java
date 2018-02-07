package filter;

import java.util.ArrayList;
import java.util.List;

class UselessJavaFileList {
    private static List<String> uselessJavaFileList;

    static {
        uselessJavaFileList = new ArrayList<>();
        uselessJavaFileList.add("R.java");
    }

    static boolean isUseless(String fileName) {
        return uselessJavaFileList.contains(fileName);
    }
}
