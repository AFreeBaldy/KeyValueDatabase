package com.laudwilliam.keyvaluedatabase.services;

import com.laudwilliam.keyvaluedatabase.enums.FileSaveType;
import com.laudwilliam.keyvaluedatabase.errors.KeyAlreadyExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyDoesNotExistException;
import com.laudwilliam.keyvaluedatabase.errors.KeyTypeException;
import com.laudwilliam.keyvaluedatabase.models.*;
import com.laudwilliam.keyvaluedatabase.util.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DatabaseServiceImplTest {

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private DatabaseServiceImpl databaseService;

    private String testKey;
    private String testData;

    @BeforeEach
    public void setUp() throws IOException {
        testKey = "testKey";
        testData = "1.0";
        doNothing().when(fileManager).saveDatabase(any(HashMap.class), any(FileSaveType.class));
    }

    @Test
    public void testGetKeyType() throws KeyTypeException {
        KeyType keyType = databaseService.getKeyType("testKey");
        assertTrue(keyType instanceof StringData);

        keyType = databaseService.getKeyType("12345");
        assertTrue(keyType instanceof NumberData);

        assertThrows(KeyTypeException.class, () -> databaseService.getKeyType("true"));
        assertThrows(KeyTypeException.class, () -> databaseService.getKeyType("false"));
    }

    @Test
    public void testGetDataType() {
        DataType dataType = databaseService.getDataType("'tue'");
        assertTrue(dataType instanceof StringData);

        dataType = databaseService.getDataType("12345.0");
        assertTrue(dataType instanceof NumberData);

        dataType = databaseService.getDataType("123");
        assertTrue(dataType instanceof IntegerData);

        dataType = databaseService.getDataType("true");
        assertTrue(dataType instanceof BooleanData);

        dataType = databaseService.getDataType("false");
        assertTrue(dataType instanceof BooleanData);
    }

    @Test
    public void testHasData() throws KeyTypeException, KeyAlreadyExistException {
        assertFalse(databaseService.hasData(testKey));

        databaseService.insertNewData(testKey, testData);

        assertTrue(databaseService.hasData(testKey));
    }

    @Test
    public void testInsertNewData() throws KeyTypeException {
        assertThrows(KeyAlreadyExistException.class, () -> {
            databaseService.insertNewData(testKey, testData);
            databaseService.insertNewData(testKey, testData);
        });
    }

    @Test
    public void testUpdateData() throws KeyTypeException, KeyAlreadyExistException {
        assertNull(databaseService.updateData(testKey, testData));

        databaseService.insertNewData(testKey, testData);
        Entry oldData = databaseService.updateData(testKey, "updatedData");

        assertEquals(testData, oldData.getData().toString());
    }

    @Test
    public void testGetData() throws KeyTypeException, KeyDoesNotExistException, KeyAlreadyExistException {
        assertThrows(KeyDoesNotExistException.class, () -> databaseService.getData(testKey));

        databaseService.insertNewData(testKey, testData);
        Entry entry = databaseService.getData(testKey);

        assertEquals(testData, entry.getData().toString());
    }

    @Test
    public void testDelData() throws KeyTypeException, KeyDoesNotExistException, KeyAlreadyExistException {
        assertThrows(KeyDoesNotExistException.class, () -> databaseService.delData(testKey));

        databaseService.insertNewData(testKey, testData);
        Entry deletedData = databaseService.delData(testKey);

        assertEquals(testData, deletedData.getData().toString());
    }

    @Test
    public void testAutoSave() throws IOException, KeyTypeException, KeyAlreadyExistException {
        for (int i = 0; i < 101; i++) {
            databaseService.insertNewData("key" + i, "data" + i);
        }

        databaseService.autoSave();
        verify(fileManager, times(1)).saveDatabase(any(HashMap.class), eq(FileSaveType.OVERRIDE_AND_CREATE));
    }
}
