import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * 使用方法：
 *
 * 在本地上下文中定义AssertUtils的bean，并注入 exceptionClassName
 * 集成了CommonException抽象类的异常类路径
 *
 * 例如:
 * <bean class="com.xxxx.xxxx.xxxx.assertion.AssertUtil">
 * 		<property name="exceptionClassName">
 * 			<value>com.xxx.xxx.xxx.xxx.CustomBizException</value>
 * 		</property>
 * </bean>
 */
public class AssertUtils {

    /**
     * 异常类Class名称
     */
    private static String exceptionClassName;

    /**
     * 异常对象构造方法
     */
    private static Constructor constructor;

    /**
     * 断言表达式的值为true，否则抛出指定错误信息。
     *
     * @param expValue          断言表达式
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void isTrue(final boolean expValue, final CommonResultCode resultCode,
                              final Object... objs) {

        if (!expValue) {

            AbstractBizException exception = null;
            String logString = getLogString(objs);
            String resultMsg = StringUtils.isBlank(logString) ? resultCode.getResultMessage()
                    : logString;

            try {
                exception = (AbstractBizException) constructor.newInstance(resultMsg);
            } catch (Throwable e) {
                throw new IllegalArgumentException(
                        "AssertUtil has not been initiallized correctly![exceptionClassName="
                                + exceptionClassName + ",resultCode=" + resultCode + ",resultMsg="
                                + resultMsg + "]", e);
            }

            exception.setResultCode(resultCode);
            throw exception;
        }
    }

    /**
     * 断言表达式的值为false，否则抛出指定错误信息。
     *
     * @param expValue          断言表达式
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void isFalse(final boolean expValue, final CommonResultCode resultCode,
                               final Object... objs) {
        isTrue(!expValue, resultCode, objs);
    }

    /**
     * 断言两个对象相等，否则抛出指定错误信息。
     *
     * @param obj1              待比较对象
     * @param obj2              待比较对象
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void equals(final Object obj1, final Object obj2,
                              final CommonResultCode resultCode, final Object... objs) {

        isTrue(obj1 == null ? obj2 == null : obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言两个对象不等，否则抛出指定错误信息。
     *
     * @param obj1              待比较对象
     * @param obj2              待比较对象
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void notEquals(final Object obj1, final Object obj2,
                                 final CommonResultCode resultCode, final Object... objs) {

        isTrue(obj1 == null ? obj2 != null : !obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言两个对象相同，否则抛出指定错误信息。
     *
     * @param base          待比较对象
     * @param target        待比较对象
     * @param resultCode    错误码
     * @param objs          任意个异常描述信息的参数
     */
    public static void is(final Object base, final Object target,
                          final CommonResultCode resultCode, final Object... objs) {

        isTrue(base == target, resultCode, objs);
    }

    /**
     * 断言两个对象不相同，否则抛出指定错误信息。
     *
     * @param base          待比较对象
     * @param target        待比较对象
     * @param resultCode    错误码
     * @param objs          任意个异常描述信息的参数
     */
    public static void isNot(final Object base, final Object target,
                             final CommonResultCode resultCode, final Object... objs) {

        isTrue(base != target, resultCode, objs);
    }

    /**
     * 断言指定对象在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"equals"，不是指对象=。
     *
     * @param base              待查对象
     * @param collection        容器集合
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void contains(final Object base, final Collection<?> collection,
                                final CommonResultCode resultCode, final Object... objs) {
        notEmpty(collection, resultCode, objs);
        isTrue(collection.contains(base), resultCode, objs);
    }

    /**
     * 断言指定对象在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"="，不是指对象equals。
     *
     * @param base              待查对象
     * @param collection        容器集合
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void in(final Object base, final Object[] collection,
                          final CommonResultCode resultCode, final Object... objs) {

        notNull(collection, resultCode, objs);

        boolean hasEqual = false;
        for (Object obj2 : collection) {

            if (base == obj2) {
                hasEqual = true;
                break;
            }
        }

        isTrue(hasEqual, resultCode, objs);
    }

    /**
     * 断言指定对象不在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"="，不是指对象equals。
     *
     * @param base          待查对象
     * @param collection    容器集合
     * @param resultCode    错误码
     * @param objs          任意个异常描述信息的参数
     */
    public static void notIn(final Object base, final Object[] collection,
                             final CommonResultCode resultCode, final Object... objs) {

        if (null == collection) {
            return;
        }

        for (Object obj2 : collection) {
            isTrue(base != obj2, resultCode, objs);
        }
    }

