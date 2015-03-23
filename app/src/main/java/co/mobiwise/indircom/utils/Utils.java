package co.mobiwise.indircom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import co.mobiwise.indircom.model.App;


public class Utils {

    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }

    public static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64Coder.decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static Queue<App> convertToStack(ArrayList<App> app_list){
        Queue<App> app_stack = new LinkedList<>();
        for(App app : app_list)
            app_stack.add(app);
        return app_stack;
    }

    public static ArrayList<App> convertToArraylist(Queue<App> app_list){
        ArrayList<App> app_arraylist = new ArrayList<>();
        for(int i = 0 ; i < app_list.size() ; i++)
            app_arraylist.add(app_list.remove());
        return app_arraylist;
    }


}
