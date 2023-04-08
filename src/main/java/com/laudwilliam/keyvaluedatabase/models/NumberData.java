package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;

@JsonTypeName("Number")
public class NumberData extends Data implements DataType, KeyType{


    public NumberData(Double object) {
        super(object);
    }

    @Override
    public Double getData() {
        return (Double) data;
    }
}
