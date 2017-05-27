package com.xinwei.rbac.entity;

import java.io.Serializable;
import java.util.Date;
//  分区，按照创建时间和创建人分区
//  按照create_time，privilege_person 增删修改查询
//  其中，create_time查询一段时间的；privilege_person可以查询多个(多个之间是或者关系)
public class DataPrivilege implements Serializable {
    private Date createTime;
//    数据拥有者或者能够操作的人，或者是个部门ID
    private Integer privilegePerson;
    //0--个人, 1--角色, 2--部门
    private Integer personOrRole;
    //0--数据创建者,1--数据权限
    private Integer isCreator;
    
    private Long dataId;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPrivilegePerson() {
        return privilegePerson;
    }

    public void setPrivilegePerson(Integer privilegePerson) {
        this.privilegePerson = privilegePerson;
    }

    public Integer getPersonOrRole() {
        return personOrRole;
    }

    public void setPersonOrRole(Integer personOrRole) {
        this.personOrRole = personOrRole;
    }

    public Integer getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Integer isCreator) {
        this.isCreator = isCreator;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
    @Override
    public String toString() {
        return "DataPrivilege [createTime=" + createTime + ", privilegePerson="
                + privilegePerson + ", personOrRole=" + personOrRole
                + ", isCreator=" + isCreator + ", dataId=" + dataId + "]";
    }
}