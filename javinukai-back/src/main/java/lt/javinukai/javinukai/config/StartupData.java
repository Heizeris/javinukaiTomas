package lt.javinukai.javinukai.config;

import lombok.RequiredArgsConstructor;
import lt.javinukai.javinukai.config.security.UserRole;
import lt.javinukai.javinukai.entity.User;
import lt.javinukai.javinukai.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class StartupData {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initialize() {
        return args -> {

            User testUser = User.builder()
                    .name("John")
                    .surname("Doe")
                    .email("jdoe@mail.com")
                    .institution("LRT")
                    .isFreelance(false)
                    .maxSinglePhotos(10)
                    .maxCollections(10)
                    .role(UserRole.ADMIN)
                    .isEnabled(true)
                    .isNonLocked(true)
                    .phoneNumber("+37047812482")
                    .birthYear(1984)
                    .password(passwordEncoder.encode("password"))
                    .build();

            userRepository.save(testUser);

        };
    }
}
