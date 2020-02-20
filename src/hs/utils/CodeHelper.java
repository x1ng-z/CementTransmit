package hs.utils;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/18 22:57
 */
public class CodeHelper {

    public static Logger logger = Logger.getLogger(CodeHelper.class);

    public static String ASCIIToConvertString(String value) {

        StringBuffer sbu = new StringBuffer();
        for (int i = 0; i < value.length() / 2; i++) {
//            System.out.println(value.substring(i*2,i*2+2));
            sbu.append((char) Integer.parseInt(value.substring(i * 2, i * 2 + 2), 16));
        }
        return sbu.toString();
    }

    public static List<Byte> StringConvertToASCII(String value, int length) {
        List<Byte> result = new ArrayList<Byte>();
        char[] ch = value.toCharArray();
        for (int i = 0; i < length; ++i) {
            if (i < ch.length) {
                result.add((byte) (Integer.valueOf(ch[i]).intValue() & 0xff));
            } else {
                result.add((byte) 0x00);
            }
        }

        return result;

    }

    public static byte[] GB2313ToByte(String value) {
        byte[] gb2312s;
        try {
            gb2312s = value.getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
        return gb2312s;

    }

    public static byte[] material_GB2313ToByte(String value) {
        byte[] gb2312s;
        List<Byte> list = new ArrayList<Byte>();
        boolean first = false;
        int locate = 1;
        try {
            for (int i = 0; i < value.length(); ++i) {
                String sub = value.substring(i, i + 1);
                byte[] subBytes = sub.getBytes("GB2312");
                if (subBytes.length == 1) {
                    first = true;
                }
                if (subBytes.length == 2 && first) {
                    if ((locate % 2) == 0) {
                        list.add(Byte.valueOf("20"));
                        locate++;
                        for (byte sub_sub : subBytes) {
                            list.add(Byte.valueOf(sub_sub));
                            locate++;
                        }

                    } else {
                        for (byte sub_sub : subBytes) {
                            list.add(Byte.valueOf(sub_sub));
                            locate++;
                        }

                    }

                } else {

                    for (byte sub_sub : subBytes) {
                        list.add(Byte.valueOf(sub_sub));
                        locate++;
                    }


                }

            }

        } catch (UnsupportedEncodingException e) {
            logger.error(e);
//            return null;
        }


        Byte[] realbyte = new Byte[list.size()];
        list.toArray(realbyte);

        byte[] wait4send = new byte[realbyte.length];
        for (int i = 0; i < realbyte.length; ++i) {
            wait4send[i] = realbyte[i].byteValue();
        }


        return wait4send;

    }

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
