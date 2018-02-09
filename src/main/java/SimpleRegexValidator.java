import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegexValidator {

	private static SimpleRegexValidator ourInstance = new SimpleRegexValidator();

	public static SimpleRegexValidator getInstance() {
		return ourInstance;
	}

	private SimpleRegexValidator() {
	}
	
	public boolean validate(RegexType regexType, String str) {
		Pattern pattern = Pattern.compile(regexType.getRegex());
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
