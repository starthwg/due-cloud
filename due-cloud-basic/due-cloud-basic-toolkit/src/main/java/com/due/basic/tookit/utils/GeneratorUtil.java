package com.due.basic.tookit.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GeneratorUtil {

    public static final String SYMBOLS_STRING = "0123456789abcdefghijklmnopqrstuvwxyz*^!@#$%?";
    public static final String SYMBOLS_CHAR = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static final String SYMBOLS_NUMBER = "0123456789";
    public static final String APPENDIX_0 = "0"; // 填充字符
    public static final String SEPARATOR_HYPHEN =  "-";

    public static long getUid() {
        return SnowflakeWorker.nextId();
    }

    public static String getUidString() {
        return String.valueOf(SnowflakeWorker.nextId());
    }

    /**
     * 获取以参数为前缀的字符串UID
     * @param prefix
     * @return
     */
    public static String getUidWithPrefix(String prefix) {
        return StringUtils.join(prefix, getUidString());
    }

    public static Long getDefaultLong() {
        return 0L;
    }

    public static Integer getDefaultInteger() {
        return 1;
    }

    /**
     * 获取以参数为前缀的字符串UID
     * @param postfix
     * @return
     */
    public static String getUidWithPostfix(String postfix) {
        return StringUtils.join(getUidString(), postfix);
    }

    public static String getRandomStringWithPrefix(String prefix, int length) {
        if (StringUtils.isBlank(prefix)) {
            return StringUtils.join(prefix, getRandomString(length));
        } else {
            return StringUtils.join(prefix, getRandomString(length - prefix.length()));
        }
    }

    public static String getRandomStringWithPostfix(String postfix, int length) {
        if (StringUtils.isBlank(postfix)) {
            return StringUtils.join(getRandomString(length), postfix);
        } else {
            return StringUtils.join(getRandomString(length - postfix.length()), postfix);
        }
    }

    public static String getRandomNumberWithPrefix(String prefix, int length) {
        if (StringUtils.isBlank(prefix)) {
            return StringUtils.join(prefix, getRandomNumber(length));
        } else {
            return StringUtils.join(prefix, getRandomNumber(length - prefix.length()));
        }
    }

    public static String getRandomNumberWithPostfix(String postfix, int length) {
        if (StringUtils.isBlank(postfix)) {
            return StringUtils.join(getRandomNumber(length), postfix);
        } else {
            return StringUtils.join(getRandomNumber(length - postfix.length()), postfix);
        }
    }

    /**
     * 根据指定的字符序列中获取指定长度的随机数
     * @param symbols
     * @param length
     * @return
     */
    public static String getRandomString(String symbols, int length) {
        if (length <= 0) return null;
        char[] chars = new char[length];
        for (int i=0; i<length; i++) {
            chars[i] = symbols.charAt(ThreadLocalRandom.current().nextInt(symbols.length()));
        }
        return String.valueOf(chars);
    }

    public static String getRandomString(int length) {
        return getRandomString(SYMBOLS_CHAR, length).toUpperCase();
    }

    public static String getRandomNumber(int length) {
        return getRandomString(SYMBOLS_NUMBER, length);
    }

    public static String getRandom(int length) { return getRandomString(SYMBOLS_STRING, length); }

    public static Long nexLong() { return RandomUtils.nextLong(); }

    public static String getSheetCode(String prefix, RedisAtomicLong redisAtomicLong) {
        redisAtomicLong.expire(1, TimeUnit.DAYS);
        return StringUtils.join(prefix, "-", DateUtil.getYMDString(DateUtil.getDate()), "-", redisAtomicLong.incrementAndGet());
    }

    public static String leftPad(String source, int size, String padding) {
        if (source == null) source = "";
        return StringUtils.leftPad(source, size, padding);
    }

    public static String leftPad(String source, int size) {
        return leftPad(source, size, APPENDIX_0);
    }

    public static String join(String... sources) {
        return StringUtils.join(sources, SEPARATOR_HYPHEN);
    }

    /**
     * 获取商品编码
     * @param gender 性别（1位：0-女，1-男，2-中性）
     * @param mainCode 商品大类（1位）
     * @param subCode 商品小类（2位）
     * @param sequenceNo 流水序号（2位）
     * @param supplierCode 面辅料供应商品牌（2位）
     * @param articleNo 面辅料序号（2位）
     * @return 商品编码
     */
    public static String getGoodsCode(Integer gender, String mainCode, String subCode, Integer sequenceNo, String supplierCode, Integer articleNo) {
        // 获取年份（2位）
        String year = DateUtil.getYearString(DateUtil.getDate());
        year = StringUtils.substring(year, 2);
        if (gender == null || gender < 0 || gender > 2) return year;
        if (StringUtils.isBlank(mainCode) || mainCode.length() > 1) return StringUtils.join(year, gender);
        if (StringUtils.isBlank(subCode) || subCode.length() > 2) return StringUtils.join(year, gender, mainCode);
        if (sequenceNo == null || sequenceNo < 0 || sequenceNo > 99) return StringUtils.join(year, gender, mainCode, GeneratorUtil.leftPad(subCode, 2));
        if (StringUtils.isBlank(supplierCode) || supplierCode.length() > 2) return StringUtils.join(year, gender, mainCode, sequenceNo < 10 ? "0"+sequenceNo : sequenceNo);
        if (articleNo == null || articleNo < 0 || articleNo > 99) return StringUtils.join(year, gender, mainCode, sequenceNo < 10 ? "0"+sequenceNo : sequenceNo, supplierCode);
        return StringUtils.join(year, gender, mainCode, subCode, sequenceNo, supplierCode, articleNo);
    }

    /**
     * 获取SPU编码
     * @param gender 性别（1位：0-女，1-男，2-中性）
     * @param mainCode 商品大类（1位）
     * @param subCode 商品小类（2位）
     * @param sequenceNo 流水序号（2位）
     * @return SPU编码
     */
    public static String getSpuCode(Integer gender, String mainCode, String subCode, Integer sequenceNo) {
        return getGoodsCode(gender, mainCode, subCode, sequenceNo, null, null);
    }

    /**
     * 获取版芯编码
     * @param gender 性别（1位：0-女，1-男，2-中性）
     * @param mainCode 商品大类（1位）
     * @param subCode 商品小类（2位）
     * @param sequenceNo 流水序号（2位）
     * @param factoryCode 工厂编码（2位）
     * @param factoryNo 工厂序号（2位）
     * @return 版芯编码
     */
    public static String getWaferCode(Integer gender, String mainCode, String subCode, Integer sequenceNo, String factoryCode, Integer factoryNo) {
        if (gender == null || gender < 0 || gender > 2) return null;
        if (StringUtils.isBlank(mainCode) || mainCode.length() > 1) return gender.toString();
        if (StringUtils.isBlank(subCode) || subCode.length() > 2) return StringUtils.join(gender, mainCode);
        if (sequenceNo == null || sequenceNo < 0 || sequenceNo > 99) return StringUtils.join(gender, mainCode, subCode);
        if (StringUtils.isBlank(factoryCode) || factoryCode.length() > 2) return StringUtils.join(gender, mainCode, subCode, sequenceNo < 10 ? "0"+sequenceNo : sequenceNo);
        if (factoryNo == null || factoryNo < 0 || factoryNo > 99) return StringUtils.join(gender, mainCode, subCode, sequenceNo < 10 ? "0"+sequenceNo : sequenceNo, factoryCode);
        return StringUtils.join(gender, mainCode, subCode, sequenceNo < 10 ? "0"+sequenceNo : sequenceNo, factoryCode, factoryNo < 10 ? "0"+factoryNo : factoryNo);
    }

    public static String getCardCode(Integer type, RedisAtomicInteger redisAtomicInteger, int size) {
        type = Optional.ofNullable(type).orElse(0);
        Date date = DateUtil.lastDayOfMonth();
        redisAtomicInteger.expireAt(date);
        return StringUtils.join(type, DateUtil.formatYMD(date), leftPad(String.valueOf(redisAtomicInteger.incrementAndGet()), size, APPENDIX_0));
    }

    public static String getCardCode(String cardCode, int size) {
        return StringUtils.join(cardCode, getRandomNumber(size));
    }

//    public static void main(String[] args) {
//        System.out.println(getRandom(24));
//        System.out.println(getUid());
//        System.out.println(nexLong());
//
//        System.out.println(getGoodsCode(1, "X", "08", 3, null, null));
//    }
}
