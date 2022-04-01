import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdbUtil {
    public String executeCMDconsole(String cmd) {

        Process p;
        String line = null;
        if (cmd.startsWith("adb")) {

            String osName = System.getProperty("os.name");


            if (osName.toLowerCase(Locale.ROOT).indexOf("mac") != -1) {
                cmd = System.getProperty("user.dir") +
                        File.separator +
                        "src" + File.separator +
                        "main" + File.separator
                        + "resources" + File.separator + cmd;

            }else  if (osName.toLowerCase(Locale.ROOT).indexOf("window") != -1) {
                cmd = System.getProperty("user.dir") +
                        File.separator +
                        "src" + File.separator +
                        "main" + File.separator
                        + "resources" + File.separator + cmd;

            }

        }
        System.out.println(":::::::::::::::::::在cmd里面输入" + cmd + "::::::::::::::::::::::");

        try {
            p = Runtime.getRuntime().exec(cmd);
            System.out.println(":::::::::::::::::::开始在控制台打印日志::::::::::::::::::::::>>>>>>");
            //p.waitFor();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));

            StringBuffer sb = new StringBuffer();
            while ((line = bReader.readLine()) != null) {
                sb.append("\n");
                sb.append(line);
            }
            Thread.sleep(new Random().nextInt(600) + 400);
            return sb.toString();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取设备
     *
     * @return
     */
    public List<String> getDevices() {
        ArrayList<String> mList = new ArrayList<>();

        String adb_devices = executeCMDconsole("adb devices");
        String[] split = adb_devices.trim().split("\n");
        for (int i = 1; i < split.length; i++) {
            mList.add(split[i].split("\t")[0]);
        }
        return mList;
    }

    public boolean startActivty(String pkg, String mainClazz) {
        List<String> devices = getDevices();
        return startActivty(devices.get(0), pkg, mainClazz);

    }

    public boolean startActivty(String device, String pkg, String mainClazz) {
        try {
            String s = executeCMDconsole("adb  -s " + device + "  shell am start -n " + pkg + "/" + mainClazz);
            System.out.println("s = " + s);
            return true;

        } catch (Exception e) {
            return false;
        }

    }


    //    adb shell  dumpsys activity activities
    public boolean getCurrActivity() {
        return getCurrActivity(getDevices().get(0));
    }

    public boolean getCurrActivity(String device) {
        try {
            String s = executeCMDconsole("adb  -s " + device + "  shell dumpsys activity activities");
            System.out.println("s = " + s);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public boolean click(int x, int y) {
        return click(getDevices().get(0), x, y);
    }


    public boolean click(String device, int x, int y) {
        try {
            executeCMDconsole("adb -s " + device + "  shell input tap " + x + "  " + y);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean power() {

        return power(getDevices().get(0));
    }

    public boolean power(String device) {
        try {
            executeCMDconsole("adb -s " + device + "  shell  svc power stayon true ");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean keyevent(int key) {
        return keyevent(getDevices().get(0), key);
    }

    public boolean keyevent(String device, int key) {
        try {
            executeCMDconsole("adb -s " + device + "  shell  input keyevent  " + key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean input(String text) {
        return input(getDevices().get(0), text);
    }

    public boolean input(String device, String text) {
        try {
            executeCMDconsole("adb -s " + device + "  shell  input text \"" + text + "\" ");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean slide(int x, int y, int x1, int y1) {
        return slide(getDevices().get(0), x, y, x1, y1);
    }


    public boolean slide(String device, int x, int y, int x1, int y1) {
        try {
            executeCMDconsole("adb -s " + device + "  shell swipe   " + x + "  " + y + " " + x1 + "  " + y1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean longClick(int x, int y) {
        return longClick(getDevices().get(0), x, y);
    }


    public boolean longClick(String device, int x, int y) {
        try {
            return slide(device, x, y, x, y);
        } catch (Exception e) {
            return false;
        }
    }
}
