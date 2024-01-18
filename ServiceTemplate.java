import com.alibaba.fastjson.JSONObject;
import com.taobao.eagleeye.EagleEye;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务处理模板
 */
@Service("serviceTemplate")
public class ServiceTemplate {

    /**
     * 服务日志定义
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTemplate.class);

    /**
     * 请求摘要日志
     */
    private static final Logger HTTP_DIGEST_LOG = LoggerFactory.getLogger("HTTP_DIGEST_LOG");

    /**
     * 模板处理
     *
     * @param <T>      服务请求对象类型
     * @param <R>      服务处理结果类型
     * @param request  服务请求对象
     * @param callback 回调服务
     *
     * @return bizResult    {@link ServiceResult}
     */
    public <T, R> ServiceResult<R> serviceProcess(T request, ServiceCallback<T, R> callback) {

        ServiceResult<R> result = null;

        try {
            // 请求初始化
            initialize(request);

            // 服务前置处理
            callback.beforeService(request);

            // 服务处理
            result = callback.executeService(request);

        } catch (MooreBizException e) {

            // 业务异常处理
            result = handleBizException(e, request);

        } catch (Exception e) {

            // 系统异常处理
            result = handleSystemException(e, request);

        } catch (Throwable t) {
            // 系统异常处理
            result = handleSystemException(t, request);

        }
        finally {

            // 服务后置处理
            try {
                callback.afterService(request, result);
            } catch (Exception e) {
                LogUtil.error(LOGGER, e, "callback afterService failure");
            }

            // 最终处理
            doFinnalProcess(result);

            // 清理上下文
            BizContextHolder.clearContext();
        }

        return result;
    }

    /**
     * 业务异常处理
     *
     * @param <T>          request类型
     * @param <R>          result类型
     * @param bizException {@link MooreBizException}
     * @param request      请求对象
     *
     * @return bizResult    返回结果
     */
    private <T, R> ServiceResult<R> handleBizException(MooreBizException bizException,
                                                       T request) {

        // 打印日志
        LogUtil.error(LOGGER, bizException, "request error [errorCode=", bizException.getResultCode().getResultCode(),
                ",errorMsg=", bizException.getResultCode().getResultMessage(), "]");

        return ServiceResult.valueOfFail(bizException.getResultCode());
    }

    /**
     * 系统异常处理，打印error日志
     *
     * @param <T>       request类型
     * @param <R>       result类型
     * @param exception {@link Exception}
     * @param request   请求对象
     *
     * @return bizResult     结果对象
     */
    private <T, R> ServiceResult<R> handleSystemException(Exception exception, T request) {

        // 打印日志
        LogUtil.error(LOGGER, exception, "request error [errorCode=", CommonResultCodeEnum.UN_KNOWN_EXCEPTION.getResultCode(),
                ",errorMsg=", CommonResultCodeEnum.UN_KNOWN_EXCEPTION.getResultMessage(), "]");

        return ServiceResult.valueOfFail(CommonResultCodeEnum.UN_KNOWN_EXCEPTION);
    }

    /**
     * 系统异常处理，打印error日志
     *
     * @param <T>       request类型
     * @param <R>       result类型
     * @param t {@link Exception}
     * @param request   请求对象
     *
     * @return bizResult     结果对象
     */
    private <T, R> ServiceResult<R> handleSystemException(Throwable t, T request) {

        // 打印日志
        LogUtil.error(LOGGER, t, "request error [errorCode=", CommonResultCodeEnum.UN_KNOWN_EXCEPTION.getResultCode(),
                ",errorMsg=", CommonResultCodeEnum.UN_KNOWN_EXCEPTION.getResultMessage(), "]");

        return ServiceResult.valueOfFail(CommonResultCodeEnum.UN_KNOWN_EXCEPTION);
    }

    /**
     * 获取HTTP请求对象
     *
     * @return
     */
    private HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) BizContextHolder.get(BizContextHolder.CTX_HTTP_REQUEST);
    }

    /**
     * 获取HTTP响应对象
     *
     * @return
     */
    private HttpServletResponse getHttpResponse() {
        return (HttpServletResponse) BizContextHolder.get(BizContextHolder.CTX_HTTP_RESPONSE);
    }

    /**
     * 请求链路跟踪
     */
    private void requestTrace() {
        getHttpResponse().addHeader("Trace-Id", EagleEye.getTraceId());
        // getHttpResponse().addHeader("Trace-Host", IPUtils.getHost());
    }

    /**
     * 最终处理
     *
     * @param bizResult
     */
    private void doFinnalProcess(ServiceResult bizResult) {

        // 链路跟踪设置
        requestTrace();

        // 打印摘要日志
        printBizDigest(bizResult);
    }

    /**
     * 请求前置初始化
     */
    private <T> void initialize(T request) {
        // 请求日志
        ServiceRequest serviceRequest = (ServiceRequest) request;
        LogUtil.info(LOGGER, serviceRequest.getServiceKey(), ",", StringUtils.isNotBlank(serviceRequest.getData()) ?
                JSONObject.parseObject(serviceRequest.getData()) : "-");
    }

    /**
     * HTTP digest日志
     *
     * @param bizResult
     */
    private void printBizDigest(ServiceResult bizResult) {

        try {
            BizContext bizContext = BizContextHolder.get();

            StringBuffer buffer = new StringBuffer(256);

            // 业务名称
            buffer.append(" ").append("MOORE").append(",");

            // 客户端地址
            buffer.append(StringUtils.isNotBlank(getHttpRequest().getRemoteAddr()) ? getHttpRequest().getRemoteAddr()
                            : "-")
                    .append(",");

            // 请求方法
            buffer.append(StringUtils.isNotBlank(getHttpRequest().getMethod()) ? getHttpRequest().getMethod() : "-")
                    .append(",");

            // 请求路径
            buffer.append(StringUtils.isNotBlank(getHttpRequest().getRequestURI()) ? getHttpRequest().getRequestURI()
                            : "-")
                    .append(",");

            // 响应状态吗
            buffer.append(getHttpResponse().getStatus())
                    .append(",");

            // 用户Id
            buffer.append("user").append(",");

            // 耗时
            buffer.append(System.currentTimeMillis() - bizContext.getStartTime())
                    .append(",");

            // 成功 or 失败
            buffer.append(bizResult != null && bizResult.getSuccess() ? "Y" :
                            "N")
                    .append(",");

            // 错误码
            buffer.append(bizResult != null && !bizResult.getSuccess() ? bizResult.getResultCode() : "-")
                    .append(",");

            // 失败信息
            buffer.append(bizResult != null && !bizResult.getSuccess() ? bizResult.getResultMessage() : " ")
                    .append(",");

            // 服务端ip地址
            buffer.append(HostUtils.getHostAddress())
                    .append(",");

            LogUtil.info(HTTP_DIGEST_LOG, buffer.toString());

        } catch (Exception e) {
            LogUtil.error(LOGGER, e, " print digest log error");
        }

    }
}
