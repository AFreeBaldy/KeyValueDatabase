package com.laudwilliam.keyvaluedatabase.services;

import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.Entry;
import com.laudwilliam.keyvaluedatabase.repositories.DataRepository;
import org.springframework.stereotype.Service;


@Service
public class DataRestServiceImpl implements DataService {
    private final DataRepository dataRepository;

    public DataRestServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    @Override
    public Entry setData(String key, String data) {
        return null;
    }

    @Override
    public Entry delData(String key) {
        return null;
    }

    @Override
    public Entry getData(String key) {
        try {
            return dataRepository.getData(key);
        } catch (KeyDoesNotExistException | KeyTypeException e) {
            throw new RuntimeException(e);
        }
    }
}
