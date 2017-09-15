package net.zdsoft.eis.base.remote.exception;

import net.zdsoft.eis.base.remote.dto.UnitRegisterResultDto;

public class UnitRegisterException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -7613708244093519904L;
    private UnitRegisterResultDto unitRegisterResultDto;

    public UnitRegisterException() {
        super();
    }

    public UnitRegisterException(String message) {
        super(message);
    }

    public UnitRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnitRegisterException(Throwable cause) {
        super(cause);
    }

    public UnitRegisterException(UnitRegisterResultDto unitRegisterResultDto) {
        super();
        this.unitRegisterResultDto = unitRegisterResultDto;
    }

    public UnitRegisterResultDto getUnitRegisterResultDto() {
        return unitRegisterResultDto;
    }

    public void setUnitRegisterResultDto(
            UnitRegisterResultDto userRegisterResultDto) {
        this.unitRegisterResultDto = userRegisterResultDto;
    }

}
