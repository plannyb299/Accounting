package com.example.financeservice.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  public static LocalDate getLocalDate(String stringDate){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = stringDate;
    LocalDate localDate = LocalDate.parse(date, formatter);

    return localDate;

  }

}
