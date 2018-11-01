package com.jgzy.utils;

import com.jgzy.constant.MongoDBAccountConstant;
import com.jgzy.constant.MongoDBAccountConstant;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ahomet-app
 * @description:
 * @author: yaobing
 * @create: 2018-07-19 13:58
 */
public class Mongodb {
    private static String host = "www.ahomet.com";
    //	private static String host = "127.0.0.1";
    private static int port = 21028;
//	private static String user = "sc";
//	private static String pwd = "sc_1!$";

    public final MongoClient getDB(String dbName, int qunar1_haoqiao2) {
        String user = qunar1_haoqiao2 == 1 ? MongoDBAccountConstant.qunarUser : MongoDBAccountConstant.haoQiaoUser;
        String pwd = qunar1_haoqiao2 == 1 ? MongoDBAccountConstant.qunarPwd : MongoDBAccountConstant.haoQiaoPwd;
        // 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        // ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(host, port);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);

        // MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential(user, dbName, pwd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);

        // 通过连接认证获取MongoDB连接
//		MongoClient mongoClient = new MongoClient(addrs, credentials);
        return new MongoClient(addrs, credentials);
    }

    /***
     * 正式服 分库连接 （按照国家分库）
     * @param dbName
     * @return
     */
    public final MongoClient getDB_FenKu(String dbName) {
        // 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        // ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(host, port);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);

        // MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential(MongoDBAccountConstant.haoQiaoUser, dbName, MongoDBAccountConstant.haoQiaoPwd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);

        // 通过连接认证获取MongoDB连接
//		MongoClient mongoClient = new MongoClient(addrs, credentials);
        return new MongoClient(addrs, credentials);
    }

    public final MongoClient getDB_HaoQiao(String dbName) {
        // 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        // ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("139.196.231.53", 27017);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);
        // MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential(MongoDBAccountConstant.getHaoQiaoUser_test, dbName, MongoDBAccountConstant.haoQiaoPwd_test.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        // 通过连接认证获取MongoDB连接
//		MongoClient mongoClient = new MongoClient(addrs, credentials);
        return new MongoClient(addrs, credentials);
    }
}