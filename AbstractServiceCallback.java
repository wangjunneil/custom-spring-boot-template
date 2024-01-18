import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServiceCallback<T, R> implements ServiceCallback<T, R> {

    /**
     * 业务日志定义
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceCallback.class);

    /**
     * 前置方法，子类若覆写，应现实调用 super.beforeService 方法， 其中包含品牌权限验证
     *
     * @param request 服务请求对象
     */
    @Override
    public void beforeService(T request) {
        // TODO 鉴权、缓存操作
    }

    @Override
    public void afterService(T request, ServiceResult<R> result) {
        // ignore
    }

    /**
     * 获取业务上下文
     *
     * @return
     */
    protected BizContext getBizContext() {
        return BizContextHolder.get();
    }

}
