package com.yncc.wisdom.lis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yncc.wisdom.lis.entity.NettyConnection;
import com.yncc.wisdom.lis.nettyService.NettyServer;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

@Component
public class CreateService{
    private static final Logger logger=LoggerFactory.getLogger(CreateService.class);

    @Value("${yncc.netty.filePath}")
    private String filePath;

    @Autowired
    private NettyServer nettyServer;

    @PostConstruct
    public void createConnection(){
        logger.info("获取配置文件路径:"+filePath);
        File file=new File(filePath);
        BufferedReader reader=null;
        StringBuffer sbf=new StringBuffer();
        try{
            reader=new BufferedReader(new FileReader(file));
            String tempStr;
            while((tempStr=reader.readLine())!=null){
                sbf.append(tempStr);
            }
            reader.close();
            NettyConnection nettyConnection=JSONObject.parseObject(sbf.toString(),NettyConnection.class);
            logger.info("启动服务... ip:"+nettyConnection.getLocalhost_ip()+" ;port:"+nettyConnection.getLocalhost_port());
            nettyServer.start(new InetSocketAddress(nettyConnection.getLocalhost_ip(),nettyConnection.getLocalhost_port()),nettyConnection.getDelimiter(),nettyConnection.getMaxFrameLength());
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
