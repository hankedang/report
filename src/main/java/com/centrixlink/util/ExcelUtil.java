package com.centrixlink.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.centrixlink.xreport.domain.web.ExcelHeader;
/**
 * @author franplk
 * Init 2016-01-11
 */
@SuppressWarnings("deprecation")
public class ExcelUtil {
	
	/**
	 * @param title 表格标题名
	 * @param headers  表格属性列名数组
	 * @param dataset 需要显示的数据集合
	 * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @throws Exception 
	 */
	public static void exportExcel(Map<String,String> headers, List<Map<String, Object>> dataset, OutputStream out, String title) throws Exception {
		if (headers == null) {
			throw new Exception("没有表头");
		}
		int rowIndex = 0;
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);
		// 生成表头样式
		HSSFCellStyle style_head = getcellStyle(workbook, 0);
		// 产生表格标题行
		HSSFRow row_head = sheet.createRow(rowIndex);
		for (String name : headers.values()) {
			setCell(row_head, 1, name, style_head);
		}
		// 设置Excel内容样式
		HSSFCellStyle style_row = getcellStyle(workbook, 1);
		rowIndex ++;
		// 遍历集合数据，产生数据行
		for(Map<String, Object> rowdata : dataset) {
			HSSFRow row_normal = sheet.createRow(rowIndex);
			for (String idx : headers.keySet()) {
				setCell(row_head, 1, idx, style_head);
				Object value = rowdata.get(idx);
				if(value == null) {
					value = "";
				}
				setCell(row_normal, 1, String.valueOf(value), style_row);
			}
			rowIndex ++;
		}
		writeTo(workbook, out);
	}
	
	/**
	 * @param title 表格标题名
	 * @param headers  表格属性列名数组
	 * @param dataset 需要显示的数据集合
	 * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @throws Exception 
	 */
	public static void exportComExcel(List<ExcelHeader> headers, List<Map<String, Object>> dataset, OutputStream out, String title) throws Exception {
		if (headers == null) {
			throw new Exception("没有表头");
		}
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		sheet.setDefaultRowHeightInPoints(15);
		// 数据的查询条件加入
		int rowIndex = 0;
		// 生成表头样式
		HSSFCellStyle style_head = getcellStyle(workbook, 0);
		// 产生表格标题行(有复合行，生成两行)
		HSSFRow row_head = sheet.createRow(rowIndex);
		HSSFRow sub_head = sheet.createRow(rowIndex+1);;
		int colIdx_head = 0;
		for (ExcelHeader head : headers) {
			List<ExcelHeader> subHeaders = head.getSubHeader();
			if(subHeaders != null){
				int subsize = subHeaders.size();
				setCell(row_head, colIdx_head, head.getName(), style_head);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIdx_head, subsize + colIdx_head - 1));
				for(ExcelHeader subhd : subHeaders){
					setCell(sub_head, colIdx_head++, subhd.getName(), style_head);
				}
			} else {
				setCell(row_head, colIdx_head, head.getName(), style_head);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, colIdx_head, colIdx_head++));
			}
		}
		// 设置Excel内容样式
		HSSFCellStyle style_row = getcellStyle(workbook, 1);
		rowIndex += 2;
		// 遍历集合数据，产生数据行
		for(Map<String, Object> rowdata : dataset) {
			HSSFRow row_normal = sheet.createRow(rowIndex);
			int colIdx_con = 0;
			for(ExcelHeader head : headers){
				List<ExcelHeader> subHeaders = head.getSubHeader();
				if(subHeaders != null){
					for(ExcelHeader subhd : subHeaders){
						String headtitle = subhd.getId();
						Object value = rowdata.get(headtitle);
						if(value == null) {
							value = "";
						}
						setCell(row_normal, colIdx_con, String.valueOf(value), style_row);
						colIdx_con++;
					}
				} else {
					String headtitle = head.getId();
					Object value = rowdata.get(headtitle);
					if(value == null) {
						value = "";
					}
					setCell(row_normal, colIdx_con, String.valueOf(value), style_row);
					colIdx_con++;
				}
			}
			rowIndex++;
		}
		writeTo(workbook, out);
	}
	
	/**
	 * 针对Excel标题和内容生成不同的样式
	 * @param workbook
	 * @param type 类型---1，内容；0，标题
	 * @return 生成的样式对象
	 */
	private static HSSFCellStyle getcellStyle(HSSFWorkbook workbook, int type) {
		HSSFCellStyle style = workbook.createCellStyle();
		if(type == 0){ // head
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont font_head = workbook.createFont();
			font_head.setFontHeightInPoints((short) 12);
			font_head.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font_head);
		} else if(type == 1) {// normal
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont font_row = workbook.createFont();
			font_row.setFontHeightInPoints((short) 10);
			font_row.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style.setFont(font_row);
		}
		return style;
	}
	
	/**在指定位置创建单元格，并设置单元格类型，赋值
	 * @param row ---- 行对象
	 * @param index ---- 列索引
	 * @param value ---- 赋值
	 * @param style ---- 单元格类型
	 */
	private static void setCell(HSSFRow row, int index, String value, HSSFCellStyle style) {
		HSSFCell cell = row.createCell(index);
		if(style != null){
			cell.setCellStyle(style);
		}
		HSSFRichTextString text = new HSSFRichTextString(value);
		cell.setCellValue(text);
	}
	
	/**
	 * 将对应生成的WorkBook写入
	 * @param workbook
	 * @param out
	 */
	private static void writeTo(HSSFWorkbook workbook, OutputStream out) {
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}