package newapp.domain.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import io.mkeasy.resolver.CommandMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dto.SearchDTO;
import newapp.domain.dto.StatDTO;
import newapp.domain.entity.CustomerReqEntity;
import newapp.domain.entity.QCodeEntity;
import newapp.domain.entity.QCustomerReqEntity;
import newapp.domain.repository.CustomerReqRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;
import static newapp.global.core.CoreResource.jpaQuery;
import static newapp.global.support.QueryDslSupport.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomerReqDao {

    private final CustomerReqRepository customerReqRepository;

    private QCustomerReqEntity qCustomerReqEntity = QCustomerReqEntity.customerReqEntity;
    private QCustomerReqEntity qNavigatorEntity = new QCustomerReqEntity("navigatorEntity");
    private QCodeEntity qKindEntity = new QCodeEntity("kindEntity");
    private QCodeEntity qProgressEntity = new QCodeEntity("progressEntity");

    /**
     * 검색 & 조회
     * @param searchDTO
     * @return
     */
    public JPAQuery<CustomerReqEntity> selectTblCallAssist(SearchDTO searchDTO) {

        JPAQuery<CustomerReqEntity> result = jpaQuery()
                .select(Projections.bean(
                        CustomerReqEntity.class
                        , qCustomerReqEntity.no
                        , as(select(qNavigatorEntity.no.max())
                                    .from(qNavigatorEntity)
                                    .where(
                                        eqAny(qNavigatorEntity.useYn, "Y")
                                        , ltOpt(qNavigatorEntity.no, searchDTO.getSearchNo())
                                    )
                            ,"prev_no")
                        , as(select(qNavigatorEntity.no.min())
                                    .from(qNavigatorEntity)
                                    .where(
                                        eqAny(qNavigatorEntity.useYn, "Y")
                                        , gtOpt(qNavigatorEntity.no, searchDTO.getSearchNo())
                                    )
                            ,"next_no")
                        , qCustomerReqEntity.kindCd
                        , qKindEntity.cname.as("kind_nm")
                        , qCustomerReqEntity.customerEntity
                        , qCustomerReqEntity.progressCd
                        , qKindEntity.cname.as("progress_nm")
                        , qCustomerReqEntity.userEntity
                    ))
                .from(qCustomerReqEntity)
                .innerJoin(qKindEntity).on(eqAny(qKindEntity.gid, "000"), eqAny(qKindEntity.cid, qCustomerReqEntity.kindCd))
                .innerJoin(qProgressEntity).on(eqAny(qProgressEntity.gid, "001"), eqAny(qProgressEntity.cid, qCustomerReqEntity.progressCd))
                .where(
                    qCustomerReqEntity.useYn.eq("Y")
                    .or(eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo()))
                    .or(eqOpt(qCustomerReqEntity.req, searchDTO.getSearchWord()))
                    .or(eqOpt(qCustomerReqEntity.res, searchDTO.getSearchWord()))
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
                                        ), "count_total")        // 전체건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            .and(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .and(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                            .or(eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo()))
                                            .or(eqOpt(qCustomerReqEntity.req, searchDTO.getSearchWord()))
                                            .or(eqOpt(qCustomerReqEntity.res, searchDTO.getSearchWord()))
                                        ), "count_search")    // 검색건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.progressCd, "001003", "001004", "001005") /* 완료(001003), 보류(001004), 기각(001005) */
                                            , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now()) /* LocalDateTime.parse("2022-03-05") */
                                        ), "count_done_today")    // 당일완료건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now())
                                        ), "count_req_today")     // 당일요청건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000000") /* 버거(000000) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "count_kind0")      // 버거건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000001") /* 개선(000001) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "count_kind1")   // 개선사항건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000002") /* 요구(000002) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "count_kind2")    // 요구건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000003") /* 문의(000003) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "count_kind3")   // 문의건수
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                            eqAny(qCustomerReqEntity.useYn, "Y")
                                            , inAny(qCustomerReqEntity.kindCd, "000004") /* 기타(000004) */
                                            .or(goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt()))
                                            .or(loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt()))
                                        ), "count_kind4")       // 기타건수
                        )
                )// .from(qCustomerReqEntity)
                .fetchFirst();

        return result;
    }

    /**
     * 파라미터를 customerEntity로 변환
     * @param commandMap
     * @return
     */
    public CustomerReqEntity toCustomerReqEntity(CommandMap commandMap) {

       CustomerReqEntity customerReqEntity = new CustomerReqEntity();

       String no = commandMap.getParam("no");
       if(!StringUtils.isEmpty(no))
           customerReqEntity.setNo(Long.parseLong(no));                             // 요청번호

       String kindCd = commandMap.getParam("kindCd");
       if(!StringUtils.isEmpty(kindCd))
           customerReqEntity.setKindCd(kindCd);                                     // 분류코드

       String customerNo = commandMap.getParam("customerNo");
       if(!StringUtils.isEmpty(customerNo))
           customerReqEntity.getCustomerEntity().setNo(Long.parseLong(customerNo)); // 고객정보

       String progressCd = commandMap.getParam("progressCd");
       if(!StringUtils.isEmpty(progressCd))
           customerReqEntity.setProgressCd(progressCd);                             // 진행정보코드

       String userId = commandMap.getParam("userId");
       if(!StringUtils.isEmpty(userId))
           customerReqEntity.getUserEntity().setUserId(userId);                     // 접수자정보

       String req = commandMap.getParam("req");
       if(!StringUtils.isEmpty(req))
           customerReqEntity.setReq(req);                                           // 요청내용

       String res = commandMap.getParam("res");
       if(!StringUtils.isEmpty(res))
           customerReqEntity.setRes(res);                                           // 응답내용

       return customerReqEntity;
   }

    /**
     * 등록/수정
      * @param customerReqEntity
     * @return
     */
    public CustomerReqEntity save(CustomerReqEntity customerReqEntity) {
        return customerReqRepository.save(customerReqEntity);
    }

    /**
     * 삭제
     * @param customerReqEntity
     */
    public void delete(CustomerReqEntity customerReqEntity) {
       customerReqRepository.delete(customerReqEntity);
    }

}
