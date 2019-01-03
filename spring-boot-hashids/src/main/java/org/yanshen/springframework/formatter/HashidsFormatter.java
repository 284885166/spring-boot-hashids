package org.yanshen.springframework.formatter;

import org.springframework.format.Formatter;
import org.yanshen.springframework.utils.HashidsUtils;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author yanshen
 * Created by yanshen on 2019/1/3.
 */
public class HashidsFormatter implements Formatter<Integer> {

    @Override
    public Integer parse(String s, Locale locale) throws ParseException {
        return HashidsUtils.decode(s);
    }

    @Override
    public String print(Integer integer, Locale locale) {
        return HashidsUtils.encode(integer);
    }
}
