package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms站点管理接口",description = "cms站点管理接口，提供页面的增、删、改、查")
public interface CmsSiteControllerApi {
    @ApiOperation("站点查询页面列表")
    public QueryResponseResult findList(QuerySiteRequest querySiteRequest);
}
