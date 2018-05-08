/**

 * Copyright (c) 2017, 吴汶泽 (wuwz@live.com).

 *

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 *

 *      http://www.apache.org/licenses/LICENSE-2.0

 *

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */
package com.esteban.framework.utils.excelPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <h3>Excel导入导出工具 </h3>
 * 已经集成的依赖包：poi-ooxml-3.8,commons-beanutils-1.9.3 <br>
 * <b>Examples:</b>
 * <pre>
 * 1. 导出并使用浏览器下载：
 * ExcelKit.$Export(entityClass,httpServletResponse).toExcel(dataList,"sheetName");
 * 
 * 2. 生成本地Excel文件：
 * ExcelKit.$Builder(entityClass).toExcel(dataList,"sheetName",new FileOutputStream(excelFile));
 * 
 * 3. 读取Excel文件并解析：
 * ExcelKit.$Import().readExcel(excelFile,new OnReadDataHandler() {
 * 	
 *  public void handler(List&lt;String&gt; rowData) {
 *  	//rowData.get(0); rowData.get(1);...
 *  }
 * });
 * </pre>
 * @author wuwz
 */
public class ExcelKit {
	private static Logger log = Logger.getLogger(ExcelKit.class);
	
	private Class<?> _class;
	public HttpServletResponse _response;
	/**
	 * 默认以此值填充空单元格,可通过 setEmptyCellValue(string)改变其默认值。
	 */
	public String _emptyCellValue = "EMPTY_CELL_VALUE";
	
	protected ExcelKit() {}
	protected ExcelKit(Class<?> _class) {
		this(_class, null);
	}
	protected ExcelKit(HttpServletResponse response) {
		this._response = response;
	}
	protected ExcelKit(Class<?> _class,HttpServletResponse response) {
		this._response = response;
		this._class = _class;
	}
	
	/**
	 * 用于生成本地文件
	 * @param _class 实体Class对象
	 * @return ExcelKit
	 */
	public static ExcelKit $Builder() {
		return new ExcelKit();
	}
	
	/**
	 * 用于生成本地文件
	 * @param _class 实体Class对象
	 * @return ExcelKit
	 */
	public static ExcelKit $Builder(Class<?> _class) {
		return new ExcelKit(_class);
	}
	
	/**
	 * 用于浏览器导出
	 * @param _class 实体Class对象
	 * @param response 原生HttpServletResponse对象
	 * @return ExcelKit
	 */
	public static ExcelKit $Export(Class<?> _class,HttpServletResponse response) {
		return new ExcelKit(_class,response);
	}
	
	/**
	 * 用于浏览器导出
	 * @param response 原生HttpServletResponse对象
	 * @return ExcelKit
	 */
	public static ExcelKit $Export(HttpServletResponse response) {
		return new ExcelKit(response);
	}
	
	/**
	 * 用于导入数据解析
	 * @return ExcelKit
	 */
	public static ExcelKit $Import() {
		return new ExcelKit();
	}
	
	public ExcelKit setEmptyCellValue(String emptyCellValue) {
		this._emptyCellValue = emptyCellValue;
		return this;
	}
	
