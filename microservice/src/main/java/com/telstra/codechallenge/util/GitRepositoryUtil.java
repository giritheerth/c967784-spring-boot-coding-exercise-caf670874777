package com.telstra.codechallenge.util;

import com.telstra.codechallenge.constant.GitRepositoryConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GitRepositoryUtil {

    private GitRepositoryUtil (){
    }

    /**
     * getLastLocalDate - get for last week
     */
    public static LocalDate getLastLocalDate(int days) {
        LocalDate now = LocalDate.now();
        long oneVal =1;
        return now.minusDays(days + now.getDayOfWeek().getValue() - oneVal);
    }

    /**
     * getDateToProcess - formatter for yyyy-mm-dd
     */
    public static String getDateToProcess(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GitRepositoryConstants.REPO_DATE_FORMAT);
        return localDate.format(formatter);
    }
}
