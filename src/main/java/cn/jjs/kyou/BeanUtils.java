package cn.jjs.kyou;

import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 从字节码层面生成类的转换器并缓存在map中，能够提供高效属性copy的能力
 *
 * @author jiangjunshen
 * @date 上午11:21 2018/8/16
 */
public class BeanUtils {

    private static final ConcurrentMap<String, BeanCopier> BEAN_COPY_MAP = new ConcurrentHashMap<>();

    /**
     * 两个类对象之间转换
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void convert(Object source, Object target) {
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    /**
     * @param source 源类
     * @param target 目标类
     * @return BeanCopier
     * 获取BeanCopier
     */
    private static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        String beanCopierKey = generateBeanKey(source, target);
        if (BEAN_COPY_MAP.containsKey(beanCopierKey)) {
            return BEAN_COPY_MAP.get(beanCopierKey);
        }
        BeanCopier beanCopier = BeanCopier.create(source, target, Boolean.FALSE);
        BEAN_COPY_MAP.putIfAbsent(beanCopierKey, beanCopier);
        return BEAN_COPY_MAP.get(beanCopierKey);
    }

    /**
     * @param source 源类
     * @param target 目标类
     * @return String
     * 生成两个类的key
     */
    private static String generateBeanKey(Class<?> source, Class<?> target) {
        return source.getName() + "@" + target.getName();
    }
}
