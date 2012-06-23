package com.bigvisible.kanbansimulatortester.suites;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;
import cucumber.junit.Cucumber.Options;

@RunWith(Cucumber.class)
@Options(features={"classpath:specs/gui/"}, glue={"com.bigvisible.kanbansimulatortester"})
public class AllGUISpecs {
}