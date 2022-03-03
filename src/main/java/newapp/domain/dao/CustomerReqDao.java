package newapp.domain.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.CustomerReqEntity;
import newapp.domain.entity.QCodeEntity;
import newapp.domain.entity.QCustomerReqEntity;
import newapp.domain.repository.CustomerReqRepository;
import org.springframework.stereotype.Repository;

import static newapp.global.core.CoreResource.jpaQuery;
import static newapp.global.support.QueryDslSupport.eqAny;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomerReqDao {

    private final CustomerReqRepository customerReqRepository;

    private QCustomerReqEntity qCustomerReqEntity = QCustomerReqEntity.customerReqEntity;
    private QCodeEntity qKindEntity = QCodeEntity.codeEntity;
    private QCodeEntity qProgressEntity = QCodeEntity.codeEntity;

    public JPAQuery<CustomerReqEntity> selectTblCallAssist(CustomerReqEntity entity) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qCustomerReqEntity.useYn.eq("Y"));
        // booleanBuilder.and(eqAny(qCustomerReqEntity.no, entity.getNo()));

        JPAQuery<CustomerReqEntity> result = jpaQuery()
                .select(Projections.bean(
                        CustomerReqEntity.class
                        , qCustomerReqEntity.no
                        , qCustomerReqEntity.kindCd
                        , qKindEntity.cname.as("kindNm")
                        , qCustomerReqEntity.customerEntity
                        , qCustomerReqEntity.progressCd
                        , qKindEntity.cname.as("progressNm")
                        , qCustomerReqEntity.userEntity
                    ))
                .from(qCustomerReqEntity)
                .innerJoin(qKindEntity).on(qKindEntity.gid.eq("000"), qKindEntity.cid.eq(qCustomerReqEntity.kindCd))
                .innerJoin(qProgressEntity).on(qProgressEntity.gid.eq("001"), qProgressEntity.cid.eq(qCustomerReqEntity.progressCd))
                .where(booleanBuilder)
                .orderBy(qCustomerReqEntity.no.desc());

        return result;
    }

    /**
     <select id="selectTblCallAssist" parameterType="egovMap" resultType="egovMap">
     SELECT
     NO
     <if test="no != null and no != ''">
     ,(SELECT MIN(NO) FROM TBL_CALL_ASSIST WHERE NO <![CDATA[>]]> #{no} AND USE_YN = 'Y') AS NEXT_NO
     ,(SELECT MAX(NO) FROM TBL_CALL_ASSIST WHERE NO <![CDATA[<]]> #{no} AND USE_YN = 'Y') AS PREV_NO
     </if>
     , FARM_NO, FARM_NM, FARM_PHONE_NO, FARM_USER, TO_CHAR(REQ_DATE, 'YYYY-MM-DD') AS REQ_DATE
     , REQ_KIND, REQ_USER, REQ_CONTENT, TO_CHAR(RES_DATE, 'YYYY-MM-DD') AS RES_DATE, RES_CONTENT, DONE_STS
     FROM TBL_CALL_ASSIST
     WHERE 1=1
     <if test="no != null and no != ''">
     AND NO = #{no}
     </if>
     AND USE_YN = 'Y'
     ORDER BY NO DESC
     </select>
     */


}
