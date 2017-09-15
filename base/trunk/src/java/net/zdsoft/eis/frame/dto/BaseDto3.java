package net.zdsoft.eis.frame.dto;

import java.io.Serializable;

/**
 * @author luxingmu
 * @version $Revision: 1.2 $, $Date: 2007/01/09 10:03:05 $
 */
public class BaseDto3 implements Serializable {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6224288049997549378L;
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
