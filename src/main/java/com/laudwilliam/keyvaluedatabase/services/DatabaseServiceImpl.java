package com.laudwilliam.keyvaluedatabase.services;

import com.laudwilliam.keyvaluedatabase.enums.FileSaveType;
import com.laudwilliam.keyvaluedatabase.errors.KeyAlreadyExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.*;
import com.laudwilliam.keyvaluedatabase.util.FileManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.HashMap;


@Service
public class DatabaseServiceImpl implements DatabaseService {
    private final HashMap<KeyType, DataType> data;
    private int saveCounter;
    private FileManager fileManager;

    public DatabaseServiceImpl() {
        data = new HashMap<>();
    }

    public DatabaseServiceImpl(FileManager fileManager) {
        data = new HashMap<>();
        this.fileManager = fileManager;
    }

    public KeyType getKeyType(String str) throws KeyTypeException {
        DataType dataType = getDataType(str);
        if (dataType instanceof BooleanData)
            throw new KeyTypeException("Invalid key exception", str);
        if (dataType instanceof IntegerData)
            dataType = new NumberData(((IntegerData) dataType).getData().doubleValue());
        return (KeyType) dataType;
    }

    public DataType getDataType(String str) {
        if (!Boolean.parseBoolean(str) && str.equalsIgnoreCase("false"))
            return new BooleanData(Boolean.FALSE);
        else if (Boolean.parseBoolean(str))
            return new BooleanData(Boolean.TRUE);
        try {
            Long aLong = Long.parseLong(str);
            return new IntegerData(aLong);
        } catch (NumberFormatException ignored) {}
        try {
            Double d = Double.parseDouble(str);
            return new NumberData(d);
        } catch (NumberFormatException ignored){}
        return new StringData(str);
    }

    @Override
    public boolean hasData(String key) throws KeyTypeException {
        KeyType keyType = getKeyType(key);
        saveCounter++;
        return data.containsKey(keyType);
    }

    public boolean hasData(KeyType key) {
        saveCounter++;
        return data.containsKey(key);
    }

    @Override
    public Entry insertNewData(String key, String value) throws KeyAlreadyExistException, KeyTypeException {
        KeyType keyType = getKeyType(key);
        if (hasData(keyType))
            throw new KeyAlreadyExistException();
        saveCounter++;
        data.put(keyType, getDataType(value));
        return null;
    }

    @Override
    public Entry updateData(String key, String value) throws KeyTypeException {
        KeyType keyType = getKeyType(key);
        saveCounter++;
        if (!hasData(key))
            return null;
        Entry oldData = new Entry(keyType, data.get(keyType));
        data.put(keyType, getDataType(value));
        return oldData;
    }

    @Override
    public Entry getData(String key) throws KeyTypeException, KeyDoesNotExistException {
        KeyType keyType = getKeyType(key);
        saveCounter++;
        if (!hasData(key))
            throw new KeyDoesNotExistException();
        return new Entry(keyType, data.get(keyType));
    }

    @Override
    public Entry delData(String key) throws KeyDoesNotExistException, KeyTypeException {
        KeyType keyType = getKeyType(key);
        saveCounter++;
        if (!hasData(key))
            throw new KeyDoesNotExistException();
        return new Entry(keyType, data.remove(keyType));
    }

    @Scheduled(fixedRate = 10000)
    public void autoSave() throws IOException {
        if (saveCounter <= 100)
            return;
        saveCounter = 0;
        fileManager.saveDatabase(data, FileSaveType.OVERRIDE_AND_CREATE);
    }

}
