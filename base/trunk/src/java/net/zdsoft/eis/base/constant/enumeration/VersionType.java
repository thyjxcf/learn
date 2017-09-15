package net.zdsoft.eis.base.constant.enumeration;

import org.apache.commons.lang.StringUtils;

public enum VersionType {
    EIS("中小学") {
        @Override
        public boolean match(String productId) {
            if (StringUtils.equalsIgnoreCase("eis-e", productId)
                    || StringUtils.equalsIgnoreCase("eis-s", productId)
                    || StringUtils.equalsIgnoreCase("eis", productId)
                    || StringUtils.equalsIgnoreCase("eis-e6", productId))
                return true;
            else
                return false;
        }
    },
    EISU("中职") {

        @Override
        public boolean match(String productId) {
            if (StringUtils.equalsIgnoreCase("eisu-e", productId)
                    || StringUtils.equalsIgnoreCase("eisu-s", productId)
                    || StringUtils.equalsIgnoreCase("eisu", productId))
                return true;
            else
                return false;
        }
    };

    private String vname;

    public static VersionType getVersionType(String productId) {
        for (VersionType version : values()) {
            if (version.match(productId))
                return version;
        }
        return null;
    }

    private VersionType(String vname) {
        this.vname = vname;
    }

    public abstract boolean match(String productId);

    public String getVname() {
        return vname;
    }
}
