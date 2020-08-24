public class IPin extends Device {

    private boolean BooleanValue;
    public IPin(){
            super();
    }

    @Override
    public void setInput(boolean value) {
        this.BooleanValue = value;
    }

    @Override
    public boolean getOutput() {
        return this.BooleanValue;
    }

}
