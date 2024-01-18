/**
 * 业务上下文
 */
public final class BizContextHolder {

    // 上下文常量定义
    public static final String CTX_HTTP_REQUEST             = "HTTP_REQUEST";
    public static final String CTX_HTTP_RESPONSE            = "HTTP_RESPONSE";

    // 对象级常量定义
    public static final String CTX_REQUEST_OBJECT           = "CTX_REQUEST_OBJECT";

    /**
     * Thread Local
     */
    private static final ThreadLocal<BizContext> CONTEXT_LOCAL = new ThreadLocal<>();

    /**
     * 私有构造
     */
    private BizContextHolder() {}

    /**
     * 初始化业务上下文
     */
    public static void initContext() {
        if (get() == null) {
            BizContext bizContext = new BizContext();
            set(bizContext);
        }
    }

    /**
     * 设置业务上下文到当前线程中
     *
     * @param bizContext
     */
    public static void set(BizContext bizContext) {
        CONTEXT_LOCAL.set(bizContext);
    }

    /**
     * 从当前线程获取业务上下文
     *
     * @return
     */
    public static BizContext get() {
        return CONTEXT_LOCAL.get();
    }

    /**
     * 清理业务上下文
     */
    public static void clearContext() {
        if (get() == null) {
            return;
        }

        CONTEXT_LOCAL.remove();
    }

    /**
     * 设置特定对象到上下文中。
     *
     * @param key   对象名称
     * @param value 对象内容
     */
    public static void put(String key, Object value) {
        if (get() != null) {
            get().putObject(key, value);
        }
    }

    /**
     * 设置特定对象到上下文中。
     *
     * @param key   对象名称
     * @return 对象内容
     */
    public static Object get(String key) {
        if (get() != null) {
            return get().getObject(key);
        }
        return null;
    }
}
