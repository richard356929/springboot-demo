/**
 * CopyRight (c) 2016 北京好数科技有限公司 保留所有权利
 */

package com.z.suite.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.z.suite.exception.SystemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luandy
 * @version 1.0.0.2017年1月17日
 */
public class JSONUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 字符串转List
     *
     * @param content
     * @param elementClasses
     * @return
     * @throws SystemException
     */
    public static List<?> getList(String content, Class<?> elementClasses) throws SystemException {
        try {
        		JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClasses);
            return mapper.readValue(content, type);
        } catch (Exception e) {
            throw new SystemException(520101, "字符串[" + content + "]转List出错");
        }
    }

    /**
     * 字符串转Map
     *
     * @param content
     * @param keyClass
     * @param valueClass
     * @return
     * @throws SystemException
     */
    public static Map<?, ?> getMap(String content, Class<?> keyClass, Class<?> valueClass) throws SystemException {
        try {
            JavaType type = mapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
            return mapper.readValue(content, type);
        } catch (Exception e) {
            throw new SystemException(520101, "字符串[" + content + "]转Map出错");
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
    
}
