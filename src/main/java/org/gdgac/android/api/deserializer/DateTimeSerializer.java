package org.gdgac.android.api.deserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.lang.reflect.Type;

/**
 * GDG Aachen
 * org.gdgac.android.api.deserializer
 * <p/>
 * User: maui
 * Date: 23.04.13
 * Time: 04:51
 */
public class DateTimeSerializer implements JsonSerializer<DateTime> {
    @Override
    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ssZZ");
        return new JsonPrimitive(dateTime.toString(fmt));
    }
}