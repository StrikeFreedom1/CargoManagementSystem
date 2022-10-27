import java.util.ArrayList;

/**
 * Author:ZengHao
 * CreateTime:2022-10-12-9:12
 * Description:Simple introduction of the code
 */
public class REvent {
    public void addNewNumber(ArrayList<String[]> inventoryList,String[] transaction){
        for(int i=0;i<inventoryList.size();i++){
            //transaction的Item在第二项
            if (inventoryList.get(i)[0].equals(transaction[1])){
                inventoryList.get(i)[1] = Integer.toString(Integer.parseInt(inventoryList.get(i)[1])+Integer.parseInt(transaction[2]));
            }
        }
    }
}
