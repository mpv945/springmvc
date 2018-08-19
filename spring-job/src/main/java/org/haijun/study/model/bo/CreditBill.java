package org.haijun.study.model.bo;

/**
 * 信用卡账单
 */
public class CreditBill {

    /**
     * 银行卡账户id
     */
    private String accountId = "";

    /**
     * 持卡人姓名
     */
    private String name = "";

    /**
     * 消费金额
     */
    private double amount = 0;

    /**
     * 消费驲日期 yyyy-MM-dd HH:mm:ss
     */
    private String date;

    /**
     * 消费地点
     */
    private String address;

    @Override
    public String toString() {
        return "CreditBill{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
