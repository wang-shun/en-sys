package com.chinacreator.asp.comp.sys.core.security.shiro.service;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.Hash;

public class DefaultSysPasswordService extends DefaultPasswordService {

    private String hashAlgorithmName;

    @Override
    public String encryptPassword(Object plaintext) {
        if ("NONE".equalsIgnoreCase(getHashAlgorithmName())) {
            return String.valueOf(plaintext);
        }
        return super.encryptPassword(plaintext);
    }

    @Override
    public boolean passwordsMatch(Object plaintext, Hash saved) {
        if ("NONE".equalsIgnoreCase(getHashAlgorithmName())) {
            return plaintext.equals(saved);
        }
        return super.passwordsMatch(plaintext, saved);

    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String saved) {
        if ("NONE".equalsIgnoreCase(getHashAlgorithmName())) {
            if (submittedPlaintext instanceof char[]) {
                return String.valueOf((char[]) submittedPlaintext)
                        .equals(saved);
            }
            return String.valueOf(submittedPlaintext).equals(saved);
        }
        return super.passwordsMatch(submittedPlaintext, saved);
    }
    
    

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }
}
