package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.CompanyEntity;
import newapp.domain.entity.QCompanyEntity;
import newapp.domain.repository.CompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static newapp.global.common.core.CoreResource.jpaQuery;
import static newapp.global.common.support.QueryDslSupport.eqAny;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CompanyDao {

    private final CompanyRepository companyRepository;
    private QCompanyEntity qCompanyEntity = QCompanyEntity.companyEntity;

    public Optional<CompanyEntity> findById(Long companyNo) {
        return companyRepository.findById(companyNo);
    }

    public List<CompanyEntity> findByProjNo(Long projNo) {
        List<CompanyEntity> result = jpaQuery().selectFrom(qCompanyEntity)
                .where(
                    eqAny(qCompanyEntity.projectEntity.projNo, projNo)
                    , eqAny(qCompanyEntity.useYn, "Y")
                )
                .fetch();
        return result;
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
