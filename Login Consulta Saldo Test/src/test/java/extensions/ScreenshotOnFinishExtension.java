package extensions;

import base.BaseTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class ScreenshotOnFinishExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Optional<Object> instance = context.getTestInstance();
        if (instance.isPresent() && instance.get() instanceof BaseTest) {
            BaseTest test = (BaseTest) instance.get();
            String name = context.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_");
            String suffix = context.getExecutionException().isPresent() ? "_ERROR" : "_OK";
            test.takeScreenshot(name + suffix);
        }
    }
}
