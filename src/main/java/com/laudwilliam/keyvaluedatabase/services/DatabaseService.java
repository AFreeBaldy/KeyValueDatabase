package com.laudwilliam.keyvaluedatabase.services;

import com.laudwilliam.keyvaluedatabase.errors.KeyAlreadyExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.Data;
import com.laudwilliam.keyvaluedatabase.models.DataType;
import com.laudwilliam.keyvaluedatabase.models.Entry;
import com.laudwilliam.keyvaluedatabase.models.KeyType;
import lombok.SneakyThrows;


public interface DatabaseService {
    boolean hasData(String key) throws KeyTypeException;

    Entry insertNewData(String key, String value) throws KeyAlreadyExistException, KeyTypeException;

    Entry updateData(String key, String value) throws KeyTypeException;

    Entry getData(String key) throws KeyTypeException, KeyDoesNotExistException;

    Entry delData(String key) throws KeyDoesNotExistException, KeyTypeException;

    DataType getDataType(String str);

    KeyType getKeyType(String key) throws KeyTypeException;
}
