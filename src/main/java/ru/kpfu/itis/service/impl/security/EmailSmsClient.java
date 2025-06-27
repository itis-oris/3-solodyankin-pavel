package ru.kpfu.itis.service.impl.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSmsClient {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public void sendCode(String email, String code) {
        try {
            Map<String, String> payload = Map.of(
                "to", email,
                "message", "Ваш код для восстановления: %s".formatted(code)
            );
            RequestBody body = RequestBody.create(
                objectMapper.writeValueAsString(payload),
                MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                .url("http://localhost:8081/api/sms/send")
                .post(body)
                .build();

            client.newCall(request).execute().close();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка отправки", e);
        }
    }
}
