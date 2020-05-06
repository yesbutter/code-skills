import java.util.HashSet;
import java.util.Set;

/**
 *
 * jdk7之前
 * -XX：PermSize=6M -XX:MaxPermSize=6M
 *
 * jdk8
 * VM args: -Xmx=6M -XX:+HeapDumpOnOutOfMemoryError
 */
public class ConstPoolOOM {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        short i = 0;
        while(true)
        {
            set.add(String.valueOf(i++).intern());
        }
    }
}
