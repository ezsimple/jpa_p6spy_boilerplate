package newapp.domain.dto;

import io.mkeasy.utils.MapUtil;
import lombok.Data;

import java.util.Map;

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

    public Map<String, Object> toCalcStat() {

        Long countSearch = this.getCountSearch();
        Long countTotal = this.getCountTotal();
        Long total = countSearch > 0 ? countSearch : countTotal == 0 ? 1 : countTotal;

        Long countProgress0 = this.getCountProgress0();
        Long countProgress1 = this.getCountProgress1();
        Long countProgress2 = this.getCountProgress2();
        Long countProgress3 = this.getCountProgress3();
        Long countProgress4 = this.getCountProgress4();
        Long countProgress5 = this.getCountProgress5();

        Long countKind0 = this.getCountKind0();
        Long countKind1 = this.getCountKind1();
        Long countKind2 = this.getCountKind2();
        Long countKind3 = this.getCountKind3();
        Long countKind4 = this.getCountKind4();

        Map map = MapUtil.newMap();

        map.put("total", total);

        double percentProgress0 = 0.0;
        if (total != 0L) {
            percentProgress0 = Math.round((countProgress0 * 100.0) / total);
            map.put("percentProgress0", percentProgress0 + "%");
        }

        double percentProgress1 = 0.0;
        if (total != 0L) {
            percentProgress1 = Math.round((countProgress1 * 100.0) / total);
            map.put("percentProgress1", percentProgress1 + "%");
        }

        double percentProgress2 = 0.0;
        if (total != 0L) {
            percentProgress2 = Math.round((countProgress2 * 100.0) / total);
            map.put("percentProgress2", percentProgress2 + "%");
        }

        double percentProgress3 = 0.0;
        if (total != 0L) {
            percentProgress3 = Math.round((countProgress3 * 100.0) / total);
            map.put("percentProgress3", percentProgress3 + "%");
        }

        double percentProgress4 = 0.0;
        if (total != 0L) {
            percentProgress4 = Math.round((countProgress4 * 100.0) / total);
            map.put("percentProgress4", percentProgress4 + "%");
        }

        double percentProgress5 = 0.0;
        if (total != 0L) {
            percentProgress5 = Math.round((countProgress5 * 100.0) / total);
            map.put("percentProgress5", percentProgress5 + "%");
        }

        double percentKind0 = 0.0;
        if (total != 0L) {
            percentKind0 = Math.round((countKind0 * 100.0) / total);
            map.put("percentKind0", percentKind0 + "%");
        }

        double percentKind1 = 0.0;
        if (total != 0L) {
            percentKind1 = Math.round((countKind1 * 100.0) / total);
            map.put("percentKind1", percentKind1 + "%");
        }

        double percentKind2 = 0.0;
        if (total != 0L) {
            percentKind2 = Math.round((countKind2 * 100.0) / total);
            map.put("percentKind2", percentKind2 + "%");
        }

        double percentKind3 = 0.0;
        if (total != 0L) {
            percentKind3 = Math.round((countKind3 * 100.0) / total);
            map.put("percentKind3", percentKind3 + "%");
        }

        double percentKind4 = 0.0;
        if (total != 0L) {
            percentKind4 = Math.round((countKind4 * 100.0) / total);
            map.put("percentKind4", percentKind4 + "%");
        }

        double percentComplete = 0.0;
        if (total != 0L) {
            percentComplete = Math.round(((countProgress3 + countProgress4 + countProgress5) * 100.0) / total);
            map.put("percentComplete", percentComplete + "%");
        }

        double percentRequest = 0.0;
        if (total != 0L) {
            percentRequest = Math.round(((countKind0 + countKind1 + countKind2) * 100.0) / total);
            map.put("percentRequest", percentRequest + "%");
        }

        return map;
    }

}
