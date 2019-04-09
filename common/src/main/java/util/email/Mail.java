package util.email;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author sunwell
 */
public class Mail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean isReplace = false;

    /**
     * 收件人邮箱，多个邮箱以“;”分隔
     */
    private List<String> toEmails;
    /**
     * 替换字符
     */
    private List<Map<String, String>> replaceCodes;
    /**
     * 抄送人邮箱，多个邮箱以“;”分隔
     */
    private String toEmailCcs;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 回复邮件
     */
    private String replyTo;

    public List<String> getToEmails() {
        return toEmails;
    }

    public void setToEmails(List<String> toEmails) {
        this.toEmails = toEmails;
    }

    public List<Map<String, String>> getReplaceCodes() {
        return replaceCodes;
    }

    public void setReplaceCodes(List<Map<String, String>> replaceCodes) {
        this.replaceCodes = replaceCodes;
    }

    public String getToEmailCcs() {
        return toEmailCcs;
    }

    public void setToEmailCcs(String toEmailCcs) {
        this.toEmailCcs = toEmailCcs;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public boolean isReplace() {
        return isReplace;
    }

    public void setReplace(boolean isReplace) {
        this.isReplace = isReplace;
    }
}
