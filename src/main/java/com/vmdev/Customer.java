package com.vmdev;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("CONTRACT_NUM")
    private Integer contractNum;

    @JsonProperty("EMAIL")
    private String email;
}
