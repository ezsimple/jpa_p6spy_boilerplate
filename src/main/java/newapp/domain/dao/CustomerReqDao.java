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
     * ๊ฒ์ & ์กฐํ
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
                .innerJoin(qCustomerReqEntity.companyEntity, qCompanyEntity) // alias๋ฅผ ์ฌ์ฉํจ์ผ๋ก์จ, cross join ๋ฐฉ์ง
                .innerJoin(qCustomerReqEntity.userEntity, qUserEntity)       // alias๋ฅผ ์ฌ์ฉํจ์ผ๋ก์จ, cross join ๋ฐฉ์ง
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
     * ํต๊ณ
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
                                        ), "countTotal")        // ์?์ฒด๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                                , eqOpt(qCustomerReqEntity.no, searchDTO.getSearchNo())
                                                , eqOpt(qCustomerReqEntity.reqContent, searchDTO.getSearchWord())
                                                , eqOpt(qCustomerReqEntity.resContent, searchDTO.getSearchWord())
                                        ), "countSearch")    // ๊ฒ์๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , inAny(qCustomerReqEntity.progressCd, "001003", "001004", "001005") /* ์๋ฃ(001003), ๋ณด๋ฅ(001004), ๊ธฐ๊ฐ(001005) */
                                                , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now()) /* LocalDateTime.parse("2022-03-05") */
                                        ), "countDoneToday")    // ๋น์ผ์๋ฃ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , inAny(qCustomerReqEntity.progressCd, "001000", "001001", "001002") /* ๋๊ธฐ(001000), ์?์(001001), ๊ฒํ?(001002) */
                                                , eqDay(qCustomerReqEntity.regDt, LocalDateTime.now())
                                        ), "countReqToday")     // ๋น์ผ์์ฒญ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001000") /* ๋๊ธฐ(000000) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress0")   // ๋๊ธฐ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001001") /* ์?์(000001) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress1")   // ์?์๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001002") /* ๊ฒํ?(000002) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress2")   // ๊ฒํ?๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001003") /* ์๋ฃ(000003) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress3")   // ์๋ฃ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001004") /* ๋ณด๋ฅ(000004) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress4")   // ๋ณด๋ฅ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.progressCd, "001005") /* ๊ธฐ๊ฐ(000005) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countProgress5")   // ๋๊ธฐ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000000") /* ๋ฒ๊ฑฐ(000000) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind0")   // ๋ฒ๊ฑฐ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000001") /* ๊ฐ์?(000001) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind1")   // ๊ฐ์?์ฌํญ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000002") /* ์๊ตฌ(000002) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind2")    // ์๊ตฌ๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000003") /* ๋ฌธ์(000003) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind3")   // ๋ฌธ์๊ฑด์
                                , as(select(qCustomerReqEntity.no.count())
                                        .from(qCustomerReqEntity)
                                        .where(
                                                eqAny(qCustomerReqEntity.useYn, "Y")
                                                , eqAny(qCustomerReqEntity.kindCd, "000004") /* ๊ธฐํ(000004) */
                                                , goeDay(qCustomerReqEntity.regDt, searchDTO.getStartDt())
                                                , loeDay(qCustomerReqEntity.regDt, searchDTO.getEndDt())
                                        ), "countKind4")   // ๊ธฐํ๊ฑด์
                        )
                ).from(qCustomerReqEntity)
                .fetchFirst();

        return result;
    }

    /**
     * ์ต์ข ๋ฑ๋ก ๋ฒํธ ๊ตฌํ๊ธฐ
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
     * ํ๋ผ๋ฏธํฐ๋ฅผ customerEntity๋ก ๋ณํ
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
                throw new Exception("no๊ฐ ๋๋ฝ ๋์์ต๋๋ค.");
            customerReqEntity.setNo(Long.parseLong(no));                         // ์์ฒญ๋ฒํธ
        }

        String kindCd = commandMap.getParam("kindCd");
        if (!StringUtils.isEmpty(kindCd))
            customerReqEntity.setKindCd(kindCd);                                     // ๋ถ๋ฅ์ฝ๋

        String reqCompanyNo = commandMap.getParam("reqCompanyNo");
        if (!StringUtils.isEmpty(reqCompanyNo)) {
            Optional<CompanyEntity> companyEntity = companyDao.findById(Long.parseLong(reqCompanyNo));
            if (companyEntity.isPresent()) {
                customerReqEntity.setCompanyEntity(companyEntity.get());             // ์์ฒญํ์ฌ๋ช
            }
        }

        String reqUserNm = commandMap.getParam("reqUserNm");
        if (!StringUtils.isEmpty(reqUserNm))
            customerReqEntity.setReqUserNm(reqUserNm);                              // ์์ฒญ์๋ช

        String reqUserPhoneNo = commandMap.getParam("reqUserPhoneNo");
        if (!StringUtils.isEmpty(reqUserPhoneNo))
            customerReqEntity.setReqUserPhoneNo(reqUserPhoneNo);                    // ์์ฒญ์์ฐ๋ฝ์ฒ

        String progressCd = commandMap.getParam("progressCd");
        if (!StringUtils.isEmpty(progressCd))
            customerReqEntity.setProgressCd(progressCd);                            // ์งํ์?๋ณด์ฝ๋

        Optional<UserEntity> userEntity = userDao.findByUserId(userId);
        customerReqEntity.setUserEntity(userEntity.get());                          // ์๋ด์๋ช

        String reqContent = commandMap.getParam("reqContent");
        if (!StringUtils.isEmpty(reqContent))
            customerReqEntity.setReqContent(reqContent);                            // ์์ฒญ๋ด์ฉ

        String resContent = commandMap.getParam("resContent");
        if (!StringUtils.isEmpty(resContent))
            customerReqEntity.setResContent(resContent);                            // ์๋ต๋ด์ฉ

        customerReqEntity.setUseYn("Y");
        customerReqEntity.setRegId(userId);
        customerReqEntity.setRegDt(now);
        customerReqEntity.setModId(userId);
        customerReqEntity.setModDt(now);

        return customerReqEntity;
    }

    /**
     * ๋ฑ๋ก/์์?
     *
     * @param customerReqEntity
     * @return
     */
    public CustomerReqEntity save(CustomerReqEntity customerReqEntity) {
        return customerReqRepository.saveAndFlush(customerReqEntity);
    }

    /**
     * ์ญ์?
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
        // query๋ฅผ ์คํํ? ํ์๊ฐ ์์ผ๋ฏ๋ก..
        LocalDateTime firstServiceDt = DateUtil.toLocalDateTime("2022-01-01", "yyyy-MM-dd");
        return firstServiceDt;
    }
}
