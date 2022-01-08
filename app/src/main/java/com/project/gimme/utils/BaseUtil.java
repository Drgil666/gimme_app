package com.project.gimme.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author DrGilbert
 * @date 2022/1/8 16:59
 */
public class BaseUtil {

    private static final char[] HEX = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};


    public static String getMd5(String key) {

        if (!TextUtils.isEmpty(key)) {
            try {
                final MessageDigest md = MessageDigest.getInstance(key);
                md.update(key.trim().getBytes());
                final byte[] bytes = md.digest();
                return toHexString(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return key;
    }

    private static String toHexString(final byte[] bytes) {

        final StringBuilder sb = new StringBuilder();

        if (bytes != null) {
            for (byte b : bytes) {
                sb.append(HEX[(b & 0xf0)] >>> 4);
                sb.append(HEX[b & 0x0f]);
            }
        }

        return sb.toString();
    }

    public static boolean checkSdMounted() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static int getSDSize() {
        String path = Environment.getExternalStorageDirectory().getPath();
        final StatFs s = new StatFs(path);
        int block = s.getAvailableBlocks();
        return s.getBlockSize() * block;
    }

    public static <T extends Serializable> void saveObject(T t, String path) {

        if (!TextUtils.isEmpty(path)) {
            ObjectOutputStream oos = null;
            final File file = new File(path);
            try {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(t);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static <T extends Serializable> T getObject(String path) {
        if (!TextUtils.isEmpty(path)) {
            ObjectInputStream ois = null;
            final File file = new File(path);
            if (file.exists()) {
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (ois != null) {
                        try {
                            ois.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