	/**
	 * 导出Excel(此方式需依赖浏览器实现文件下载,故应先使用$Export()构造器)
	 * @param data 数据集合
	 * @param sheetName 工作表名字
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcelModel(List<?> data,List<Map<String,Object>> dataCount,String sheetName) {
		if(_response == null) {
			throw new RuntimeException("请先初始化  HttpServletResponse 对象! (通过调用 $Export(Class<?> _class,HttpServletResponse response))");
		}
		try {
			return toExcelModel(data, dataCount, sheetName, _response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 针对转换方法的默认实现,默认为Excel2007文件。
	 * @param data 数据集合
	 * @param sheetName 工作表名字
	 * @param out 输出流
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcelModel(List<?> data, List<Map<String,Object>> dataCount, String sheetName, OutputStream out) {

		return toExcelModel(data, dataCount, sheetName, ExcelType.EXCEL2007, new OnSettingHanlder() {

			@Override
			public CellStyle getHeadCellStyle(Workbook wb) {
				CellStyle cellStyle = wb.createCellStyle();
				Font font = wb.createFont();
				cellStyle.setFillForegroundColor((short) 12);
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框为细边框
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框为细边框
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);// 下边框为细边框
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框为细边框
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 对齐
				cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
				cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
				font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				font.setFontHeightInPoints((short) 12);// 字体大小
				font.setColor(HSSFColor.WHITE.index);
				// 应用标题字体到标题样式
				cellStyle.setFont(font);
				return cellStyle;
			}

			@Override
			public CellStyle getBodyCellStyle(Workbook wb) {
				CellStyle bodyStyle = wb.createCellStyle();
				Font font = wb.createFont();
				font.setFontHeightInPoints((short) 12);// 字体大小
				// 应用标题字体到标题样式
				bodyStyle.setFont(font);
				return bodyStyle;
			}
			
			@Override
			public String getExportFileName(String sheetName) {
				return String.format("导出-%s-%s", sheetName,System.currentTimeMillis());
			}
		}, out);
	}

	public boolean toExcelModel(List<?> data, List<Map<String,Object>> dataCount, String sheetName, ExcelType type, OnSettingHanlder handler,
			OutputStream out) {
		long begin = System.currentTimeMillis();
		
		if (data == null || data.size() < 1) {
			if(log.isDebugEnabled())
				log.debug("没有检测到导出数据，将生成 Excel导入模版。");
		}
		
		// 导出列查询。
		List<ExportItem> exportItems = new ArrayList<ExportItem>();
		for (Field field : _class.getDeclaredFields()) {

			ExportConfig ec = field.getAnnotation(ExportConfig.class);
			if (ec != null) {
				exportItems.add(
					new ExportItem.Builder()
						.setField(field.getName())
						.setDisplay("field".equals(ec.value()) ? field.getName() : ec.value())
						.setIsExportData(ec.isExportData())
						.setWidth(ec.width())
						.create()
				);
			}
		}

		// 创建工作薄。
		Workbook wb = this.createWorkbook(type);

		// 创建Sheet
		Sheet sheet = wb.createSheet(sheetName);

		// 创建表头
		Row header = sheet.createRow(0);
		for (int i = 0; i < exportItems.size(); i++) {
			ExportItem exportItem = exportItems.get(i);
			Cell cell = header.createCell(i);
			sheet.setColumnWidth(i, (short) (exportItem.getWidth() * 35.7));
			cell.setCellValue(exportItem.getDisplay());
			CellStyle style = handler.getHeadCellStyle(wb);
			if (style != null) {
				cell.setCellStyle(style);
			}
		}

		int i = 0;
		
		// 产生数据行
		if(data != null && data.size() > 0) {
			
			for (;i < data.size(); i++) {

				Row body = sheet.createRow(i + 1);

				for (int j = 0; j < exportItems.size(); j++) {
					ExportItem exportItem = exportItems.get(j);
					sheet.setColumnWidth(j, (short) (exportItem.getWidth() * 35.7));
					Cell cell = body.createCell(j);
					if (exportItem.getIsExportData()) {
						try {
							cell.setCellValue(BeanUtils.getProperty(data.get(i), exportItem.getField()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						cell.setCellValue("******");
					}

					CellStyle style = handler.getBodyCellStyle(wb);
					if (style != null) {
						cell.setCellStyle(style);
					}
				}
			}
		}
		
		i++;
		
		//产生统计行
		if(dataCount != null && dataCount.size() > 0){
			for (int x=0; x < dataCount.size(); x++) {

				Row body = sheet.createRow(i + x);

				for (int j = 0; j < exportItems.size(); j++) {
					Cell cell = body.createCell(j);
					try {
						if(dataCount.get(x).containsKey(exportItems.get(j).getField())){
							cell.setCellValue(dataCount.get(x).get(exportItems.get(j).getField())+"");
						}else{
							cell.setCellValue("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
					style.setBorderTop(CellStyle.BORDER_THIN);// 上边框为细边框
					style.setBorderRight(CellStyle.BORDER_THIN);// 右边框为细边框
					style.setBorderBottom(CellStyle.BORDER_THIN);// 下边框为细边框
					style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框为细边框
					style.setAlignment(CellStyle.ALIGN_LEFT);// 对齐
					style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
					font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
					font.setFontHeightInPoints((short) 12);// 字体大小
					font.setColor(HSSFColor.WHITE.index);
					// 应用标题字体到标题样式
					style.setFont(font);
					
					cell.setCellStyle(style);
				}
			}
		}

		try {
			// 生成Excel文件并下载.
			if(_response != null) {
				// 浏览器响应头
				String fileName = String.format("%s%s", handler.getExportFileName(sheetName),getExcelSuffix(type));
				_response.setContentType(getContentType(type));
				_response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				
				if(out == null) {
					out = _response.getOutputStream();
				}
			}
			wb.write(out);
			out.flush();
			out.close();
			
			log.info(String.format("Excel处理完成,共生成数据:%s行 (不包含表头),耗时：%s seconds.", 
					(data != null ? data.size() : 0),
					(System.currentTimeMillis() - begin) / 1000F));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * 导出Excel(此方式需依赖浏览器实现文件下载,故应先使用$Export()构造器)
	 * @param data 数据集合
	 * @param sheetName 工作表名字
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcelMap(List<Map<String,Object>> data,List<Map<String,Object>> dataCount,String sheetName,String configName) {
		if(_response == null) {
			throw new RuntimeException("请先初始化  HttpServletResponse 对象! (通过调用 $Export(Class<?> _class,HttpServletResponse response))");
		}
		try {
			return toExcelMap(data, dataCount, sheetName, configName, _response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 针对转换方法的默认实现,默认为Excel2007文件。
	 * @param data 数据集合
	 * @param sheetName 工作表名字
	 * @param out 输出流
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcelMap(List<Map<String,Object>> data, List<Map<String,Object>> dataCount, String sheetName, String configName, OutputStream out) {

		return toExcelMap(data, dataCount, sheetName, configName, ExcelType.EXCEL2007, new OnSettingHanlder() {

			@Override
			public CellStyle getHeadCellStyle(Workbook wb) {
				CellStyle cellStyle = wb.createCellStyle();
				Font font = wb.createFont();
				cellStyle.setFillForegroundColor((short) 12);
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框为细边框
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框为细边框
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);// 下边框为细边框
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框为细边框
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 对齐
				cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
				cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
				font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				font.setFontHeightInPoints((short) 12);// 字体大小
				font.setColor(HSSFColor.WHITE.index);
				// 应用标题字体到标题样式
				cellStyle.setFont(font);
				return cellStyle;
			}

			@Override
			public CellStyle getBodyCellStyle(Workbook wb) {
				CellStyle bodyStyle = wb.createCellStyle();
				Font font = wb.createFont();
				font.setFontHeightInPoints((short) 12);// 字体大小
				// 应用标题字体到标题样式
				bodyStyle.setFont(font);
				return bodyStyle;
			}
			
			@Override
			public String getExportFileName(String sheetName) {
				return String.format("导出-%s-%s", sheetName,System.currentTimeMillis());
			}
		}, out);
	}

	public boolean toExcelMap(List<Map<String,Object>> data, List<Map<String,Object>> dataCount, String sheetName, String configName, ExcelType type, OnSettingHanlder handler,
			OutputStream out) {
		long begin = System.currentTimeMillis();
		
		if (data == null || data.size() < 1) {
			if(log.isDebugEnabled())
				log.debug("没有检测到导出数据，将生成 Excel导入模版。");
		}
		
		// 导出列查询。
		List<Map<String,String>> exportItems=ExportXmlDom4jUtil.getExportConfigFromXML(configName);

		// 创建工作薄。
		Workbook wb = this.createWorkbook(type);

		// 创建Sheet
		Sheet sheet = wb.createSheet(sheetName);

		// 创建表头
		Row header = sheet.createRow(0);
		for (int i = 0; i < exportItems.size(); i++) {
			Cell cell = header.createCell(i);
			sheet.setColumnWidth(i, (short) (200 * 35.7));
			cell.setCellValue(exportItems.get(i).get("titleText"));
			CellStyle style = handler.getHeadCellStyle(wb);
			if (style != null) {
				cell.setCellStyle(style);
			}
		}

		int i = 0;
		
		// 产生数据行
		if(data != null && data.size() > 0) {
			
			for (;i < data.size(); i++) {

				Row body = sheet.createRow(i + 1);

				for (int j = 0; j < exportItems.size(); j++) {
					Map<String,String> exportItem = exportItems.get(j);
					sheet.setColumnWidth(j, (short) (200 * 35.7));
					Cell cell = body.createCell(j);
					CellStyle style = handler.getBodyCellStyle(wb);
					if (style != null) {
						cell.setCellStyle(style);
					}
					
					if ("false".equals(exportItem.get("isExportData"))) {
						cell.setCellValue("******");
					} else {
						if ("true".equals(exportItem.get("isNumber"))) {
							int digit=2;
							if (!StringUtils.isEmpty((exportItem.get("digit")))) {
								try {
									digit=Integer.parseInt(exportItem.get("digit")+"");
								} catch (Exception e) {
									digit=2;
								}
							}
							try {
								double d=Double.valueOf(data.get(i).get(exportItem.get("name"))+"");
//								BigDecimal b =new BigDecimal(d);  
//								d = b.setScale(digit,BigDecimal.ROUND_HALF_UP).doubleValue();
								String fromatStr="#.";
								if(digit>0){
									for(int r=1;r<=digit;r++){
										fromatStr+="0";
									}
								}
								String val=new java.text.DecimalFormat(fromatStr).format(d);
								cell.setCellValue(val+"");
								continue;
							} catch (Exception e) {
								cell.setCellValue("NaN");
								e.printStackTrace();
								continue;
							}
						}
						
						if ("true".equals(exportItem.get("isDate"))) {
							String formatStr="yyyy-MM-dd HH:mm:ss";
							String baseFormatStr="yyyy-MM-dd HH:mm:ss";
							if (!StringUtils.isEmpty((exportItem.get("formatStr")))) {
								try {
									formatStr=exportItem.get("formatStr")+"";
								} catch (Exception e) {
									formatStr="yyyy-MM-dd HH:mm:ss";
								}
							}
							try {
								String date=data.get(i).get(exportItem.get("name"))+"";
								SimpleDateFormat sf=new SimpleDateFormat(formatStr);
								SimpleDateFormat dataSf=new SimpleDateFormat(baseFormatStr);
								date=sf.format(dataSf.parse(date));
								cell.setCellValue(date);
								continue;
							} catch (Exception e) {
								cell.setCellValue("NaN");
								e.printStackTrace();
								continue;
							}
						} 
						
						if ("true".equals(exportItem.get("isConvert"))) {
							try {
								String convertStr=exportItem.get("convertStr");
								if(!StringUtils.isEmpty(convertStr)){
									boolean flag=false;
									String[] convertor=convertStr.split(";");
									for(int q=0;q<convertor.length;q++){
										String keyStr=convertor[q].split("=")[0];
										String valueStr=convertor[q].split("=")[1];
										System.out.println(q+"*********"+keyStr+"***********"+valueStr);
										if(keyStr.equals(data.get(i).get(exportItem.get("name"))+"")){
											cell.setCellValue(valueStr);
											flag=true;
											break;
										}
									}
									if(!flag){
										if(!StringUtils.isEmpty(exportItem.get("convertDefault"))){
											cell.setCellValue(exportItem.get("convertDefault"));
										}else{
											cell.setCellValue(data.get(i).get(exportItem.get("name"))+"");
										}
									}
									continue;
								}
							} catch (Exception e) {
								cell.setCellValue("erro");
								e.printStackTrace();
								continue;
							}
						}
						
						try {
							cell.setCellValue(data.get(i).get(exportItem.get("name"))+"");
						} catch (Exception e) {
							cell.setCellValue("erro");
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		i++;
		
		//产生统计行
		if(dataCount != null && dataCount.size() > 0){
			for (int x=0; x < dataCount.size(); x++) {

				Row body = sheet.createRow(i + x);

				for (int j = 0; j < exportItems.size(); j++) {
					Cell cell = body.createCell(j);
					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
					style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
					font.setFontHeightInPoints((short) 12);// 字体大小
					font.setColor(HSSFColor.WHITE.index);
					style.setFont(font);
					cell.setCellStyle(style);
					
					try {
						if(dataCount.get(x).containsKey(exportItems.get(j).get("name"))){
							if ("true".equals(exportItems.get(j).get("isNumber"))) {
								int digit=2;
								if (!StringUtils.isEmpty((exportItems.get(j).get("digit")))) {
									try {
										digit=Integer.parseInt(exportItems.get(j).get("digit")+"");
									} catch (Exception e) {
										digit=2;
									}
								}
								try {
									double d=Double.valueOf(dataCount.get(x).get(exportItems.get(j).get("name"))+"");
//									BigDecimal b =new BigDecimal(d);  
//									d = b.setScale(digit,BigDecimal.ROUND_HALF_UP).doubleValue();
									String fromatStr="#.";
									if(digit>0){
										for(int r=1;r<=digit;r++){
											fromatStr+="0";
										}
									}
									String val=new java.text.DecimalFormat(fromatStr).format(d);
									cell.setCellValue(val+"");
									continue;
								} catch (Exception e) {
									cell.setCellValue("NaN");
									e.printStackTrace();
									continue;
								}
							}
							cell.setCellValue(dataCount.get(x).get(exportItems.get(j).get("name"))+"");
						}else{
							cell.setCellValue("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			// 生成Excel文件并下载.
			if(_response != null) {
				// 浏览器响应头
				String fileName = String.format("%s%s", handler.getExportFileName(sheetName),getExcelSuffix(type));
				_response.setContentType(getContentType(type));
				_response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				
				if(out == null) {
					out = _response.getOutputStream();
				}
			}
			wb.write(out);
			out.flush();
			out.close();
			
			log.info(String.format("Excel处理完成,共生成数据:%s行 (不包含表头),耗时：%s seconds.", 
					(data != null ? data.size() : 0),
					(System.currentTimeMillis() - begin) / 1000F));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * 读取Excel数据(默认读取第一个工作表的所有数据,包含表头)
	 * @param excelFile Excel文件(本地)
	 */
	public void readExcel(File excelFile,OnReadDataHandler handler) {
		readExcel(excelFile, 0, handler);
	}
	
