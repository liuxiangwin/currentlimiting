package com.example.resilience;

/**
 * @author : chenzhen
 * @date : 2019-07-13 19:09
 */
public class CommonException extends RuntimeException{

    public CommonException(String errorMsg){
        super(errorMsg);
    }

}
