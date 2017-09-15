package net.zdsoft.leadin.util;

import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;


/*
 * @author Brave Tao
 * @since 2004-11-26
 * @version $Id: RegUtils.java,v 1.1 2006/10/08 09:46:47 chenzy Exp $
 * @since
 */
public abstract class RegUtils {
    private static final Logger LOG = LoggerFactory.getLogger(RegUtils.class);
    public static final PatternCompiler COMPILER = new Perl5Compiler();
    public static final PatternMatcher MATCHER = new Perl5Matcher();

    public synchronized static String[] extract(String source, String regPattern) {
        //解析
        Pattern p;

        //解析archive
        try {
            p = COMPILER.compile(regPattern);

            if (MATCHER.contains(source, p)) {
                MatchResult result = MATCHER.getMatch();
                int count = result.groups();
                String[] results = new String[count];

                for (int i = 0; i < count; i++) {
                    results[i] = result.group(i);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug(results[i]);
                    }
                }

                return results;
            }
        } catch (MalformedPatternException e) {
            //ignore
        }

        return null;
    }

    /**
     * 验证str字符串，是否符合expression正则表达式
     * 
     * @param expression
     * @param str
     * @return
     */
    public static boolean regexValidator(String expression, String str) {
    	if (expression == null || str == null) {
    		return true;
    	}
    	java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(expression);
    	Matcher matcher = pattern.matcher(str);
    	return matcher.matches();
    }
}