	/**
	 * 读取指定sheetIndex的数据（包含表头）
	 * @param excelFile Excel文件(本地)
	 * @param sheetIndex 工作表索引
	 * @param handler 行数据处理回调
	 */
	public void readExcel(File excelFile,int sheetIndex,OnReadDataHandler handler) {
		readExcel(excelFile, handler, sheetIndex, 0, -1, 0, -1);
	}
	
	/**
	 * 读取Excel数据
	 * @param excelFile Excel文件(本地)
	 * @param handler 行数据处理回调
	 * @param sheetIndex 工作表索引
	 * @param startRowIndex 开始行索引
	 * @param endRowIndex 结束行索引,-1为所有
	 * @param startCellIndex 开始列索引
	 * @param endCellIndex 结束列索引,-1为所有
	 */
	public void readExcel(File excelFile,OnReadDataHandler handler,int sheetIndex,int startRowIndex,int endRowIndex,int startCellIndex, int endCellIndex) {
		long totalRows = 0l;
		long begin = System.currentTimeMillis();
		Workbook wb = createWorkbook(excelFile);
		
		if(wb != null) {
			Sheet sheet = wb.getSheetAt(sheetIndex);
			
			if(sheet != null) {
				
				if(endRowIndex == -1) {
					endRowIndex = sheet.getPhysicalNumberOfRows();
				}
				if(endCellIndex == -1) {
					endCellIndex = sheet.getRow(0).getPhysicalNumberOfCells();
				}
				
				for (int i = startRowIndex; i < endRowIndex; i++) {
					List<String> rowData = new ArrayList<String>();
					Row row = sheet.getRow(i);
					if(row != null) {
						
						for (int j = startCellIndex; j < endCellIndex; j++) {
							Cell cell = row.getCell(j);
							if(cell != null) {
								// 统一以字符串的方式获取
								cell.setCellType(Cell.CELL_TYPE_STRING);
								rowData.add(cell.getStringCellValue());
							} else {
								rowData.add(_emptyCellValue);
							}
//							rowData.add(cell != null ? cell.getStringCellValue() : _emptyCellValue);
						}
					}
					if(rowData.size() > 0) {
						// 处理当前行数据
						handler.handler(rowData); 
						totalRows++;
					}
				}
			}
		}
		log.info(String.format("Excel数据读取并处理完成,共读取数据：%s行,耗时：%s seconds.", totalRows,(System.currentTimeMillis() - begin) / 1000F));
	}

