package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sport;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;

public interface SportService {
    List<Sport> findAll();
    Sport findByName(String name);
    Sport findById(Long id);
}