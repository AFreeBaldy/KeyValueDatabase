package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("String")
public class StringData extends Data implements KeyType, DataType{
    public StringData(String data) {
        super(data);
    }

    @Override
    public String getData() {
        return (String) super.getData();
    }
}
