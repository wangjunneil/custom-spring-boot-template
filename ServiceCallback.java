/**
 * 服务回调接口
 */
public interface ServiceCallback<T, R> {

    /**
     * 服务前置操作
     *
     * @param request 服务请求对象
     */
    void beforeService(T request);

    /**
     * 服务执行
     *
     * @param request 服务请求对象类型
     * @return bizResult {@link ServiceResult}
     */
    ServiceResult<R> executeService(T request);

    /**
     * 服务后置处理
     *
     * @param request 服务请求对象
     * @param result {@link ServiceResult}
     */
    void afterService(T request, ServiceResult<R> result);

}
