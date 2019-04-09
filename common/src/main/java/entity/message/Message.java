package entity.message;

import java.io.Serializable;

/**
 * @author sunwell.gu
 */
public class Message implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final int SCOPE_ALL = 0;
    public static final int SCOPE_CONTACT = 1;
    public static final int SCOPE_CAMPAIGN = 2;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_DEFAULT = 1;
    public static final int TYPE_ONLINE = 2;

    public static final int STATUS_ALL = 0;
    public static final int STATUS_UNREAD = 1;
    public static final int STATUS_READ = 2;

    public static final int TOKEN_TYPE_DEFAULT = 0;
    public static final int TOKEN_TYPE_USERNAME = 1;


    private long id;
    private String appCode;
    private String content;
    private int type;
    private int scope;
    private int status;
    private long gmtCreate;
    private long gmtModified;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
