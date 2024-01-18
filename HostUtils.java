import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 主机信息
 */
public class HostUtils {

    private static InetAddress localHost = getHost();

    private static InetAddress getHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static InetAddress getLocalHost() {
        return localHost;
    }

    public static String getHostInfo() {
        return "ip=" + getHostAddress() + ",name=" + getHostName();
    }

    public static String getHostName() {
        if (localHost != null) {
            return localHost.getHostName();
        }
        return "";
    }

    public static String getHostAddress() {
        if (localHost != null) {
            return localHost.getHostAddress();
        }
        return "";
    }

}
