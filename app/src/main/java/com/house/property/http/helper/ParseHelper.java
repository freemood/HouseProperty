package com.house.property.http.helper;

import com.google.gson.JsonElement;

/**
 * 数据解析helper
 *
 */
public interface ParseHelper {
    Object[] parse(JsonElement jsonElement);
}
