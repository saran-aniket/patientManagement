package com.learn.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
                                    @Value("${billing.service.grpc.port:9001}") int serverPort) {
        log.info("Connecting to billing service at {}:{}", serverAddress, serverPort);
        blockingStub = BillingServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress(serverAddress, serverPort).
                        usePlaintext().
                        build());
        log.info("Connected to billing service");
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest billingRequest = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setEmail(email)
                .setName(name).build();
        BillingResponse response = blockingStub.createBillingAccount(billingRequest);
        log.info("Recieved response from billing service via GRPC: {}", response);
        return response;

    }
}
