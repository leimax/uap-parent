package com.deppon.uap.framework.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/*"})
public class CacheHelperTest {

    private CacheHelper<String> cacheHelper;

    @Test
    public void test(){
        cacheHelper.get("");
    }

}