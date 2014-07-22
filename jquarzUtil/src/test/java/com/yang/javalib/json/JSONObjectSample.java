package com.yang.javalib.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONObjectSample {

    // 创建JSONObject对象
    private static JSONObject createJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "huangwuyi");
        jsonObject.put("sex", "男");
        jsonObject.put("QQ", "413425430");
        jsonObject.put("Min.score", new Integer(99));
        jsonObject.put("nickname", "梦中心境");
        return jsonObject;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObjectSample.createJSONObject();//静待方法，直接通过类名+方法调用
        // 输出jsonobject对象
        System.out.println("jsonObject：" + jsonObject);

        // 判读输出对象的类型
        boolean isArray = jsonObject.isArray();
        boolean isEmpty = jsonObject.isEmpty();
        boolean isNullObject = jsonObject.isNullObject();
        System.out.println("是否为数组:" + isArray + "， 是否为空:" + isEmpty
                + "， isNullObject:" + isNullObject);

        // 添加属性，在jsonObject后面追加元素。
        jsonObject.element("address", "福建省厦门市");
        System.out.println("添加属性后的对象：" + jsonObject);

        // 返回一个JSONArray对象
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(0, "this is a jsonArray value");
        jsonArray.add(1, "another jsonArray value");
        jsonObject.element("jsonArray", jsonArray);
        //在jsonObject后面住家一个jsonArray
        JSONArray array = jsonObject.getJSONArray("jsonArray");
        System.out.println(jsonObject);
        
        
        System.out.println("返回一个JSONArray对象：" + array);
        // 添加JSONArray后的值
        // {"username":"huangwuyi","sex":"男","QQ":"413425430","Min.score":99,"nickname":"梦中心境","address":"福建省厦门市","jsonArray":["this is a jsonArray value","another jsonArray value"]}
        System.out.println("结果=" + jsonObject);

        // 根据key返回一个字符串
        String username = jsonObject.getString("username");
        System.out.println("username==>" + username);

        // 把字符转换为 JSONObject
        String temp = jsonObject.toString();
        JSONObject object = JSONObject.fromObject(temp);
        // 转换后根据Key返回值
        System.out.println("qq=" + object.get("QQ"));

    }

}