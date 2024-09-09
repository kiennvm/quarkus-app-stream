package com.vmdev;

import io.debezium.serde.DebeziumSerdes;
import io.quarkus.kafka.client.serialization.JsonbSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Collections;

@ApplicationScoped
public class TopologyProducer {

    @ConfigProperty(name = "customers.topic")
    String customersTopic;

    @ConfigProperty(name = "contracts.topic")
    String contractsTopic;

    @ConfigProperty(name = "contract-aggregate.topic")
    String contractAggregateTopic;

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<Long> longSerde = DebeziumSerdes.payloadJson(Long.class);
        longSerde.configure(Collections.emptyMap(), true);

        Serde<Integer> integerSerde = DebeziumSerdes.payloadJson(Integer.class);
        integerSerde.configure(Collections.emptyMap(), true);

        Serde<Contract> contractsSerde = DebeziumSerdes.payloadJson(Contract.class);
        contractsSerde.configure(Collections.singletonMap("from.field", "after"), false);

        Serde<Customer> customersSerde = DebeziumSerdes.payloadJson(Customer.class);
        customersSerde.configure(Collections.singletonMap("from.field", "after"), false);

        JsonbSerde<ContractAggregate> contractAggregateSerde =
                new JsonbSerde<>(ContractAggregate.class);

        KTable<Integer, Customer> customers = builder.table(
                customersTopic,
                Consumed.with(integerSerde, customersSerde)
        );

        KTable<Long, Contract> contracts = builder.table(
                contractsTopic,
                Consumed.with(longSerde, contractsSerde)
        );

        KTable<Long, ContractAggregate> contractAggregateKTable = contracts.join(customers, Contract::getContractNum, (customer, contract) -> ContractAggregate.builder().build());
        contractAggregateKTable.toStream().to(contractAggregateTopic, Produced.with(longSerde, contractAggregateSerde));

        return builder.build();
    }

}