package com.execodex.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Asset {
    private Long id;
    private String name;
    private Double price;

}
