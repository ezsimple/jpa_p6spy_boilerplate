package newapp.domain.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import io.mkeasy.resolver.CommandMap;
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

    /**
     * 검색 & 조회
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
                            ,"prevNo")
                        , as(select(qNavigatorEntity.no.min())
                                    .from(qNavigatorEntity)
                                    .where(
                                        eqAny(qNavigatorEntity.useYn, "Y")
                                        , gtOpt(qNavigatorEntity.no, searchDTO.getSearchNo())
                                    )
                            ,"nextNo")
                        , qCustomerReqEntity.reqContent
                        , qCustomerReqEntity.resContent
                        , qCustomerReqEntity.kindCd
                        , qKindEntity.cname.as("kindNm")
                        , qCustomerReqEntity.companyEntity.name.as("reqCompanyNm")
                        , qCustomerReqEntity.reqUserNm.as("reqUserNm")
                        , qCustomerReqEntity.reqUserPhoneNo.as("reqUserPhoneNo")
                        , qCustomerReqEntity.reqUserEmail.as("reqUserEmail")
                        , qCustomerReqEntity.progressCd
                        , qProgressEntity.cname.as("progressNm")
                        , qCustomerReqEntity.userEntity.username.as("consultUserNm")
                        , Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qCustomerReqEntity.regDt, "%Y-%m-%d").as("reqDate")
                        , Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qCustomerReqEntity.modDt, "%Y-%m-%d").as("resDate")
                    ))
                .from(qCustomerReqEntity)
                .innerJoin(qKindEntity).on(eqAny(qKindEntity.code6, qCustomerReqEntity.kindCd))
                .innerJoin(qProgressEntity).on(eqAny(qProgressEntity.code6, qCustomerReqEntity.progressCd))
                .where(
                    eqAny(qCustomerReqEntity.useYn, "Y")
                    , eqAny(qCustomerReqEntity.delYn, "N")
                    .or(eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo()))
                    .or(eqOpt(qCustomerReqEntity.reqContent, searchDTO.getSearchWord()))
                    .or(eqOpt(qCustomerReqEntity.resContent, searchDTO.getSearchWord()))
                    .or(goeOpt(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                    .or(loeOpt(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                )
                .orderBy(qCustomerReqEntity.no.desc());

        return result;
    }

    /**
     * 통계
     * @param searchDTO
     * @return
     */
    public StatDTO statTblCallAssist2(SearchDTO searchDTO) {
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
                                            .and(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .and(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                            .or(eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo()))
                                            .or(eqOpt(qCustomerReqEntity.reqContent, searchDTO.getSearchWord()))
                                            .or(eqOpt(qCustomerReqEntity.resContent, searchDTO.getSearchWord()))
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
                                            , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now())
                                        ), "countReqToday")     // 당일요청건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000000") /* 버거(000000) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "countKind0")   // 버거건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000001") /* 개선(000001) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "countKind1")   // 개선사항건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000002") /* 요구(000002) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "countKind2")    // 요구건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000003") /* 문의(000003) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "countKind3")   // 문의건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000004") /* 기타(000004) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "countKind4")   // 기타건수
                        )
                )// .from(qCustomerReqEntity)
                .fetchFirst();

        return result;
    }

    /**
     * 최종 등록 번호 구하기
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
     * @param commandMap
     * @return
     */
    public CustomerReqEntity toCustomerReqEntity(CommandMap commandMap) {

        String userId = sessionUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        CustomerReqEntity customerReqEntity = new CustomerReqEntity();

        String iuFlag = commandMap.getParam("iuFlag");

        if(StringUtils.equals(iuFlag, "I")) {
            Long maxNo = getMaxNo();
            customerReqEntity.setNo(maxNo + 1);
        }

        if(StringUtils.equals(iuFlag, "U")) {
            String no = commandMap.getParam("no");
            if (!StringUtils.isEmpty(no))
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
        customerReqEntity.setDelYn("N");
        customerReqEntity.setRegId(userId);
        customerReqEntity.setRegDt(now);
        customerReqEntity.setModId(userId);
        customerReqEntity.setModDt(now);

        return customerReqEntity;
    }

    /**
     * 등록/수정
      * @param customerReqEntity
     * @return
     */
    public CustomerReqEntity save(CustomerReqEntity customerReqEntity) {
        return customerReqRepository.saveAndFlush(customerReqEntity);
    }

    /**
     * 삭제
     * @param customerReqEntity
     */
    public void delete(CustomerReqEntity customerReqEntity) {
       customerReqRepository.delete(customerReqEntity);
    }

}
