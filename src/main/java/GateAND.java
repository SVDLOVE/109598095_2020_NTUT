public class GateAND extends Device{
    @Override
    public boolean getOutput()
    {
        boolean BooleanValue=iPins.get(0).getOutput();
        for(int i=1;i<iPins.size();i++){
            BooleanValue&=iPins.get(i).getOutput();
        }
        return BooleanValue;
    }
}
