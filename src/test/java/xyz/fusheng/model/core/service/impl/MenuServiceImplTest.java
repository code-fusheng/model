package xyz.fusheng.model.core.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.fusheng.model.core.entity.Menu;

import java.util.List;

@SpringBootTest
class MenuServiceImplTest {

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @Test
    void getFormatMenuListByRoleId() {
        Long roleId = 1L;
        List<Menu> menuList = menuServiceImpl.getFormatMenuListByRoleId(roleId);
        menuList.forEach(System.out::println);
    }
}
