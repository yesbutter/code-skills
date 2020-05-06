import java.util.ArrayList;
import java.util.List;

/**
 * 设置运行参数 -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 限制Java堆大小20MB，不可扩展 。抛出异常时自动Dump内存快照，方便分析
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<>();
        while (true)
        {
            list.add(new OOMObject());
        }
    }
}
