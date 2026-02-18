package framework.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class SeleniumLogger implements WebDriverListener {
    private static final Logger log = LogManager.getLogger(SeleniumLogger.class);

    // ---------- NAVIGATION ----------
    @Override
    public void beforeGet(WebDriver driver, String url) {
        log.info("NAVIGATE: {}", url);
    }

    // ---------- CLICKS ----------
    @Override
    public void beforeClick(WebElement element) {
        log.info("CLICK: {}", describe(element));
    }

    // ---------- TYPING ----------
    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        String target = describe(element);

        // Mask likely password fields
        if (looksSensitive(element)) {
            log.info("TYPE: [MASKED] into {}", target);
            return;
        }

        String typed = joinKeys(keysToSend);
        log.info("TYPE: '{}' into {}", typed, target);
    }


    // ---------- ERRORS ----------
    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        Throwable cause = (e.getCause() != null) ? e.getCause() : e;

        String argText;
        try {
            argText = (args == null) ? "" : Arrays.toString(args);
        } catch (Exception ex) {
            argText = "<unable to render args>";
        }

        log.error("SELENIUM ERROR in {}.{} args={} -> {}",
                targetName(target),
                method.getName(),
                argText,
                cause.toString());
    }

    // ---------- HELPERS ----------
    private String describe(WebElement element) {
        try {
            // Prefer stable testing hooks first
            String dataTest = safeAttr(element, "data-test");
            if (notBlank(dataTest)) {
                return "[data-test=" + dataTest + "]";
            }

            String id = safeAttr(element, "id");
            if (notBlank(id)) {
                return "#" + id;
            }

            String name = safeAttr(element, "name");
            String type = safeAttr(element, "type");
            String tag = safeTag(element);

            // Build something like: input[name=username][type=text]
            StringBuilder sb = new StringBuilder();
            if (notBlank(tag)) {
                sb.append(tag);
            } else {
                sb.append("element");
            }

            if (notBlank(name)) {
                sb.append("[name=").append(name).append("]");
            }
            if (notBlank(type)) {
                sb.append("[type=").append(type).append("]");
            }

            String aria = safeAttr(element, "aria-label");
            if (notBlank(aria)) {
                sb.append("[aria-label=").append(aria).append("]");
            }

            String text = safeText(element);
            if (notBlank(text)) {
                // Keep it short
                if (text.length() > 40) {
                    text = text.substring(0, 40) + "...";
                }
                sb.append(" text='").append(text).append("'");
            }

            return sb.toString();

        } catch (Exception ex) {
            // last resort
            try {
                return element.toString();
            } catch (Exception e) {
                return "<unknown element>";
            }
        }
    }

    private boolean looksSensitive(WebElement element) {
        try {
            String type = safeAttr(element, "type");
            if (notBlank(type) && "password".equalsIgnoreCase(type.trim())) {
                return true;
            }
            String name = safeAttr(element, "name");
            String id = safeAttr(element, "id");

            return containsIgnoreCase(name, "pass")
                    || containsIgnoreCase(id, "pass")
                    || containsIgnoreCase(name, "password")
                    || containsIgnoreCase(id, "password");
        } catch (Exception e) {
            return false;
        }
    }

    private String joinKeys(CharSequence... keys) {
        if (keys == null || keys.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                sb.append(keys[i]);
            }
        }
        return sb.toString();
    }

    private String safeAttr(WebElement e, String attr) {
        try {
            return e.getAttribute(attr);
        } catch (Exception ex) {
            return null;
        }
    }

    private String safeTag(WebElement e) {
        try {
            return e.getTagName();
        } catch (Exception ex) {
            return null;
        }
    }

    private String safeText(WebElement e) {
        try {
            return e.getText();
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean notBlank(String s) {
        return s != null && s.trim().length() > 0;
    }

    private boolean containsIgnoreCase(String s, String needle) {
        if (s == null || needle == null) return false;
        return s.toLowerCase().contains(needle.toLowerCase());
    }

    private String targetName(Object target) {
        if (target == null) return "<null>";
        try {
            return target.getClass().getSimpleName();
        } catch (Exception e) {
            return "<unknown>";
        }
    }

}
