import java.util.List;

public class xmldatacrc {
    List<String> fileNamex;
    boolean crc=false;

    public List<String> getFileNamex() {
        return fileNamex;
    }

    public void setFileNamex(List<String> fileNamex) {
        this.fileNamex = fileNamex;
    }

    public boolean isCrc() {
        return crc;
    }

    public void setCrc(boolean crc) {
        this.crc = crc;
    }
}
