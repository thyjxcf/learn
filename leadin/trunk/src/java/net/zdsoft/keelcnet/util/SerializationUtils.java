package net.zdsoft.keelcnet.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keelcnet.exception.InfrastructureException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * @author taoy
 * @since 1.0
 * @version $Id: SerializationUtils.java,v 1.1 2006/12/30 08:51:36 liangxiao Exp $
 */
public abstract class SerializationUtils {
    public static final Object fromBase64(String str) {
        if (str == null) {
            return null;
        }

        Object obj = null;
        ByteArrayInputStream in = null;
        try {
            BASE64Decoder dec = new BASE64Decoder();
            byte[] bytes = dec.decodeBuffer(str);

            in = new ByteArrayInputStream(bytes);
            obj = new ObjectInputStream(in).readObject();
        }
        catch (Exception exc) {
            throw new InfrastructureException(exc.toString(), exc);
        }
        finally {
            FileUtils.close(in);
        }

        return obj;
    }

    public static final String toBase64(Serializable obj) {
        if (obj == null) {
            return null;
        }

        String res = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            new ObjectOutputStream(out).writeObject(obj);
            byte[] bytes = out.toByteArray();

            if ((bytes != null) && (bytes.length > 0)) {
                BASE64Encoder b64e = new BASE64Encoder();
                res = new String(b64e.encodeBuffer(bytes));
            }
        }
        catch (Exception exc) {
            throw new InfrastructureException(exc.toString(), exc);
        }
        finally {
            FileUtils.close(out);
        }

        return res;
    }

}
