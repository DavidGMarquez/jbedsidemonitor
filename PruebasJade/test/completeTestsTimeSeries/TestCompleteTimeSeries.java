/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsTimeSeries;

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
    completeTestsTimeSeries.CreateTest.class,
    completeTestsTimeSeries.CompleteTestOrder.class,
    completeTestsTimeSeries.Basic2AlgorithmsTest.class,
    completeTestsTimeSeries.Complete_TimeSeriesTest.class
})
public class TestCompleteTimeSeries {
}
