package util.crm;

public class CrmLoginRes {
    private boolean success = false;
    private Object error_msg;
    private CrmUser crmUser;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getError_msg() {
        return error_msg;
    }

    public void setError_msg(Object error_msg) {
        this.error_msg = error_msg;
    }

    public CrmUser getCrmUser() {
        return crmUser;
    }

    public void setCrmUser(CrmUser crmUser) {
        this.crmUser = crmUser;
    }

    public CrmLoginRes() {
    }

    public CrmLoginRes(boolean success, Object error_msg, CrmUser crmUser) {
        this.success = success;
        this.error_msg = error_msg;
        this.crmUser = crmUser;
    }
}