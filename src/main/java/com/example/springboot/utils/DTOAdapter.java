package com.example.springboot.utils;

import com.example.springboot.exceptions.UnsupportedParamsException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class DTOAdapter {

    public static <O, T> T adaptToEntity(O source, Class<T> target) {
        try {
           return adapt(source, target);
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException exception) {
            throw new UnsupportedParamsException(exception.getMessage());
        }
    }

    public static <O, T> ArrayList<T> adaptToEntityList(ArrayList<O> sourceList, Class<T> target) {
        ArrayList<T> newAdaptedList = new ArrayList<>();

        try {
            for(O source : sourceList) {
                newAdaptedList.add(adapt(source, target));
            }
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException exception) {
            throw new UnsupportedParamsException(exception.getMessage());
        }

        return newAdaptedList;
    }

    private static <O, T> T adapt(O source, Class<T> target) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        T newAdaptedObject = target.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, newAdaptedObject);
        return newAdaptedObject;
    }

}
