package com.pivotallabs.pivottexts.textsdatastorage;

import java.time.LocalDateTime;

public class PivotText {
    private int pivotId;
    private String pivotLocation;
    private String pivotFirstName;
    private String pivotLastName;
    private String message;
    private Long receivedAt;

    public void setPivotId(int pivotId) {
        this.pivotId = pivotId;
    }

    public int getPivotId() {
        return pivotId;
    }

    public void setPivotLocation(String pivotLocation) {
        this.pivotLocation = pivotLocation;
    }

    public String getPivotLocation() {
        return pivotLocation;
    }

    public void setPivotFirstName(String pivotFirstName) {
        this.pivotFirstName = pivotFirstName;
    }

    public String getPivotFirstName() {
        return pivotFirstName;
    }

    public void setPivotLastName(String pivotLastName) {
        this.pivotLastName = pivotLastName;
    }

    public String getPivotLastName() {
        return pivotLastName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Long receivedAt) {
        this.receivedAt = receivedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PivotText pivotText = (PivotText) o;

        if (pivotId != pivotText.pivotId) return false;
        if (pivotLocation != null ? !pivotLocation.equals(pivotText.pivotLocation) : pivotText.pivotLocation != null)
            return false;
        if (pivotFirstName != null ? !pivotFirstName.equals(pivotText.pivotFirstName) : pivotText.pivotFirstName != null)
            return false;
        if (pivotLastName != null ? !pivotLastName.equals(pivotText.pivotLastName) : pivotText.pivotLastName != null)
            return false;
        if (message != null ? !message.equals(pivotText.message) : pivotText.message != null) return false;
        return !(receivedAt != null ? !receivedAt.equals(pivotText.receivedAt) : pivotText.receivedAt != null);

    }

    @Override
    public int hashCode() {
        int result = pivotId;
        result = 31 * result + (pivotLocation != null ? pivotLocation.hashCode() : 0);
        result = 31 * result + (pivotFirstName != null ? pivotFirstName.hashCode() : 0);
        result = 31 * result + (pivotLastName != null ? pivotLastName.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (receivedAt != null ? receivedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PivotText{" +
                "pivotId=" + pivotId +
                ", pivotLocation='" + pivotLocation + '\'' +
                ", pivotFirstName='" + pivotFirstName + '\'' +
                ", pivotLastName='" + pivotLastName + '\'' +
                ", message='" + message + '\'' +
                ", receivedAt=" + receivedAt +
                '}';
    }
}
