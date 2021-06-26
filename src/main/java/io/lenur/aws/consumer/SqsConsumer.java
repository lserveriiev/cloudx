package io.lenur.aws.consumer;

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
public class SqsConsumer {

	@Autowired
	private final SnsService snsService;
	@Autowired
	private final SqsService sqsService;

	@Scheduled(fixedRate = 5000)
	public void consumeUploadedAssets() {
		for (var message: sqsService.getMessages()) {
			LOGGER.info(String.format("The message with body \"%s\" has been consumed", message.body()));
			snsService.publishMessage(message.body());
			sqsService.deleteMessage(message);
		}
	}
}