    /**
     * 断言对象为空，否则抛出指定错误信息。
     *
     * @param str               断言字符串
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void blank(final String str, final CommonResultCode resultCode,
                             final Object... objs) {

        isTrue(StringUtils.isBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为非空，否则抛出指定错误信息。
     *
     * @param str               断言字符串
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void notBlank(final String str, final CommonResultCode resultCode,
                                final Object... objs) {

        isTrue(StringUtils.isNotBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为null，否则抛出指定错误信息。
     *
     * @param object            待检查对象
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void isNull(final Object object, final CommonResultCode resultCode,
                              final Object... objs) {

        isTrue(object == null, resultCode, objs);
    }

    /**
     * 断言对象非null，否则抛出指定错误信息。
     *
     * @param object            待检查对象
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void notNull(final Object object, final CommonResultCode resultCode,
                               final Object... objs) {

        isTrue(object != null, resultCode, objs);
    }

    /**
     * 断言集合不为空或null，否则抛出指定错误信息。
     *
     * @param collection        待检查集合
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void notEmpty(final Collection collection, final CommonResultCode resultCode,
                                final Object... objs) {

        isTrue(collection != null && collection.size() != 0, resultCode, objs);
    }

    /**
     * 断言集合为空或null，否则抛出指定错误信息。
     *
     * @param collection        待检查集合
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void empty(final Collection collection, final CommonResultCode resultCode,
                             final Object... objs) {
        isTrue(collection == null || collection.size() == 0, resultCode, objs);
    }

    /**
     * 断言map不为空或null，否则抛出指定错误信息。
     *
     * @param map               待检查map
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void notEmpty(final Map map, final CommonResultCode resultCode,
                                final Object... objs) {

        isTrue(map != null && map.size() != 0, resultCode, objs);
    }

    /**
     * 断言map为空或null，否则抛出指定错误信息。
     *
     * @param map               待检查map
     * @param resultCode        错误码
     * @param objs              任意个异常描述信息的参数
     */
    public static void empty(final Map map, final CommonResultCode resultCode,
                             final Object... objs) {

        //isTrue(CollectionUtils.isEmpty(map), resultCode, objs);
        isTrue(map == null || map.size() == 0, resultCode, objs);
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param objs      任意个要输出到日志的参数
     * @return          日志字符串
     */
    private static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();

        for (Object o : objs) {
            log.append(o);
        }
        return log.toString();
    }

    /**
     * 初始化AssertUtil配置。
     */
    @SuppressWarnings("unchecked")
    private static void initConfig() {

        Class exceptionClassTmp = null;

        // 1,加载异常类
        if (StringUtils.isBlank(exceptionClassName)) {
            throw new IllegalArgumentException("exceptionClassName has not set!");
        }

        try {
            exceptionClassTmp = Class.forName(exceptionClassName);
        } catch (Throwable e) {
            throw new IllegalArgumentException("loading exceptionClass failed![exceptionClassName="
                    + exceptionClassName + "]", e);
        }

        // 必须是AssertException的子类
        if (!AbstractBizException.class.isAssignableFrom(exceptionClassTmp)) {
            throw new IllegalArgumentException(
                    "illegal exceptionClass type, must be the subclass of MarketCommonException![exceptionClassName="
                            + exceptionClassName + "]");
        }

        Constructor constructorTmp = null;

        // 2,获取构造方法
        try {
            constructorTmp = exceptionClassTmp.getConstructor(String.class);
        } catch (Throwable e) {
            throw new IllegalArgumentException("constructor method not found![exceptionClassName="
                    + exceptionClassName + "]", e);
        }

        // 3,缓存反射结果
        constructor = constructorTmp;
    }

    /**
     * Setter method for property <tt>exceptionClassName</tt>.
     *
     * @param exceptionClassName value to be assigned to property exceptionClassName
     */
    public void setExceptionClassName(String exceptionClassName) {
        AssertUtils.exceptionClassName = exceptionClassName;
        initConfig();
    }
}
