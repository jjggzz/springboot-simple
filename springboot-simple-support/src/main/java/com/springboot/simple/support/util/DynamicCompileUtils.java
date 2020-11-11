package com.springboot.simple.support.util;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jgz
 * @date 2020/8/27
 * @desc 动态编译工具
 **/
public class DynamicCompileUtils {
    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    /**
     * 根据代码模板生成字节码对象
     * @param codeTemplate 代码模板
     * @return {@link Class<> 返回的字节码对象}
     * @author jgz
     * @date 2020/8/27
     */
    public static Class<?> loadNewInstance(String codeTemplate) {
        if (StringUtils.isNotBlank(codeTemplate)) {
            return groovyClassLoader.parseClass(codeTemplate);
        }
        throw new RuntimeException("groovy动态编译:类模板为空");
    }

    



}
