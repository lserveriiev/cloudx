package io.lenur.aws.exception;

public class S3IOException extends RuntimeException {
    public S3IOException(Throwable cause) {
        super(cause);
    }
}
