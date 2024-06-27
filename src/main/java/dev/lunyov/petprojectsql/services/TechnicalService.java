package dev.lunyov.petprojectsql.services;

import dev.lunyov.petprojectsql.models.Technical;
import dev.lunyov.petprojectsql.repositories.TechnicalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicalService {

    private final TechnicalRepository technicalRepository;

    @Autowired
    public TechnicalService(TechnicalRepository technicalRepository) {
        this.technicalRepository = technicalRepository;
    }

    public Optional<Technical> findById(Long id) {
        return technicalRepository.findById(id);
    }

    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }
}