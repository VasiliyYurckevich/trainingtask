package com.qulix.yurkevichvv.trainingtask.servlets.dropdown;

import java.util.List;
import java.util.stream.Collectors;

public interface DropDownItemConverter<T> {

    DropDownListItem convert(T object);

    T convert(DropDownListItem dropDownListItem);

    default List<T> convertToEntityList(List<DropDownListItem> list){
         return  list.stream()
                .map(item -> convert(item))
                .collect(Collectors.toList());

    };

    default List<DropDownListItem> convertToDropDownList(List<T> list){
        return  list.stream()
                .map(item -> convert(item))
                .collect(Collectors.toList());

    };
}
