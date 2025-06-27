package ru.kpfu.itis.mapper;

import ru.kpfu.itis.dto.request.UserRequest;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;

public class  Mapper {
    public static Sportsman toSportsman(UserRequest userRequest) {
        return Sportsman.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .gender(userRequest.getGender())
                .age(userRequest.getAge())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();
    }
    public static Coach toCoach(UserRequest userRequest) {
        return Coach.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .gender(userRequest.getGender())
                .age(userRequest.getAge())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();
    }
}
