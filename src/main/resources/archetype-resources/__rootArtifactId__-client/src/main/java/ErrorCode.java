package ${package};

/**
 * 根据阿里巴巴的错误码规范定义的枚举类，控制整个项目的异常输出
 */
public enum ErrorCode {
    
    // 成功码
    SUCCESS("00000", "成功"),

    // 一级宏观错误码
    USER_ERROR("A0001", "用户端错误"),
    SYSTEM_ERROR("B0001", "系统执行出错"),
    THIRD_PARTY_ERROR("C0001", "第三方服务出错"),

    // 二级宏观错误码 - 用户端错误
    USER_REGISTER_ERROR("A0100", "用户注册错误"),
    USER_LOGIN_ERROR("A0200", "用户登录异常"),
    USER_AUTHORIZE_ERROR("A0300", "访问权限异常"),
    USER_PARAM_ERROR("A0400", "用户请求参数错误"),
    USER_REQUEST_ERROR("A0500", "用户请求服务异常"),
    USER_RESOURCE_ERROR("A0600", "用户资源异常"),
    USER_FILE_ERROR("A0700", "用户上传文件异常"),
    USER_PASSIVE_LOGOUT("A0800", "用户当前版本异常"),
    USER_DEVICE_ERROR("A0900", "用户设备异常"),

    // 二级宏观错误码 - 系统执行出错
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),
    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200", "系统容灾功能被触发"),
    SYSTEM_RESOURCE_ANOMALY("B0300", "系统资源异常"),

    // 二级宏观错误码 - 第三方服务出错
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),
    THIRD_PARTY_SERVICE_ERROR("C0200", "第三方系统执行超时"),
    THIRD_PARTY_SERVICE_TIMEOUT("C0300", "数据库服务出错"),
    CACHE_SERVICE_ERROR("C0400", "缓存服务出错"),

    // 三级宏观错误码 - 用户基本信息错误 (A01xx)
    USER_ALREADY_EXIST("A0101", "用户已存在"),
    USER_MOBILE_ERROR("A0102", "用户手机号异常"),
    USER_EMAIL_ERROR("A0103", "用户邮箱异常"),
    USER_PASSWORD_STRENGTH_ERROR("A0104", "密码长度为8-16个字符且必须包含小写字母、大写字母、数字和特殊字符中的3种"),


    // 三级宏观错误码 - 认证/登录错误 (A02xx)
    USER_NOT_FOUND("A0201", "用户不存在"),
    USER_PASSWORD_ERROR("A0202", "用户密码错误"),

    // 三级宏观错误码 - 会话/授权错误 (A03xx)
    SESSION_TOKEN_ERROR("A0301", "用户令牌异常"),
    SESSION_NOT_FOUND("A0302", "会话不存在"),
    SESSION_EXPIRED("A0303", "会话已过期"),
    SESSION_INVALID("A0304", "会话无效"),

    ;
    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static boolean isAuthError(String code) {
        return code.startsWith("A02") || code.startsWith("A03");
    }
}