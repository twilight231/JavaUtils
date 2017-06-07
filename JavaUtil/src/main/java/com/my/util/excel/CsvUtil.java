package com.my.util.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;


public class CsvUtil {
	
	private static final Log logger = LogFactory.getLog(CsvUtil.class);
	
	   //读取csv文件
    public List<String[]> readCsv(String filePath) throws Exception {
        List<String[]> csvList = new ArrayList<String[]>();
        if (isCsv(filePath)) {
            CsvReader reader = new CsvReader(filePath, ',', Charset.forName("GBK"));
            reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
            while (reader.readRecord()) { //逐行读入除表头的数据
                csvList.add(reader.getValues());
            }
            reader.close();
        } else {
            System.out.println("此文件不是CSV文件！");
        }
        return csvList;
    }
    //判断是否是csv文件
    private boolean isCsv(String fileName) {
        return fileName.matches("^.+\\.(?i)(csv)$");
    }
    
   	private static final String ENCODING = "UTF-8";

	/**
	 * csv格式读
	 * 
	 * @param String
	 * @return List<Map<String, Object>>
	 * @throws CsvException
	 */
	public static List<Map<String, Object>> csvReader(String csvText) {
		List<Map<String, Object>> csvList = new ArrayList<Map<String, Object>>();
		try {
			CsvReader reader = CsvReader.parse(csvText);
			// 考虑到图片大小,设置最大长度不受限制
			reader.setSafetySwitch(false);
			String[] headers = null;
			if (reader.readHeaders()) {
				headers = reader.getHeaders();
				while (reader.readRecord()) {
					Map<String, Object> csvMap = new HashMap<String, Object>();
					for (String head : headers) {
						csvMap.put(head, reader.get(head));
					}
					csvList.add(csvMap);
				}
				logger.debug("CsvUtils--->csvList:{}");
				return csvList;
			} else {
				logger.error("csv读取头失败");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("csv读取编码异常");
		} catch (IOException e) {
			logger.error("csv读取IO异常", e);
		}
		return null;
	}

	/**
	 * csv写
	 * 
	 * @param csvList
	 * @return
	 * @throws CsvException
	 */
	public static String csvWriter(List<Map<String, Object>> csvList) {
		String csvText = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		CsvWriter writer = new CsvWriter(stream, ',', Charset.forName(ENCODING));
		if (csvList != null && csvList.size() > 0) {
			// 获取第一行map key,
			Map<String, Object> headMap = csvList.get(0);

			if (headMap != null && 0 <= headMap.size()) {
				// 提取cvs列
				Iterator<String> it = headMap.keySet().iterator();
				try {
					while (it.hasNext()) {
						writer.write(it.next());
					}
					writer.endRecord();
					boolean falg = false;
					for (Map<String, Object> csvMap : csvList) {
						if (falg) {
							writer.endRecord();
						}
						for (Entry<String, Object> map : csvMap.entrySet()) {
							String value = toStringWithOutNull(map.getValue());
							writer.write(value);
						}
						falg = true;
					}
					writer.close();
					csvText = stream.toString();
					stream.close();
					return csvText;
				} catch (IOException e) {
					logger.warn("csv写入IO异常");
				}
			} else {
				logger.warn("拆解cvs数据为空");
			}
		} else {
			logger.warn("拆解cvs数据为空");
		}
		return null;
	}
	
	private static String toStringWithOutNull(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
