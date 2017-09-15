/* 
 * @(#)ChinaExcel.java    Created on 2005-11-9
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.chinaexcel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


@SuppressWarnings({ "unused", "unchecked" })
public class ChinaExcel {
	protected Document document;

	protected Element f1, general, defaultCell, brList, ftList;

	protected Element penList, rowHeader, cellList, print, colHeader;

	private boolean isDClickLabelSort = true; // 是否双击列头排序；

	private int fixedRows = 2; // 设定列头行数(默认为2)

	private boolean isShowGrid = false; // 是否显示grid

	private boolean isShowHeader = false; // 是否显示行列头

	private int defaultCellHAg = 0; // 缺省单元文本的水平对齐方式(默认左对齐)

	private int defaultCellVAg = 6; // 缺省单元文本的垂直对齐方式(默认居中对齐)

	private int defaultCellSwty = 0; // 缺省单元的数字显示格式

	private boolean isWriteByRow = true; // 是否按行写

	private Map<String, String> cellFontStyleMap = new HashMap<String, String>(); // 指定单元格的字体样式

	private Map<String, String> cellBgColorMap = new HashMap<String, String>(); // 指定单元格的背景色

	private Map<String, String> cellHorzTextAlignMap = new HashMap<String, String>();// 指定单元格的水平对齐方式

	private Map<String, String> cellVertTextAlignMap = new HashMap<String, String>();// 指定单元格的竖直对齐方式

	private Map<String, String> cellDigitShowStyleMap = new HashMap<String, String>();// 指定单元格的数值显示格式

	private Map<String, String> cellSlashStyleMap = new HashMap<String, String>();// 指定单元格的斜线（对角线）格式

	private Map<String, String> cellBorderMap = new HashMap<String, String>(); // 指定单元格的边框样式

	private Map<String, String> cellCombiNationMap = new HashMap<String, String>(); // 指定单元格的合并

	private Map<String, String> cellAutoWrapMap = new HashMap<String, String>(); // 指定单元格文本自动换行

	private Map<String, String> cellFormulaMap = new HashMap<String, String>(); // 指定单元格的公式

	private List cellRowSizeList = new ArrayList(); // 指定单元格的行高

	private List cellColSizeList = new ArrayList(); // 指定单元格的列宽

	/**
	 * @param isDClickLabelSort
	 *            The isDClickLabelSort to set.
	 */
	public void setDClickLabelSort(boolean isDClickLabelSort) {
		this.isDClickLabelSort = isDClickLabelSort;
	}

	/**
	 * @param fixedRows
	 *            The fixedRows to set.
	 *            设定双击列头排序时，需要设定列头行数（默认为3，前两行作为title，第三行为字段名）
	 */
	public void setFixedRows(int fixedRows) {
		this.fixedRows = fixedRows;
	}

	/**
	 * @param isShowGrid
	 *            The isShowGrid to set.
	 */
	public void setShowGrid(boolean isShowGrid) {
		this.isShowGrid = isShowGrid;
	}

	/**
	 * @param isShowHeader
	 *            The isShowHeader to set.
	 */
	public void setShowHeader(boolean isShowHeader) {
		this.isShowHeader = isShowHeader;
	}

	/**
	 * @param defaultCellHAg
	 *            The defaultCellHAg to set. 0：左对齐（默认） 6：居中 2：右对齐
	 */
	public void setDefaultCellHAg(int defaultCellHAg) {
		this.defaultCellHAg = defaultCellHAg;
	}

	/**
	 * @param defaultCellSwty
	 *            The defaultCellSwty to set. 5：千分位格式 1：普通数字格式 2：人民币显示格式
	 *            3：美圆显示格式 4：百分比显示格式 6：欧元显示格式 7：财务大写格式 8:文本
	 */
	public void setDefaultCellSwty(int defaultCellSwty) {
		this.defaultCellSwty = defaultCellSwty;
	}

	/**
	 * @param defaultCellVAg
	 *            The defaultCellVAg to set. 6：居中（默认） 0：居左 8：居下
	 */
	public void setDefaultCellVAg(int defaultCellVAg) {
		this.defaultCellVAg = defaultCellVAg;
	}

	/**
	 * @param isWriteByRow
	 *            The isWriteByRow to set.
	 */
	public void setWriteByRow(boolean isWriteByRow) {
		this.isWriteByRow = isWriteByRow;
	}

	/**
	 * 设定指定单元格的字体样式
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param style
	 *            根据ftList中的设置（索引对应即可 如取用obj0，则传入0）
	 */
	public void setCellFontStyle(int startRow, int startCol, int endRow,
			int endCol, int style) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellFontStyleMap.put(i + "&" + j, String.valueOf(style));
			}
		}
	}

	/**
	 * 设定指定单元格的背景色
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param color
	 *            根据brList中的设置（索引对应即可 如取用obj0，则传入0）
	 */
	public void setCellBgColor(int startRow, int startCol, int endRow,
			int endCol, int style) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellBgColorMap.put(i + "&" + j, String.valueOf(style));
			}
		}
	}

	/**
	 * 设定指定单元格的水平对齐方式
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param align
	 *            0：左对齐（默认） 6：居中 2：右对齐
	 */
	public void setCellHorzTextAlign(int startRow, int startCol, int endRow,
			int endCol, int align) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellHorzTextAlignMap.put(i + "&" + j, align + "");
			}
		}
	}

	/**
	 * 设定指定单元格的竖直对齐方式
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param align
	 *            6：居中（默认） 0：居左 8：居下
	 */
	public void setCellVertTextAlign(int startRow, int startCol, int endRow,
			int endCol, int align) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellVertTextAlignMap.put(i + "&" + j, align + "");
			}
		}
	}

	/**
	 * 设定指定单元格的显示格式
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param showStyle
	 *            5：千分位格式 1：普通数字格式 2：人民币显示格式 3：美圆显示格式 4：百分比显示格式 6：欧元显示格式
	 *            7：财务大写格式 8:文本
	 * @param digitPoint
	 *            小数点后的位数
	 */
	public void setCellDigitShowStyle(int startRow, int startCol, int endRow,
			int endCol, int showStyle, int digitPoint) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellDigitShowStyleMap.put(i + "&" + j, showStyle + "&"
						+ digitPoint);
			}
		}
	}

	/**
	 * 设定指定单元格的斜线（对角线）
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param nSlashStyle
	 *            斜线类型 0：无斜线
	 *            1：对角线，2：上角双斜线，3：下角双斜线，4：横线，5：竖线，6：动态竖线，7：动态横线，8：反对角线
	 * @param lineStyle
	 *            根据PenList中的定义（索引值对应即可，同字体样式）
	 */
	public void setCellSlashStyle(int startRow, int startCol, int endRow,
			int endCol, int nSlashStyle, int lineStyle) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellSlashStyleMap.put(i + "&" + j, nSlashStyle + "&"
						+ lineStyle);
			}
		}
	}

	/**
	 * 设定指定单元格的边框样式
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param style
	 *            根据PenList中的定义（索引值对应即可，同字体样式）若不想要边框，则传入no即可
	 */
	public void setCellBorder(int startRow, int startCol, int endRow,
			int endCol, String style) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellBorderMap.put(i + "&" + j, style.trim());
			}
		}
	}

	/**
	 * 设定指定行的行高
	 * 
	 * @param startRow
	 * @param endRow
	 * @param height
	 */
	public void setCellRowSize(int startRow, int endRow, int height) {
		for (int i = startRow; i <= endRow; i++) {
			cellRowSizeList.add(i + "&" + height);
		}
	}

	/**
	 * 设定指定列的列宽
	 * 
	 * @param startCol
	 * @param endCol
	 * @param width
	 */
	public void setCellColSize(int startCol, int endCol, int width) {
		for (int i = startCol; i <= endCol; i++) {
			cellColSizeList.add(i + "&" + width);
		}
	}

	/**
	 * 将指定单元格合并
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 */
	public void cellCombiNation(int startRow, int startCol, int endRow,
			int endCol) {
		int rows = endRow - startRow;
		int cols = endCol - startCol;
		String rowsValue = ""; // 竖向合并
		String colsValue = ""; // 横向合并
		if (rows > 0 || cols > 0) {
			for (int i = startRow; i <= endRow; i++) {
				for (int j = startCol; j <= endCol; j++) {
					rowsValue = "";
					colsValue = "";
					if (cols > 0) {
						if (j == startCol) {
							colsValue = String.valueOf(cols);
						} else {
							colsValue = String.valueOf(-startCol);
							rowsValue = "&" + String.valueOf(-startRow);
						}
					}
					if (rows > 0) {
						if (i == startRow)
							rowsValue = "&" + String.valueOf(rows);
						else {
							rowsValue = "&" + String.valueOf(-startRow);
							colsValue = String.valueOf(-startCol);
						}
					}
					cellCombiNationMap.put(i + "&" + j, colsValue + rowsValue);
				}
			}
		}
	}

	/**
	 * 设定指定单元格自动换行
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 */
	public void setCellAutoWrap(int startRow, int startCol, int endRow,
			int endCol) {
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startCol; j <= endCol; j++) {
				cellAutoWrapMap.put(i + "&" + j, String.valueOf(257));
			}
		}
	}

	/**
	 * 为指定单元格设置公式
	 * 
	 * @param row
	 * @param col
	 * @param formula
	 *            如MAX(A1:B1)
	 */
	public void setCellFormula(int row, int col, String formula) {
		int pos = 0;
		pos = formula.indexOf("=");
		if (pos >= 0)
			cellFormulaMap.put(row + "&" + col, formula.trim());
		else
			cellFormulaMap.put(row + "&" + col, "=" + formula.trim());
	}

	/**
	 * 根据EXCEL中的列号返回相应ABC...(如1--A；2--B...)
	 * 
	 * @param num
	 * @return
	 */
	public String getABCByNum(int num) {
		char letter = (char) (num - 1 + 'A');
		return String.valueOf(letter);
	}

	public ChinaExcel() {
	}

	// public ChinaExcel(int rows, int cols, List datas) {
	// getChinaExcel(rows, cols, datas);
	// }

    public String genChinaExcelXmlData(int rows, int cols, List datas)
			throws Exception {
		// xml数据
		String xmlData = null;
		try {
			// 格式化输出,类型IE浏览一样
			OutputFormat format = OutputFormat.createPrettyPrint();

			// 指定XML字符集编码
			format.setEncoding("GBK");

			// 设置对xml中text的内容保持原状，不去除多余的空格
			format.setTrimText(false);

			StringWriter sw = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(sw, format);

			// 根据已有的数据生成xml文件
			xmlWriter.write(getChinaExcelDoc(rows, cols, datas));

			xmlData = sw.toString();
			xmlWriter.close();
			sw.close();
		} catch (IOException e) {
			throw new Exception("写XML文件错误！", e);
		}
		return xmlData;
	}

	/**
	 * 返回完整的超级报表DOM
	 * 
	 * @return Document
	 */
	public Document getChinaExcelDoc(int rows, int cols, List datas) {
		if (document == null) {
			document = DocumentHelper.createDocument();
			Element root = document.addElement("CHINAEXCEL");
			f1 = root.addElement("F1");
			f1.add(getGeneral(rows, cols));
			// f1.add(getPrint());
			f1.add(getDefaultCell());
			f1.add(getCellList(rows, cols, datas));
			f1.add(getBrList());
			f1.add(getFtList());
			f1.add(getPenList());
			Element rowColHeader = getRowHeader();
			if (rowColHeader != null)
				f1.add(rowColHeader);
			rowColHeader = getColHeader();
			if (rowColHeader != null)
				f1.add(rowColHeader);
		}
		return document;
	}

	/**
	 * 写报表主体CellList
	 * 
	 * @param rows
	 * @param cols
	 * @param datas
	 * @return
	 */	
	public Element getCellList(int row, int col, List datas) {
		if (cellList == null) {
			int rowCols;
			cellList = new DOMElement("CellList");
			String nodeNamePre;
			if (this.isWriteByRow) { // 按行写
				cellList.addElement("WriteByRow").addText("1");
				nodeNamePre = "Row";
				rowCols = row;
			} else { // 按列写
				nodeNamePre = "Col";
				rowCols = col;
			}

			// getGeneral().addElement("TotalRow").addText(String.valueOf(rows));
			// getGeneral().addElement("TotalCol").addText(String.valueOf(cols));

			// 循环报表数据
			if (datas != null) {
				String mapValue, mapKey;
				String showStyle, digitPoint;
				String slashStyle, lineStyle;
				String rows, cols;
				String value = "";
				int pos;

				for (int i = 0; i < rowCols; i++) {
					int intRowCol = i + 1; // 当前为第几行或第几列数据
					Element rowColElement = cellList.addElement(nodeNamePre
							+ intRowCol);

					// 取得一行/一列数据
					List cellDatas = (List) datas.get(i);

					// 设置列数/行数
					Element cou = rowColElement.addElement("Cou").addText(
							String.valueOf(cellDatas.size()));

					for (int j = 1; j <= cellDatas.size(); j++) {
						Element cell = rowColElement.addElement("CL" + j);
						Element cellRow = cell.addElement("Row").addText(
								String.valueOf(intRowCol));

						if (this.isWriteByRow)
							mapKey = intRowCol + "&" + j;
						else
							mapKey = j + "&" + intRowCol;

						// 对象线即斜线设置
						mapValue = cellSlashStyleMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							pos = mapValue.indexOf("&");
							if (pos > 0) {
								slashStyle = mapValue.substring(0, pos);
								lineStyle = mapValue.substring(pos + 1);								
                                Element SPenId = cell.addElement("SPenId")
										.addText(lineStyle);
								Element slty = cell.addElement("slty").addText(
										slashStyle);
							}
						}

						// 公式设定
						mapValue = cellFormulaMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element f = cell.addElement("f").addText(mapValue);
						}

						// 是否合并单元格
						mapValue = cellCombiNationMap.get(mapKey);

						if (mapValue != null && mapValue.length() > 0) {
							pos = mapValue.indexOf("&");
							Element colsEle, rowsEle;
							if (pos == 0) {
								rows = mapValue.substring(pos + 1);
								rowsEle = cell.addElement("Rows").addText(rows);
							} else if (pos > 0) {
								cols = mapValue.substring(0, pos);
								rows = mapValue.substring(pos + 1);
								colsEle = cell.addElement("Cols").addText(cols);
								rowsEle = cell.addElement("Rows").addText(rows);
							} else
								colsEle = cell.addElement("Cols").addText(
										mapValue);
						}

						// 是否自动换行
						mapValue = cellAutoWrapMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element f1 = cell.addElement("fl")
									.addText(mapValue);
						}

						// 字体设置
						mapValue = cellFontStyleMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element ftId1 = cell.addElement("FtId").addText(
									mapValue);
						}
						// 背景色设置
						mapValue = cellBgColorMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element brId3 = cell.addElement("BrId").addText(
									mapValue);
						}
						// 水平对齐方式
						mapValue = cellHorzTextAlignMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element hAg = cell.addElement("hAg").addText(
									mapValue);
						}
						// 竖直对齐方式
						mapValue = cellVertTextAlignMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							Element vAg = cell.addElement("vAg").addText(
									mapValue);
						}
						// 单元显示格式
						mapValue = cellDigitShowStyleMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							pos = mapValue.indexOf("&");
							if (pos > 0) {
								showStyle = mapValue.substring(0, pos);
								digitPoint = mapValue.substring(pos + 1);
								Element swty = cell.addElement("swty").addText(
										showStyle);
								Element dpt = cell.addElement("dpt").addText(
										digitPoint);
							}
						}
						// 边框样式
						mapValue = cellBorderMap.get(mapKey);
						if (mapValue != null && mapValue.length() > 0) {
							if (mapValue != "no") {
								Element penT = cell.addElement("TPenId")
										.addText(mapValue);
								Element penB = cell.addElement("BPenId")
										.addText(mapValue);
								Element penL = cell.addElement("LPenId")
										.addText(mapValue);
								Element penR = cell.addElement("RPenId")
										.addText(mapValue);
							}
						}
						value = cellDatas.get(j - 1).toString();
						if (!value.equals("") && value != null) {
							Element cellT = cell.addElement("t").addText(value);
						}

					}
				}

			}

