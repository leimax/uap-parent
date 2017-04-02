package com.deppon.uap.framework.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

public abstract class CacheUtils {

    public static <T> String objectFormatJson(T object) {
        if (object != null) {
            if (object instanceof CharSequence
                    && StringUtils.isBlank((CharSequence) object)) {
                return "";
            }
            try {
                return JSON.toJSONString(
                        object, SerializerFeature.WriteClassName);
            } catch (Exception e) {
                return "null";
            }
        }
        return "null";
    }

    public static Object jsonParseObject(String json) {
        if (StringUtils.isBlank(json)) {
            return "";
        } else if (StringUtils.equalsIgnoreCase(json, "null")) {
            return null;
        }
        return JSON.parse(json);
    }

}
