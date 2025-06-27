package ru.kpfu.itis.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.model.Sport;
import ru.kpfu.itis.service.interfaces.SportService;

@Component
@RequiredArgsConstructor
public class StringToSportConverter implements Converter<String, Sport> {

    private final SportService sportService;

    @Override
    public Sport convert(String source) {
        try {
            Long id = Long.valueOf(source);
            return sportService.findById(id);
        } catch (NumberFormatException e) {
            return sportService.findByName(source);
        }
    }
}