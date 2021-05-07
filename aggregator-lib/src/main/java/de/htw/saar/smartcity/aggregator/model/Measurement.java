package de.htw.saar.smartcity.aggregator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Measurement<T> implements Serializable {

    protected T value;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime time;


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Measurement{");
        sb.append(", value=").append(value);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
