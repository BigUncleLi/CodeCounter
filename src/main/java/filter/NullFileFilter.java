package filter;

public class NullFileFilter implements FileFilter{
    @Override
    public boolean accept(String fileName) {
        return true;
    }
}
