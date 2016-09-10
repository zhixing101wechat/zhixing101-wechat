package com.baidu.lbsyun.req;

/**
 * Geotable
 *
 */
public class Geotable {

    /**
     * geotable的中文名称
     *
     */
    private String name;

    /**
     * geotable持有数据的类型
     *
     */
    private int geotype;

    /**
     * 是否发布到检索
     *
     */
    private int is_published;

    /**
     * 用户的访问权限key
     *
     */
    private String ak;

    /**
     * 用户的权限签名
     *
     */
    private String sn;

    /**
     * 时间戳
     *
     */
    private long timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeotype() {
        return geotype;
    }

    public void setGeotype(int geotype) {
        this.geotype = geotype;
    }

    public int getIs_published() {
        return is_published;
    }

    public void setIs_published(int is_published) {
        this.is_published = is_published;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
