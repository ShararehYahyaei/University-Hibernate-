package org.example.service;

import org.example.entity.AdminUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    @Test
    void validateLogin() {
       AdminService adminService = new AdminService();
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername("Admin1");
        adminUser.setPassword("1111");
        assertEquals(true,adminService.validateLogin(adminUser.getUsername(), adminUser.getPassword()));
    }
}