package net.zdsoft.keelcnet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import net.zdsoft.keelcnet.config.BootstrapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.core.util.XMLUtils;
import com.opensymphony.util.TextUtils;

/**
 * General Utilities, global function included.
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: GeneralUtil.java,v 1.2 2007/01/10 03:53:02 liangxiao Exp $
 */
public final class GeneralUtil {
    private static final Logger log = LoggerFactory.getLogger(GeneralUtil.class);
    private static String DEFAULT_FORMATTING_PROPERTIES_FILE_NAME = "default-formatting.properties";
    private static DecimalFormat defaultDecimalNumberFormatter;
    private static DecimalFormat defaultLongNumberFormatter;
    private static SimpleDateFormat defaultDateFormatter;
    private static SimpleDateFormat defaultDateTimeFormatter;
    private static SimpleDateFormat defaultTimeFormatter;
    private static Properties formattingProperties = new Properties();

    static {
        loadDefaultProperties();

        try {
            saveDefaultFormattingPropertiesFile();
        }
        catch (IOException e) {
            log
                    .error("Error while trying to store the default formatting properties!"
                            + e.toString());
        }
    }

    private GeneralUtil() {
    }

    public static void loadDefaultProperties() {
        try {
            File defaultFormattingPropertiesFile = new File(BootstrapManager
                    .getStoreHome(), DEFAULT_FORMATTING_PROPERTIES_FILE_NAME);

            if (defaultFormattingPropertiesFile.isFile()) {
                formattingProperties.load(new FileInputStream(
                        defaultFormattingPropertiesFile));
            }

            setDefaultDecimalNumberFormatterPattern(getDefaultDecimalNumberFormatterPattern());
            setDefaultLongNumberFormatterPattern(getDefaultLongNumberFormatterPattern());
            setDefaultDateFormatterPattern(getDefaultDateFormatterPattern());
            setDefaultDateTimeFormatterPattern(getDefaultDateTimeFormatterPattern());
            setDefaultTimeFormatterPattern(getDefaultTimeFormatterPattern());
        }
        catch (Exception e) {
            log
                    .error("Error while trying to load the object formatting properties!"
                            + e.toString());
        }
    }

    public static Date convertToDateWithChinaLocale(String buildDateString) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        formatter.setLenient(false);

        Date date = null;

        try {
            date = formatter.parse(buildDateString.toString());
        }
        catch (ParseException e) {
            log.info("Could not parse : " + buildDateString + " : "
                    + e.toString());
        }

