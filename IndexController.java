import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求入口
 */
@Controller
public class IndexController {

    @Resource
    private DomainServiceProcess domainServiceProcess;

    // ------------------------------------------------------------------------------------------- public method

    /**
     * TMAP请求入口
     *
     * @param request HttpServletRequest
     * @param biz     业务域
     * @param method  请求方法
     * @param data    请求体
     *
     * @return 响应内容
     */
    @RequestMapping(value = {"/api/{biz}/{method}.do"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ServiceResult invoke(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("biz") String biz,
            @PathVariable("method") String method,
            @RequestBody(required = false) String data) {

        // 初始化上下文
        BizContextHolder.initContext();
        BizContextHolder.put(BizContextHolder.CTX_HTTP_REQUEST, request);
        BizContextHolder.put(BizContextHolder.CTX_HTTP_RESPONSE, response);

        // 模型组装
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setBiz(biz);
        serviceRequest.setMethod(method);
        serviceRequest.setData(data);

        // 请求处理
        return domainServiceProcess.process(serviceRequest);
    }

}
