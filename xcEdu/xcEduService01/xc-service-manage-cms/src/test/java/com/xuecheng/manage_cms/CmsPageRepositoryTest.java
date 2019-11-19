package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //自定义查询条件测试
    @Test
    public void testFindAllByExample(){
        //参数
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //查询站点
        //cmsPage.setSiteId("s01dfewrqerqerqe");
        cmsPage.setPageAliase("轮播");
        //条件匹配器,默认是精确匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains()包含
        //ExampleMatcher.GenericPropertyMatchers.exact()精确匹配，默认的
//        ExampleMatcher.GenericPropertyMatchers.startsWith()前缀匹配
        //定义example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }
    //测试分页
    @Test
    public void testFindPage(){
        //参数
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //添加
    @Test
    public void testInsert(){
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s02");
        cmsPage.setTemplateId("t02");
        cmsPage.setPageName("测试页面2");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param2");
        cmsPageParam.setPageParamValue("value2");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);

        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("5da026489ecec142a401cf25");
    }

    //修改
    @Test
    public void testUpdate(){
    Optional<CmsPage> optional = cmsPageRepository.findById("5da026059ecec137aca72604");
    if(optional.isPresent()){
        CmsPage cmsPage = optional.get();
        //设置修改值
        cmsPage.setPageName("测试页面010101");
        cmsPageRepository.save(cmsPage);
        }
    }

    //根据页面名称查询
    @Test
    public void testfindByPageName(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面010101");
        System.out.println(cmsPage.toString());
    }

    @Test
    public void findByPageNameAndSiteIdAndPageWebPathTest(){
        String pageName = "答复全文";
        String siteId = "";
        String pageWebPath = "";
        CmsPage cmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(pageName, siteId, pageWebPath);
        System.out.println(cmsPage);
    }
}
