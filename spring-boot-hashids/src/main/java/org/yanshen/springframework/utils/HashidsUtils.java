package org.yanshen.springframework.utils;

import org.hashids.Hashids;

/**
 * hashids编码和解码
 *
 * @author yanshen
 * Created by yanshen on 2019/1/3
 */
public class HashidsUtils {

    /**
     * 编码
     *
     * @param id
     * @return
     */
    public static String encode(int id) {
        Hashids hashids = new Hashids("springboothashids", 8);
        return hashids.encode(id);
    }

    /**
     * 解码
     *
     * @param hasdids
     * @return
     */
    public static int decode(String hasdids) {
       try {
           Hashids hashids = new Hashids("springboothashids", 8);
           return (int) hashids.decode(hasdids)[0];
       } catch (Exception ex) {
           System.out.println("invalid id is " + hasdids);
           throw new IllegalArgumentException("invalid id is " + hasdids);
       }
    }

}
