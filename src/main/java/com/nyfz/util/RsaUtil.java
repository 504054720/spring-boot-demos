package com.nyfz.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

public class RsaUtil {

	public static KeyPair generateKeyPair() throws Exception{
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			final int KEYSIZE = 1024;
			keyPairGenerator.initialize(KEYSIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
	}
	public static void main(String[] args) {
		try {
			KeyPair key = generateKeyPair();
			RSAPublicKey pKey = (RSAPublicKey) key.getPublic();
			System.out.println("public:_"+key.getPublic());
			System.out.println("private:_"+key.getPrivate());
			System.out.println("expoent:_"+pKey.getPublicExponent());
			System.out.println("moudel:_"+pKey.getModulus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HashMap<String, String> map = new HashMap<String, String>();
		}
	}
}
