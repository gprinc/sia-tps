import java.util.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

class Reader {
    public static String[] readFile(String strings) throws IOException {
        BufferedReader BufferedReader = new BufferedReader(new FileReader(strings));
        String str;

        try {
            ArrayList<String> list = new ArrayList<String>();
            while((str = BufferedReader.readLine()) != null){
                list.add(str);
            }

            String[] stringArr = list.toArray(new String[0]);
            return stringArr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (BufferedReader != null) {
                    BufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}