package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Boolean")
public class BooleanData extends Data implements DataType{
    public BooleanData(Boolean data) {
        super(data);
    }

    @Override
    public Boolean getData() {
        return (Boolean) super.getData();
    }
}
