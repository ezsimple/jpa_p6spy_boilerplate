package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.CompanyEntity;
import newapp.domain.repository.CompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CompanyDao {
    private final CompanyRepository companyRepository;

    public Optional<CompanyEntity> findById(Long companyNo) {
        return companyRepository.findById(companyNo);
    }

    public CompanyEntity save(CompanyEntity companyEntity) {
        Long companyNo = companyEntity.getNo();
        if(findById(companyNo).isPresent()) {
            log.warn("Alread exist company no : {}", companyNo);
            return companyEntity;
        }
        return companyRepository.saveAndFlush(companyEntity);
    }
}
