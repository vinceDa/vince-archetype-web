#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config.interceptor;

import com.google.common.base.Strings;
import org.slf4j.MDC;

import java.util.UUID;

public class SidManager {
    private static final String MDC_SID_SOA = "SID_vince";
    private static final ThreadLocal<String> SID_THREAD_LOCAL_SOA = ThreadLocal.withInitial(SidManager::generateSid);

    public static String getSid() {
        return SID_THREAD_LOCAL_SOA.get();
    }

    public static void setSid(String sid) {
        if (!Strings.isNullOrEmpty(sid)) {
            SID_THREAD_LOCAL_SOA.set(sid);
            MDC.put(MDC_SID_SOA, sid);
        }
    }

    public static void clear() {
        SID_THREAD_LOCAL_SOA.remove();
        MDC.remove(MDC_SID_SOA);
    }

    public static void resetSid() {
        String newSid = generateSid();
        setSid(newSid);
    }

    private static String generateSid() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
}