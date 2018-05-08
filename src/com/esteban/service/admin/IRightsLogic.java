package com.esteban.service.admin;

import java.util.List;
import java.util.Map;

import com.esteban.framework.utils.Page;
import com.esteban.model.Rights;
import com.esteban.model.RightsExample;
import com.esteban.service.base.BaseService;

public interface IRightsLogic extends BaseService<Rights, RightsExample>{

	List<Map<String, Object>> listAllInfo(Rights rights, Page webPage);

}
