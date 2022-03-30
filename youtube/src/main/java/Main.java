import java.io.*;
import java.util.List;
import java.util.Random;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        AdbUtil adbc = new AdbUtil();
        int j = 0;
        String s = "come";
        int x = 310;
        int y = 860;

        do {
            adbc.click(1858, 302);
            j++;
            s = "COme" + j;
            adbc.power();
            adbc.keyevent(Even.KEYCODE_MENU);
            for (int i = 0; i < 10; i++) {
                adbc.keyevent(Even.KEYCODE_DEL);
            }
            adbc.input(s);
            adbc.click(x, y);
            adbc.keyevent(Even.KEYCODE_ENTER);
        } while (true);
    }

}
