package com.execodex.app.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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

    public Mono<ServerResponse> uploadFile(ServerRequest serverRequest) {
        String bucket = serverRequest.pathVariable("bucket");

        return serverRequest.multipartData()
                .flatMapMany(parts -> Flux.fromIterable(parts.get("file")))
                .cast(FilePart.class)
                .flatMap(filePart -> {
                    return DataBufferUtils.join(filePart.content())
                            .flatMap(dataBuffer -> {
                                long contentLength = dataBuffer.readableByteCount();
                                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                        .bucket(bucket)
                                        .key(filePart.filename())
                                        .contentType(filePart.headers().getContentType().toString())
                                        .contentLength(contentLength)
                                        .build();

                                return Mono.fromFuture(s3AsyncClient.putObject(putObjectRequest,
                                        AsyncRequestBody.fromByteBuffer(dataBuffer.asByteBuffer())))
                                        .doFinally(signalType -> DataBufferUtils.release(dataBuffer))
                                        .thenReturn(filePart.filename());
                            });
                })
                .collectList()
                .flatMap(filenames -> ServerResponse.ok().bodyValue("Uploaded files: " + String.join(", ", filenames)))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Error: " + e.getMessage()));
    }

    // In the future, implement renameBucket method

}
