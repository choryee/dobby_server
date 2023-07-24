package com.emgram.kr.dobby.utils;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaldavDataConverter {
    // URL로 부터 ID 추출
    public static String splitIdfromUrl(String url){
        if(url.length() < 1)
            return url;
        String splitUrl = url.replace(".ics", "");
        if("/".equals(splitUrl.charAt(splitUrl.length() - 1)))
            splitUrl = splitUrl.replaceFirst(".$","");
        String[] splitUrlList = splitUrl.split("/");
        return splitUrlList[splitUrlList.length - 1];
    }

    // 리스트를 length 만큼 분리
    public static <T> List<List<T>> partition(List<T> source, int length){
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return new ArrayList<>();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                        n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length))
                .collect(Collectors.toList());
    }

    // 이벤트 데이터를 Map형식으로 변환
    public static Map<String, Object> eventDataToHash(String eventData){
        return convertBegin(eventData);
    }

    private static Map<String, Object> convertBegin(String originString){
        originString = originString.replaceAll("\"", "");
        Map<String, Object> result = new HashMap<>();
        String[] splitString = originString.split("\n");

        String key = null;
        String leftString = "";
        for(String target : splitString){
            if(target.contains("BEGIN:") && key == null){
                key = target.split(":")[1];
                continue;
            }
            if(key != null && notBeginString(target, key)){
                leftString += target + "\n";
            }
            if(target.equals("END:" + key)){
                Map<String, Object> child = convertBegin(leftString);
                if(child.size() == 0){
                    child.putAll(convertDetail(leftString));
                }
                result.put(toCamel(key), child);
                key = null;
                leftString = "";
                continue;
            }
            if(key == null && notBeginString(target, "")){
                int splitIndex = target.indexOf(":");
                String cKey = target.substring(0, splitIndex);
                String value = target.substring(splitIndex+1);
                if(cKey.contains(";")){
                    result.putAll(convertAttribute(cKey, value));
                }else{
                    result.put(toCamel(cKey), value);
                }
            }
        }
        return result;
    }

    private static boolean notBeginString(String target, String key){
        return !target.contains("BEGIN:"+key) && !target.contains("END:"+key);
    }

    private static Map<String, Object> convertDetail(String originString){
        Map<String, Object> result = new HashMap<>();
        String[] splitString = originString.split("\n");

        for(String target : splitString){
            int splitIndex = target.indexOf(":");
            String key = target.substring(0, splitIndex);
            String value = target.substring(splitIndex+1);
            if(key.contains(";")){
                result.putAll(convertAttribute(key, value));
            }else{
                result.put(toCamel(key), value);
            }
        }
        return result;
    }

    private static Map<String, Object> convertAttribute(String key, String value){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> attrResult = new HashMap<>();
        String[] attributes = key.split(";");
        attrResult.put("val", value);
        for(String attr : attributes){
            if(!attr.contains("="))
                continue;
            String[] attrKeyset = attr.split("=");
            attrResult.put(toCamel(attrKeyset[0]), attrKeyset[1]);
        }
        result.put(toCamel(attributes[0]), attrResult);
        return result;
    }

    private static String toCamel(String str){
        return CaseFormat.LOWER_HYPHEN
                .to(CaseFormat.LOWER_CAMEL, str.toLowerCase());
    }
}