	public Workbook createWorkbook(File file) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));//2003
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(new FileInputStream(file));//2003以上
			} catch (Exception e1) {
				throw new RuntimeException("不能读取有效的Excel数据！");
			}
		}
		return workbook;
	}

	public Workbook createWorkbook(ExcelType type) {
		if (type == ExcelType.EXCEL2003)
			return new HSSFWorkbook();
		return new XSSFWorkbook();
	}
	
	public String getExcelSuffix(ExcelType type) {
		if (type == ExcelType.EXCEL2003) {
			return ".xls";
		} else {
			return ".xlsx";
		}
	}
	
	public String getContentType(ExcelType type) {
		if (type == ExcelType.EXCEL2003) {
			return "application/vnd.ms-excel";
		} else {
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}
	}
}

class ExportItem {

	private String field; // 属性名
	private String display; // 显示名
	private Short width; // 宽度
	private Boolean isExportData; // 是否导出数据

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Short getWidth() {
		return width;
	}

	public void setWidth(Short width) {
		this.width = width;
	}

	public Boolean getIsExportData() {
		return isExportData;
	}

	public void setIsExportData(Boolean isExportData) {
		this.isExportData = isExportData;
	}

	public ExportItem() {
		super();
	}

	public ExportItem(Builder b) {
		this.field = b.field;
		this.display = b.display;
		this.width = b.width;
		this.isExportData = b.isExportData;
	}

	public static class Builder {
		private String field;
		private String display;
		private Short width;
		private Boolean isExportData;

		public Builder setField(String field) {
			this.field = field;
			return this;
		}

		public Builder setDisplay(String display) {
			this.display = display;
			return this;
		}

		public Builder setWidth(Short width) {
			this.width = width;
			return this;
		}

		public Builder setIsExportData(Boolean isExportData) {
			this.isExportData = isExportData;
			return this;
		}

		public ExportItem create() {
			return new ExportItem(this);
		}

	}

}
