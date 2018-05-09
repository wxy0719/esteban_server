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
package com.esteban.core.framework.utils.excelPlugin;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 导出Excel设置接口。
 * @author wuwz
 */
public interface OnSettingHanlder {
	/**
	 * 设置表头样式
	 * @param wb 当前Wordbook对象
	 * @return 处理后的样式
	 */
	CellStyle getHeadCellStyle(Workbook wb);

	/**
	 * 设置内容部分的单元格样式
	 * @param wb 当前Wordbook对象
	 * @return 处理后的样式
	 */
	CellStyle getBodyCellStyle(Workbook wb);
	
	/**
	 * 设置导出的文件名（无需处理后缀）
	 * @param sheetName sheetName
	 * @return 处理后的文件名
	 */
	String getExportFileName(String sheetName);
}
