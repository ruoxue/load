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

            } else if (osName.toLowerCase(Locale.ROOT).indexOf("window") != -1) {
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
        for (int i = 0; i < getDevices().size(); i++) {
            getCurrActivity(getDevices().get(i));

        }
        return true;
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
        for (int i = 0; i < getDevices().size(); i++) {
            click(getDevices().get(i), x, y);
        }
        return true;
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


        for (int i = 0; i < getDevices().size(); i++) {
            power(getDevices().get(i));

        }
        return true;

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
        for (int i = 0; i < getDevices().size(); i++) {
            keyevent(getDevices().get(i), key);

        }
        return true;
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
        for (int i = 0; i < getDevices().size(); i++) {
            input(getDevices().get(i), text);
        }
        return true;
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
        for (int i = 0; i < getDevices().size(); i++) {
            slide(getDevices().get(i), x, y, x1, y1);
        }
        return true;
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

        for (int i = 0; i < getDevices().size(); i++) {
            longClick(getDevices().get(i), x, y);
        }

        return true;
    }


    public boolean longClick(String device, int x, int y) {
        try {
            return slide(device, x, y, x, y);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> size() {

        List<String> xes = new ArrayList<>();

        for (int i = 0; i < getDevices().size(); i++) {
            String size = size(getDevices().get(i));
            xes.add(size);
        }

        return xes;
    }


    public String size(String device) {
        try {
            String s = executeCMDconsole("adb -s " + device + "  shell wm size");
            s = s.trim();
            return s.replace("Physical size:", "").trim();

        } catch (Exception e) {
            return null;
        }
    }


    public boolean clickScale(int x, int y) {
        for (int i = 0; i < getDevices().size(); i++) {
            clickScale(getDevices().get(i), x, y);
        }
        return true;
    }


    public boolean clickScale(String device, int x, int y) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[0]);
            int posY = Integer.parseInt(xes[1]);

            click(device, x * posX / 100, y * posY / 100);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean slideScale(int x, int y, int x1, int y1) {
        for (int i = 0; i < getDevices().size(); i++) {
            slideScale(getDevices().get(i), x, y, x1, y1);
        }
        return true;
    }


    public boolean slideScale(String device, int x, int y, int x1, int y1) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[0]);
            int posY = Integer.parseInt(xes[1]);

            slide(device, x * posX / 100, y * posY / 100, x1 * posX / 100, y1 * posY / 100);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean longClickScale(int x, int y) {

        for (int i = 0; i < getDevices().size(); i++) {
            longClickScale(getDevices().get(i), x, y);
        }

        return true;
    }


    public boolean longClickScale(String device, int x, int y) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[0]);
            int posY = Integer.parseInt(xes[1]);

            return slide(device, x*posX/100, y*posY/100, x*posX/100, y*posY/100);
        } catch (Exception e) {
            return false;
        }
    }









    public boolean clickScaleHor(int x, int y) {
        for (int i = 0; i < getDevices().size(); i++) {
            clickScaleHor(getDevices().get(i), x, y);
        }
        return true;
    }


    public boolean clickScaleHor(String device, int x, int y) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[1]);
            int posY = Integer.parseInt(xes[0]);

            click(device, x * posX / 100, y * posY / 100);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean slideScaleHor(int x, int y, int x1, int y1) {
        for (int i = 0; i < getDevices().size(); i++) {
            slideScaleHor(getDevices().get(i), x, y, x1, y1);
        }
        return true;
    }


    public boolean slideScaleHor(String device, int x, int y, int x1, int y1) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[1]);
            int posY = Integer.parseInt(xes[0]);

            slide(device, x * posX / 100, y * posY / 100, x1 * posX / 100, y1 * posY / 100);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean longClickScaleHor(int x, int y) {

        for (int i = 0; i < getDevices().size(); i++) {
            longClickScaleHor(getDevices().get(i), x, y);
        }

        return true;
    }


    public boolean longClickScaleHor(String device, int x, int y) {
        try {
            String size = size(device);
            String[] xes = size.split("x");
            int posX = Integer.parseInt(xes[1]);
            int posY = Integer.parseInt(xes[0]);

            return slide(device, x*posX/100, y*posY/100, x*posX/100, y*posY/100);
        } catch (Exception e) {
            return false;
        }
    }





}
