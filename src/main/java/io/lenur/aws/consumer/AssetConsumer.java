package io.lenur.aws.consumer;

import io.lenur.aws.entity.Asset;
import io.lenur.aws.service.AssetService;
import io.lenur.aws.service.SnsService;
import io.lenur.aws.service.SqsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
@AllArgsConstructor
@Transactional
public class AssetConsumer {

	@Autowired
	private final SnsService snsService;
	@Autowired
	private final SqsService sqsService;
	@Autowired
	private final AssetService assetService;

	@Scheduled(fixedRate = 5000)
	public void consumeUploadedAssets() {
		for (var message: sqsService.getMessages()) {
			var assetId = Long.valueOf(message.body());
			LOGGER.info(String.format("The asset with id %s has been uploaded", assetId));
			var asset = assetService.getById(assetId);
			snsService.publishMessage(buildEmailMessage(asset));
			sqsService.deleteMessage(message);
		}
	}

	private String buildEmailMessage(Asset asset) {
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