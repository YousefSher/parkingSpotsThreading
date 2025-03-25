public class Reporter {
    private Gate[] gates;
    private int[] servedByGateNum;
    private int gatesNum;
    private int s = 0;
    private int totalServed = 0;
    private int fullValue = 0;
    public Reporter(int gatesNum){
        this.gatesNum = gatesNum;
        this.gates = new Gate[gatesNum];
        this.servedByGateNum = new int[gatesNum];
    }
    public int findIndex(Car car){
        int index;
        for(int i = 0; i < s; i++){
            if(car.gate == gates[i]){
                index = i;
                return index;
            }
        }
        if(s < gatesNum){
            gates[s] = car.gate;
            index = s;
            s++;
            return index;
        }
        return -1;
    }
    public void reportCar(Car car){
        int index = findIndex(car);
        if(index != -1){
            servedByGateNum[index]++;
            totalServed ++;
        }
        return;
    }
    public void updateFullValue(int value){
        this.fullValue = value;
        return;
    }
    public void printReport(){
        System.out.println
        ("...\nTotal Cars Served: "+totalServed+"\nCurrent Cars in Parking: "+(fullValue)+"\nDetails:");
        for(int i = 0; i < gatesNum; i++){
            System.out.println("Gate "+gates[i].gateNumber+" served "+servedByGateNum[i]+" cars.");
        }
        return;
    }

}
