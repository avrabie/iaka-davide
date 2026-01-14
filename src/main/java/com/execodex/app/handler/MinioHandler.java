package com.execodex.app.handler;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioHandler {

    private final MinioClient minioClient;

    public MinioHandler(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public Mono<ServerResponse> createBucket(ServerRequest serverRequest) {
        String bucket = serverRequest.pathVariable("bucket");
        BucketExistsArgs bucketChecker = BucketExistsArgs.builder().bucket(bucket).build();
        // Creates bucket if missing; throws on error
        try {
            if (!minioClient.bucketExists(bucketChecker)) {
                MakeBucketArgs makeBucket = MakeBucketArgs.builder().bucket(bucket).build();
                minioClient.makeBucket(makeBucket);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ServerResponse.ok().bodyValue("Bucket created successfully");
    }
}
