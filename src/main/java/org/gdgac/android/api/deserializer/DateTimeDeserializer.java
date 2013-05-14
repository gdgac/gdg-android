package org.gdgac.android.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
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
 * Time: 01:39
 */
public class DateTimeDeserializer implements JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ssZZ");
        //2013-05-15T16:30:00+02:00
        return fmt.parseDateTime(jsonElement.getAsJsonPrimitive().getAsString());
    }
}