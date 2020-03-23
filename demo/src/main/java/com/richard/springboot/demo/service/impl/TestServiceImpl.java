package com.richard.springboot.demo.service.impl;

import com.richard.springboot.demo.bean.TestBean;
import com.richard.springboot.demo.dao.TestMapper;
import com.richard.springboot.demo.service.TestService;
import com.z.suite.exception.SystemException;
import com.z.suite.util.http.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zcy
 * @create 2020-03-20 20:06:24
 */
@Service
public class TestServiceImpl implements TestService {

    Logger logger = LogManager.getLogger("TestServiceImpl");

    @Autowired
    private TestMapper testMapper;

    @Override
    public void testService() throws SystemException {
        List<TestBean> testBeanList = testMapper.testSelect();
        logger.debug(testBeanList);
        String result = HttpUtil.httpGet("http://www.baidu.com", null, null);
        logger.debug(result);
    }
}