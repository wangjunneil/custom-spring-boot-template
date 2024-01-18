/**
 * 公共异常对象
 *
 * 注意：子类继承此异常对象，必须实现默认构造方法，和带异常描述参数的构造方法。即：XXXX(String msg)。
 * 另外：子类中使用的结果码，必须实现CommonResultCode接口
 */
public abstract class AbstractBizException extends RuntimeException {

    /**
     * 构造一个<code>CommonBizException</code>对象。
     */
    public AbstractBizException() {
    }

    /**
     * 构造一个<code>CommonBizException</code>对象。
     *
     * @param msg           异常描述
     */
    public AbstractBizException(String msg) {
        super(msg);
    }

    /**
     * 构造一个<code>IpayCommonException</code>对象。
     *
     * @param cause         异常原因
     */
    public AbstractBizException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个<code>IpayCommonException</code>对象。
     *
     * @param msg           异常描述
     * @param cause         异常原因
     */
    public AbstractBizException(String msg, Throwable cause) {
        super(msg, cause);
    }

    // ~~~ 抽象方法

    /**
     * 设置错误枚举信息
     *
     * @param resultCode 错误码
     */
    public abstract void setResultCode(CommonResultCode resultCode);
}
