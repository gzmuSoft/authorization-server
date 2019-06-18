package cn.edu.gzmu.authserver.util;

/**
 * 函数式接口，用来自定义校验规则測試
 *
 * @param <T> 第一个
 * @param <V> 第二个
 * @author echo
 * @version 1.0
 * @date 19-5-22 14:54
 */
@FunctionalInterface
public interface VerifyParameterPredicate<T, V> {
    /**
     * 测试
     *
     * @param t 第一个
     * @param v 第二个
     * @return 结果
     */
    boolean test(T t, V v);
}
