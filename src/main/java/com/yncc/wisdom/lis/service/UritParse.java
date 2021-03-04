package com.yncc.wisdom.lis.service;

import com.alibaba.fastjson.JSONObject;
import com.yncc.wisdom.lis.entity.*;
import com.yncc.wisdom.lis.util.enCode.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UritParse{
    private static final Logger logger=LoggerFactory.getLogger(UritParse.class);

    public void parse(String params){
        logger.info("优利特仪器报文解析.");
        logger.info("仪器报文:"+params);
        try {
            String stringHex=params.substring(params.indexOf("0B")+2,params.indexOf("1C"));
            if(stringHex!=null){
                String stringStr=HexUtil.hexStringToString(stringHex);
                String[] resultArray=stringStr.split("\\r");

                String[] msh=!resultArray[0].equals("")?resultArray[0].split("\\|",-1):null;
                UritMsh uritMsh=new UritMsh();
                if(msh.length>0){
                    uritMsh.setCompanyName(!msh[2].equals("")?msh[2]:null);
                    uritMsh.setInstrName(!msh[3].equals("")?msh[3]:null);
                    uritMsh.setResultTime(!msh[6].equals("")?msh[6]:null);
                    uritMsh.setInstrType(!msh[9].equals("")?msh[9]:null);
                }

                UritPid uritPid=new UritPid();
                String[] pid=!resultArray[1].equals("")?resultArray[1].split("\\|",-1):null;
                if(pid.length>0){
                    uritPid.setPatType(!pid[1].equals("")?pid[1]:null);
                    uritPid.setPatID(!pid[2].equals("")?pid[2]:null);
                    uritPid.setPatBarCode(!pid[3].equals("")?pid[3]:null);
                    uritPid.setPatBedCode(!pid[4].equals("")?pid[4]:null);
                    uritPid.setPatName(!pid[5].equals("")?pid[5]:null);
                    uritPid.setPatBirth(!pid[7].equals("")?pid[7]:null);
                    uritPid.setPatSex(!pid[8].equals("")?pid[8]:null);
                }

                UritObr uritObr=new UritObr();
                String[] obr=!resultArray[2].equals("")?resultArray[2].split("\\|",-1):null;
                if(obr.length>0){
                    uritObr.setSampleType(!obr[1].equals("")?obr[1]:null);
                    uritObr.setREQID(!obr[2].equals("")?obr[2]:null);
                    uritObr.setSampleID(!obr[3].equals("")?obr[3]:null);
                    uritObr.setCompanyName(!obr[4].equals("")?obr[4]:null);
                    uritObr.setInstrName(!obr[5].equals("")?obr[5]:null);
                    uritObr.setSampleTime(!obr[7].equals("")?obr[7]:null);
                    uritObr.setStartTime(!obr[8].equals("")?obr[8]:null);
                    uritObr.setSymptom(!obr[14].equals("")?obr[14]:null);
                    uritObr.setSendDOCName(!obr[17].equals("")?obr[17]:null);
                    uritObr.setSendDP(!obr[19].equals("")?obr[19]:null);
                }

                String[] obxs= Arrays.copyOfRange(resultArray,3,resultArray.length);
                List<UritObx> uritObxes=new ArrayList<>();
                for(String obxStr:obxs){
                    String[] obx=obxStr.split("\\|",-1);
                    UritObx uritObx=new UritObx();
                    if(obx.length>0){
                        uritObx.setValueType(!obx[2].equals("")?obx[2]:null);
                        if(uritObx.getValueType().equals("ED")){
                            uritObx.setItemID(!obx[3].equals("")?obx[3]:null);
                            uritObx.setItemName(!obx[4].equals("")?obx[4]:null);
                            uritObx.setInstrID(!obx[5].equals("")?obx[5].split("\\^")[0]:null);
                            uritObx.setFormartName(!obx[5].equals("")?obx[5].split("\\^")[2]:null);
                            uritObx.setImage(!obx[5].equals("")?obx[5].split("\\^")[4]:null);
                            uritObx.setItemResultTime(!obx[14].equals("")?obx[14]:null);
                            uritObx.setDocDP(!obx[15].equals("")?obx[15]:null);
                            uritObx.setDOCName(!obx[16].equals("")?obx[16]:null);
                        }else{
                            uritObx.setIndex(!obx[1].equals("")?obx[1]:null);
                            uritObx.setItemID(!obx[3].equals("")?obx[3]:null);
                            uritObx.setItemName(!obx[4].equals("")?obx[4]:null);
                            uritObx.setTestResult(!obx[5].equals("")?obx[5]:null);
                            uritObx.setUnit(!obx[6].equals("")?obx[6]:null);
                            uritObx.setConsultValue(!obx[7].equals("")?obx[7]:null);
                            uritObx.setFlag(!obx[8].equals("")?obx[8]:null);
                            uritObx.setOD(!obx[13].equals("")?obx[13]:null);
                            uritObx.setItemResultTime(!obx[14].equals("")?obx[14]:null);
                            uritObx.setDocDP(!obx[15].equals("")?obx[15]:null);
                            uritObx.setDOCName(!obx[16].equals("")?obx[16]:null);
                            uritObx.setMethod(!obx[17].equals("")?obx[17]:null);
                        }
                    }
                    uritObxes.add(uritObx);
                }

                Urit urit=new Urit();
                urit.setUritMsh(uritMsh);
                urit.setUritPid(uritPid);
                urit.setUritObr(uritObr);
                urit.setUritObxs(uritObxes);

                UritResult uritResult=new UritResult();
                uritResult.save(urit);
                logger.info("解析好的报文:"+ JSONObject.toJSONString(uritResult));
            }else{
                logger.debug("报文不完整.");
            }
        }catch(Exception exception){
            logger.debug("报文解析错误.");
            exception.printStackTrace();
        }
    }
}
