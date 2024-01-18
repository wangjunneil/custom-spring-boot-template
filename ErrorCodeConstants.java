/**
 * 错误码常量定义
 */
public interface ErrorCodeConstants {

    /** 错误码固定前缀 */
    String PREFIX           = "MOORE_RS_";

    // -------------------------------------- 错误级别定义

    /** 返回码级别-INFO */
    String INFO             = "1";

    /** 返回码级别-WARN */
    String WARN             = "3";

    /** 返回码级别-ERROR */
    String ERROR            = "5";

    /** 返回码级别-FALTAL */
    String FALTAL           = "7";

    /** 返回码类型-业务错误 */
    String BIZ              = "1";

    /** 返回码类型-系统错误 */
    String SYS              = "2";

    /** 返回码类型-第三方错误 */
    String THIRD_PARTY      = "3";

    // -------------------------------------- 按业务域定义

    /** 摩尔服务 */
    String BIZ_MOLE_SERVICE  = "010";

    /** 业务域01 */
    String BIZ_EXAMPLE_01 = "020";

    /** 业务域02 */
    String BIZ_EXAMPLE_02 = "030";
}
