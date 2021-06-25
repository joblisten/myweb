package com.example.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * 加密,解密
 * AEC,ECB,PKCS5Padding,128,abcdefghigklmnop,base64,UTF-8
 */
@Component
@Slf4j
public class DecryptUtil {
    /**
     * 算法/模式/自动填充
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 密钥
     */
    private static final String KEY = "abcdefghigklmnop";

    public static final String LIST="list";





    /**
     * 解密
     * @param details
     * @return
     */
    public static JSONObject decry(String details) {

        Cipher cipher = null;
        JSONObject jsonObject = null;
        if(StringUtils.isEmpty(details)){
            throw new NullPointerException("传入detail参数为空");
        }

        try {
            //得到对象
            cipher = Cipher.getInstance(ALGORITHMSTR);
            //初始化,解码--new SecretKeySpec：密钥,算法
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
            //把字符串转为base64位byte数组
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(details));
            jsonObject = JSONObject.parseObject(new String(bytes, "UTF-8"));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * 字符串加密
     * @param obj
     * @return
     * @throws Exception
     */
    public static String encrypt(Object obj) throws Exception {
        String source=JSONArray.toJSON(obj==null?new JSONObject():obj).toString();
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
        byte[] bytes = cipher.doFinal(source.getBytes("UTF-8"));
        return Base64.encodeBase64String(bytes);
    }


    /**
     * 空数组
     * @return
     * @throws Exception
     */
    public static String encryptToArray(Object obj) throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put(LIST, obj==null?new Object[0]:obj);
        String source=JSONArray.toJSON(hashMap).toString();
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
        byte[] bytes = cipher.doFinal(source.getBytes("UTF-8"));
        return Base64.encodeBase64String(bytes);
    }


}
