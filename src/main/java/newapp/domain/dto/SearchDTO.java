package newapp.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {
    Long searchNo;          // 선택번호
    String searchWord;      // 검색어
    LocalDateTime startDt;  // 검색시작일
    LocalDateTime endDt;    // 검색종료일
}
