package com.sls.listService.legacy;

public abstract class AbstractFilePasrer<T> implements CSVList<T> {

    public static String[] splitLine(String row) {
        String[] tokens = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return tokens;
    }
}
