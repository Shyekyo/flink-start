package controller;

import client.RedisClient;
import domain.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ContactService;
import service.ProductService;
import util.Result;
import util.ResultUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@Controller
public class BackstageController {
    @Resource
    private RedisClient redisClient;

    private int topSize = 10;

    @Autowired
    ProductService productService;

    @Autowired
    ContactService contactService;

    /**
     * 获取后台数据
     * @return json
     */
    @GetMapping("/index")
    public String getBackStage(Model model){
        // 获取 top 榜单数据
        List<String> topList = redisClient.getTopList(topSize);
        //System.out.println(topList);
        List<ContactEntity> topProduct = contactService.selectByIds(topList);
        model.addAttribute("topProduct", topProduct);
        return "index";
    }

    /**
     * 获取1小时内日志接入量
     * @return
     */
    @ResponseBody
    @GetMapping("/meter")
    public Result getMeter(){
        // 获取 1小时内接入量
//        String meter = redisClient.getMeter();
        String meter = "69";
        return ResultUtils.success(meter);
    }

}