        return date;
    }

    public static void saveDefaultFormattingPropertiesFile() throws IOException {
        File file = new File(BootstrapManager.getStoreHome(),
                DEFAULT_FORMATTING_PROPERTIES_FILE_NAME);

        if (!file.isFile() && (file.exists() == false)) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileOutputStream out = new FileOutputStream(file);
        formattingProperties.store(out, null);
    }

    public static String getDefaultTimeFormatterPattern() {
        return formattingProperties.getProperty("time.format", "HH:mm:ss");
    }

    public static void setDefaultTimeFormatterPattern(
            String defaultTimeFormatterPattern) {
        formattingProperties.setProperty("time.format",
                defaultTimeFormatterPattern);
        defaultTimeFormatter = (SimpleDateFormat) createDateFormatter(defaultTimeFormatterPattern);
    }

    public static String getDefaultDateTimeFormatterPattern() {
        return formattingProperties.getProperty("datetime.format",
                "yyyy-MMM-dd HH:mm");
    }

    public static void setDefaultDateTimeFormatterPattern(
            String defaultDateTimeFormatterPattern) {
        formattingProperties.setProperty("datetime.format",
                defaultDateTimeFormatterPattern);
        defaultDateTimeFormatter = (SimpleDateFormat) createDateFormatter(defaultDateTimeFormatterPattern);
    }

    public static String getDefaultDateFormatterPattern() {
        return formattingProperties.getProperty("date.format", "yyyy-MMM-dd");
    }

    public static void setDefaultDateFormatterPattern(
            String defaultDateFormatterPattern) {
        formattingProperties.setProperty("date.format",
                defaultDateFormatterPattern);
        defaultDateFormatter = (SimpleDateFormat) createDateFormatter(defaultDateFormatterPattern);
    }

    public static String getDefaultLongNumberFormatterPattern() {
        return formattingProperties.getProperty("long.number.format",
                "###############");
    }

    public static void setDefaultLongNumberFormatterPattern(
            String defaultLongNumberFormatterPattern) {
        formattingProperties.setProperty("long.number.format",
                defaultLongNumberFormatterPattern);
        defaultLongNumberFormatter = new DecimalFormat(
                defaultLongNumberFormatterPattern);
    }

    public static String getDefaultDecimalNumberFormatterPattern() {
        return formattingProperties.getProperty("decimal.number.format",
                "###############.##########");
    }

    public static void setDefaultDecimalNumberFormatterPattern(
            String defaultDecimalNumberFormatterPattern) {
        formattingProperties.getProperty("decimal.number.format",
                defaultDecimalNumberFormatterPattern);
        defaultDecimalNumberFormatter = new DecimalFormat(
                defaultDecimalNumberFormatterPattern);
    }

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return "";
        }
        else {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));

            return sw.toString();
        }
    }

    public static String format(Number num) {
        if (!(num instanceof Double) && !(num instanceof BigDecimal)
                && !(num instanceof Float)) {
            return null;
        }

        try {
            return defaultDecimalNumberFormatter.format(num);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return defaultLongNumberFormatter.format(num);
    }

    public static String format(Date date) {
        try {
            return defaultDateFormatter.format(date);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String format(String str) {
        return (str == null) ? "" : str;
    }

    public static String format(Object obj) {
        try {
            if (obj instanceof Number) {
                return format((Number) obj);
            }

            if (obj instanceof Date) {
                return format((Date) obj);
            }

            if (obj instanceof String) {
                return format((String) obj);
            }

            return obj.toString();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String formatDateTime(Date date) {
        try {
            return defaultDateTimeFormatter.format(date);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String formatTime(Date date) {
        try {
            return defaultTimeFormatter.format(date);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Date convertToDate(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }

        Date date = null;

        if (date == null) {
            try {
                date = defaultDateFormatter.parse(obj.toString());
            }
            catch (ParseException e) {
                log.info("Could not parse : " + obj + " : " + e, e);
            }
        }

        return date;
    }

    public static Long convertToLong(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        }

        try {
            return new Long(defaultLongNumberFormatter.parse(obj.toString())
                    .longValue());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Character convertToCharacter(Object obj) {
        if (obj instanceof Character) {
            return (Character) obj;
        }

        try {
            return new Character(obj.toString().charAt(0));
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BigDecimal convertToBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }

        try {
            return new BigDecimal(defaultDecimalNumberFormatter.parse(
                    obj.toString()).doubleValue());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Double convertToDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }

        try {
            return new Double(defaultDecimalNumberFormatter.parse(
                    obj.toString()).doubleValue());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer convertToInteger(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }

        try {
            return new Integer(defaultLongNumberFormatter.parse(obj.toString())
                    .intValue());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Boolean convertToBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }

        try {
            return new Boolean(obj.toString());
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertToString(Object obj) {
        String result;
        result = obj.toString();

        if (result.equals("")) {
            result = null;
        }

        return result;
    }

    private static DateFormat createDateFormatter(String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        return formatter;
    }

    public static String urlEncode(String url) {
        if (url == null) {
            return null;
        }

        try {
            return URLEncoder.encode(url, BootstrapManager
                    .getEncoding());
        }
        catch (UnsupportedEncodingException e) {
            log.error("Error while trying to encode the URL!", e);

            return url;
        }
    }

    public static String urlDecode(String url) {
        if (url == null) {
            return null;
        }

        try {
            return URLDecoder.decode(url, BootstrapManager
                    .getEncoding());
        }
        catch (UnsupportedEncodingException e) {
            log.error("Error while trying to decode the URL!", e);

            return url;
        }
    }

    public static String summarise(String content) {
        if (!TextUtils.stringSet(content)) {
            return content;
        }

        content = content.replaceAll("h[0-9]\\.", "");
        content = content.replaceAll("[\\[\\]\\*_\\^\\-\\~\\+\\n]", "");
        content = content.replaceAll(
                "\\{([^:\\}\\{]+)(?::([^\\}\\{]*))?\\}(?!\\})", "");
        content = content.replaceAll("\\n", "");
        content = content.replaceAll("\\r", "");

        if (content.length() > 255) {
            return TextUtils.trimToEndingChar(content, 251) + "...";
        }
        else {
            return content;
        }
    }

    public static String doubleUrlEncode(String s) {
        return urlEncode(urlEncode(s));
    }

    public static boolean isAllAscii(String s) {
        char[] sChars = s.toCharArray();

        for (int i = 0; i < sChars.length; i++) {
            char sChar = sChars[i];

            if (sChar > '\177') {
                return false;
            }
        }

        return true;
    }

    public static boolean isAllLettersOrNumbers(String s) {
        char[] sChars = s.toCharArray();

        for (int i = 0; i < sChars.length; i++) {
            char sChar = sChars[i];

            if (!Character.isLetterOrDigit(sChar)) {
                return false;
            }
        }

        return true;
    }

    // public static String getVersionNumber() {
    // return CFVersion.getVersion();
    // }
    //
    // public static Date getBuildDate() {
    // return CFVersion.getBuildDate();
    // }
    // public static String getBuildDateString() {
    // return format(getBuildDate());
    // }
    // public static String getBuildNumber() {
    // return CFVersion.getBuildNumber();
    // }

    public static boolean stringSet(String str) {
        return (str != null) && (str.length() > 0);
    }

    public static String formatLongTime(long time) {
        StringBuffer result = new StringBuffer();

        if (time > 0x36ee80L) {
            time = scaleTime(time, 0x36ee80L, result);
            result.append(":");
        }

        time = scaleTime(time, 60000L, result);
        result.append(":");
        time = scaleTime(time, 1000L, result);
        result.append(".").append(time);

        return result.toString();
    }

    private static long scaleTime(long time, long scale, StringBuffer buf) {
        long report = time / scale;
        time -= (report * scale);

        String result = Long.toString(report);

        if (report < 10L) {
            result = "0" + result;
        }

        buf.append(result);

        return time;
    }

    public static String formatDateFull(Date date) {
        return DateFormat.getDateInstance(0).format(date);
    }

    public static String getCharacterEncoding() {
        return BootstrapManager.getEncoding();
    }

    public static String escapeXml(String stringToEscape) {
        return XMLUtils.escape(stringToEscape);
    }

    public static String maskEmail(String emailAddress) {
        StringBuffer buf = new StringBuffer(emailAddress.length() + 20);

        for (int i = 0; i < emailAddress.length(); i++) {
            char c = emailAddress.charAt(i);

            if (c == '.') {
                buf.append(" dot ");

                continue;
            }

            if (c == '@') {
                buf.append(" at ");
            }
            else {
                buf.append(c);
            }
        }

        return buf.toString();
    }

    public static String escapeCDATA(String s) {
        if (s.indexOf("]]") < 0) {
            return s;
        }
        else {
            return s.replaceAll("\\]\\]", "]] ");
        }
    }

    public static String unescapeCDATA(String s) {
        if (s.indexOf("]] ") < 0) {
            return s;
        }
        else {
            return s.replaceAll("\\]\\] ", "]]");
        }
    }
}
