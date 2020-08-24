import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class LogicSimulator {
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;

    public void load(String file1Path) throws IOException {
        FileReader fr = new FileReader("FilePath/filename.txt");
        BufferedReader br = new BufferedReader(fr);
        int inputPins=0, gates=0;
        initializeinputPin(br,inputPins,gates);
        Vector<String[]> gatestring=new Vector<>();
        makeGates(br, gatestring, gates);//初始化每個Gate
        br.close();
        Vector<Integer> isoPin=new Vector(gates,1);//用來辨別哪個gate是outputPin
        makeCircuit(gatestring, isoPin, gates);//建立邏輯電路
        findOutputPin(isoPin, gates);//找到outputPin
        fr.close();
    }

    void initializeinputPin(BufferedReader input, int inputPins, int gates) throws IOException {
        inputPins=Integer.parseInt(input.readLine());
        gates=Integer.parseInt(input.readLine());
        circuits.setSize(gates);
        for (int i = 0; i < inputPins; i++) {//建立iPin物件
            iPins.add(new IPin());
        }
    }

    void makeGates(BufferedReader input, Vector<String[]> gatestring, int gates) throws IOException {
        int gatePin;
        for (int i = 0; i < gates; i++) {
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

    void makeCircuit(Vector<String[]> gatestring, Vector<Integer> isoPin, int gates) {
        double gatePin;
        for (int i = 0; i < gates; i++) {
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


    void findOutputPin(Vector<Integer> isoPin, int gates) {
        for (int i = 0; i < gates; i++) {
            if (isoPin.get(i) == 1) {
                oPins.add(circuits.get(i));
            }
        }
    }

    int getiPinsSize() {
        return  iPins.size();
    }


    int getoPinsSize() {
        return  oPins.size();
    }


    int getCircuitSize() {
        return  circuits.size();
    }

    public String getSimulationResult(Vector<Boolean> inputValues) {

        return "";
    }
}
