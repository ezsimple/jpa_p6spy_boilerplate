package newapp.global.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static org.apache.commons.lang3.StringUtils.*;

public class QueryDslSupport {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	protected JPAQueryFactory jpaQuery;

    public BooleanExpression isNull(StringExpression stringPath) {
        return stringPath.isNull();
    }
    
    public BooleanExpression isNull(StringPath stringPath, boolean ...isAppend) {
        if(BooleanUtils.and(isAppend)) {
            return isNull(stringPath);
        }
        return null;
    }

    public static BooleanExpression isNotNull(StringPath stringPath) {
        return stringPath.isNotNull();
    }

    public BooleanExpression isNotNull(StringPath stringPath, boolean ...isAppend) {
        if(BooleanUtils.and(isAppend)) {
            return isNotNull(stringPath);
        }
        return null;
    }

    public static BooleanExpression isAny(BooleanExpression b) {
        return b;
    }

    public static BooleanExpression isOpt(BooleanExpression b, boolean isAppend) {
        if(isAppend) {
            return b;
        }
        return null;
    }
    
    public static BooleanExpression isOpt(BooleanExpression b, String kword) {
        if(isNotEmpty(kword)) {
            return b;
        }
        return null;
    }

    public static BooleanExpression eqAny(JPQLQuery<Long> query, Long n) {
        return query.eq(n);
    }

    public static BooleanExpression eqAny(StringExpression stringPath1, StringTemplate stringPath2) {
        return stringPath1.eq(stringPath2);
    }

    public static BooleanExpression eqAny(StringExpression stringPath1, StringExpression stringPath2) {
        return stringPath1.eq(stringPath2);
    }

    public static BooleanExpression eqAny(StringExpression stringPath, String kword) {
        return stringPath.eq(kword);
    }

    public static BooleanExpression eqAny(NumberPath<Long> numberPath, Long num) {
        return numberPath.eq(num);
    }

    public static BooleanExpression eqDay(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        if(kword == null) { return null; }
        StringExpression stringExpression = expes.year().stringValue().concat(expes.month().stringValue()).concat(expes.dayOfMonth().stringValue());
        String day = kword.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return stringExpression.eq(day);
    }



    public static BooleanExpression eqOpt(StringExpression stringPath, String kword) {
        return eqOpt(stringPath, kword, true);
    }
    
    public static BooleanExpression eqOpt(StringExpression stringPath, String kword, boolean append) {
        if(!append) {
            return null;
        }
        if(isEmpty(kword)) {
            return null;
        }
        return eqAny(stringPath, kword);
    }
    
    public static BooleanExpression eqAulOpt(StringExpression stringPath, String kword) {
        return eqAulOpt(stringPath, kword, true);
    }
    
    public static BooleanExpression eqAulOpt(StringExpression stringPath, String kword, boolean append) {
        if(!append) {
            return null;
        }
        if(isEmpty(kword)) {
            return null;
        }
        return stringPath.containsIgnoreCase(kword);
    }

    public static BooleanExpression eqOpt(NumberPath<Long> numberPath, Long num) {
        if(num == null) {
            return null;
        }
        return numberPath.eq(num);
    }

    public static BooleanExpression eqOpt(Long num, NumberPath<Long> numberPath) {
        if(num == null) {
            return null;
        }
        return eqAny(numberPath, num);
    }

    public static BooleanExpression ltOpt(NumberPath<Long> numberPath, Long num) {
        if(num == null) {
            return null;
        }
        return numberPath.lt(num);
    }

    public static BooleanExpression gtOpt(NumberPath<Long> numberPath, Long num) {
        if(num == null) {
            return null;
        }
        return numberPath.gt(num);
    }

    public BooleanExpression inAny(StringExpression stringPath, JPQLQuery<String> query) {
        return stringPath.in(query);
    }

    public static BooleanExpression inAny(StringExpression st, String ...kword) {
        return st.in(kword);
    }

