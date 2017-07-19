package com.bigdullrock.fitnesse;

import org.junit.runner.RunWith;

import fitnesse.junit.FitNesseRunner;
import nl.hsac.fitnesse.junit.HsacFitNesseRunner;

/**
 * Test class to allow fixture code to be debugged, or run by build server.
 */
@RunWith(HsacFitNesseRunner.class)
@FitNesseRunner.Suite("FitnesseSpringSuite")
public class FixtureDebugTest {
}
