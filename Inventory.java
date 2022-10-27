import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Author:ZengHao
 * CreateTime:2022-10-11-20:13
 * Description:Simple introduction of the code
 */
public class Inventory {
    public static void main(String[] args) throws FileNotFoundException {
        //在该类中实现程序的业务逻辑

        //创建文件操作流
        File inventory = new File("Inventory.txt");
        File transactions = new File("Transactions.txt");
        File shipping = new File("shipping.txt");
        File errors = new File("Errors.txt");
        File newInventory = new File("NewInventory.txt");
        BufferedReader inventoryFile = new BufferedReader(new FileReader(inventory));
        BufferedReader transactionFile = new BufferedReader(new FileReader(transactions));
        try {
            BufferedWriter shippingFile = new BufferedWriter(new FileWriter(shipping));//以覆盖的形式写入文件
            BufferedWriter errorsFile = new BufferedWriter(new FileWriter(errors));
            BufferedWriter newInventoryFile = new BufferedWriter(new FileWriter(newInventory));
            //创建集合保存数据
            ArrayList<String[]> inventoryList = new ArrayList<>();
            ArrayList<String[]> transactionList = new ArrayList<>();
            ArrayList<String[]> shippingList = new ArrayList<>();
            ArrayList<String[]> errorsList = new ArrayList<>();
            //将文件的数据读取到内存中
            String line = null;
            while ((line = inventoryFile.readLine()) != null) {
                String[] arr = line.split("\t");
                inventoryList.add(arr);
//                for (int j=0;j<arr.length;j++){
//                    System.out.println(arr[j]);
//                }
            }
            while ((line = transactionFile.readLine()) != null) {
                //注意这里split没有把第一个tab分割出来，所以先要消除第一个tab不然length就是5,一个tab就是一个字符
                line = line.substring(1);
                String[] arr = line.split("\t");
                transactionList.add(arr);
//                System.out.println(arr.length);//字符数组打印的是地址
//                for(int j=0;j<arr.length;j++){
//                    System.out.println(arr[j]);
//                }
            }

            //按照事务处理的先后顺序处理事务
            //处理A事务
            for (int i = 0; i < transactionList.size(); i++) {
                if (transactionList.get(i)[0].equals("A")) {
                    //TODO 调用AEvent的方法
                    AEvent aEvent = new AEvent();
                    aEvent.addNewKind(inventoryList, transactionList.get(i));
                    //删除处理的A事务
                    transactionList.remove(i);
                    i--;
                }
            }
            //处理R事务
            for (int i = 0; i < transactionList.size(); i++) {
                if (transactionList.get(i)[0].equals("R")) {
                    //调用REvent的方法
                    REvent rEvent = new REvent();
                    rEvent.addNewNumber(inventoryList, transactionList.get(i));
                    transactionList.remove(i);
                    i--;
                }
            }
            //首先要对O事务的需求量进行排序。
            transactionList.sort(new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    //只能对O事务进行排序
                    if (o1[0].equals("O") && o2[0].equals("O")) {
                        return Integer.parseInt(o1[2]) - Integer.parseInt(o2[2]);
                    } else {
                        return 0;
                    }
                }
            });

            //处理O事务
            for (int i = 0; i < transactionList.size(); i++) {
                if (transactionList.get(i)[0].equals("O")) {
                    //调用OEvent的方法，首先需要对需求按照从小到大的顺序排序
                    OEvent oEvent = new OEvent();
                    oEvent.outGoodsNumber(inventoryList, transactionList.get(i), shippingList, errorsList);
                    transactionList.remove(i);
                    i--;
                }
            }
            //处理D事务
            for (int i = 0; i < transactionList.size(); i++) {
                if (transactionList.get(i)[0].equals("D")) {
                    //调用DEvent的方法
                    DEvent dEvent = new DEvent();
                    dEvent.deleteKind(inventoryList, transactionList.get(i), errorsList);
                    transactionList.remove(i);
                    i--;
                }
            }
            //记录发货信息到新的文件
            for (String[] s : shippingList) {
                shippingFile.write(s[0] + "\t" + s[1] + "\t" + s[2] + "\n");
            }
            //记录错误信息到新的文件
            for (String[] s : errorsList) {
                errorsFile.write(s[0] + "\t" + s[1] + "\t" + s[2] + "\n");
            }
            //更新库存，查看剩余的数据
            for (String[] s : inventoryList) {
                newInventoryFile.write(s[0] + "\t" + s[1] + "\t" + s[2] + "\t" + s[3] + "\n");
            }
            //关闭文件操作流
            inventoryFile.close();
            transactionFile.close();
            shippingFile.close();
            errorsFile.close();
            newInventoryFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
