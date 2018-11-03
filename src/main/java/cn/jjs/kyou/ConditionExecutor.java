package cn.jjs.kyou;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author jiangjunshen
 * @date 下午9:20 2018/10/30
 */
public class ConditionExecutor {

    public static <T> void ifThen(Predicate<T> predicate, Consumer<T> consumer, T t) {
        if (predicate.test(t)) {
            consumer.accept(t);
        }
    }

    public static <T, R> R ifThenElse(Predicate<T> predicate, Function<T, R> ifFunc, Function<T, R> elseFunc, T t) {
        return predicate.test(t) ? ifFunc.apply(t) : elseFunc.apply(t);
    }

    public static <T, R> R ifThenElseDefault(Predicate<T> predicate, Function<T, R> ifFunc, T t, R r) {
        return predicate.test(t) ? ifFunc.apply(t) : r;
    }
}
