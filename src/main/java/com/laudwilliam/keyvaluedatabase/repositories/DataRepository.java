package com.laudwilliam.keyvaluedatabase.repositories;

import com.laudwilliam.keyvaluedatabase.errors.KeyAlreadyExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.Data;
import com.laudwilliam.keyvaluedatabase.models.DataType;
import com.laudwilliam.keyvaluedatabase.models.Entry;
import com.laudwilliam.keyvaluedatabase.models.KeyType;
import com.laudwilliam.keyvaluedatabase.services.DatabaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class DataRepository {

    @Autowired
    private DatabaseService databaseService;
    public Entry setData(String key, String value, Boolean override) throws KeyAlreadyExistException, KeyTypeException {
        if(!override && databaseService.hasData(key))
            throw new KeyAlreadyExistException();
        if(!override)
            return databaseService.insertNewData(key, value);
        return databaseService.updateData(key, value);
    }

    public Entry getData(String key) throws KeyDoesNotExistException, KeyTypeException {
        if(!databaseService.hasData((key)))
            throw new KeyDoesNotExistException();
        return databaseService.getData((key));
    }

    public Entry delData(String key) throws KeyDoesNotExistException, KeyTypeException {
        if(!databaseService.hasData( key))
            throw new KeyDoesNotExistException();
        return databaseService.delData(key);
    }
}
