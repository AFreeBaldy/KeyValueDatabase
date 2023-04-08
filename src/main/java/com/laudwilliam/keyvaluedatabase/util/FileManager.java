package com.laudwilliam.keyvaluedatabase.util;

import com.laudwilliam.keyvaluedatabase.enums.FileSaveType;
import com.laudwilliam.keyvaluedatabase.models.DataType;
import com.laudwilliam.keyvaluedatabase.models.KeyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Objects;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class FileManager {
    @Value("${com.laudwilliam.database.save-location}")
    private String databaseSaveLocation;
    public void saveDatabase(HashMap<KeyType, DataType> data, FileSaveType fileSaveType) throws IOException {
        saveObject(databaseSaveLocation, data, fileSaveType);
    }

    public void saveObject(String filePath, Object data, FileSaveType fileSaveType) throws IOException {
        if (Objects.requireNonNull(fileSaveType) == FileSaveType.OVERRIDE_AND_CREATE) {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(data);
            }
        }
    }


}
