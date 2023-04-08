package com.laudwilliam.keyvaluedatabase.repositories;

import com.laudwilliam.keyvaluedatabase.errors.KeyAlreadyExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.Entry;
import com.laudwilliam.keyvaluedatabase.models.StringData;
import com.laudwilliam.keyvaluedatabase.repositories.DataRepository;
import com.laudwilliam.keyvaluedatabase.services.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataRepositoryTest {

    @Mock
    private DatabaseService databaseService;

    @InjectMocks
    private DataRepository dataRepository;

    private String testKey;
    private String testData;

    @BeforeEach
    public void setUp() {
        testKey = "testKey";
        testData = "testData";
    }

    @Test
    public void testSetData() throws KeyAlreadyExistException, KeyTypeException {
        when(databaseService.hasData(testKey)).thenReturn(false);

        Entry entry = dataRepository.setData(testKey, testData, false);
        verify(databaseService, times(1)).insertNewData(testKey, testData);

        when(databaseService.hasData(testKey)).thenReturn(true);

        assertThrows(KeyAlreadyExistException.class, () -> dataRepository.setData(testKey, testData, false));

        Entry updatedEntry = dataRepository.setData(testKey, testData, true);
        verify(databaseService, times(1)).updateData(testKey, testData);
    }

    @Test
    public void testGetData() throws KeyDoesNotExistException, KeyTypeException {
        when(databaseService.hasData(testKey)).thenReturn(false);
        assertThrows(KeyDoesNotExistException.class, () -> dataRepository.getData(testKey));

        when(databaseService.hasData(testKey)).thenReturn(true);
        when(databaseService.getData(testKey)).thenReturn(new Entry(new StringData(testKey), new StringData(testData)));

        Entry entry = dataRepository.getData(testKey);
        assertEquals(testData, entry.getData().toString());
    }

    @Test
    public void testDelData() throws KeyDoesNotExistException, KeyTypeException {
        when(databaseService.hasData(testKey)).thenReturn(false);
        assertThrows(KeyDoesNotExistException.class, () -> dataRepository.delData(testKey));

        when(databaseService.hasData(testKey)).thenReturn(true);
        when(databaseService.delData(testKey)).thenReturn(new Entry(new StringData(testKey), new StringData(testData)));

        Entry deletedEntry = dataRepository.delData(testKey);
        assertEquals(testData, deletedEntry.getData().toString());
    }
}
