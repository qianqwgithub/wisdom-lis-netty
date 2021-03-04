package com.yncc.wisdom.lis.util.spring;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlUtil{
    static final YamlUtil instance=new YamlUtil();
    private static Map<String,Map<String, Object>> ymlMap=new HashMap<>();

    static {
        Yaml yaml=new Yaml();
        try (InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream("application.yml");) {
            ymlMap = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
        }
    }

    public String getValue(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return ymlMap.get(key).toString();
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) ymlMap.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        Map<String,Object> result=new HashMap<>(finalValue);

        return result==null?null:result.get(separatorKeys[separatorKeys.length - 1]).toString();
    }
}
