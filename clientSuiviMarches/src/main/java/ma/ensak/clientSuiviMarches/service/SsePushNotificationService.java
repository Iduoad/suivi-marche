package ma.ensak.clientSuiviMarches.service;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ma.ensak.clientSuiviMarches.proxies.MicroserviceNotificationProxy;

@Service
@EnableScheduling
public class SsePushNotificationService {

	final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	@Autowired
	MicroserviceNotificationProxy microSNProxy;

	public void addEmitter(final SseEmitter emitter) {
		emitters.add(emitter);
	}

	public void removeEmitter(final SseEmitter emitter) {
		emitters.remove(emitter);
	}

	@Async
	@Scheduled(fixedRate = 5000)
	public void doNotify() throws IOException {
		List<SseEmitter> deadEmitters = new ArrayList<>();
		emitters.forEach(emitter -> {
			try {
				emitter.send(SseEmitter.event()
						.data(microSNProxy.notReadableNotifications()));
			} catch (Exception e) {
				deadEmitters.add(emitter);
			}
		});
		emitters.removeAll(deadEmitters);
	}
	
	

}
