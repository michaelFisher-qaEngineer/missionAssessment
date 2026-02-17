package utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;

public class Verifications {

	public static void verifyElementText(WebElement element, String expectedText) {
		String actualText = element.getText().trim();
		if (!actualText.equals(expectedText.trim())) {
			throw new AssertionError("Expected text: '" + expectedText + "', but found: '" + actualText + "'");
		}
	}

	public static void verifyAllIntsEqual(List<Integer> values, int expectedValue, String contextLabel) {
		if (values == null) {
			throw new AssertionError(contextLabel + ": values list was null.");
		}

		if (values.size() == 0) {
			throw new AssertionError(contextLabel + ": no values found to verify.");
		}
		for (int i = 0; i < values.size(); i++) {
			int actual = values.get(i);

			if (actual != expectedValue) {
				throw new AssertionError(contextLabel + " mismatch at index " + i + " (expected " + expectedValue
						+ ", found " + actual + ")");
			}
		}
	}

	public static void verifyEqualsInt(int actual, int expected, String contextLabel) {
		if (actual != expected) {
			throw new AssertionError(contextLabel + " expected " + expected + " but found " + actual);
		}
	}

	public static void verifyEqualsBigDecimal(BigDecimal actual, BigDecimal expected, String contextLabel) {
		if (actual == null || expected == null) {
			if (actual != expected) {
				throw new AssertionError(contextLabel + " expected " + expected + " but found " + actual);
			}
			return;
		}

		if (actual.compareTo(expected) != 0) {
			throw new AssertionError(contextLabel + " expected " + expected + " but found " + actual);
		}
	}

	public static void verifyEqualsCurrency(BigDecimal actual, BigDecimal expected, String contextLabel) {
		if (actual == null || expected == null) {
			if (actual != expected) {
				throw new AssertionError(contextLabel + " expected " + expected + " but found " + actual);
			}
			return;
		}

		BigDecimal actualRounded = actual.setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedRounded = expected.setScale(2, RoundingMode.HALF_UP);

		if (actualRounded.compareTo(expectedRounded) != 0) {
			throw new AssertionError(contextLabel + " expected " + expectedRounded + " but found " + actualRounded
					+ " (raw expected " + expected + ", raw actual " + actual + ")");
		}

	}

	public static String verifyEqualsString(String actual, String expected, String contextLabel) {
		if (actual == null || expected == null) {
			if (actual != expected) {
				throw new AssertionError(contextLabel + " expected '" + expected + "' but found '" + actual + "'");
			}
			return actual;
		}

		if (!actual.equals(expected)) {
			throw new AssertionError(contextLabel + " expected '" + expected + "' but found '" + actual + "'");
		}
		return actual;
	}

	public static void verifyNotNull(int id, String string) {
		if (id == 0) {
			throw new AssertionError(string + " expected to be generated but was 0");
		}
	}

	public static void verifyNotNull(String value, String string) {
		if (value == null || value.trim().isEmpty()) {
			throw new AssertionError(string + " expected to be generated but was null or empty");
		}
	}

	public static void verifyNotNullOrEmpty(String value, String message) {
		if (value == null || value.trim().isEmpty()) {
			throw new AssertionError(message + " but was null or empty");
		}
	}

	public static void verifyNotNull(Object value, String message) {
		if (value == null) {
			throw new AssertionError(message + " but was null");
		}
	}

	public static void verifyAllUnique(List<Integer> values, String context) {
		if (values == null) {
			throw new AssertionError(context + " list is null");
		}

		// set will automatically remove duplicates, so if the size changes then we know
		// there were duplicates
		Set<Integer> unique = new HashSet<Integer>(values);
		if (values.size() != unique.size()) {
			throw new AssertionError(context + " contains duplicate values: " + values);
		}
	}

}
