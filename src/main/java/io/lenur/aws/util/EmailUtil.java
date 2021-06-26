package io.lenur.aws.util;

import io.lenur.aws.entity.Asset;

public class EmailUtil {
    public static String generateBody(Asset asset) {
        var builder = new StringBuilder();

        builder.append("An asset has been uploaded \n\n");
        builder.append("The asset's metadata: \n");
        builder.append(String.format("Name: %s \n", asset.getName()));
        builder.append(String.format("Size: %d \n", asset.getSize()));
        builder.append(String.format("Mime type: %s\n\n", asset.getMimeType()));
        builder.append(String.format("Download: /assets/%s/download", asset.getS3Key()));

        return builder.toString();
    }
}
