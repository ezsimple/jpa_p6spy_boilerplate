package newapp.domain.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dto.CustomerReqDTO;
import newapp.domain.dto.SearchDTO;
import newapp.domain.dto.StatDTO;
import newapp.domain.entity.*;
import newapp.domain.repository.CustomerReqRepository;
import newapp.global.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;
import static newapp.global.common.core.CoreResource.jpaQuery;
import static newapp.global.common.support.QueryDslSupport.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomerReqDao {

    private final CustomerReqRepository customerReqRepository;
    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final SessionUtil sessionUtil;

    private QCustomerReqEntity qCustomerReqEntity = QCustomerReqEntity.customerReqEntity;
    private QCustomerReqEntity qNavigatorEntity = new QCustomerReqEntity("navigatorEntity");
    private QCodeEntity qKindEntity = new QCodeEntity("kindEntity");
    private QCodeEntity qProgressEntity = new QCodeEntity("progressEntity");
    private QCompanyEntity qCompanyEntity = QCompanyEntity.companyEntity;
    private QUserEntity qUserEntity = QUserEntity.userEntity;

    /**
     * 검색 & 조회
     *
     * @param searchDTO
     * @return
     */
    public JPAQuery<CustomerReqDTO> selectTblCallAssist(SearchDTO searchDTO) {

        JPAQuery<CustomerReqDTO> result = jpaQuery()
                .select(Projections.bean(
                        CustomerReqDTO.class
                        , qCustomerReqEntity.no
                        , as(select(qNavigatorEntity.no.max())
                                        .from(qNavigatorEntity)
                                        .where(
                                                eqAny(qNavigatorEntity.useYn, "Y")
                                                , ltOpt(qNavigatorEntity.no, searchDTO.getSearchNo())
                                        )
                                , "prevNo")
                        , as(select(qNavigatorEntity.no.min())
                                        .from(qNavigatorEntity)
                                        .where(
                                                eqAny(qNavigatorEntity.useYn, "Y")
                                                , gtOpt(qNavigatorEntity.no, searchDTO.getSearchNo())
                                        )
                                , "nextNo")
                        , qCustomerReqEntity.reqContent
                        , qCustomerReqEntity.resContent
                        , qCustomerReqEntity.kindCd
                        , qKindEntity.cname.as("kindNm")
                        , qCustomerReqEntity.reqUserNm.as("reqUserNm")
                        , qCustomerReqEntity.reqUserPhoneNo.as("reqUserPhoneNo")
                        , qCustomerReqEntity.reqUserEmail.as("reqUserEmail")
                        , qCustomerReqEntity.progressCd
                        , qProgressEntity.cname.as("progressNm")
                        , qCustomerReqEntity.companyEntity.no.as("reqCompanyNo")
                        , qCustomerReqEntity.companyEntity.name.as("reqCompanyNm")
                        , qCustomerReqEntity.userEntity.username.as("userNm")
                        , Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qCustomerReqEntity.regDt, "%Y-%m-%d").as("reqDate")
                        , Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qCustomerReqEntity.modDt, "%Y-%m-%d").as("resDate")
                ))
                .from(qCustomerReqEntity)
                .innerJoin(qKindEntity).on(eqAny(qKindEntity.code6, qCustomerReqEntity.kindCd))
                .innerJoin(qProgressEntity).on(eqAny(qProgressEntity.code6, qCustomerReqEntity.progressCd))
                .innerJoin(qCustomerReqEntity.companyEntity, qCompanyEntity) // alias를 사용함으로써, cross join 방지
                .innerJoin(qCustomerReqEntity.userEntity, qUserEntity)       // alias를 사용함으로써, cross join 방지
                .where(
                        eqAny(qCustomerReqEntity.useYn, "Y")
                        , eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo())
                        , eqOpt(qCustomerReqEntity.reqContent, searchDTO.getSearchWord())
                        , eqOpt(qCustomerReqEntity.resContent, searchDTO.getSearchWord())
                        , goeOpt(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                        , loeOpt(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                )
                .orderBy(qCustomerReqEntity.no.desc());

        return result;
    }

    /**
     * 통계
     *
     * @param searchDTO
     * @return
     */
    public StatDTO statTblCallAssist(SearchDTO searchDTO) {
        StatDTO result = jpaQuery().select(
                        Projections.bean(
                                StatDTO.class
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                        ), "countTotal")        // 전체건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                                , eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo())
                                                , eqOpt(qCustomerReqEntity.reqContent, searchDTO.getSearchWord())
                                                , eqOpt(qCustomerReqEntity.resContent, searchDTO.getSearchWord())
                                        ), "countSearch")    // 검색건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , inAny(qCustomerReqEntity.progressCd, "001003", "001004", "001005") /* 완료(001003), 보류(001004), 기각(001005) */
                                                , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now()) /* LocalDateTime.parse("2022-03-05") */
                                        ), "countDoneToday")    // 당일완료건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , inAny(qCustomerReqEntity.progressCd, "001000", "001001", "001002") /* 대기(001000), 접수(001001), 검토(001002) */
                                                , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now())
                                        ), "countReqToday")     // 당일요청건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001000") /* 대기(000000) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress0")   // 대기건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001001") /* 접수(000001) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress1")   // 접수건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001002") /* 검토(000002) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress2")   // 검토건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001003") /* 완료(000003) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress3")   // 완료건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001004") /* 보류(000004) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress4")   // 보류건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001005") /* 기각(000005) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress5")   // 대기건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000000") /* 버거(000000) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind0")   // 버거건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000001") /* 개선(000001) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind1")   // 개선사항건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000002") /* 요구(000002) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind2")    // 요구건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000003") /* 문의(000003) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind3")   // 문의건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000004") /* 기타(000004) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind4")   // 기타건수
                        )
                ).from(qCustomerReqEntity)
                .fetchFirst();

        return result;
    }

    /**
     * 최종 등록 번호 구하기
     *
     * @return
     */
    public Long getMaxNo() {
        SearchDTO result = jpaQuery().select(Projections.bean(
                        SearchDTO.class
                        , qCustomerReqEntity.no.max().as("maxNo")
                )).from(qCustomerReqEntity)
                .fetchFirst();
        return result.getMaxNo() == null ? 0L : result.getMaxNo();
    }

    /**
     * 파라미터를 customerEntity로 변환
     *
     * @param commandMap
     * @return
     */
    public CustomerReqEntity toCustomerReqEntity(CommandMap commandMap) throws Exception {

        String userId = sessionUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        CustomerReqEntity customerReqEntity = new CustomerReqEntity();

        String iuFlag = commandMap.getParam("iuFlag");

        if (StringUtils.equals(iuFlag, "I")) {
            Long maxNo = getMaxNo();
            customerReqEntity.setNo(maxNo + 1);
        }

        String no = commandMap.getParam("no");
        if (StringUtils.equals(iuFlag, "U") || StringUtils.equals(iuFlag, "D")) {
            if(StringUtils.isEmpty(no))
                throw new Exception("no가 누락 되었습니다.");
            customerReqEntity.setNo(Long.parseLong(no));                         // 요청번호
        }

        String kindCd = commandMap.getParam("kindCd");
        if (!StringUtils.isEmpty(kindCd))
            customerReqEntity.setKindCd(kindCd);                                     // 분류코드

        String reqCompanyNo = commandMap.getParam("reqCompanyNo");
        if (!StringUtils.isEmpty(reqCompanyNo)) {
            Optional<CompanyEntity> companyEntity = companyDao.findById(Long.parseLong(reqCompanyNo));
            if (companyEntity.isPresent()) {
                customerReqEntity.setCompanyEntity(companyEntity.get());             // 요청회사명
            }
        }

        String reqUserNm = commandMap.getParam("reqUserNm");
        if (!StringUtils.isEmpty(reqUserNm))
            customerReqEntity.setReqUserNm(reqUserNm);                              // 요청자명

        String reqUserPhoneNo = commandMap.getParam("reqUserPhoneNo");
        if (!StringUtils.isEmpty(reqUserPhoneNo))
            customerReqEntity.setReqUserPhoneNo(reqUserPhoneNo);                    // 요청자연락처

        String progressCd = commandMap.getParam("progressCd");
        if (!StringUtils.isEmpty(progressCd))
            customerReqEntity.setProgressCd(progressCd);                            // 진행정보코드

        Optional<UserEntity> userEntity = userDao.findByUserId(userId);
        customerReqEntity.setUserEntity(userEntity.get());                          // 상담자명

        String reqContent = commandMap.getParam("reqContent");
        if (!StringUtils.isEmpty(reqContent))
            customerReqEntity.setReqContent(reqContent);                            // 요청내용

        String resContent = commandMap.getParam("resContent");
        if (!StringUtils.isEmpty(resContent))
            customerReqEntity.setResContent(resContent);                            // 응답내용

        customerReqEntity.setUseYn("Y");
        customerReqEntity.setRegId(userId);
        customerReqEntity.setRegDt(now);
        customerReqEntity.setModId(userId);
        customerReqEntity.setModDt(now);

        return customerReqEntity;
    }

    /**
     * 등록/수정
     *
     * @param customerReqEntity
     * @return
     */
    public CustomerReqEntity save(CustomerReqEntity customerReqEntity) {
        return customerReqRepository.saveAndFlush(customerReqEntity);
    }

    /**
     * 삭제
     *
     * @param customerReqEntity
     */
    public void delete(CustomerReqEntity customerReqEntity) {
        customerReqRepository.deleteById(customerReqEntity.getNo());
    }

    public LocalDateTime findFirstReqDt() {
        // SearchDTO result = jpaQuery().select(Projections.bean(
        //                SearchDTO.class
        //                , qCustomerReqEntity.regDt.min().as("minRegDt")
        //        )).from(qCustomerReqEntity)
        //        .fetchFirst();
        // query를 실행할 필요가 없으므로..
        LocalDateTime firstServiceDt = DateUtil.toLocalDateTime("2022-01-01", "yyyy-MM-dd");
        return firstServiceDt;
    }
}
