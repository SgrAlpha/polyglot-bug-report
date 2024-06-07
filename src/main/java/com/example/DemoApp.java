package com.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.proxy.ProxyHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.Map;

public class DemoApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApp.class);

    public boolean runScript(Map<Object, Object> perms, String script) {
        try (
                Context context = Context.newBuilder("js")
                        .option("js.ecmascript-version", "2023")
                        .logHandler(new SLF4JBridgeHandler())
                        .build()
        ) {
            return context.eval("js", script)
                    .execute(ProxyHashMap.from(perms))
                    .asBoolean();
        } catch (Exception e) {
            LOGGER.error("Error running script", e);
            return false;
        }
    }

    public static void main(String[] args) {
        DemoApp demoApp = new DemoApp();
        System.out.println(demoApp.runScript(Map.of(), "invalid script"));
        Map<Object, Object> perms = Map.of("perm1", true, "perm2", false);
        System.out.println(demoApp.runScript(perms, "(perms => perms.has('perm1') && perms.get('perm1'))"));
    }

}
