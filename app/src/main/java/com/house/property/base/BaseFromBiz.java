package com.house.property.base;


import com.house.property.http.retrofit.HttpFormRequest;

/**
 * 基础业务类
 *
 */
public class BaseFromBiz {


    protected HttpFormRequest mHttpRequest;

    public BaseFromBiz() {
        mHttpRequest = new HttpFormRequest();
    }

    protected HttpFormRequest getRequest() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpFormRequest();
        }
        return mHttpRequest;
    }


}
