/*
 * This file was automatically generated by EvoSuite
 * Tue Dec 24 19:22:06 GMT 2019
 */

package View.ViewModel;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import Utils.Point;
import View.ViewModel.AutonomyCar;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class AutonomyCar_ESTest extends AutonomyCar_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      AutonomyCar autonomyCar0 = new AutonomyCar((Point) null, 0, "gas");
      autonomyCar0.getPoint();
      assertEquals(0, autonomyCar0.getAutonomy());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      AutonomyCar autonomyCar0 = new AutonomyCar((Point) null, 0, "gas");
      int int0 = autonomyCar0.getAutonomy();
      assertEquals(0, int0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      AutonomyCar autonomyCar0 = new AutonomyCar(point0, (-7), "gas");
      int int0 = autonomyCar0.getAutonomy();
      assertEquals((-7), int0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      AutonomyCar autonomyCar0 = null;
      try {
        autonomyCar0 = new AutonomyCar((Point) null, 0, "i*-[r");
        fail("Expecting exception: Throwable");
      
      } catch(Throwable e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("View.ViewModel.AutonomyCar", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      AutonomyCar autonomyCar0 = null;
      try {
        autonomyCar0 = new AutonomyCar((Point) null, 1, (String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("View.ViewModel.AutonomyCar", e);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      AutonomyCar autonomyCar0 = new AutonomyCar(point0, 3462, "gas");
      autonomyCar0.getType();
      assertEquals(3462, autonomyCar0.getAutonomy());
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      doReturn("@@").when(point0).toString();
      AutonomyCar autonomyCar0 = new AutonomyCar(point0, 3462, "gas");
      autonomyCar0.getPoint();
      assertEquals(3462, autonomyCar0.getAutonomy());
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      AutonomyCar autonomyCar0 = new AutonomyCar(point0, 3462, "gas");
      int int0 = autonomyCar0.getAutonomy();
      assertEquals(3462, int0);
  }
}
