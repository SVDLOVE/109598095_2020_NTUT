public class GateNOT extends  Device {
    @Override
    public boolean getOutput()
    {
        return !iPins.get(0).getOutput();
    }
}
