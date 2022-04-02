package com.mi.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

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
        System.out.println(new  SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()) +Thread.currentThread().getName()+":::::::::::::::::::在cmd里面输入" + cmd + "::::::::::::::::::::::");

        try {
            p = Runtime.getRuntime().exec(cmd);
            int i = new Random().nextInt(300) + 200;

            System.out.println(new  SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date())+Thread.currentThread().getName()+":::::::::::::::::::开始在控制台打印日志,延迟"+i+"ms::::::::::::::::::::::>>>>>>");
            //p.waitFor();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));

            StringBuffer sb = new StringBuffer();
            while ((line = bReader.readLine()) != null) {
                sb.append("\n");
                sb.append(line);
            }
            Thread.sleep(i);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            getCurrActivity(devices.get(i));

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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            click(devices.get(i), x, y);
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

        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            power(devices.get(i));

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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            keyevent(devices.get(i), key);

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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            input(devices.get(i), text);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            slide(devices.get(i), x, y, x1, y1);
        }
        return true;
    }


    public boolean slide(String device, int x, int y, int x1, int y1) {
        try {

            executeCMDconsole("adb -s " + device + "  shell input swipe   " + x + "  " + y + " " + x1 + "  " + y1 +"  500");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean longClick(int x, int y) {
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            longClick(devices.get(i), x, y);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            String size = size(devices.get(i));
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            clickScale(devices.get(i), x, y);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            slideScale(devices.get(i), x, y, x1, y1);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            longClickScale(devices.get(i), x, y);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            clickScaleHor(devices.get(i), x, y);
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
        List<String> devices = getDevices();
        for (int i = 0; i < devices.size(); i++) {
            slideScaleHor(devices.get(i), x, y, x1, y1);
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
        List<String> devices =  getDevices();
        for (int i = 0; i < devices.size(); i++) {
            longClickScaleHor(devices.get(i), x, y);
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
