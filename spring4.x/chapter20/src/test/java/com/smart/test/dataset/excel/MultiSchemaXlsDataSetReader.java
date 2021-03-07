package com.smart.test.dataset.excel;

import org.apache.commons.lang.StringUtils;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.*;
import org.dbunit.dataset.excel.XlsDataSet;
import org.unitils.core.UnitilsException;
import org.unitils.dbunit.util.MultiSchemaDataSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * 一个Excel相当于一个数据库，其中包括多张表
 * 该类表示数据库的集合（来自于多个Excel）
 * */
public class MultiSchemaXlsDataSetReader {
    private final String defaultSchemaName;
    private static final Pattern pattern = Pattern.compile("\\.");

    public MultiSchemaXlsDataSetReader(String defaultSchemaName) {
        this.defaultSchemaName = defaultSchemaName;
    }

    public MultiSchemaDataSet readDataSetXls(File... dataSetFiles) {
        try {
            return tryReadDataSetXls(dataSetFiles);
        } catch (Exception e) {
            throw new UnitilsException("EXCEL", e);
        }
    }

    public MultiSchemaDataSet tryReadDataSetXls(File... dataSetFiles) {
        MultiSchemaDataSet dataSets = new MultiSchemaDataSet();
        Map<String, List<ITable>> dataBases = getDataBasesFrom(dataSetFiles);
        for (Entry<String, List<ITable>> dataBase : dataBases.entrySet()) {
            IDataSet dataSet = tablesToDataSet(dataBase.getValue());
            dataSets.setDataSetForSchema(dataBase.getKey(), dataSet);
        }
        return dataSets;
    }

    private IDataSet tablesToDataSet(List<ITable> tables){
        try {
            return new DefaultDataSet(tables.toArray(new ITable[]{}));
        } catch (Exception e) {
            throw new UnitilsException("DataSet", e);
        }

    }

    private Map<String, List<ITable>> getDataBasesFrom(File... excelFiles) {
        try {
            return tryGetDataBasesFrom(excelFiles);
        } catch (Exception e) {
            throw new UnitilsException("Create Tables Failure:" + Arrays.toString(excelFiles), e);
        }
    }

    private Map<String, List<ITable>> tryGetDataBasesFrom(File... excelFiles) throws IOException, DataSetException {
        Map<String, List<ITable>> dataBases = new HashMap<>();
        for (File file : excelFiles) {
            IDataSet excel = new XlsDataSet(new FileInputStream(file));
            String[] tableNames = excel.getTableNames();
            for (String each : tableNames) {
                SchemaAndTableName schemaAndTableName = new SchemaAndTableName(each, defaultSchemaName);
                List<ITable> dataBase = getDataBase(schemaAndTableName.schemaName, dataBases);
                ITable table = excel.getTable(each);
                dataBase.add(new XlsTable(schemaAndTableName.tableName, table));
            }
        }
        return dataBases;
    }

    private List<ITable> getDataBase(String schemaName, Map<String, List<ITable>> dataBases) {
        if (!dataBases.containsKey(schemaName)) {
            dataBases.put(schemaName, new ArrayList<>());
        }
        return dataBases.get(schemaName);
    }

    private static class SchemaAndTableName {
        String schemaName;
        String tableName;

        SchemaAndTableName(String schemaAndTableName, String defaultSchemaName) {
            String[] names = pattern.split(schemaAndTableName);
            if (isSchemaAndTableName(names)) {
                schemaName = names[0];
                tableName = names[1];
            } else {
                schemaName = defaultSchemaName;
                tableName = schemaAndTableName;
            }
        }

        boolean isSchemaAndTableName(String[] names) {
            return names.length == 2;
        }
    }


    private static class XlsTable extends AbstractTable {
        private final ITable delegate;
        private final String tableName;

        public XlsTable(String tableName, ITable table) {
            this.delegate = table;
            this.tableName = tableName;
        }

        @Override
        public int getRowCount() {
            return delegate.getRowCount();
        }

        @Override
        public ITableMetaData getTableMetaData() {
            ITableMetaData meta = delegate.getTableMetaData();
            try {
                return new DefaultTableMetaData(tableName, meta.getColumns(), meta.getPrimaryKeys());
            } catch (DataSetException e) {
                throw new UnitilsException("Don't get the meta info from " + meta, e);
            }
        }

        @Override
        public Object getValue(int row, String column) throws DataSetException {
            Object delta = delegate.getValue(row, column);
            return toNullIfEmptyString(delta);
        }

        private Object toNullIfEmptyString(Object maybeString) {
            if (maybeString instanceof String && StringUtils.isEmpty((String) maybeString)) {
                return null;
            } else {
                return maybeString;
            }
        }

    }
}
