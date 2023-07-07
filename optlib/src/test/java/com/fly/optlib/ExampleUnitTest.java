package com.fly.optlib;



import com.fly.optlib.parse.OtpAuthUriException;
import com.fly.optlib.parse.UrlParser;
import com.fly.optlib.tokens.HotpToken;
import com.fly.optlib.tokens.ITokenMeta;
import com.fly.optlib.utils.SeedConvertor;

import org.junit.Test;

import java.io.IOException;



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
//    otpauth://hotp/dindin:mark?secret=63AAAA2A&counter=1
    @Test
    public void seed() throws IOException {
        String seed="63000018";

        //可能是是使用的 hex 模式 ConvertFromEncodingToBA 多此一举
        byte[] b=  SeedConvertor.ConvertFromEncodingToBA(seed, SeedConvertor.BASE32_FORMAT);
        System.out.println("hex "+HotpToken.byteArrayToHexString(b));

        seed = SeedConvertor.ConvertFromBA(SeedConvertor.ConvertFromEncodingToBA(seed, 1), 0);
        System.out.println("seed:"+seed);

        HotpToken token = new HotpToken("mark", "123456789", seed, 1, 6,"dindin");
        String otp = token.generateOtp();
        System.out.println("code:"+otp);
        String url=token.getUrl();
        System.out.println("url "+url);


// 加密传入的数据是byte类型的，并非使用decode方法将原始数据转二进制，String类型的数据 使用 str.getBytes()即可
        String str = url;
// 这里 encodeToString 则直接将返回String类型的加密数据
        String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        System.out.println("encodeToString >>> " + enToStr);

// 对base64加密后的数据进行解密
        System.out.println("decode >>>" + new String(Base64.decode(enToStr.getBytes(), Base64.DEFAULT)));


    }

    /**
     * 解析方法
     *
     *
     *  String scanResult="b3RwYXV0aDovL2hvdHAvZGluZGluOiVFNSU4RiVBRSVFNSU4RiVBRSVFNiU5OSVCQSVFOSU4MCU4OT9zZWNyZXQ9UlpOQjJITlpSRlEyUFhNVzZNQjdaSVNKSzI3WkQ0VUwmY291bnRlcj0x";
     *  应为如下
     *  url:otpauth://hotp/dindin:%E5%8F%AE%E5%8F%AE%E6%99%BA%E9%80%89?secret=RZNB2HNZRFQ2PXMW6MB7ZISJK27ZD4UL&counter=1
     *  code:565730
     *  @param data
     * @return
     */
    private String getPswd(String data){
        String url = new String(Base64.decode(data.getBytes(), Base64.DEFAULT));
        try {
            ITokenMeta token = UrlParser.parseOtpAuthUrl(url);
            byte[] d = SeedConvertor.ConvertFromEncodingToBA(token.getSecretBase32(),
                    SeedConvertor.BASE32_FORMAT);

            System.out.println("hex "+HotpToken.byteArrayToHexString(d));
            String hexSeed = SeedConvertor.ConvertFromBA(
             d ,
                    SeedConvertor.HEX_FORMAT);
            HotpToken hotpToken=new HotpToken(token.getName(),"",hexSeed,token.getCounter(),6,token.getOrganisation());
            return hotpToken.generateOtp();
        } catch (OtpAuthUriException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Test
    public void decode() throws Exception {

        String scanResult="b3RwYXV0aDovL2hvdHAvZGluZGluOiVFNSU4RiVBRSVFNSU4RiVBRSVFNiU5OSVCQSVFOSU4MCU4OT9zZWNyZXQ9UlpOQjJITlpSRlEyUFhNVzZNQjdaSVNKSzI3WkQ0VUwmY291bnRlcj0x";
        String code= getPswd(scanResult);
        System.out.println("code:"+code);


    }


}