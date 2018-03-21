package com.freemahn.logparser.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Pavel Gordon
 */
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime datetime;
    private String ip;
    private String request;
    private Integer code;
    private String userAgent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String requestType) {
        this.request = request;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public static Builder newBuilder() {
        return new Log().new Builder();
    }

    public class Builder {

        public Builder dateTime(LocalDateTime datetime) {
            Log.this.datetime = datetime;
            return this;
        }

        public Builder ip(String ip) {
            Log.this.ip = ip;
            return this;
        }

        public Builder request(String request) {
            Log.this.request = request;
            return this;
        }

        public Builder code(Integer code) {
            Log.this.code = code;
            return this;
        }

        public Builder userAgent(String ua) {
            Log.this.userAgent = ua;
            return this;
        }

        public Log build() {
            return Log.this;
        }
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", ip='" + ip + '\'' +
                ", request='" + request + '\'' +
                ", code=" + code +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
