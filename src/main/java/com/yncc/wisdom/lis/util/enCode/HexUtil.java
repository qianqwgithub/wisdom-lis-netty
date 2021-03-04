/*
 * @Description:
 * @Author: qianqw
 * @Date: 2019-07-05 11:06:26
 * @LastEditTime: 2019-07-22 17:18:39
 */
package com.yncc.wisdom.lis.util.enCode;

import org.springframework.stereotype.Component;

@Component
public class HexUtil{
    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * @description:byte数组转换为十六进制的字符串
     * @author: qianqw
     * @param{b:bytes数组}
     * @return:16进制字符串(字母大写)
     */
    public String conver16HexStr(byte[] b){
        StringBuffer result=new StringBuffer();
        for (int i=0;i<b.length;i++){
            if((b[i] & 0xff)<0x10) {
                result.append("0");
            }
            result.append(Long.toString(b[i] & 0xff,16));
        }
        return result.toString().toUpperCase();
    }

    /**
     * @description:十六进制的字符串转换为byte数组
     * @author: qianqw
     * @param{hex16Str:16进制字符串(A)}
     * @return:byte
     */
    public byte[] conver16HexToByte(String hex16Str){
        char[] c=hex16Str.toCharArray();
        byte[] b=new byte[c.length/2];
        for (int i=0;i<b.length;i++){
            int pos=i*2;
            b[i]=(byte)("0123456789ABCDEF".indexOf(c[pos])<<4|"0123456789ABCDEF".indexOf(c[pos+1]));
        }
        return b;
    }

    /**
     * @description:位移转换
     * @author: zoe
     * @param{type}
     * @return:
     */
    protected static char[] encodeHex(byte[] data){
        int length=data.length;
        char[] out=new char[length<<1];
        for(int i=0,j=0;i<length;i++){
            int l=data[i]>>>4;
            if(l<10){
                out[j++]=(char)(l+'0');
            }else{
                out[j++]=(char)(l-10+'A');
            }
            int r=0x0F&data[i];
            if(r<10){
                out[j++]=(char)(r+'0');
            }else{
                out[j++]=(char)(r-10+'A');
            }
        }
        return out;
    }

    protected static byte[] decodeHex(char[] str){
        int length=str.length;
        byte[] out=new byte[length>>1];
        for(int i=0,j=0;i<length;i+=2){
            Byte high=(byte)str[i];
            high=(byte)(high<<4);
            Byte low=(byte)str[i+1];
            out[i/2]=(byte)(high|low);
        }
        return out;
    }
}