package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySiteRequest {
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("站点名称")
    private String siteName;
}
