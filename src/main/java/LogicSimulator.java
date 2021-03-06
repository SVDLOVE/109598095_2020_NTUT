import java.io.*;
import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class LogicSimulator {
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;

    public LogicSimulator(){

    }

    private static class GatesAndInputPins{
        private int gates,inputPins;
        GatesAndInputPins(){
            this.gates=0;
            this.inputPins=0;
        }

        public void setInputPins(int inputPins) {
            this.inputPins = inputPins;
        }

        public void setGates(int gates) {
            this.gates = gates;
        }

        public int getGates() {
            return gates;
        }

        public int getInputPins() {
            return inputPins;
        }
    }
    public Boolean load(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()) return false;
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        GatesAndInputPins gatesAndInputPins= new GatesAndInputPins();
        initializeinputPin(br,gatesAndInputPins);
        Vector<String[]> gatestring=new Vector<>();
        makeGates(br, gatestring, gatesAndInputPins);
        fr.close();
        br.close();
        Vector<Integer> isoPin=new Vector<>();
        for(int i=0;i<gatesAndInputPins.getGates();i++){
            isoPin.add(1);
        }
        makeCircuit(gatestring, isoPin, gatesAndInputPins);
        findOutputPin(isoPin, gatesAndInputPins);
        return true;
    }

    void initializeinputPin(BufferedReader input,GatesAndInputPins gatesAndInputPins) throws IOException {
        gatesAndInputPins.setInputPins(Integer.parseInt(input.readLine()));
        gatesAndInputPins.setGates(Integer.parseInt(input.readLine()));
        iPins= new Vector<>();
        for (int i = 0; i < gatesAndInputPins.getInputPins(); i++) {
            iPins.add(new IPin());
        }
    }

    void makeGates(BufferedReader input, Vector<String[]> gatestring, GatesAndInputPins gatesAndInputPins) throws IOException {
        int gatePin;
        circuits=new Vector<>();
        for (int i = 0; i < gatesAndInputPins.getGates(); i++) {
            gatestring.add(input.readLine().split(" "));
            gatePin=Integer.parseInt(gatestring.get(i)[0]);
            if (gatePin == 1) {//AND
                circuits.add(new GateAND());
            }
            else if (gatePin == 2) {//OR
                circuits.add(new GateOR());
            }
            else if (gatePin == 3) {//NOT
                circuits.add(new GateNOT());
            }
        }
    }

    void makeCircuit(Vector<String[]> gatestring, Vector<Integer> isoPin, GatesAndInputPins gatesAndInputPins) {
        double gatePin;
        for (int i = 0; i < gatesAndInputPins.getGates(); i++) {
            int size = gatestring.get(i).length;
            for (int j = 1; j < size - 1; j++)
            {
                gatePin =Double.parseDouble(gatestring.get(i)[j]);
                if (gatePin == 0) {
                    break;
                }
                else if (gatePin < 0) {
                    int index = (int)abs(gatePin) - 1;
                    circuits.get(i).addInputPin(iPins.get(index));
                }
                else {
                    gatePin = floor(gatePin);
                    int index = (int)gatePin - 1;
                    if (isoPin.get(index) == 1) isoPin.set(index,0);
                    circuits.get(i).addInputPin(circuits.get(index));
                }
            }
        }
    }


    void findOutputPin(Vector<Integer> isoPin, GatesAndInputPins gatesAndInputPins) {
        oPins=new Vector<>();
        for (int i = 0; i < gatesAndInputPins.getGates(); i++) {
            if (isoPin.get(i) == 1) {
                oPins.add(circuits.get(i));
            }
        }
    }

    public int getiPinsSize() {
        return  iPins.size();
    }


    public int getoPinsSize() {
        return  oPins.size();
    }


    public int getCircuitSize() {
        return  circuits.size();
    }

    public String getSimulationResult(Vector<Boolean> inputValues) {
        StringBuilder truthtable = new StringBuilder("Simulation Result:");
        truthtable.append(displayTableTop());
        int size = iPins.size();
        for (int i = 0; i < size; i++) {
            String truefalse = "1";
            if(!inputValues.get(i)){
                truefalse="0";
            }
            truthtable.append(truefalse).append(" ");
            iPins.get(i).setInput(inputValues.get(i));
        }
        truthtable.append("|");
        for (Device oPin : oPins) {
            String truefalse = "1";
            if (!oPin.getOutput()) {
                truefalse = "0";
            }
            truthtable.append(" ").append(truefalse);
        }
        truthtable.append("\n");
        return truthtable.toString();
    }

    String getTruthTable() {
        StringBuilder truthtable = new StringBuilder("Truth table:");
        truthtable.append(displayTableTop());
        int isize = iPins.size();
        for (int i = 0; i < mypow(isize); i++) {
            for (int j = 0, l = isize - 1; j < isize; j++, l--) {
                int m = (i / mypow(l)) % 2;
                boolean truefalse = true;
                if(m==0){
                    truefalse=false;
                }
                iPins.get(j).setInput(truefalse);
                truthtable.append(m).append(" ");
            }
            truthtable.append("|");
            for (Device oPin : oPins) {
                String truefalse = "1";
                if (!oPin.getOutput()) {
                    truefalse = "0";
                }
                truthtable.append(" ").append(truefalse);
            }
            truthtable.append("\n");
        }
        return truthtable.toString();
    }

    String displayTableTop() {
        String truthtable = "\n";
        truthtable+=displayTableRow("i ","","|"," o","");
        truthtable+=displayTableRow(""," ","|"," "," ");
        truthtable+=displayTableRow("--","","+","--","");
        return truthtable;
    }

    String displayTableRow(String leftone,String lefttwo,String middle,String rightone,String righttwo) {
        StringBuilder truthtable = new StringBuilder();
        int isize = iPins.size();
        int osize = oPins.size();
        //System.out.println(osize+" "+ isize);
        for (int i = 1; i <= isize; i++) {
            if(lefttwo.equals(" ")) leftone=Integer.toString(i);
            truthtable.append(leftone).append(lefttwo);
        }
        truthtable.append(middle);
        for (int i = 1; i <= osize; i++) {
            if(lefttwo.equals(" ")) righttwo=Integer.toString(i);
            truthtable.append(rightone).append(righttwo);
        }
        truthtable.append("\n");
        return truthtable.toString();
    }



    int mypow(int n) {
        if (n == 0) return 1;
        return 2 * mypow(n - 1);
    }
}
