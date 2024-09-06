import com.alibaba.cola.exception.BizException;
import ${package}.ErrorCode;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * 密码的充血模型
 *
 * @author vince 2024-08-28 17:49
 */
public class Password {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private final String password;

    private final String salt;

    /**
     * 密码加密
     * @return 加密后的密码
     */
    public String encode() {
        return encode(this.password, this.salt);
    }

    /**
     * 密码加密
     * @return 加密后的密码
     */
    private String encode(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }

    /**
     * 密码校验
     * @param rawPassword 待校验的密码
     * @return 是否匹配
     */
    public boolean notMatches(String rawPassword) {
        return !this.password.equals(encode(rawPassword, this.salt));
    }


    /**
     * 注册时只有密码, 盐内部生成
     */
    public Password(String password) {
        if (password == null) {
            throw new BizException(ErrorCode.USER_PARAM_ERROR.getCode(), ErrorCode.USER_PARAM_ERROR.getMessage());
        }

        this.password = password;
        if (isWeak()) {
            throw new BizException(ErrorCode.USER_PASSWORD_STRENGTH_ERROR.getCode(), ErrorCode.USER_PASSWORD_STRENGTH_ERROR.getMessage());
        }
        this.salt = generateSalt();
    }

    /**
     * 已存在的用户拥有密码和盐, 盐不变. 可用于登录时的密码校验
     */
    public Password(String password, String salt) {
        if (password == null || password.isEmpty()) {
            throw new BizException(ErrorCode.USER_PARAM_ERROR.getCode(), ErrorCode.USER_PARAM_ERROR.getMessage());
        }

        if (salt == null || salt.isEmpty()) {
            throw new BizException(ErrorCode.USER_PARAM_ERROR.getCode(), ErrorCode.USER_PARAM_ERROR.getMessage());
        }

        this.password = password;
        this.salt = salt;
    }

    /**
     * 检测密码是否满足高强度要求
     * @return 是否为高强度密码
     */
    private boolean isWeak() {
        // 密码长度至少为12个字符
        if (password.length() < 12) {
            return true;
        }

        // 必须包含小写字母、大写字母、数字和特殊字符
        boolean hasLowerCase = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasUpperCase = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find();

        return !hasLowerCase || !hasUpperCase || !hasDigit || !hasSpecialChar;
    }

    /**
     * 生成盐
     */
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