//			 int curRow;
//			 List data = null;
//			 ChinaExcelRow cer;
//			
//			 // 标题
//			 curRow = 1;
//			 if (datas != null && datas.size() >= 1)
//			 data = (List) datas.get(curRow - 1);
//			 else
//			 data = null;
//			 cer = new ChinaExcelRow(curRow, col, data,
//			 ChinaExcelRow.ROWTYPE_TITLE);
//			 cellList.add(cer.getRow());
			//
			// // Header
			// curRow = 2;
			// if (datas != null && datas.size() >= 2)
			// data = (List) datas.get(curRow - 1);
			// else
			// data = null;
			// cer = new ChinaExcelRow(curRow, cols, data,
			// ChinaExcelRow.ROWTYPE_HEADER);
			// cellList.add(cer.getRow());
			//
			// for (int i = 2; i < rows; i++) {
			// curRow = i + 1;
			// if (datas != null && datas.size() > i)
			// data = (List) datas.get(i);
			// else
			// data = null;
			// cer = new ChinaExcelRow(curRow, cols, data,
			// ChinaExcelRow.ROWTYPE_NORMAL);
			// cellList.add(cer.getRow());
			// }
		}
		return cellList;
	}

	/**
	 * 设置公用部分
	 */
	public Element getGeneral(int rows, int cols) {
		if (general == null) {
			general = new DOMElement("General");
			general.addElement("TotalRow").addText(String.valueOf(rows));
			general.addElement("TotalCol").addText(String.valueOf(cols));
			general.addElement("VerMajor").addText("2");
			general.addElement("VerMinor").addText("6");

			// 双击列头排序
			if (this.isDClickLabelSort)
				general.addElement("DClickLabelSort").addText("1");

			general.addElement("FixedRows").addText(
					String.valueOf(this.fixedRows));

			// 不显示grid和行列头
			if (!this.isShowGrid)
				general.addElement("ShowGrid").addText("0");
			if (!this.isShowHeader)
				general.addElement("ShowHeader").addText("0");
		}
		return general;
	}

	/**
	 * 设置打印部分
	 */
	public Element getPrint() {
		if (print == null) {
			print = new DOMElement("Print");
			print.addElement("lHeaderLogFont").addElement("CSet")
					.addText("134");
			print.addElement("cHeaderLogFont").addElement("CSet")
					.addText("134");
			print.addElement("rHeaderLogFont").addElement("CSet")
					.addText("134");
			print.addElement("lFtLogFont").addElement("CSet").addText("134");
			print.addElement("cFtLogFont").addElement("CSet").addText("134");
			print.addElement("rFtLogFont").addElement("CSet").addText("134");
		}
		return print;
	}

	/**
	 * 置单元格默认对齐方式为居中
	 */
	public Element getDefaultCell() {
		if (defaultCell == null) {
			defaultCell = new DOMElement("DefaultCell");
			defaultCell.addElement("hAg").addText(
					String.valueOf(this.defaultCellHAg));
			defaultCell.addElement("swty").addText(
					String.valueOf(this.defaultCellSwty));
			defaultCell.addElement("vAg").addText(
					String.valueOf(this.defaultCellVAg));
		}
		return defaultCell;
	}

	/**
	 * 设置背景色
	 */
	public Element getBrList() {
		if (brList == null) {
			brList = new DOMElement("BrList");
			brList.addElement("Cou").addText("2");
			Element obj0 = brList.addElement("Obj0");
			obj0.addElement("Color").addText("14803425");
			Element obj1 = brList.addElement("Obj1");
			obj1.addElement("Color").addText("255");
		}
		return brList;
	}

	/**
	 * 字体样式
	 */
	public Element getFtList() {
		if (ftList == null) {
			ftList = new DOMElement("FtList");
			ftList.addElement("Cou").addText("5");
			Element ftObj0 = ftList.addElement("Obj0");
			ftObj0.addElement("CSet").addText("134");

			Element ftObj1 = ftList.addElement("Obj1");
			ftObj1.addElement("CSet").addText("134");
			ftObj1.addElement("Hei").addText("-21");

			Element ftObj2 = ftList.addElement("Obj2");
			ftObj2.addElement("CSet").addText("134");
			ftObj2.addElement("Hei").addText("-21");
			ftObj2.addElement("Wei").addText("700");

			Element ftObj3 = ftList.addElement("Obj3");
			ftObj3.addElement("CSet").addText("134");
			ftObj3.addElement("Wei").addText("700");
			
			Element ftObj4 = ftList.addElement("Obj4");
			ftObj4.addElement("CSet").addText("134");
			ftObj4.addElement("FName").addText("宋体");
			ftObj4.addElement("Hei").addText("10");
			ftObj4.addElement("Wei").addText("700");			
		}
		return ftList;
	}

	/**
	 * 边框样式
	 */
	public Element getPenList() {
		if (penList == null) {
			penList = new DOMElement("PenList");
			penList.addElement("Cou").addText("1");
			Element penObj0 = penList.addElement("Obj0");
			penObj0.addElement("Color").addText("0");
			penObj0.addElement("Style").addText("0");
			Element penWid = penObj0.addElement("Wid");
			penWid.addElement("x").addText("1");
		}
		return penList;
	}

	/**
	 * 行高定义
	 */
	public Element getRowHeader() {
		if (rowHeader == null) {
			int rowCnt = cellRowSizeList.size();
			if (rowCnt == 0)
				return rowHeader;
			int pos;
			String row, height;
			String rowSizeStr = "";

			rowHeader = new DOMElement("RowHeader");
			rowHeader.addElement("Cou").addText(String.valueOf(rowCnt));
			for (int i = 0; i < rowCnt; i++) {
				rowSizeStr = cellRowSizeList.get(i).toString();
				pos = rowSizeStr.indexOf("&");
				if (pos > 0) {
					row = rowSizeStr.substring(0, pos);
					height = rowSizeStr.substring(pos + 1);
					Element rowObj = rowHeader.addElement("Obj" + i);
					rowObj.addElement("row").addText(row);
					rowObj.addElement("size").addText(height);
				}
			}
		}
		return rowHeader;
	}

	/**
	 * 列宽定义
	 */
	public Element getColHeader() {
		if (colHeader == null) {
			int colCnt = cellColSizeList.size();
			if (colCnt == 0)
				return colHeader;
			int pos;
			String col, width;
			String colSizeStr = "";

			colHeader = new DOMElement("ColHeader");
			colHeader.addElement("Cou").addText(String.valueOf(colCnt));
			for (int i = 0; i < colCnt; i++) {
				colSizeStr = cellColSizeList.get(i).toString();
				pos = colSizeStr.indexOf("&");
				if (pos > 0) {
					col = colSizeStr.substring(0, pos);
					width = colSizeStr.substring(pos + 1);
					Element rowObj = colHeader.addElement("Obj" + i);
					rowObj.addElement("row").addText(col);
					rowObj.addElement("size").addText(width);
				}
			}
		}
		return colHeader;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List> datas = new ArrayList<List>();
		List<String> data1 = new ArrayList<String>();
		data1.add("成绩分段统计表");
		datas.add(data1);

		List<String> data2 = new ArrayList<String>();
		data2.add("班级");
		data2.add("人数");
		data2.add("最高分");
		data2.add("最低分");
		data2.add("60以下");
		datas.add(data2);

		ChinaExcel ce = new ChinaExcel();
		
		
		try {

			// 根据已有的数据生成xml文件
			ce.setCellRowSize(1, 1, 65); // 设置第一行行高为65
			ce.setDefaultCellHAg(6); // 默认居中
			ce.setCellBorder(2, 1, 2, 5, "0"); // 对应penList中的obj0，设置第二行加四边的边框线
			ce.setCellBgColor(2, 1, 2, 5, 0); // 对应brList中的obj0,设置第二行的背景色
			ce.setCellFontStyle(2, 1, 2, 5, 3);
			ce.setCellFontStyle(1, 1, 1, 5, 2);
			String xmlData = ce.genChinaExcelXmlData(2, 5, datas);
			System.out.println(xmlData);

			// for (short i = 1;i < 11;i++){
			// System.out.println((char)(i - 1 + 'A'));
			// }
		} catch (Exception ex) {
			System.out.println("生成规定格式的XML数据文件出错！");
		}

	}

}
