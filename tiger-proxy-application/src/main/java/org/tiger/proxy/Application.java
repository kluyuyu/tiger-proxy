package org.tiger.proxy;


import org.apache.log4j.Logger;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by liufish on 17/5/14.
 */
public class Application {

    private static final Logger logger = Logger.getLogger(Application.class);




    public static void main(String [] args)throws Exception{

        Yaml yaml = new Yaml();

        String filePath = Application.class.getClassLoader().getResource("application.yml").getPath();

        File file = new File(filePath);

        Map<String,Map> ymlData = yaml.loadType(new FileInputStream(file),HashMap.class);


        Map<String,Map> proxy = ymlData.get("proxy");
//        frontendPort: 3309
//        characterEncoding: utf8
//        frontendSchema: cater
//        executor: 256
//        backendConnectionType: bio
//        backendPoolMaxActive : 64
//        backendPoolInitIdle : 16
//        isMergeResults: true

        List<Map> users = (List) ymlData.get("users");

        List<Map> tables = (List)ymlData.get("tables");

        List<Map> dataSources = (List)ymlData.get("dataSources");





        //解析log

//        logging:
//        file: /work/logs/tiger/proxy.log
//        level.*: INFO

        System.out.println();

        logger.error("fish");

    }


}
