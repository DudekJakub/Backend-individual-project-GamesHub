package com.gameshub.mapper;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JsonDateFormatterTest {

    @Test
    void formatStringDateToLocalDate() {
        //Given
        String dateToParse = "2006-01-01";

        //When
        LocalDate resultDate = JsonDateFormatter.formatStringDateToLocalDate(dateToParse);

        //Then
        assertEquals(resultDate.toString(), dateToParse);
    }
}