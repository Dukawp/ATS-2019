/*
 * This file was automatically generated by EvoSuite
 * Tue Dec 24 19:22:09 GMT 2019
 */

package View.ViewModel;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import Utils.Point;
import View.ViewModel.SpecificCar;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class SpecificCar_ESTest extends SpecificCar_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      SpecificCar specificCar0 = new SpecificCar((Point) null, "%.2f");
      Point point0 = specificCar0.getPoint();
      assertNull(point0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      SpecificCar specificCar0 = new SpecificCar(point0, "View.ViewModel.SpecificCar");
      String string0 = specificCar0.getNumberPlate();
      assertEquals("View.ViewModel.SpecificCar", string0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      SpecificCar specificCar0 = new SpecificCar(point0, "");
      String string0 = specificCar0.getNumberPlate();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      doReturn((String) null).when(point0).toString();
      SpecificCar specificCar0 = new SpecificCar(point0, (String) null);
      Point point1 = specificCar0.getPoint();
      assertSame(point1, point0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      Point point0 = mock(Point.class, new ViolatedAssumptionAnswer());
      SpecificCar specificCar0 = new SpecificCar(point0, (String) null);
      String string0 = specificCar0.getNumberPlate();
      assertNull(string0);
  }
}
