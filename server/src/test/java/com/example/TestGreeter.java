package com.example;


import java.util.Objects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestGreeter {
	
	 private Greeter greeter;
	
	@BeforeClass(alwaysRun=true)
	public void setup() {
		greeter = new Greeter();
	}
	
	 @Test(groups = {"smoke", "regression"})
	  public void greetShouldIncludeTheOneBeingGreeted() {
		 System.out.println(Objects.isNull(greeter));
	    String someone = "World";
	    Assert.assertTrue(greeter.greet(someone).contains(someone + "sometext"));
	  }
	 
	 @Test(groups = {"regression"})
	  public void greetShouldIncludeGreetingPhrase() {
	    String someone = "World";
	    Assert.assertTrue(greeter.greet(someone).length()>someone.length());
	  }

	 @Test(groups = {"regression"})
	  public void greetShouldIncludeHello() {
	    String someone = "World";
	    Assert.assertTrue(greeter.greet(someone).contains("Hello"));
	  }

	 @Test(groups = {"regression", "smoke"})
	  public void greetShouldIncludeHowAreYou() {
	    String someone = "World";
	    Assert.assertTrue(greeter.greet(someone).contains("How are you"));
	  }
}
