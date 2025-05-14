package ${package}.types;

import com.alibaba.cola.exception.BizException;
import ${package}.ErrorCode;

public class Mobile {

    private final String mobile;

    public Mobile(String mobile) {
        if (mobile == null) {
            throw new BizException(ErrorCode.USER_PARAM_ERROR.getCode(), ErrorCode.USER_PARAM_ERROR.getMessage());
        }

        if (!isValid(mobile)) {
            throw new BizException(ErrorCode.USER_MOBILE_ERROR.getCode(), ErrorCode.USER_MOBILE_ERROR.getMessage());
        }
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public static boolean isValid(String mobile) {
        String pattern = "^0?[1-9]{2,3}-?\\d{8}$";
        return mobile.matches(pattern);
    }
}
