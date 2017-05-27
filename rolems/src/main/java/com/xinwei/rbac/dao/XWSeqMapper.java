package com.xinwei.rbac.dao;

import com.xinwei.rbac.entity.XWSeq;
import java.util.List;

public interface XWSeqMapper {
    int deleteByPrimaryKey(String seqName);

    int insert(XWSeq record);

    XWSeq selectByPrimaryKey(String seqName);

    List<XWSeq> selectAll();

    int updateByPrimaryKey(XWSeq record);
    
    int updateCurrentValueBySeqName(String seqName);
    
}