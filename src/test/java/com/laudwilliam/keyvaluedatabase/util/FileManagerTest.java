package com.laudwilliam.keyvaluedatabase.util;

import com.laudwilliam.keyvaluedatabase.enums.FileSaveType;
import com.laudwilliam.keyvaluedatabase.models.DataType;
import com.laudwilliam.keyvaluedatabase.models.KeyType;
import com.laudwilliam.keyvaluedatabase.models.NumberData;
import com.laudwilliam.keyvaluedatabase.models.StringData;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FileManager.class)
@ExtendWith(MockitoExtension.class)
class FileManagerTest {


    @Autowired
    private FileManager fileManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        File file = new File("file.txt");
        file.delete();
    }

    static Stream<Arguments> load_hashmap() {
        HashMap<KeyType, DataType> hashMap1 = new HashMap<>();
        hashMap1.put(new StringData(""), new NumberData(1.0));
        hashMap1.put(new StringData(""), new NumberData(1.0));
        hashMap1.put(new StringData(""), new NumberData(1.0));

        HashMap<KeyType, DataType> hashMap2 = new HashMap<>();
        hashMap2.put(new StringData(""), new NumberData(1.0));
        hashMap2.put(new StringData(""), new NumberData(1.0));
        hashMap2.put(new StringData(""), new NumberData(1.0));
        hashMap2.put(new StringData(""), new NumberData(1.0));

        return Stream.of(
                Arguments.of(hashMap1, FileSaveType.OVERRIDE_AND_CREATE),
                Arguments.of(hashMap2, FileSaveType.OVERRIDE_AND_CREATE)
        );
    }

    @ParameterizedTest
    @MethodSource("load_hashmap")
    void saveDatabase(HashMap<KeyType, DataType> hashMap, FileSaveType fileSaveType) {
        // Given

        String fileName = fileManager.getDatabaseSaveLocation();
        HashMap<KeyType, DataType> myObject;

        try {
            fileManager.saveDatabase(hashMap, fileSaveType);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            myObject = (HashMap<KeyType, DataType>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThat(Files.exists(Path.of(fileName))).isTrue();
        assertThat(hashMap.equals(myObject)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("load_hashmap")
    void saveObject(Object object, FileSaveType fileSaveType)  {
        // Given
        String fileName = "file.txt";
        Object myObject;

        // Test
        try {
            fileManager.saveObject(fileName, object, fileSaveType);
            myObject = new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertThat(Files.exists(Path.of(fileName))).isTrue();
        assertThat(object.equals(myObject)).isTrue();

        File file = new File("file.txt");
        file.delete();
    }

    @Test
    void test() {

    }
}