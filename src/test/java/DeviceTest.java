import org.junit.*;
import static org.junit.Assert.*;

public class DeviceTest
{
    @Before
    public  void setUp(){

    }

    @After
    public void  tearDowm(){

    }

    @Test
    public  void testMethodName(){

    }

    @Test
    public void testDevicePolymorphism()
    {
        Device device = new IPin();
        assertEquals(IPin.class.getName(), device.getClass().getName());

        device = new OPin();
        assertEquals(OPin.class.getName(), device.getClass().getName());

        device = new GateNOT();
        assertEquals(GateNOT.class.getName(), device.getClass().getName());

        device = new GateOR();
        assertEquals(GateOR.class.getName(), device.getClass().getName());

        /* implement OPin, GateNOT, GateAND, GateOR test */
    }

    @Test
    public void testIPinAndOPin()
    {
        // 0 = 0
        IPin iPin = new IPin();
        iPin.setInput(false);

        OPin oPin = new OPin();
        oPin.addInputPin(iPin);

        assertEquals(false, oPin.getOutput());

        /* implement 1 = 1 test */

        iPin = new IPin();
        iPin.setInput(true);

        oPin = new OPin();
        oPin.addInputPin(iPin);

        assertEquals(true, oPin.getOutput());
    }

    @Test
    public void testGateNOT()
    {
        // NOT 0 = 1
        IPin iPin = new IPin();
        iPin.setInput(false);

        GateNOT gateNOT = new GateNOT();
        gateNOT.addInputPin(iPin);

        assertEquals(true, gateNOT.getOutput());

        iPin = new IPin();
        iPin.setInput(true);

        gateNOT = new GateNOT();
        gateNOT.addInputPin(iPin);

        assertEquals(false, gateNOT.getOutput());
        /* implement NOT 1 = 0 test */
    }

    @Test
    public void testGateAND()
    {
        // 0 AND 0 = 0
        IPin iPin1 = new IPin();
        IPin iPin2 = new IPin();
        iPin1.setInput(false);
        iPin2.setInput(false);

        GateAND gateAND = new GateAND();
        gateAND.addInputPin(iPin1);
        gateAND.addInputPin(iPin2);

        assertEquals(false, gateAND.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(true);
        iPin2.setInput(false);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin1);
        gateAND.addInputPin(iPin2);

        assertEquals(false, gateAND.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(false);
        iPin2.setInput(true);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin1);
        gateAND.addInputPin(iPin2);

        assertEquals(false, gateAND.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(true);
        iPin2.setInput(true);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin1);
        gateAND.addInputPin(iPin2);

        assertEquals(true, gateAND.getOutput());

    /* implement 0 AND 1 = 0,
                 1 AND 0 = 0,
                 1 AND 1 = 1 test */
    }

    @Test
    public void testGateOR()
    {
        // 0 OR 0 = 0
        IPin iPin1 = new IPin();
        IPin iPin2 = new IPin();
        iPin1.setInput(false);
        iPin2.setInput(false);

        GateOR gateOR = new GateOR();
        gateOR.addInputPin(iPin1);
        gateOR.addInputPin(iPin2);

        assertEquals(false, gateOR.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(false);
        iPin2.setInput(true);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin1);
        gateOR.addInputPin(iPin2);

        assertEquals(true, gateOR.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(true);
        iPin2.setInput(false);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin1);
        gateOR.addInputPin(iPin2);

        assertEquals(true, gateOR.getOutput());

        iPin1 = new IPin();
        iPin2 = new IPin();
        iPin1.setInput(true);
        iPin2.setInput(true);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin1);
        gateOR.addInputPin(iPin2);

        assertEquals(true, gateOR.getOutput());

    /* implement 0 OR 1 = 1,
                 1 OR 0 = 1,
                 1 OR 1 = 1 test */
    }

}
