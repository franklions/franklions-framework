package com.franklions.example.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/14
 * @since Jdk 1.8
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    private Map<String, String[]> params = new HashMap<String, String[]>();

    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
        //将现有parameter传递给params
        this.params.putAll(request.getParameterMap());
    }

    /**
     * 重载构造函数
     * @param request
     * @param extraParams
     */
    public RequestParameterWrapper(HttpServletRequest request, Map<String, Object> extraParams) {
        this(request);
        addParameters(extraParams);
    }

    public void addParameters(Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }


    /**
     * 重写getParameter，代表参数从当前类中的map获取
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 同上
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * 添加参数
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    public void putHeader(String name,String value){

        String headerKey = getHeader(name);
        if(headerKey== null || headerKey.length() < 1) {
            this.customHeaders.putIfAbsent(name, value);
        }
    }

    @Override
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);

        if (headerValue != null){
            return headerValue;
        }

        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<String>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (customHeaders.containsKey(name)) {
            values.add(customHeaders.get(name));
        }
        return Collections.enumeration(values);
    }
}
