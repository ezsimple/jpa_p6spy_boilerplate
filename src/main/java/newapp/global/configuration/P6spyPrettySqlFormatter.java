package newapp.global.configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * -- 작성자 : mhlee
 * -- 수정내역 --
 * -- 2021-10-05 : 최초작성
 */
@Slf4j
public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {

    private final String basePackage = "newapp";
    private final List<String> filterStrings = Arrays.asList(new String[]{"<generated>", "doFilterInternal"});

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        sql = formatSql(category, sql);
        // if(StringUtils.isEmpty(sql)) return "";

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String callTrace = getCallStackTrace();
        return sdf.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql + callTrace;
    }

    private String getCallStackTrace() {
        StringBuffer buf = new StringBuffer();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (int i = stackTrace.length - 1; i >= 0; i--) {
            String trace = stackTrace[i].toString() + '\n';
            if (trace.contains(this.getClass().getName())) continue;
            if (okSkipFilter(trace)) continue;
            if (trace.startsWith(basePackage)) {
                buf.append('\t' + trace);
                // break; // 가장 마지막에 호출한 소스만 찾기
            }
        }
        return "\n\n" + buf;
    }

    private boolean okSkipFilter(String trace) {
        AtomicBoolean skipFlag = new AtomicBoolean(false);
        filterStrings.stream().forEach(o -> {
            if (trace.contains(o)) {
                skipFlag.set(true);
                return;
            }
        });
        if (skipFlag.get()) return true;
        return false;
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) return sql;

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
            if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
                // cast 연산시 as signed 같은 내용을 구분할 수 없습니다.
                if (StringUtils.startsWith(tmpsql, "select")) {
                    sql = sql.replaceAll(" as [fc].*_[0-9a-zA-Z].*_", "");
                }
            }
            sql = "|\nHibernate:" + sql;
        }

        return sql;
    }
}
