package kim.jiho.redislettucetutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import kim.jiho.redislettucetutorial.receiver.Receiver;

@SpringBootApplication
public class RedisLettuceTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisLettuceTutorialApplication.class, args);
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory factory, MessageListenerAdapter listener) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.addMessageListener(listener, new PatternTopic("tejava"));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver() {
		return new Receiver();
	}

	@Bean
	StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		return new StringRedisTemplate(factory);
	}

}
