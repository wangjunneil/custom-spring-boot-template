import com.taobao.eagleeye.EagleEye;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class LogUtil {

    /** 摘要日志的内容分隔符 */
    public static final String SEP = ",";

    /** 修饰符 */
    private static final char RIGHT_TAG = ']';

    /** 修饰符 */
    private static final char LEFT_TAG = '[';

    /**
     * 打印info日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void info(Logger logger, Object... objs) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(objs));
        }
    }

    /**
     * 打印info日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void info(Logger logger, Throwable e, Object... objs) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(objs), e);
        }
    }

    /**
     * 打印warn日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void warn(Logger logger, Object... objs) {
        logger.warn(getLogString(objs));
    }

    /**
     * 打印warn日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void warn(Logger logger, Throwable e, Object... objs) {
        logger.warn(getLogString(objs), e);
    }

    /**
     * 打印error日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void error(Logger logger, Throwable e, Object... objs) {
        if(e == null){
            logger.error(getLogString(objs));
        }else{
            logger.error(getLogString(objs), e);
        }

    }

    /**
     * 打印error日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void error(Logger logger, Object... objs) {
        logger.error(getLogString(objs));
    }

    /**
     * 打印error日志。
     *
     * @param messageName 报警日志的标题
     * @param logger      日志对象
     * @param objs        任意个要输出到日志的参数
     */
    public static void error(String messageName, Logger logger, Object... objs) {
        logger.error(getLogString(messageName, objs));
    }

    /**
     * 打印error日志。
     *
     * @param messageName 报警日志的标题
     * @param logger      日志对象
     * @param objs        任意个要输出到日志的参数
     */
    public static void alert(String messageName, Logger logger, Object... objs) {
        logger.error(getLogString(messageName, objs));
    }

    /**
     * 打印debug日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void debug(Logger logger, Object... objs) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(objs));
        }
    }

    /**
     * 打印debug日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void debug(Logger logger, Throwable e, Object... objs) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(objs), e);
        }
    }

    /**
     * 生成输出到日志的字符串
     * <p>输出格式:[sofaId][messageName]objs...
     *
     * @param messageName 报警日志的标题
     * @param objs        任意个要输出到日志的参数
     *
     * @return 日志字符串
     */
    public static String getLogString(String messageName, Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        log.append(fetchInvokeId()).append(SEP);
        // 预留扩展位
        log.append(SEP).append(RIGHT_TAG);

        log.append(LEFT_TAG).append(messageName).append(RIGHT_TAG);

        if (objs != null) {
            for (Object o : objs) {
                log.append(o);
            }
        }

        return log.toString();
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param objs 任意个要输出到日志的参数
     *
     * @return 日志字符串
     */
    public static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        log.append(StringUtils.isNotBlank(fetchInvokeId()) ? fetchInvokeId() : "-")
                .append(RIGHT_TAG);

        if (objs != null) {
            log.append(" ");
            for (Object o : objs) {
                log.append(o);
            }
        }
        return log.toString();
    }

    /**
     * 获取sofa上下文的调用id。
     *
     * @return 调用id
     */
    public static String fetchInvokeId() {
        return EagleEye.getTraceId();
    }

}
