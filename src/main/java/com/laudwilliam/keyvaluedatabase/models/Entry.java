package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class Entry implements Serializable {
    @JsonProperty("Key")
    private KeyType key;
    @JsonProperty("Value")
    private DataType data;
}
