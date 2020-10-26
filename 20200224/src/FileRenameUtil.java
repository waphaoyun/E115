import com.oreilly.servlet.multipart.FileRenamePolicy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class FileRenameUtil implements FileRenamePolicy {

    public File rename(File file) {
        String fileName = file.getName();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");
       // String newName = uuid+extName;//abc.jpg
        String newName = uuid+fileName;//abc.jpg
        file = new File(file.getParent(),newName);
        return file;

    }

}
