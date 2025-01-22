package com.atguigu.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private Integer id;
    private String  name;
    private String city;
    private String cityCode;
    private String positionName;
    private String positionCode;
    private Integer page;

    public Position(String name, String city, String cityCode, String positionName, String positionCode, Integer page) {
        this.name = name;
        this.city = city;
        this.cityCode = cityCode;
        this.positionName = positionName;
        this.positionCode = positionCode;
        this.page = page;
    }
}
