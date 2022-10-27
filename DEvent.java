import java.util.ArrayList;

/**
 * Author:ZengHao
 * CreateTime:2022-10-12-9:12
 * Description:Simple introduction of the code
 */
public class DEvent {
    public void deleteKind(ArrayList<String[]> inventoryList,String[] transaction,ArrayList<String[]> errorsList){
        for(int i=0;i<inventoryList.size();i++){
            if (inventoryList.get(i)[0].equals(transaction[1])){
                //只有在数量为零的时候才能删除
                if(inventoryList.get(i)[1].equals("0")){
                    inventoryList.remove(i);
                }else{
                    //添加到错误文件
                    errorsList.add(new String[]{transaction[3],transaction[1],inventoryList.get(i)[1]});
                }
            }
        }

    }
}
