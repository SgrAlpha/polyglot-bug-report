package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class DemoAppTest {

    private DemoApp demoApp;

    @BeforeEach
    void setUp() {
        demoApp = new DemoApp();
    }

    @Test
    void testRunBadScript() {
        Map<Object, Object> perms = Map.of();
        assertFalse(demoApp.runScript(perms, "invalid script"));
    }

    @Test
    void testRunScript() {
        Map<Object, Object> perms = Map.of("perm1", true, "perm2", false);
        assertTrue(demoApp.runScript(perms, "(perms => perms.has('perm1') && perms.get('perm1'))"));
    }

}