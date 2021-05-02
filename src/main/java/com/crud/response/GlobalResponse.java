package com.crud.response;

public class GlobalResponse {
    private boolean success;
    private Object messages;
    private Object result;
    private Object additionalEntity;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getAdditionalEntity() {
        return additionalEntity;
    }

    public void setAdditionalEntity(Object additionalEntity) {
        this.additionalEntity = additionalEntity;
    }
}
