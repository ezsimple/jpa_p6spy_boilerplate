package newapp.domain.dto;

import lombok.Data;

@Data
public class CustomerReqDTO {

    private Long no;                       // 요청번호
    private Long prevNo;                   // 이전번호
    private Long nextNo;                   // 다음번호
    private String kindCd;                 // 분류코드
    private String kindNm;                 // 분류명
    private String reqCompanyNm;           // 요청회사명
    private String reqUserNm;              // 요청자명
    private String reqUserPhoneNo;         // 요청자연락처
    private String reqUserEmail;           // 요청자이메일
    private String progressCd;             // 진행정보코드
    private String progressNm;             // 진행정보명
    private String consultUserNm;          // 접수자명
    private String reqContent;             // 요청내용
    private String resContent;             // 응답내용
    private String reqDate;                // 접수일자
    private String resDate;                // 처리일자

}
