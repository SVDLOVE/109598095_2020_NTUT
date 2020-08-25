import java.util.Scanner;

public class TextUI {
    public static int LOAD=1;
    public static int SIMULATION =2;
    public static int DISPLAY =3;
    public static int EXIT =4;
    private LogicSimulator LS = new LogicSimulator();
    Boolean loadfile = false;
    private void displayMenu() {
        System.out.println("1. Load logic circuit file \n2. Simulation \n3. Display truth table \n4. Exit Command: \n");
    }
    public void processCommand() {
        int CommandNumber;
        while (true)
        {
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            if (cin >> CommandNumber && CommandNumber <= 4 && CommandNumber >= 1) {//確保輸入的值在1到4之間
                if (processFourCommend(CommandNumber) == false) break;//輸入4就break結束迴圈
            }
            else {
                //輸入不在1~4時的狀況
                cout << "please input number in 1~4" << endl << endl;
                cin.clear();
                cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        }
    }
    private Boolean processFourCommend(int CommandNumber) {//處理四種指令
        if (CommandNumber == LOAD) {
            loadCommand();
        }
        else if (CommandNumber == SIMULATION) {
            if (notLoadFille() == true) return true;
            simulationCommend();
        }
        else if (CommandNumber == DISPLAY) {
            if (notLoadFille() == true) return true;
            cout << LS->getTruthTable() << endl;
        }
        else if (CommandNumber == EXIT) {
            cout << "Goodbye, thanks for using LS." << endl;
            return false;
        }
        return true;
    }
    private Boolean notLoadFille() {//判斷是否有成功load檔案
        if (loadfile == false) {
            cout << "Please load an lcf file, before using this operation." << endl << endl;
            return true;
        }
        return false;
    }
    private void loadCommand() {//執行load指令
        string FilePath;
        cout << "Please key in a file path: ";
        cin >> FilePath;
        loadfile = LS->load(FilePath);
        if (loadfile == false) {
            cout << "File not found or file format error!!" << endl << endl;
        }
        else {
            cout << "Circuit: " << LS->getiPinsSize() << " input pins, " << LS->getoPinsSize()
                    << " output pins and " << LS.getCircuitSize() << " gates" << endl << endl;
        }
    }
    private void simulationCommend() {//執行simulation指令
        int PinQuantity = LS.getiPinsSize();
        vector<int> PinValues(PinQuantity);
        int value = 0;
        for (int i = 0; i < PinQuantity; i++) {
            cout << "Please key in the value of input pin " << (i + 1) << ":";
            if (cin >> value && value >= 0 && value <= 1) {
                PinValues[i] = value;
            }
            else {
                i--;
                cout << "The value of input pin must be 0/1" << endl;
                cin.clear();
                cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        }
        cout << LS->getSimulationResult(PinValues) << endl;
    }
}
