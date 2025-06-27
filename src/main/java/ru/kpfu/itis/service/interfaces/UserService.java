package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.dto.request.ConfirmResetRequest;
import ru.kpfu.itis.dto.request.UserRequest;

public interface UserService {
    void register(UserRequest userRequest);
    void resetPassword(ConfirmResetRequest confirmResetRequest);
}
