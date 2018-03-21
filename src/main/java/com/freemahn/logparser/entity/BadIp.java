package com.freemahn.logparser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pavel Gordon
 */
@Entity
@Table(name = "bad_ips")
public class BadIp {
    @Id
    private String ip;

    private Long count;
    private String commentary;

    public BadIp(String ip, Long count) {
        this.ip = ip;
        this.count = count;
    }

    public BadIp() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public String toString() {
        return "BadIp{" +
                "ip='" + ip + '\'' +
                ", count=" + count +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
