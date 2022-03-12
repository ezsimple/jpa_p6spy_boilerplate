package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.CodeEntity;
import newapp.domain.repository.CodeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CodeDao {

    private final CodeRepository codeRepository;

    public Optional<CodeEntity> findByCode6(String code6) {
        return codeRepository.findByCode6(code6);
    }

    public CodeEntity save(CodeEntity codeEntity) {
        String code6 = codeEntity.getCode6();
        if(findByCode6(code6).isPresent()) {
            log.warn("Already exist code6 : {}", code6);
            return codeEntity;
        }
        return codeRepository.saveAndFlush(codeEntity);
    }
}
