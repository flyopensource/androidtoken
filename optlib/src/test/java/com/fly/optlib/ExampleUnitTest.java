package com.fly.optlib;

import android.util.Base64;

import com.fly.optlib.tokens.HotpToken;
import com.fly.optlib.utils.Base32;
import com.fly.optlib.utils.SeedConvertor;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void hotpCreate() {
        HotpToken token = new HotpToken("mark", "123456789", "6300046", 1, 6,"fly");
        String otp = token.generateOtp();
        System.out.println(otp);
    }

    @Test
    public void seed() throws IOException {
        String seed="63000018";
        seed = SeedConvertor.ConvertFromBA(SeedConvertor.ConvertFromEncodingToBA(seed, 1), 0);

        System.out.println(seed);
        HotpToken token = new HotpToken("mark", "123456789", seed, 1, 6,"dindin");
        String otp = token.generateOtp();
        System.out.println(otp);
        String url=token.getUrl();
        System.out.println("url "+url);







// 加密传入的数据是byte类型的，并非使用decode方法将原始数据转二进制，String类型的数据 使用 str.getBytes()即可
        String str = url;
// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
        String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
        System.out.println("encode >>>" + strBase64);

// 这里 encodeToString 则直接将返回String类型的加密数据
        String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        System.out.println("encodeToString >>> " + enToStr);

// 对base64加密后的数据进行解密
        System.out.println("decode >>>" + new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT)));


    }


}