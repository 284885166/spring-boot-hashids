package org.yanshen.demo.vo;

import org.yanshen.springframework.formatter.HashidsFormat;

import java.io.Serializable;

/**
 * @author yanshen
 * Created by yanshen on 2019/1/3.
 */
public class MemberVo implements Serializable {

    /**
     * 使用@HashidsFormat注解进行hashids转换
     */
    @HashidsFormat
    private int memberId;
    private String nickName;
    private String loginName;
    private String password;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("memberId:").append(memberId).append("\n");
        sb.append("nickName:").append(nickName).append("\n");
        sb.append("loginName:").append(loginName).append("\n");
        sb.append("password:").append(password).append("\n");
        return sb.toString();
    }
}
