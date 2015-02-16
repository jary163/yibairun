package com.qmoney.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Vector;

/**
 * RSA 加密工具
 * 出于安全考虑，商户私钥应安全存储在服务器端，客户端调用服务器接口完成签名.
 * @author wangjun
 * 2013-12-2
 */
public class RSATool {

    public static PrivateKey privateKey ;             //商户私钥

    public static Signature signatureChecker ;

    public static void init(InputStream in) {
        try {
            InputStreamReader reader1 = new InputStreamReader(in);
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(reader1);
            String str = null;
            while ((str = reader.readLine()) != null) {
                if (str.charAt(0) != '-') {
                    sb.append(str);
                    sb.append("\r");
                }
            }
            byte[] baseData = Base64.decode(sb.toString());
            ASN1InputStream stream = new ASN1InputStream(baseData);
            Vector<BigInteger> v = new Vector<BigInteger>();
            stream.buildEncodableVector(v);
            if (v.size() == 0)
                throw new InvalidKeyException();
            BigInteger mod = v.elementAt(1);
            BigInteger pubExp = v.elementAt(2);
            BigInteger privExp = v.elementAt(3);
            BigInteger p1 = v.elementAt(4);
            BigInteger p2 = v.elementAt(5);
            BigInteger exp1 = v.elementAt(6);
            BigInteger exp2 = v.elementAt(7);
            BigInteger crtCoef = v.elementAt(8);
            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(mod, pubExp);
            RSAPrivateCrtKeySpec privSpec = new RSAPrivateCrtKeySpec(mod,
                    pubExp, privExp, p1, p2, exp1, exp2, crtCoef);
            KeyFactory fact = KeyFactory.getInstance("RSA","BC");
            signatureChecker = Signature.getInstance("SHA1WithRSA");
            privateKey = fact.generatePrivate(privSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA签名算法.
     * 出于安全考虑，商户私钥应安全存储在服务器端，客户端调用服务器接口完成签名.
     * 2013-12-2
     * @param text
     * @return
     */
    public static String sign(String text) {
        try {
            signatureChecker.initSign(privateKey);
            signatureChecker.update(text.getBytes());
            byte[] s1 = signatureChecker.sign();
            return new String(Base64.encode(s1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
