package com.laudwilliam.keyvaluedatabase.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NumberDataTest {

    @InjectMocks
    private NumberData numberData;

    @Test
    public void testSerialize() throws JsonProcessingException {
        numberData = new NumberData(123.0);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(numberData);
        assertEquals("{\"type\":NumberData,\"type\":\"number\"}", json);
    }
}
