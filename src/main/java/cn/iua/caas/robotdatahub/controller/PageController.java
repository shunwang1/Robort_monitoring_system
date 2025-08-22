package cn.iua.caas.robotdatahub.controller;

import cn.iua.caas.robotdatahub.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageService pageService;

//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @GetMapping("/get-visit-count")
    public Map<String, Object> getVisitCount() {
        try{
            return pageService.getVisitCount();
        }
        catch (Exception e){
            log.error("获取访问量失败", e);
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("count", -1);
            return fallback;
        }
    }

    @PostMapping("/visit-count-add")
    public void addVisitCount() {
        try{
        pageService.addVisity();}
        catch (Exception e){
            log.error("增加访问量失败", e);
        }
    }
}