package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Type")
public interface DataType extends Serializable {
}
