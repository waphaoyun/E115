import java.util.Date;

public class EcuFileInfo {
  //  ecu_name,time,version,file_url,file_name,for_check,verified,verifyfail,description
  private   String ecu_name;
   private Date time;
   private String version;
    private   String file_url;
    private  String file_name;
    private  String for_check;
    private   short verified;
    private  short verifyfail;
    private String description;
    private  int id;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFor_check() {
        return for_check;
    }

    public void setFor_check(String for_check) {
        this.for_check = for_check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEcu_name() {
        return ecu_name;
    }

    public void setEcu_name(String ecu_name) {
        this.ecu_name = ecu_name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public short getVerified() {
        return verified;
    }

    public void setVerified(short verified) {
        this.verified = verified;
    }

    public short getVerifyfail() {
        return verifyfail;
    }

    public void setVerifyfail(short verifyfail) {
        this.verifyfail = verifyfail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EcuFileInfo{" +
                "ecu_name='" + ecu_name + '\'' +
                ", time=" + time +
                ", version='" + version + '\'' +
                ", file_url='" + file_url + '\'' +
                ", file_name='" + file_name + '\'' +
                ", verified=" + verified +
                ", verifyfail=" + verifyfail +
                ", description='" + description + '\'' +
                '}';
    }
}
