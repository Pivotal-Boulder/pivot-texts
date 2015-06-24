package com.pivotallabs.pivottexts.pivottextsservice;

public class TextMessage {
    private final String fromNumber;
    private final String body;

    public TextMessage(String fromNumber, String body) {
        this.fromNumber = fromNumber;
        this.body = body;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextMessage that = (TextMessage) o;

        if (fromNumber != null ? !fromNumber.equals(that.fromNumber) : that.fromNumber != null) return false;
        return !(body != null ? !body.equals(that.body) : that.body != null);

    }

    @Override
    public int hashCode() {
        int result = fromNumber != null ? fromNumber.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "fromNumber='" + fromNumber + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
