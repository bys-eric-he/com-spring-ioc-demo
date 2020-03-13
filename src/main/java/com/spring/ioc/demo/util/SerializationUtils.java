package com.spring.ioc.demo.util;

import com.spring.ioc.demo.bean.User;

import java.io.*;

/**
 * 序列化对象
 */
public class SerializationUtils {

    private static String filePath = "C:\\code\\Person.txt";

    /**
     * Description: 序列化User对象
     *
     * @param user
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void SerializeObject(User user) throws FileNotFoundException,
            IOException {

        if (!fileIsExist(filePath)) {
            return;
        }
        // ObjectOutputStream 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(
                new File(filePath)));
        oo.writeObject(user);
        System.out.println("User->对象序列化成功！");
        oo.close();
    }

    /**
     * Description: 反序列User对象
     *
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static User DeserializeObject() throws Exception, IOException {
        if (!fileIsExist(filePath)) {
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                new File(filePath)));
        User user = (User) ois.readObject();
        System.out.println("User->对象反序列化成功！");
        return user;
    }

    /**
     * 判断指定文件是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    private static boolean fileIsExist(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            boolean result = file.createNewFile();
            if (!result) {
                System.out.println("文件->" + path + "不存在,且创建失败！");
                return false;
            } else {
                System.out.println("文件->" + path + "创建成功!");
            }
        }
        return true;
    }
}
