package com.midasin.sample.db.generate.model;

import java.io.Serializable;

public class Account implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.sn
     *
     * @mbg.generated
     */
    private Long sn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.sn
     *
     * @return the value of account.sn
     *
     * @mbg.generated
     */
    public Long getSn() {
        return sn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.sn
     *
     * @param sn the value for account.sn
     *
     * @mbg.generated
     */
    public void setSn(Long sn) {
        this.sn = sn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.username
     *
     * @return the value of account.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.username
     *
     * @param username the value for account.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sn=").append(sn);
        sb.append(", username=").append(username);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}