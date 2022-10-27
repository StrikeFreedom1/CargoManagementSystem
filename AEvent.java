import java.util.ArrayList;

/**
 * Author:ZengHao
 * CreateTime:2022-10-12-9:12
 * Description:Simple introduction of the code
 */
public class AEvent {
    //定义方法实现新货物的添加
    public void addNewKind(ArrayList<String[]> inventoryList,String[] transaction){
        inventoryList.add(new String[]{transaction[1],"0",transaction[2],transaction[3]});
    }
}
