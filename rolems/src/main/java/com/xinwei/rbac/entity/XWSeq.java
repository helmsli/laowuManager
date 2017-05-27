package com.xinwei.rbac.entity;

import java.io.Serializable;

public class XWSeq implements Serializable {
    private String seqName;

    private Long initValue;

    private Long maxValue;

    private Long currentValue;

    private Integer seqStep;

    private Integer tableCircleSize;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Long getInitValue() {
        return initValue;
    }

    public void setInitValue(Long initValue) {
        this.initValue = initValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getSeqStep() {
        return seqStep;
    }

    public void setSeqStep(Integer seqStep) {
        this.seqStep = seqStep;
    }

    public Integer getTableCircleSize() {
        return tableCircleSize;
    }

    public void setTableCircleSize(Integer tableCircleSize) {
        this.tableCircleSize = tableCircleSize;
    }
}