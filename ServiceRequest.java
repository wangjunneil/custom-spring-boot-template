import com.alibaba.fastjson.JSONObject;

/**
 * 服务请求封装
 */
public class ServiceRequest {

    /**
     * 所属域
     */
    private String domain = "api";

    /**
     * 所属业务
     */
    private String biz;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求体内容
     */
    private String data;

    // ------------------------------------------------------------------------ method

    /**
     * 获取对应匹配服务关键字组合
     *
     * @return
     */
    public String getServiceKey() {
        StringBuffer sb = new StringBuffer();

        sb.append(domain)
                .append("_")
                .append(biz)
                .append("_")
                .append(method);

        return sb.toString();
    }

    /**
     * 解析data数据体
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseData(Class<T> clazz) {
        // 避免在 before 和 execute 中 二次解析
        T tObj = (T) BizContextHolder.get(BizContextHolder.CTX_REQUEST_OBJECT);

        if (tObj == null) {
            tObj = JSONObject.parseObject(data, clazz);
            BizContextHolder.put(BizContextHolder.CTX_REQUEST_OBJECT, tObj);
        }

        return tObj;
    }

    /**
     * Getter method for property <tt>domain</tt>.
     *
     * @return property value of domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param domain value to be assigned to property domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Getter method for property <tt>biz</tt>.
     *
     * @return property value of biz
     */
    public String getBiz() {
        return biz;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param biz value to be assigned to property biz
     */
    public void setBiz(String biz) {
        this.biz = biz;
    }

    /**
     * Getter method for property <tt>method</tt>.
     *
     * @return property value of method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param method value to be assigned to property method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public String getData() {
        return data;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param data value to be assigned to property data
     */
    public void setData(String data) {
        this.data = data;
    }
}
