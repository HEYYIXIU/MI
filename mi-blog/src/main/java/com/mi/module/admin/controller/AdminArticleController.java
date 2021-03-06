package com.mi.module.admin.controller;

import com.mi.data.vo.ArticleCustom;
import com.mi.data.vo.Pager;
import com.mi.module.blog.entity.Article;
import com.mi.module.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 后台-文章; InnoDB free: 11264 kB 控制器
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017-07-09.
 */
@Controller
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    IArticleService iArticleService;
    @Autowired
    IFriendlinkService iFriendlinkService;
    @Autowired
    ITypeService iTypeService;
    @Autowired
    ITagService iTagService;
    @Autowired
    IUserInfoService iUserInfoService;

    /**
     * 初始化文章分页信息
     * @param pager
     * @return
     */
    @RequestMapping("/initPage")
    @ResponseBody
    public Pager initPage(Pager pager) {
        iArticleService.initPage(pager);
        return pager;
    }
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/insertPage")
    public String insertPage(){
        return "admin/article/articleAdd";
    }
    /**
     * 初始化文章列表
     * @param pager 分页对象
     * @param categoryId 搜索条件 分类id
     * @param tagIds 搜索条件 tag集合
     * @param title 搜索条件 文章标题
     * @param model
     * @return
     */
    @RequestMapping("/load")
    public String loadArticle(Pager pager, Integer categoryId, String tagIds, String title, Model model) {
        /**
         * 设置start位置
         */
        pager.setStart(pager.getStart());
        //封装查询条件
        Map<String, Object> param = new HashMap<>();
        if (tagIds != null && !"".equals(tagIds)) {
            param.put("tags", tagIds.split(","));
        }else {
            param.put("tags", null);
        }
        param.put("categoryId", categoryId);
        param.put("title",title);
        param.put("pager", pager);
        //获取文章列表
        List<Article> articleList = iArticleService.loadArticle(param);
        model.addAttribute("articleList", articleList);
        return "admin/article/articleTable";
    }
}
