package com.example.educore;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModulithTest {

    @Test
    void verifies_module_boundaries() {
        ApplicationModules.of(EduCoreApplication.class).verify();
    }
}
