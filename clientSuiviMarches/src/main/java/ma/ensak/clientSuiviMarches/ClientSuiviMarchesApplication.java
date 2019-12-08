package ma.ensak.clientSuiviMarches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("ma.ensak.clientSuiviMarches")
public class ClientSuiviMarchesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSuiviMarchesApplication.class, args);
	}

}
