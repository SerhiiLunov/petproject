package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.repository.TechnicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TechnicalRepositoryWrapper {
    private final TechnicalRepository technicalRepository;

    @Autowired
    public TechnicalRepositoryWrapper(TechnicalRepository technicalRepository) {
        this.technicalRepository = technicalRepository;
    }
}