    public static BooleanExpression inOpt(StringExpression stringPath, String ...kword) {
        if(ArrayUtils.isNotEmpty(kword)) {
            return inAny(stringPath, kword);
        }
        return null;
    }

    public static BooleanExpression inAny(NumberPath<?> numberPath, Long ...kword) {
        return numberPath.in(kword);
    }
    
    public static BooleanExpression inOpt(NumberPath<?> numberPath, Long ...kword) {
        if(ArrayUtils.isNotEmpty(kword)) {
            return inAny(numberPath, kword);
        }
        return null;
    }

    public BooleanExpression notInAny(StringExpression stringPath, String ...kword) {
        return stringPath.notIn(kword);
    }

    public BooleanExpression notInOpt(StringExpression stringPath, String ...kword) {
        if(ArrayUtils.isNotEmpty(kword)) {
            return notInAny(stringPath, kword);
        }
        return null;
    }

    public BooleanExpression notInOpt(boolean isAppend, StringExpression stringPath, String ...kword) {
        if(isAppend) {
            return notInOpt(stringPath, kword);
        }
        return null;
    }

    public static BooleanExpression sWithAny(StringExpression stringPath, String kword) {
        return stringPath.startsWith(kword);
    }

    public static BooleanExpression sWithOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return sWithAny(stringPath, kword);
    }

    public static BooleanExpression likeOpt(JPQLQuery<String> subQuery, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        
        return stringTemplate("{0}", subQuery).like(kword);
    }

    public static BooleanExpression likeAny(StringExpression stringPath, String kword) {
        return stringPath.like(kword);
    }

    public static BooleanExpression likeOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return likeAny(stringPath, kword);
    }

    public BooleanExpression likeAny(NumberPath<?> numberPath, String kword) {
        return numberPath.like(kword);
    }

    public BooleanExpression likeOpt(NumberPath<?> numberPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return likeAny(numberPath, kword);
    }

    public BooleanExpression notlikeAny(StringExpression stringPath, String kword) {
        return stringPath.notLike(kword);
    }

    public BooleanExpression notlikeOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return notlikeAny(stringPath, kword);
    }

    public BooleanExpression notlikeOpt(boolean isAppend, StringExpression stringPath, String kword) {
        if(isAppend) {
            return notlikeOpt(stringPath, kword);
        }
        return null;
    }

    // <=
    public BooleanExpression loeAny(StringExpression stringPath, String kword) {
        return stringPath.loe(kword);
    }

    public static BooleanExpression loeDay(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        if(kword == null) { return null; }
        StringExpression stringExpression = expes.year().stringValue().concat(expes.month().stringValue()).concat(expes.dayOfMonth().stringValue());
        String day = kword.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return stringExpression.loe(day);
    }

    public static BooleanExpression loeOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return stringPath.loe(kword);
    }

    public BooleanExpression loeOpt(StringExpression stringPath, String kword, boolean ...isAppend) {
        if(BooleanUtils.and(isAppend)) {
            return loeOpt(stringPath, kword);
        }
        return null;
    }

    public static BooleanExpression loeOpt(NumberExpression<Long> stringPath, Long kword) {
        if(kword == null) {
            return null;
        }
        return stringPath.loe(kword);
    }

    public static BooleanExpression loeOpt(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        if(kword == null) {
            return null;
        }
        return expes.loe(kword);
    }

    public static BooleanExpression eqAny(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        return expes.eq(kword);
    }

    // >=
    public BooleanExpression goeAny(StringExpression stringPath, String kword) {
        return stringPath.goe(kword);
    }

    public static BooleanExpression goeDay(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        if(kword == null) { return null; }
        StringExpression stringExpression = expes.year().stringValue().concat(expes.month().stringValue()).concat(expes.dayOfMonth().stringValue());
        String day = kword.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return stringExpression.goe(day);
    }

    public static BooleanExpression goeOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return stringPath.goe(kword);
    }

    public static BooleanExpression goeOpt(NumberExpression<Long> stringPath, Long kword) {
        if(kword == null) {
            return null;
        }
        return stringPath.goe(kword);
    }

    public static BooleanExpression goeOpt(DateTimePath<LocalDateTime> expes, LocalDateTime kword) {
        if(kword == null) {
            return null;
        }
        return expes.goe(kword);
    }


    public static BooleanExpression btnAny(StringExpression stringPath, String from, String to) {
        return stringPath.between(from, to);
    }

    public BooleanExpression btnAny(NumberExpression<Long> expes, Long from, Long to) {
        return expes.between(from, to);
    }

    public static BooleanExpression btnOpt(StringExpression stringPath, String from, String to) {
        if( isEmpty(from) || isEmpty(to)) {
            return null;
        }
        return btnAny(stringPath, from, to);
    }


    public static BooleanExpression btnOpt(NumberExpression<Long> expes, Long from, Long to) {
        if( from == null || to == null ) {
            return null;
        }
        return expes.between(from, to);
    }

    public static BooleanExpression btnOpt(DateTimePath<LocalDateTime> expes, LocalDateTime from, LocalDateTime to) {
        if( from == null || to == null ) {
            return null;
        }
        return expes.between(from, to);
    }

    public static BooleanExpression btnOpt(DateTimePath<LocalDateTime> expes, LocalDate from, LocalDate to) {
        if( from == null || to == null ) {
            return null;
        }
        
        return expes.between(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX));
    }
    
    public static BooleanExpression neAny(StringExpression stringPath, String kword) {
        return stringPath.ne(kword);
    }

    public static BooleanExpression neOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return neAny(stringPath, kword);
    }



    public static BooleanExpression ltAny(StringExpression stringPath, String kword) {
        return stringPath.lt(kword);
    }

    public static BooleanExpression ltOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return ltAny(stringPath, kword);
    }


    public BooleanExpression gtAny(StringExpression stringPath, String kword) {
        return stringPath.gt(kword);
    }

    public static BooleanExpression gtOpt(StringExpression stringPath, String kword) {
        if(isEmpty(kword)) {
            return null;
        }
        return stringPath.gt(kword);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static OrderSpecifier<?> orderBy(String fieldName, String orderType) {
        Order order = Order.DESC;
        if("asc".equalsIgnoreCase(orderType)) {
            order = Order.ASC;
        }

        return new OrderSpecifier(order, Expressions.path(Object.class, fieldName));
    }
    
    public static OrderSpecifier<?> order(String fieldName, String orderType) {
        if(isEmpty(fieldName)) {
            return null;
        }
        return orderBy(fieldName, orderType);
    }
    
    public static OrderSpecifier<?> order(String chkNor, OrderSpecifier<?> o) {
        if(isNotBlank(chkNor)) {
            return null;
        }
        return o;
    }
    
    public static OrderSpecifier<?>[] orders(OrderSpecifier<?> ...o) {
        List<OrderSpecifier<?>> list = new ArrayList<OrderSpecifier<?>>();
        for(OrderSpecifier<?> orderSpec : o) {
            if( orderSpec != null ) {
                list.add(orderSpec);
            }
        }
        return list.toArray(new OrderSpecifier<?>[list.size()]);
    }
    
    public static Predicate toPredicate(BooleanExpression ...expers) {
        BooleanBuilder builer = new BooleanBuilder();
        for(BooleanExpression bExpres : expers) {
            if(bExpres != null) {
                builer.and(bExpres);
            }
        }
        return builer;
    }
    
    public static StringExpression FN_COMM_CODE_NAME(StringExpression code, String lang) {
        return stringTemplate("FN_COMM_CODE_NAME({0}, {1})", code, lang);
    }
    
    public static StringExpression FN_ADMIN_NAME(StringExpression expres) {
        return stringTemplate("FN_ADMIN_NAME({0})", expres);
    }

}
