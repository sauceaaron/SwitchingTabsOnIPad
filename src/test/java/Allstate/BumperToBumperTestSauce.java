package Allstate;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

public class BumperToBumperTestSauce extends BumperToBumperTestBase
{
	@Before
	public void setup() throws MalformedURLException
	{
		useSauceIpadSimulator();
	}

	@Test
	public void shouldOpenPrivacyStatementInNewTab()
	{
		executeSteps();
	}
}
