package newapp.domain.dto;

import lombok.Data;
import newapp.domain.entity.CustomerEntity;
import newapp.domain.entity.UserEntity;

@Data
public class CustomerReqDTO {

    private Long no;                       // 요청번호
    private Long prevNo;                   // 이전번호
    private Long nextNo;                   // 다음번호
    private String kindCd;                 // 분류코드
    private String kindNm;                 // 분류명
    private CustomerEntity customerEntity; // 고객정보
    private String progressCd;             // 진행정보코드
    private String progressNm;             // 진행정보명
    private UserEntity userEntity;         // 접수자정보
    private String req;                    // 요청내용
    private String res;                    // 응답내용

}
