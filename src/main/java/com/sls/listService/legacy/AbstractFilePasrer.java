package com.sls.listService.legacy;

public abstract class AbstractFilePasrer<T> implements CSVList<T> {

    public static String[] splitLine(String row) {
        return row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }
}