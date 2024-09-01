#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.base;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

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
     * 注册时只有密码, 盐内部生成
     */
    private Password(String password) {
        this.password = password;
        this.salt = generateSalt();
    }

    /**
     * 已存在的用户拥有密码和盐, 盐不变. 可用于登录时的密码校验
     */
    public Password(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    /**
     * 密码加密
     * @return 加密后的密码
     */
    public String encode() {
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
    public boolean matches(String rawPassword) {
        return rawPassword.equals(encode());
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
}
