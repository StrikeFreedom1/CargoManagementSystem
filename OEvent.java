import java.util.ArrayList;

/**
 * Author:ZengHao
 * CreateTime:2022-10-12-9:12
 * Description:Simple introduction of the code
 */
public class OEvent {
    public void outGoodsNumber(ArrayList<String[]> inventoryList, String[] transaction, ArrayList<String[]> shippingList, ArrayList<String[]> errorsList) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i)[0].equals(transaction[1])) {
                //数量不足
                if (Integer.parseInt(inventoryList.get(i)[1]) < Integer.parseInt(transaction[2])) {
                    errorsList.add(new String[]{transaction[3], transaction[1], inventoryList.get(i)[1]});
                } else {
                    //数量充足时才发货，并添加发货信息
                    inventoryList.get(i)[1] = Integer.toString(Integer.parseInt(inventoryList.get(i)[1]) - Integer.parseInt(transaction[2]));
                    //添加信息，同类别的信息要合并
                    for (int j=0;j<shippingList.size();j++){
                        if (shippingList.get(j)[0].equals(transaction[3]) && shippingList.get(j)[1].equals(transaction[1])){
                            //更改数据
                            shippingList.get(j)[2] = Integer.toString(Integer.parseInt(shippingList.get(j)[2])+Integer.parseInt(transaction[2]));
                            return;//退出函数
                        }
                    }
                    //添加信息对于未曾出现的
                    shippingList.add(new String[]{transaction[3],transaction[1],transaction[2]});
                }
                return;
            }
        }
    }
}
