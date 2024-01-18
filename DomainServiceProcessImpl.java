import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务处理bean匹配查找处理
 */
public class DomainServiceProcessImpl implements DomainServiceProcess {

    /**
     * 业务日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainServiceProcessImpl.class);

    /**
     * 请求处理器
     */
    private Map<String, ServiceCallback> serviceMap = new HashMap<>();

    /**
     * 服务模板
     */
    @Resource
    private ServiceTemplate serviceTemplate;

    /**
     * 请求处理
     *
     * @param request
     *
     * @return
     */
    @Override
    public ServiceResult process(ServiceRequest request) {
        String serviceKey = request.getServiceKey();

        // 查找匹配服务
        ServiceCallback serviceCallback = serviceMap.get(serviceKey);

        if (serviceCallback == null) {

            // 清除上下文
            BizContextHolder.clearContext();

            // 警告日志输出
            LogUtil.warn(LOGGER,    "can not find process service, " + serviceKey);

            // 错误结果返回
            return ServiceResult.valueOfFail(CommonResultCodeEnum.UN_KNOWN_EXCEPTION);
        }

        // 递交请求处理
        ServiceResult serviceResult = serviceTemplate.serviceProcess(request, serviceCallback);

        return serviceResult;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param serviceMap value to be assigned to property serviceMap
     */
    public void setServiceMap(Map<String, ServiceCallback> serviceMap) {
        this.serviceMap = serviceMap;
    }

}
