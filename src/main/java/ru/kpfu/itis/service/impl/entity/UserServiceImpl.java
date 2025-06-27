package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.Role;
import ru.kpfu.itis.dto.request.ConfirmResetRequest;
import ru.kpfu.itis.dto.request.UserRequest;
import ru.kpfu.itis.exception.EmailAlreadyExistsException;
import ru.kpfu.itis.mapper.Mapper;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.service.interfaces.UserService;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Пользователь с таким email уже существует",userRequest);
        }
        User user;
        Role role = userRequest.getRole();
        user = switch (role) {
            case COACH -> Mapper.toCoach(userRequest);
            case SPORTSMAN -> Mapper.toSportsman(userRequest);
        };

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ConfirmResetRequest confirmResetRequest) {
        User user = userRepository.findByEmail(confirmResetRequest.getEmail()).orElseThrow(
                () -> new EmailAlreadyExistsException("Пользователь с таким email уже существует")
        );
        user.setPassword(passwordEncoder.encode(confirmResetRequest.getNewPassword()));
        userRepository.save(user);
    }


}
