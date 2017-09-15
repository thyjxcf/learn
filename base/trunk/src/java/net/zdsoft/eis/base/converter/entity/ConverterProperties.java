package net.zdsoft.eis.base.converter.entity;import java.util.Properties;public class ConverterProperties {	/**	 * 名称	 */	private String name;	/**	 * 预览地址	 */	private String viewUrl;	/**	 * 文档截图的缩略图尺寸	 */	private String documentPreviewWidth;	private String documentPreviewHeight;	/**	 * 视频截图的缩略图尺寸	 */	private String vidoePreviewWidth;	private String vidoePreviewHeight;	/**	 * 表名	 */	private String tableName;	public ConverterProperties(Properties properties) {		super();		this.name = properties.getProperty("name");		this.viewUrl = properties.getProperty("view.url");		this.documentPreviewWidth = properties.getProperty("document.previewWidth");		this.documentPreviewHeight = properties.getProperty("document.previewHeight");		this.vidoePreviewWidth = properties.getProperty("vidoe.previewWidth");		this.vidoePreviewHeight = properties.getProperty("vidoe.previewHeight");		this.tableName = properties.getProperty("table.name");	}	public Integer getDocumentPreviewWidth() {		return Integer.valueOf(documentPreviewWidth);	}	public void setDocumentPreviewWidth(String documentPreviewWidth) {		this.documentPreviewWidth = documentPreviewWidth;	}	public Integer getDocumentPreviewHeight() {		return Integer.valueOf(documentPreviewHeight);	}	public void setDocumentPreviewHeight(String documentPreviewHeight) {		this.documentPreviewHeight = documentPreviewHeight;	}	public Integer getVidoePreviewWidth() {		return Integer.valueOf(vidoePreviewWidth);	}	public void setVidoePreviewWidth(String vidoePreviewWidth) {		this.vidoePreviewWidth = vidoePreviewWidth;	}	public Integer getVidoePreviewHeight() {		return Integer.valueOf(vidoePreviewHeight);	}	public void setVidoePreviewHeight(String vidoePreviewHeight) {		this.vidoePreviewHeight = vidoePreviewHeight;	}	public String getTableName() {		return tableName;	}	public void setTableName(String tableName) {		this.tableName = tableName;	}	public String getName() {		return name;	}	public void setName(String name) {		this.name = name;	}	public String getViewUrl() {		return viewUrl;	}	public void setViewUrl(String viewUrl) {		this.viewUrl = viewUrl;	}}