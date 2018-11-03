package cn.jjs.kyou;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * <p>将查询的某个对象转成mybatis中查询条件map, 非null非static的字段都会作为条件.</p>
 * mybatis的mapper文件中, 有时候会将查询条件封装, 可能会加入非DO对象中的字段, 此时
 * 传DO对象作为查询参数会报错, 可转为map再查询
 *
 * @author jiangjunshen
 * @date 下午8:47 2018/9/5
 */
public class QueryTranslator {

    public static <S> Map<String, Object> query2Map(S s) {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] fields = s.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(s);
                if (Objects.nonNull(value) && (field.getModifiers() & Modifier.STATIC) == 0) {
                    resultMap.put(field.getName(), value);
                }
            }
            return resultMap;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("对象成员无法访问, 请检查其可见性");
        }
    }
}
