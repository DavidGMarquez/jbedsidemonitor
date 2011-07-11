/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import completeTestsTimeSeries.*;
import auxiliarTools.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    completeTestsEventSeries.BasicTest.class,
    completeTestsEventSeries.MediumEventTest.class,
    completeTestsEventSeries.Complete_EventSeriesTest.class
})
public class TestCompleteEventSeries {
}
