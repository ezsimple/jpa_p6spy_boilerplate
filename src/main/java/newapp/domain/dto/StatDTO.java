package newapp.domain.dto;

import lombok.Data;

@Data
public class StatDTO {

    Long countTotal = 0L;           // 전체건수
    Long countSearch = 0L;          // 검색건수
    Long countDoneToday = 0L;       // 당일완료건수
    Long countReqToday = 0L;        // 당일요청건수

    Long countProgress0 = 0L;       // 대기건수
    Long countProgress1 = 0L;       // 접수건수
    Long countProgress2 = 0L;       // 검토건수
    Long countProgress3 = 0L;       // 완료건수
    Long countProgress4 = 0L;       // 보류건수
    Long countProgress5 = 0L;       // 기각건수

    Long countKind0 = 0L;           // 버거건수
    Long countKind1 = 0L;           // 개선건수
    Long countKind2 = 0L;           // 요구건수
    Long countKind3 = 0L;           // 문의건수
    Long countKind4 = 0L;           // 기타건수

}
