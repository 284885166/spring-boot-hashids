package org.yanshen.springframework.formatter;

import java.lang.annotation.*;

/**
 * @author yanshen
 * Created by yanshen on 2019/1/3.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface HashidsFormat {
}
