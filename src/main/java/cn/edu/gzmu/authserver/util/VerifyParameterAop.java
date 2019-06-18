package cn.edu.gzmu.authserver.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.ValidationException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author echo
 * @version 1.0
 * @date 19-5-22 14:54
 */
@Aspect
@Component
public class VerifyParameterAop {
    private static final String FIELD_MARK = "|";
    private static final String CONDITION_MARK = "-";
    private static final String TIP_MARK = "#";

    private JSONObject param;

    /**
     * 切点
     */
    @Pointcut("@annotation(cn.edu.gzmu.authserver.util.VerifyParameter)")
    public void verifyParameter() {
    }

    /**
     * verify 验证
     *
     * @param joinPoint 切点
     * @throws NoSuchMethodException 异常
     */
    @Before("verifyParameter()")
    public void verify(JoinPoint joinPoint) throws NoSuchMethodException {
        Object target = joinPoint.getTarget();
        Method method = target.getClass().getMethod(joinPoint.getSignature().getName(), JSONObject.class);
        param = (JSONObject) joinPoint.getArgs()[0];
        VerifyParameter annotation = method.getAnnotation(VerifyParameter.class);
        check(annotation.required(), (condition, value) -> Objects.nonNull(value), () -> " 为必填项！");
        check(annotation.range(), (condition, value) -> value instanceof Long
                        && ((Long) value > getRangeLeft(condition)
                        && (Long) value < getRangeRight(condition)),
                () -> " 不存在或不在指定大小范围内！");
        check(annotation.size(), (condition, value) -> Objects.nonNull(value)
                        && value.toString().length() < getRangeRight(condition)
                        && value.toString().length() > getRangeLeft(condition),
                () -> " 不存在或长度不合法！");
        check(annotation.max(), (condition, value) -> Objects.nonNull(value)
                        && Long.parseLong(value.toString()) < getMax(condition),
                () -> " 不存在或数值过大！");
        check(annotation.min(), (condition, value) -> Objects.nonNull(value)
                        && Long.parseLong(value.toString()) < getMin(condition),
                () -> " 不存在或数值过小！");
        check(annotation.number(), (condition, value) -> Objects.nonNull(value)
                        && StringUtils.isNumeric(value.toString()),
                () -> " 不存在或不为数字类型！");
        check(annotation.equal(), (condition, value) -> Objects.nonNull(value)
                        && value.toString().equals(getCondition(condition)),
                () -> " 的值不合法！");
    }

    /**
     * 对其参数进行检查
     *
     * @param expressions 检查的表达式
     * @param condition   条件
     * @param defaultTip  默认提示信息
     */
    private void check(String[] expressions, VerifyParameterPredicate<String, Object> condition,
                       Supplier<String> defaultTip) {
        for (String expression : expressions) {
            if (StringUtils.isEmpty(expression)) {
                continue;
            }
            String field = getField(expression);
            Object value = getValue(field);
            String tip = getTip(expression.trim());
            if (!condition.test(getCondition(expression), value)) {
                throw new ValidationException(Objects.isNull(tip)
                        ? field + defaultTip.get()
                        : tip);
            }
        }
    }

    /**
     * 获取表达式中的字段
     *
     * @param expression 表达式
     * @return 字段
     */
    private String getField(String expression) {
        return (StringUtils.substringBefore
                (StringUtils.substringBefore(
                        expression,
                        FIELD_MARK),
                        TIP_MARK)).trim();
    }

    /**
     * 获取表达式中的条件
     *
     * @param expression 表达式
     * @return 条件
     */
    private String getCondition(String expression) {
        return (expression.contains(FIELD_MARK) || expression.contains(TIP_MARK))
                ?
                (StringUtils.substringAfter
                        (StringUtils.substringBefore(
                                expression,
                                TIP_MARK),
                                FIELD_MARK)).trim()
                : null;
    }

    /**
     * 获取表达式中的自定义提示信息
     *
     * @param expression 表达式
     * @return 提示信息
     */
    private String getTip(String expression) {
        return expression.contains(TIP_MARK)
                ? StringUtils.substringAfterLast(expression, TIP_MARK).trim()
                : null;
    }

    /**
     * 获取字段对应的参数中的值
     *
     * @param field 字段
     * @return 值
     */
    private Object getValue(String field) {
        List<String> keys = Stream.of(field.split("\\.")).collect(Collectors.toList());
        String target = keys.remove(keys.size() - 1);
        JSONObject param = this.param;
        for (String key : keys) {
            param = param.getJSONObject(key);
            Assert.notNull(param, String.format("%s 为必填项！", key));
        }
        return param.get(target);
    }

    /**
     * 获取表达式中右侧条件
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private String getConditionRight(String condition) {
        return StringUtils.substringAfter(condition, CONDITION_MARK);
    }

    /**
     * 获取表达式中左侧条件
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private String getConditionLeft(String condition) {
        return StringUtils.substringBefore(condition, CONDITION_MARK);
    }

    /**
     * 获取表达式中最大的长度，为 Integer 类型
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private Integer getRangeRight(String condition) {
        String after = getConditionRight(condition);
        return StringUtils.isNumeric(after) ? Integer.parseInt(after) : 0;
    }

    /**
     * 获取表达式中最小的长度，为 Integer 类型
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private Integer getRangeLeft(String condition) {
        String before = getConditionLeft(condition);
        return StringUtils.isNumeric(before) ? Integer.parseInt(before) : 0;
    }

    /**
     * 获取表达式中最大的值，为 Long 类型
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private Long getMax(String condition) {
        String max = getConditionRight(condition);
        return StringUtils.isNumeric(max) ? Long.parseLong(max) : Long.MAX_VALUE;
    }

    /**
     * 获取表达式中最小的值，为 Long 类型
     *
     * @param condition 条件表达式
     * @return 结果
     */
    private Long getMin(String condition) {
        String min = getConditionLeft(condition);
        return StringUtils.isNumeric(min) ? Long.parseLong(min) : Long.MIN_VALUE;
    }
}