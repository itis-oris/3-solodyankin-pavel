package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.SportNotFoundException;
import ru.kpfu.itis.model.Sport;
import ru.kpfu.itis.repository.SportRepository;
import ru.kpfu.itis.service.interfaces.SportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    @Override
    public List<Sport> findAll() {
        return sportRepository.findAll();
    }

    @Override
    public Sport findByName(String name) {
        return sportRepository.findByName(name).orElseThrow(
                () -> new SportNotFoundException("Спорт не найден")
        );
    }

    @Override
    public Sport findById(Long id) {
        return sportRepository.findById(id).orElseThrow(
                () -> new SportNotFoundException("Спорт не найден")
        );
    }

}