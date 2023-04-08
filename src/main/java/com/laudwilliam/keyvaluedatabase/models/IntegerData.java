package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Integer")
public class IntegerData extends Data implements DataType, KeyType{
    public IntegerData(Long data) {
        super(data);
    }

    @Override
    public Long getData() {
        return (Long) super.getData();
    }
}
