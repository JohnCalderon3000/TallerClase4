package extensions;

import base.BaseTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class ScreenshotOnFailureExtension implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Optional<Object> instance = context.getTestInstance();
        if (instance.isPresent() && instance.get() instanceof BaseTest) {
            BaseTest test = (BaseTest) instance.get();
            String name = context.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_") + "_ERROR";
            test.takeScreenshot(name);
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Optional<Object> instance = context.getTestInstance();
        if (instance.isPresent() && instance.get() instanceof BaseTest) {
            BaseTest test = (BaseTest) instance.get();
            String name = context.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_") + "_OK";
            test.takeScreenshot(name);
        }
    }
}
