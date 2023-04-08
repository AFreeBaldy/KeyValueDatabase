package com.laudwilliam.keyvaluedatabase.services;

import com.laudwilliam.keyvaluedatabase.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.LinkedList;

public interface DataService {

    Entry setData(String key, String value);

    Entry delData(String key);

    Entry getData(String key);
}
