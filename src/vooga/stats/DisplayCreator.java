package vooga.stats;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.golden.gamedev.object.SpriteGroup;

/**
 * Create display from either XML source or resource bundle source
 * @author Chao Chen
 *
 */

public class DisplayCreator
{
    private static DisplayTracker myDisplayTracker;
    private static Map<String, String> myClassMap;
    protected static int myDisplayCount;
    private static int myDisplayGroupCount;

    /**
     * 
     * @param xmlFileLocation
     *            - the XML File Location. String xmlFileLocation must be path
     *            from the root of the project
     * @return OverlayTracker containing all stats and overlays.
     */
    public static DisplayTracker createOverlays(String xmlLocation) {
        myDisplayTracker = new DisplayTracker();
        initializeClassMap();
        myDisplayCount = 0;
        myDisplayGroupCount = 0;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(xmlLocation));

            NodeList statList = doc.getElementsByTagName("Stat");
            NodeList displayList = doc.getElementsByTagName("DisplayGroup");
            if (!processStats(statList)) return null;
            if (!processDisplays(displayList)) return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return myDisplayTracker;
    }

    private static void initializeClassMap() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/vooga/stats/resources/statsDisplay.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> set = props.stringPropertyNames();
        myClassMap = new HashMap<String, String>();
        for (String str : set) {
            myClassMap.put(str, props.getProperty(str));
        }
    }
    
    private static boolean processStats(NodeList statList) {
        for (int s = 0; s < statList.getLength(); s++) {
            Node stat = statList.item(s);
            if (isElement(stat)) {
                Element element = (Element) stat;
                String type = element.getAttribute("type");
                String value = element.getAttribute("value");
                String name = element.getAttribute("name");
                if(name.equals("")){
                    name = "Statistic " + s + 1;
                }
                if (type.toLowerCase().equals("integer")) {
                    myDisplayTracker.putStat(name , new NumStat(Integer.valueOf(value)));
                } else if (type.toLowerCase().equals("string")) {
                    myDisplayTracker.putStat(name , new StringStat<String>(value));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean processDisplays(NodeList overlayList) {
        Set<String> classSet = myClassMap.keySet();
        for (int s = 0; s < overlayList.getLength(); s++) {
            SpriteGroup tempGroup = new SpriteGroup("group" + s);
            Node group = overlayList.item(s);
            if (isElement(group)) {
                Element eleGroup = (Element) group;
                myDisplayGroupCount++;
                for (String tagName : classSet) {
                    NodeList currentDisplays = eleGroup.getElementsByTagName(tagName);
                    for (int i = 0; i < currentDisplays.getLength(); i++) {
                        Node display = currentDisplays.item(i);
                        if (isElement(display)) {
                            Element eleDisplay = (Element) display;
                            tempGroup.add(createDisplay(eleDisplay, tagName));
                        }
                    }
                }
                String name = eleGroup.getAttribute("name");
                if (name == null) {
                    name = "DisplaylayGroup " + myDisplayGroupCount;
                }
                myDisplayTracker.putStatsGroup(name, tempGroup);
            } else {
                return false;
            }
        }
        return true;
    }

    private static Display createDisplay(Element display, String type) {
        try {
            myDisplayCount++;
            Class<?> cls = Class.forName(myClassMap.get(type));
            NamedNodeMap nNM = display.getAttributes();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < nNM.getLength(); i++) {
                Attr temp = (Attr) nNM.item(i);
                String name = temp.getName();
                map.put(name, display.getAttribute(name));
            }
            Class partypes[] = { Map.class, DisplayTracker.class };
            Object arglist[] = { map, myDisplayTracker };
            Constructor ct = cls.getConstructor(partypes);
            Display current = (Display) ct.newInstance(arglist);
            String name = map.get("name");
            if (name == null) {
                name = "Display " + myDisplayCount;
            }
            myDisplayTracker.putDisplays(name, current);
            return current;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static boolean isElement(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

    /**
     * Converts a string into a color.
     */
    protected static Color stringToColor(String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            try {
                Field f = Color.class.getField(value);
                return (Color) f.get(null);
            } catch (Exception ce) {
                return Color.black;
            }
        }
    }

    protected static int stringToFontStyle(String style) {
        if (style == null) {
            return Font.PLAIN;
        }
        try {
            Field f = Font.class.getField(style);
            return f.getInt(null);
        } catch (Exception ce) {
            return Font.PLAIN;
        }
    }
}