package com.richard.springboot.demo.dao;

import com.richard.springboot.demo.bean.TestBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zcy
 * @create 2020-03-20 20:09:40
 */
@Repository
public interface TestMapper {
    List<TestBean> testSelect();
}
