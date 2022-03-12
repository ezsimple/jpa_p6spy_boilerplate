package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.LangEntity;
import newapp.domain.repository.LangRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LangDao {

    private final LangRepository langRepository;

    public Optional<LangEntity> findById(String langCd) {
        return langRepository.findById(langCd);
    }

    public LangEntity save(LangEntity langEntity) {
        Optional<LangEntity> lang = findById(langEntity.getLang());
        if(lang.isPresent()) {
            log.warn("Alread exist langCd : {}", lang.get().getLang());
            return langEntity;
        }
        return langRepository.saveAndFlush(langEntity);
    }
}
