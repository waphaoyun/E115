public class Hang
{
    public long address;
    public boolean first;
    public boolean end;
    public int id;
    public int len;
    //新增
    public  int savepath;
    public Hang(long address, boolean first, boolean end, int id, int len,int savepath)//新增
    {
        this.address = address;
        this.first = first;
        this.end = end;
        this.id = id;
        this.len = len;
        this.savepath=savepath;//新增
    }
}