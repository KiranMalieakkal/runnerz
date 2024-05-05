package dev.kme.runnerz;

import dev.kme.runnerz.users.User;
import dev.kme.runnerz.users.UserHttpClient;
import dev.kme.runnerz.users.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class  Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}
	@Bean
	UserHttpClient userHttpClient(){
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner runner(UserHttpClient client){
		return args-> {
			List<User> users = client.findAll();
			System.out.println(users);

			User user = client.findById(1);
			System.out.println(user);
		};
	}
//	@Bean
//	CommandLineRunner runner(UserRestClient client){
//		return args-> {
//			List<User> users = client.findAll();
//			System.out.println(users);
//
//			User user = client.findById(1);
//			System.out.println(user);
//		};
//	}

//	@Bean
//	CommandLineRunner runner(RunRepository runRepository){
//		return args-> {
//			Run run = new Run(100, "CommandLine", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 1,Location.OUTDOOR);
//			runRepository.create(run);
//		};
//	}

}
