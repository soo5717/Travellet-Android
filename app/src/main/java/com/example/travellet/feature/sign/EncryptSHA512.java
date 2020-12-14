package com.example.travellet.feature.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 수연 on 2020-11-14.
 * Class: EncryptSHA512 (완료)
 * Description: 비밀번호 암호화 : SHA-512 방식 사용 -> 별도의 복호화 과정 존재 안함.
 * => 추가적으로 다른 알고리즘들 찾아서 적용해봐도 좋을 듯!
 * => 서버에서 암호화를 진행해도 괜찮을 듯? 시간 나면 구현해보자.
 */
class EncryptSHA512 { //default 같은 패키지 내에서만 사용 가능한 클래스
    static String encryptSHA512(String target){
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(target.getBytes());

            StringBuilder sb = new StringBuilder();

            for(byte b : sh.digest())
                sb.append(Integer.toHexString(0xff & b));

            //암호화 된 문자열 반환
            return sb.toString();

        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
