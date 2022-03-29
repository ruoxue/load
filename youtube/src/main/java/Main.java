import java.io.*;
import java.util.Random;

public class Main {
    public void executeCMDconsole(String cmd) {

        System.out.println("在cmd里面输入" + cmd);
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            System.out.println(":::::::::::::::::::开始在控制台打印日志::::::::::::::::::::::>>>>>>");
            //p.waitFor();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
            String line = null;
            while ((line = bReader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws  InterruptedException {

        Main adbc = new Main();

        String s="come ";
        do {
            adbc.executeCMDconsole("adb shell svc power stayon true");
            Thread.sleep(new Random().nextInt(600) + 400);


            adbc.executeCMDconsole("adb shell input keyevent 82");
            Thread.sleep(new Random().nextInt(600) + 400);

            for (int i = 0; i < 10; i++) {

                adbc.executeCMDconsole("adb shell input keyevent 67");
            }
            Thread.sleep(new Random().nextInt(600) + 400);


            adbc.executeCMDconsole("adb shell input text \""+s+"\" ");
            Thread.sleep(new Random().nextInt(600) + 400);

            adbc.executeCMDconsole("adb shell input keyevent 66");
            Thread.sleep(new Random().nextInt(600) + 400);

        } while (true);


    }

}
