package com.esteban.core.system.dao;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperDao extends IDao<Oper,OperExample>{

}