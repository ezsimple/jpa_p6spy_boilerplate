package newapp.domain.dto;

import lombok.Data;

@Data
public class StatDTO {

    Long countTotal = 0L;           // 전체건수
    Long countSearch = 0L;          // 검색건수
    Long countDoneToday = 0L;       // 당일완료건수
    Long countReqToday = 0L;        // 당일요청건수
    Long countKind0 = 0L;           // 버거카운트
    Long countKind1 = 0L;           // 개선카운트
    Long countKind2 = 0L;           // 요구카운트
    Long countKind3 = 0L;           // 문의카운트
    Long countKind4 = 0L;           // 기타카운트

    /**
     * 버거비율
     * @return
     */
    public double getPercentKind0() {
        if (countSearch == 0L && countTotal == 0L) return 0.0;
        Long total = countSearch > 0 ? countSearch : countTotal;
        return Math.round(countKind0 / total * 100.0);
    }

    /**
     * 개선비율
     * @return
     */
    public double getPercentKind1() {
        if (countSearch == 0L && countTotal == 0L) return 0.0;
        Long total = countSearch > 0 ? countSearch : countTotal;
        return Math.round(countKind1 / total * 100.0);
    }

    /**
     * 요구비율
     * @return
     */
    public double getPercentKind2() {
        if (countSearch == 0L && countTotal == 0L) return 0.0;
        Long total = countSearch > 0 ? countSearch : countTotal;
        return Math.round(countKind2 / total * 100.0);
    }

    /**
     *
     * 문의비율
     * @return
     */
    public double getPercentKind3() {
        if (countSearch == 0L && countTotal == 0L) return 0.0;
        Long total = countSearch > 0 ? countSearch : countTotal;
        return Math.round(countKind3 / total * 100.0);
    }

    /**
     * 기타비율
     * @return
     */
    public double getPercentKind4() {
        if (countSearch == 0L && countTotal == 0L) return 0.0;
        Long total = countSearch > 0 ? countSearch : countTotal;
        return Math.round(countKind4 / total * 100.0);
    }

}
