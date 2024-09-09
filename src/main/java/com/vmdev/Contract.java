package com.vmdev;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Contract {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("CONTRACT_NUM")
    private Integer contractNum;

    @JsonProperty("BROKER_CODE")
    private String brokerCode;

    @JsonProperty("CONTRACT_NAME")
    private String contractName;
}
