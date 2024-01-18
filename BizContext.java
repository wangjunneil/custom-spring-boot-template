import java.util.HashMap;
import java.util.Map;

public class BizContext {

    /**
     * 请求时间
     */
    private long startTime;

    /**
     * 用于存放特殊缓存变量的备用map
     */
    private final Map<String, Object> objectMap = new HashMap<>();

    /**
     * 默认构造方法
     */
    public BizContext() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Getter method for property <tt>startTime</tt>.
     *
     * @return property value of startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 取出缓存变量。
     *
     * @param key 缓存key
     *
     * @return 缓存value
     */
    public Object getObject(String key) {
        return objectMap.get(key);
    }

    /**
     * 放入缓存变量。
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void putObject(String key, Object value) {
        objectMap.put(key, value);
    }

}
