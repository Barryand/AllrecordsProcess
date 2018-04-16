package com.barry;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Barry on 2018/4/8.
 */
public class Main {
    public static void main(String[] args) {
        String filename = "allRecord.txt";
        File file = new File(filename);
        String tempLine, outLine;
        String outputFilename = "Record1.txt";
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(outputFilename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int MinuteHand = 1, SecondHand = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((tempLine = bufferedReader.readLine()) != null) {
                if (SecondHand > 100) {
                    SecondHand = 1;
                    MinuteHand++;
                    outputFilename = outputFilename.replaceAll("\\d+.txt", "") + MinuteHand + ".txt";
                    outputStream = new PrintWriter(new FileOutputStream(outputFilename));
                }
                outLine = Reorganize(tempLine, MinuteHand);

                outputStream.println(outLine);
                outputStream.flush();
                SecondHand++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件读取异常");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
    }

    private static String  Reorganize(String tempLine, int MinuteHand) {
        String[] AP = {"TP-LINK_3051", "TP-LINK_35EB", "TP-LINK_3625", "TP-LINK_5958",
                "TP-LINK_E7D2", "Four-Faith-2", "Four-Faith-3"};
        StringBuffer sb = new StringBuffer(MinuteHand + "");
        Matcher matcher = Pattern.compile("\\d+").matcher(tempLine);
        for (int i = 0; i < 7; i++) {
            if (tempLine.indexOf(AP[i]) != -1) {
                matcher.find(tempLine.indexOf(AP[i]) + 14);
                sb.append(" ").append(i).append(":").append(matcher.group());
            } else {
                sb.append(" ").append(i).append(":").append(0);
            }
        }
        return sb.toString();
    }
}
