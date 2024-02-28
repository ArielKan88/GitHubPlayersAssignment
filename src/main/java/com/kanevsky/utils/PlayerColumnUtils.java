package com.kanevsky.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanevsky.entities.PlayerEntity;
import com.kanevsky.views.PlayerView;
import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerColumnUtils {
    public static Map<String, String> getCsvColumnNameToDbColumnName() {
        Class<?> viewClass = PlayerView.class;
        Class<?> entityClass = PlayerEntity.class;
        Map<String, String> annotationMap = new LinkedHashMap<>();

        Field[] viewFields = viewClass.getDeclaredFields();
        Field[] entityFields = entityClass.getDeclaredFields();

        for (Field viewField : viewFields) {
            JsonProperty jsonPropertyAnnotation = viewField.getAnnotation(JsonProperty.class);
            if (jsonPropertyAnnotation != null) {
                String jsonProperty = jsonPropertyAnnotation.value();
                for (Field entityField : entityFields) {
                    Column columnAnnotation = entityField.getAnnotation(Column.class);
                    if (columnAnnotation != null && entityField.getName().equals(viewField.getName())) {
                        annotationMap.put(jsonProperty, columnAnnotation.name());
                        break;
                    }
                }
            }
        }

        return annotationMap;
    }


    public static Map<String, Integer> getColumnSQLTypes() {
        Map<String, Integer> columnSQLTypes = new HashMap<>();

        Field[] fields = PlayerEntity.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = columnAnnotation.name();
                int sqlType = getSQLType(field.getType());
                columnSQLTypes.put(columnName, sqlType);
            }
        }

        return columnSQLTypes;
    }

    private static int getSQLType(Class<?> fieldType) {
        if (fieldType.equals(String.class)) {
            return Types.VARCHAR;
        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            return Types.INTEGER;
        } else if (fieldType.equals(LocalDate.class)) {
            return Types.DATE;
        }
        return Types.OTHER;
    }
}