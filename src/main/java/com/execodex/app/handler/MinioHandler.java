package com.execodex.app.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Service
public class MinioHandler {

    private final S3AsyncClient s3AsyncClient;

    public MinioHandler(S3AsyncClient s3AsyncClient) {
        this.s3AsyncClient = s3AsyncClient;
    }

    public Mono<ServerResponse> createBucket(ServerRequest serverRequest) {
        String bucket = serverRequest.pathVariable("bucket");

        CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket(bucket).build();

        return Mono.fromFuture(s3AsyncClient.createBucket(bucketRequest))
                .thenReturn("Bucket created successfully")
                .onErrorResume(e -> e instanceof BucketAlreadyExistsException || e instanceof BucketAlreadyOwnedByYouException,
                        e -> Mono.just("Bucket already exists"))
                .flatMap(message -> ServerResponse.ok().bodyValue(message))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Error: " + e.getMessage()))
        ;
    }
}
