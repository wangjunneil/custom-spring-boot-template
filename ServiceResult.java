/**
 * 请求服务结果
 * @author zhejian.wj
 */
public class ServiceResult<T> implements CommonResultCode {

    /**
     * 返回结果实例
     */
    protected T data;

    /**
     * 业务是否处理成功
     */
    protected boolean success;

    /**
     * 业务结果码
     */
    protected String resultCode = "";

    /**
     * 业务结果消息
     */
    protected String resultMessage = "";


    /**
     * 返回失败结果
     *
     * @param resultCode
     * @return
     */
    public static <T> ServiceResult<T> valueOfFail(CommonResultCode resultCode) {
        ServiceResult<T> serviceResult = new ServiceResult<>();
        serviceResult.success = false;
        serviceResult.resultCode = resultCode.getResultCode();
        serviceResult.resultMessage = resultCode.getResultMessage();
        return serviceResult;
    }

    /**
     * 返回成功结果
     *
     * @param <T>
     * @return
     */
    public static <T> ServiceResult<T> valueOfSuccess(T data) {
        ServiceResult<T> serviceResult = new ServiceResult<>();
        serviceResult.setResultCode(CommonResultCodeEnum.SUCCESS.getResultCode());
        serviceResult.setResultMessage(CommonResultCodeEnum.SUCCESS.getResultMessage());
        serviceResult.success = true;
        serviceResult.setData(data);
        return serviceResult;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param data value to be assigned to property data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param resultMessage value to be assigned to property resultMsg
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }
}
