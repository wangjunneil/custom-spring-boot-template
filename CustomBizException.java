/**
 * MooreException 通用异常，统一的异常定义
 * 不同的业务领域异常，可以通过错误码的分类来实现
 *
 * @author zhejian.wj
 */
public class MooreBizException extends AbstractBizException {

    /** 错误码 */
    private CommonResultCode resultCode;

    /**
     * Constructor.
     */
    public MooreBizException() {
        super();
    }

    /**
     * 构建方法.
     * 但是不建议使用此方法,  优先使用带有resultCode方法.
     *
     * @param msg
     */
    public MooreBizException(String msg) {
        super(msg);
    }
    /**
     * Constructor.
     *
     * @param resultCode the result code
     * @param msg        the msg
     */
    public MooreBizException(CommonResultCode resultCode, String msg) {
        super(msg);
        setResultCode(resultCode);
    }

    @Override
    public void setResultCode(CommonResultCode resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     *
     * @return property value of resultCode
     */
    public CommonResultCode getResultCode() {
        return resultCode;
    }

    @Override
    public String toString(){
        if(resultCode == null){
            return "unknow result code." + super.toString();
        }
        return resultCode.getResultCode() + ":" + resultCode.getResultMessage();
    }

}
