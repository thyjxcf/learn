package net.zdsoft.keelcnet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 
 * @author taoy
 * @since 1.0
 * @version $Id: XMLUtils.java,v 1.1 2006/12/30 07:15:39 jiangl Exp $
 */
public class XMLUtils {
    private static final Logger LOG = LoggerFactory.getLogger(XMLUtils.class);
    private static final String IND_STRING = "    ";

    public XMLUtils() {
    }

    public static String concatContents(Element node) {
        StringBuffer contents = new StringBuffer();
        NodeList name_content = node.getChildNodes();

        for (int j = 0; j < name_content.getLength(); j++) {
            Node content_item = name_content.item(j);

            if (((content_item.getNodeType() == 3) || (content_item
                    .getNodeType() == 4))
                    && (((Text) content_item).getData() != null)) {
                contents.append(((Text) content_item).getData());
            }
        }

        return contents.toString();
    }

    public static String makeIndent(int level) {
        String result = "";

        for (int i = 0; i < level; i++)
            result = result + IND_STRING;

        return result;
    }

    public static boolean isCDATA(Element node) {
//        StringBuffer contents = new StringBuffer();
        NodeList name_content = node.getChildNodes();

        for (int j = 0; j < name_content.getLength(); j++) {
            Node content_item = name_content.item(j);

            if (content_item.getNodeType() == 4) {
                return true;
            }
        }

        return false;
    }

    public static final String getDefaultText(Element el) {
        String result = null;
        NodeList nodeList = el.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() != 3) {
                continue;
            }

            String data = ((Text) node).getData();

            if (data == null) {
                continue;
            }

            result = data;

            break;
        }

        return result;
    }

    public static final String[] getValuesForNodes(Element rootEl,
            String[] nodeNames) {
        String[] result = new String[nodeNames.length];
        NodeList nodeList = rootEl.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == 1) {
                Element el = (Element) node;
                String tagName = el.getTagName();

                for (int j = 0; j < nodeNames.length; j++)
                    if (tagName.equals(nodeNames[j])) {
                        result[j] = getDefaultText(el);
                    }
            }
        }

        return result;
    }

    public static final Element findChildNode(Element rootEl, String nodeName) {
        Element result = null;
        NodeList nodeList = rootEl.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() != 1) {
                continue;
            }

            Element el = (Element) node;
            String tagName = el.getTagName();

            if (!tagName.equals(nodeName)) {
                continue;
            }

            result = el;

            break;
        }

        return result;
    }

    public static final String[] getMultValuesForNode(Element rootEl,
            String nodeName) {
        Collection<String> result = new ArrayList<String>();
        NodeList nodeList = rootEl.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == 1) {
                Element el = (Element) node;
                String tagName = el.getTagName();

                if (tagName.equals(nodeName)) {
                    result.add(getDefaultText(el));
                }
            }
        }

        String[] tmp = new String[result.size()];

        return (result.size() <= 0) ? null : (String[]) result.toArray(tmp);
    }

    public static final String[][] getValuesForNode(Element rootEl,
            String nodeName, String[] nodeAttributes) {
        Collection<String[]> result = new ArrayList<String[]>();
        NodeList nodeList = rootEl.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == 1) {
                Element el = (Element) node;
                String tagName = el.getTagName();

                if (tagName.equals(nodeName)) {
                    String[] values = new String[1 + nodeAttributes.length];
                    values[0] = getDefaultText(el);

                    for (int j = 0; j < nodeAttributes.length; j++)
                        values[j + 1] = getAttributeText(el, nodeAttributes[j]);

                    result.add(values);
                }
            }
        }

        return (result.size() <= 0) ? null : (String[][]) result
                .toArray(new String[0][0]);
    }

    public static final Map<String, List<String>> getBodiesOfTag(Element rootEl, Map<String, String> tag_names) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        NodeList nodeList = rootEl.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == 1) {
                Element el = (Element) node;
                String tagName = el.getTagName();

                if (tag_names.containsKey(tagName)) {
                    List<String> allvalues = (ArrayList<String>) result.get(tagName);

                    if (allvalues == null) {
                        allvalues = new ArrayList<String>();
                        result.put(tagName, allvalues);
                    }

                    allvalues.add(concatContents(el));
                }
            }
        }

        return result;
    }

    public static final String getAttributeText(Element el, String attributeName) {
        String result = null;
        Attr attr = el.getAttributeNode(attributeName);

        if (attr != null) {
            result = attr.getValue();
        }

        return result;
    }

    public static final Element getDocumentElement(InputStream in) {
        Element result = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setCoalescing(true);
        factory.setValidating(false);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);
            result = doc.getDocumentElement();
        }
        catch (Exception e) {
            LOG.error(e.toString());
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                LOG.error(ex.toString());
            }
        }

        return result;
    }

    public static final Element getDocumentElement(ServletContext ctx,
            String fileName) {
        try {
            @SuppressWarnings("unused")
            java.net.URL url = ctx.getResource(fileName);

            return getDocumentElement(ctx.getResourceAsStream(fileName));
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    public static final Element getDocumentElement(String fileName) {
        Element result = null;
        File file = new File(fileName);

        if (file.exists()) {
            try {
                InputStream fis = new FileInputStream(file);
                result = getDocumentElement(fis);
            }
            catch (FileNotFoundException e) {
                // ignore
            }
        }

        return result;
    }
}
