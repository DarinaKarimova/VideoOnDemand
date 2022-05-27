package com.example.saat.Specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key; //fieldName
    private String operation;
    private Object value; //fieldValue


    public boolean isOrPredicate() {
        return true;
    }
}
