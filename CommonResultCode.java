/**
 * 通用结果接口定义
 */
public interface CommonResultCode {

    /**
     * 获取结果码对应的Code。
     *
     * @return 结果码
     */
    String getResultCode();

    /**
     * 获取结果码对应的描述信息。
     *
     * @return 描述信息
     */
    String getResultMessage();

}
