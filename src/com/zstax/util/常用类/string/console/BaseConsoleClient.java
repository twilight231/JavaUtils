package com.zstax.util.常用类.string.console;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * �����г���Ļ��࣬��һ��������
 * ������Apache��֯�������н������İ�
 */
public abstract class BaseConsoleClient {
    /**����ѡ�����Ϊ-h����--help����������ΪDisplays help */
    private static final Option HELP =
        OptionBuilder.withDescription("Displays help")
        .withLongOpt("help")
        .create("h");
    /** debugѡ��Ƿ��ӡ������Ϣ     */
    public static final Option DEBUG = new Option("debug", "Enables debug mode");

    // ����Ĳ���ѡ��
    private Options options = new Options();
    // ����İ�����Ϣ
    private String usageMsg;
    // �����ͷ��Ϣ
    private String header;
    //�Ƿ�debug
    private boolean debugMode; 
    /**
     * Ĭ�Ϲ��캯��
     */
    protected BaseConsoleClient(){
    	//����ӡdebug��Ϣ
        debugMode = false;
        //�������͵���ѡ����ӵ�����Ĳ���ѡ����
        options.addOption(HELP);
        options.addOption(DEBUG);
        //Ĭ�ϰ�����Ϣ������
        usageMsg = getClass().getName();
    }
    
    /**
     * ����һ������Ĳ���ѡ��
     * @param option
     */
    protected void addOption(Option option){
        this.options.addOption(option);
    }
    
    protected void setUsageMsg(String msg){
        this.usageMsg = msg;
    }
    
    protected void setHeader(String header){
        this.header = header;
    }
    
    /**
     * ��ʾ������Ϣ
     */
    public void displayUsage(){
        HelpFormatter formatter = new HelpFormatter();
        String header = (this.header == null) ?
                "Options:" : this.header;
        formatter.printHelp(usageMsg, header, options, null, false);
    }
    
    /**
     * ���������в���
     * @param args
     * @return
     * @throws Exception
     */
    public CommandLine parse(String[] args) throws Exception{
        //�½�һ�������н�����
        CommandLineParser parser = new BasicParser();
        //�ý��������������в���
        CommandLine line = parser.parse(options, args);
        //�������������-h�����ӡ������Ϣ�����˳�
        if (line.hasOption("h")){
            displayUsage();
            System.exit(0);
        }      
        if (line.hasOption("debug")){
            this.debugMode = true;
        }
        return line;        
    }
    
    public boolean isDebugMode(){
        return this.debugMode;	
    }
}
