package com.example.kakaopay.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

public class RequestContextUtil {

    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_DATE = "requestDate";

    /**
     * 요청 ID, 시간 저장
     *
     * @param obj
     */
    public static void setRequestId(String obj) {
        setAttr(REQUEST_ID, obj);
        setAttr(REQUEST_DATE, LocalDateTime.now());
    }

    /**
     * 요청 ID 반환
     *
     * @return
     */
    public static String getRequestId() {
        return (String) getAttr(REQUEST_ID);
    }

    /**
     * 요청 시간 반환
     *
     * @return
     */
    public static LocalDateTime getRequestDate() {
        return (LocalDateTime) getAttr(REQUEST_DATE);
    }

    /**
     * Request 영역에서 객체를 꺼내온다
     *
     * @param name
     * @return
     */
    public static Object getAttr(String name) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getAttribute(name, ServletRequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Request 영역에 객체를 저정한다
     *
     * @param key
     * @param obj
     */
    public static void setAttr(String key, Object obj) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        attr.setAttribute(key, obj, ServletRequestAttributes.SCOPE_REQUEST);
    }
}
