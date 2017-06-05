package com.zstax.util.常用类.导出Excel;

public ActionForward picExcel(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		ServletContext context = this.getServlet().getServletContext();
		try {
			String path = context.getRealPath("/reports/hab/apsfa/PicStat.xls");//������Excel�ļ��ڷ������ϵ����·��
			java.io.File reportFile1 =
				new File(context.getRealPath("/reports/hab/apsfa/statPic.xls"));//��Excel�ļ��Ƿ������ϵģ�һ������һ��ͳ��ͼƬ��Excelģ��
			java.io.FileInputStream fos = new FileInputStream(reportFile1);//�ļ�������
			java.io.FileOutputStream os = new FileOutputStream(path);//�ļ������

			String barName =
				FileHelper.getUploadDir() + "/" + "collentResult2.jpg";//Ҫ������Excel�ļ��е�ͼƬ·��

			org.apache.poi.poifs.filesystem.POIFSFileSystem fs = new POIFSFileSystem(fos);
			org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new HSSFWorkbook(fs);
			org.apache.poi.hssf.usermodel.HSSFSheet sheet = wb.getSheetAt(0);//Excel�ļ��еĵ�һ��������
			org.apache.poi.hssf.usermodel.HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			org.apache.poi.hssf.usermodel.HSSFClientAnchor anchor =new HSSFClientAnchor(
					0,0,350,100,(short) 0,1,(short) 10,10);

			HkSfaStatBC bc = new HkSfaStatBCImpl();//��̨ʵ���� --���ͳ��
			HkSfaActivityBC abc = new HkSfaActivityBCImpl();//��̨ʵ���� --��ƻ
			String activitypk = request.getParameter("activitypk");//�PK
			List childGroups = abc.getChildgroup(activitypk);//�õ��˻������С��

			double[][] data = bc.problemCollect(activitypk);//�õ�ͳ�ƽ����һ����ά����

			org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(11);//�ڵ�һ���������д���һ�У���12�У�
			for (int k = 0; k < childGroups.size(); k++) {
				ApSfaGroupactivity activityGroup =(ApSfaGroupactivity) childGroups.get(k);//ApSfaGroupactivityΪ�С���Pojo
				org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell((short) (k + 1));//��row�д�����Ԫ��
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);//���ñ���
				cell.setCellValue(activityGroup.getName());//����Ԫ������ֵ
			}

			/* ͬǰһ��ѭ������Excel������� */
			String[] strs ={ "������", "����", "���Ĺر�", "������", "δ���", "�������", "��ѡ������" };
			for (int i = 12; i < strs.length + 12; i++) {
				org.apache.poi.hssf.usermodel.HSSFRow row1 = sheet.createRow(i);
				for (int j = 0; j < data[0].length + 1; j++) {
					org.apache.poi.hssf.usermodel.HSSFCell cell = row1.createCell((short) j);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					if (j == 0) {
						cell.setCellValue(strs[i - 12]);
					} else {
						int a = (int) data[i - 12][j - 1];
						cell.setCellValue(a);
					}
				}
			}

			java.io.File jpgfile = new File(barName);//barName Ϊͳ��ͼƬ�ڷ������ϵ�·��
			org.apache.commons.io.output.ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();//�ֽ������������д�������ļ�
			java.awt.image.BufferedImage bufferImg = ImageIO.read(jpgfile);
			javax.imageio.ImageIO.write(bufferImg, "jpg", byteArrayOut);

			patriarch.createPicture(anchor,wb.addPicture(
					byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));//��ͳ��ͼƬ��ӵ�Excel�ļ���
			wb.write(os);
			os.close();
			net.sf.excelutils.ExcelUtils.addValue("actionServlet", this);
			String config = null;

			config = "/reports/hab/apsfa/PicStat.xls";
			response.setHeader("Content-Disposition","attachment; filename=\"" + "PicStat.xls\"");
			net.sf.excelutils.ExcelUtils.export(getServlet().getServletContext(),config,response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}