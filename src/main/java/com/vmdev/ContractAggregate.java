package com.vmdev;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractAggregate {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("CONTRACT_NUM")
    private Integer contractNum;

    @JsonProperty("BROKER_CODE")
    private String brokerCode;

    @JsonProperty("CONTRACT_NAME")
    private String contractName;

    @JsonProperty("EMAIL")
    private String email;
}
