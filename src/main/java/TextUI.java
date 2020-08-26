import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

public class TextUI {
    public static int LOAD=1;
    public static int SIMULATION =2;
    public static int DISPLAY =3;
    public static int EXIT =4;
    private final LogicSimulator LS = new LogicSimulator();

    public TextUI(){

    }

    private static class  BooleanLoad{
        private Boolean loadfile;

        BooleanLoad(){
            this.loadfile = false;
        }

        public void setLoadfile(Boolean loadfile) {
            this.loadfile = loadfile;
        }

        public Boolean getLoadfile() {
            return loadfile;
        }
    }

    private void displayMenu() {
        System.out.println("1. Load logic circuit file \n2. Simulation \n3. Display truth table \n4. Exit Command: \n");
    }
    public void processCommand() throws IOException {
        String CommandString;
        BooleanLoad loadfile= new BooleanLoad();
        while (true)
        {
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            CommandString=scanner.next();
            if (isInteger(CommandString) && Integer.parseInt(CommandString) <= 4 && Integer.parseInt(CommandString) >= 1) {
                if (!processFourCommend(Integer.parseInt(CommandString), loadfile)) break;
            }
            else {
                System.out.println("please input number in 1~4\n") ;
            }
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private Boolean processFourCommend(int CommandNumber,BooleanLoad loadfile) throws IOException {
        if (CommandNumber == LOAD) {
            loadCommand(loadfile);
        }
        else if (CommandNumber == SIMULATION) {
            if (notLoadFille(loadfile)) return true;
            simulationCommend();
        }
        else if (CommandNumber == DISPLAY) {
            if (notLoadFille(loadfile)) return true;
            System.out.println( LS.getTruthTable());
        }
        else if (CommandNumber == EXIT) {
            System.out.println("Goodbye, thanks for using LS.");
            return false;
        }
        return true;
    }
    private Boolean notLoadFille(BooleanLoad loadfile) {
        if (!loadfile.getLoadfile()) {
            System.out.println("Please load an lcf file, before using this operation.\n");
            return true;
        }
        return false;
    }
    private void loadCommand(BooleanLoad loadfile) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String FilePath;
        System.out.println("Please key in a file path: ");
        FilePath=scanner.next();
        loadfile.setLoadfile(LS.load(FilePath));
        if (!loadfile.getLoadfile()) {
            System.out.println("File not found or file format error!!\n");
        }
        else {
            System.out.println("Circuit: " + LS.getiPinsSize() + " input pins, " + LS.getoPinsSize()
                    + " output pins and " + LS.getCircuitSize() + " gates\n");
        }
    }
    private void simulationCommend() {
        Scanner scanner = new Scanner(System.in);
        int PinQuantity = LS.getiPinsSize();
        Vector<Boolean> PinValues= new Vector<>();
        for(int i=0;i<PinQuantity;i++){
            PinValues.add(true);
        }
        String value;
        for (int i = 0; i < PinQuantity; i++) {
            System.out.println("Please key in the value of input pin " + (i + 1) + ":");
            value=scanner.next();
            if (isInteger(value) && Integer.parseInt(value) <= 1 && Integer.parseInt(value) >= 0) {
                if(Integer.parseInt(value)==0){
                    PinValues.set(i,false);
                }
            }
            else {
                i--;
                System.out.println("The value of input pin must be 0/1");
            }
        }
        System.out.println(LS.getSimulationResult(PinValues));
    }
}
