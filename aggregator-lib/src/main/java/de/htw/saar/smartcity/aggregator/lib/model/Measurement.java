package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The type Measurement.
 *
 * @param <T> the type parameter of the underlying value
 */
public class Measurement<T> implements Serializable {

    /**
     * The Value.
     */
    protected T value;

    /**
     * The Time.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime time;

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Measurement{");

        String loggedValue = value.toString();
        if(loggedValue.length() > 100) {
            loggedValue = loggedValue.substring(0, 97);
            loggedValue += "...";
        }
        sb.append("value=").append(loggedValue);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
