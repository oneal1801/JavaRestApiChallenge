package com.api.usersChallenge;

import com.api.usersChallenge.models.PhoneEntity;
import com.api.usersChallenge.models.UserEntity;
import com.api.usersChallenge.repository.PhoneEntityRepository;
import com.api.usersChallenge.repository.UserEntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class UsersChallengeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(UsersChallengeApplication.class, args);
		UserEntityRepository userEntityRepository = configurableApplicationContext.getBean(UserEntityRepository.class);

		String password = new BCryptPasswordEncoder().encode("TestPassword");
		UserEntity user1 = UserEntity.builder().name("Winsmer Marte").email("oneal.marte1801@gmail.com").password(password).build();
		PhoneEntity phone1 = PhoneEntity.builder().number("2503080").cityCode("829").countryCode("1").userId(user1).build();
		PhoneEntity phone2 = PhoneEntity.builder().number("2503081").cityCode("829").countryCode("1").userId(user1).build();
		PhoneEntity phone3 = PhoneEntity.builder().number("2503082").cityCode("829").countryCode("1").userId(user1).build();
		List<PhoneEntity> phoneNumbers = Arrays.asList(phone1, phone2, phone3);
		user1.setPhone(phoneNumbers);
		UserEntity user2 = UserEntity.builder().name("Oneal Marte").email("oneal1801@gmail.com").password(password).build();
		PhoneEntity phone4 = PhoneEntity.builder().number("2503080").cityCode("829").countryCode("1").userId(user2).build();
		PhoneEntity phone5 = PhoneEntity.builder().number("2503081").cityCode("829").countryCode("1").userId(user2).build();
		List<PhoneEntity> phoneNumbers2 = Arrays.asList(phone4, phone5);
		user2.setPhone(phoneNumbers2);
		List<UserEntity> listOfUsers = Arrays.asList(user1,user2);
		userEntityRepository.saveAll(listOfUsers);
	}

}
