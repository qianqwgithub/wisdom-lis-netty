package com.yncc.wisdom.lis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yncc.wisdom.lis.entity.*;
import com.yncc.wisdom.lis.util.spring.MyRestErrorHandler;
import com.yncc.wisdom.lis.util.spring.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.util.*;

@Service
public class UritResult{
    private static final Logger logger= LoggerFactory.getLogger(UritResult.class);

    /**
     * description 结果存库
     * param [urit]
     * return void
     * author Cash
     * createTime 2021/2/25 10:48
     **/
    public void save(Urit urit){
        String filePath=new YamlUtil().getValue("yncc.netty.filePath");
        File file=  new File(filePath);
        BufferedReader reader=null;
        StringBuffer sbf=new StringBuffer();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new MyRestErrorHandler());
        try{
            reader=new BufferedReader(new FileReader(file));
            String tempStr;
            while((tempStr=reader.readLine())!=null) {
                sbf.append(tempStr);
            }
            reader.close();
            NettyConnection nettyConnection=JSONObject.parseObject(sbf.toString(),NettyConnection.class);
            //通过小组和设备获取对应项目()
            String selectProjectUrl="http://"+nettyConnection.getServer_ip()+":"+nettyConnection.getServer_port()+"/api/wisdom-lis-basic/lis_project_equip_r/projectequipr/selectProject";
//            String selectProjectUrl="http://"+nettyConnection.getServer_ip()+":"+6500+"/lis_project_equip_r/projectequipr/selectProject?groupCode="+ nettyConnection.getGroupCode()+"&equipCode="+nettyConnection.getEquipCode();
            JSONObject response=restTemplate.getForObject(selectProjectUrl,JSONObject.class);
            List<Result> results=new ArrayList<>();
            if(response.get("code").equals(200)) {
                 results=JSON.parseObject(JSON.toJSONString(response.get("data")), new TypeReference<List<Result>>(){});
            }else{
                logger.info("获取小组和设备项目失败.");
                logger.info("errorCode:"+response.get("code")+"; msg:"+response.get("msg"));
                return;
            }

            //结果对项目
            for(Result result:results){
                result.setSampleNo(urit.getUritObr().getSampleID());
                result.setSampleDispenseGroupId(result.getGroupId());
                result.setInspectTime(urit.getUritMsh().getResultTime());
                result.setProjectId(result.getProjectBaseId());
                for(UritObx uritObx:urit.getUritObxs()){
                    if(result.getChannelNum()!=null){
                        if(uritObx.getItemID().equals(result.getChannelNum())){
                            result.setResult(uritObx.getTestResult());
                        }
                    }else if(result.getSpareChannelNum()!=null){
                        if(uritObx.getItemID().equals(result.getSpareChannelNum())){
                            result.setResult(uritObx.getTestResult());
                        }
                    }
                }
            }

            //结果存库
//            String saveProjectUrl="http://"+nettyConnection.getServer_ip()+":"+nettyConnection.getServer_port()+"/api/wisdom-lis-work/lis_routin_inspection/routininspection/saveProject";
            String saveProjectUrl="http://"+nettyConnection.getServer_ip()+":"+6501+"/lis_routin_inspection/routininspection/saveProject";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> request=new HttpEntity<>(JSONArray.toJSONString(results),headers);
            ResponseEntity<String> response1=restTemplate.postForEntity(saveProjectUrl,request,String.class);
            if(!response.get("code").equals(200)){
                logger.info("结果存库失败.");
                logger.info("errorCode:" + response.get("code") + "; msg:" + response.get("msg"));
            }


            logger.info("结果回传结果:"+response1);

            //通过样本号，小组，和时间查询样本，查询对应子项
                //projectId
            // 通过项目ID将结果存到结果表中
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
