package cn.iua.caas.robotdatahub.service.impl;

import cn.iua.caas.robotdatahub.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void addVisity() {
        redisTemplate.opsForValue().increment("visit_count");
    }

    @Override
    public Map<String, Object> getVisitCount() {
        String count = redisTemplate.opsForValue().get("visit_count");
        Map<String, Object> map = new HashMap<>();
        map.put("count", count == null ? 0 : Integer.parseInt(count));
        return map;
    }
}
