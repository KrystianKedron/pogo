//+======================================================================
//
// Project:   Tango
//
// Description:	java source code for utilities
//
// $Author: verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2009,2010,2011,2012,2013
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: $
// $Date:  $
//
// $HeadURL: $
//
//-======================================================================

package org.tango.pogo.pogo_gui.tools;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;
import fr.esrf.tango.pogo.pogoDsl.*;
import fr.esrf.tangoatk.widget.util.ErrorPane;
import fr.esrf.tangoatk.widget.util.JSmoothProgressBar;
import fr.esrf.tangoatk.widget.util.Splash;
import org.eclipse.emf.common.util.EList;
import org.tango.pogo.pogo_gui.PogoConst;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.ArrayList;


public class Utils {
    private static Utils instance = null;

    private static ImageIcon tango_icon = null;
    public ImageIcon root_icon;
    public ImageIcon class_icon;
    public ImageIcon collec_icon;
    public ImageIcon classprop_icon;
    public ImageIcon cmd_icon;
    public ImageIcon cmd_exp_icon;
    public ImageIcon scalar_icon;
    public ImageIcon scalar_exp_icon;
    public ImageIcon spectrum_icon;
    public ImageIcon spectrum_exp_icon;
    public ImageIcon image_icon;
    public ImageIcon image_exp_icon;
    public ImageIcon devprop_icon;
    public ImageIcon state_icon;
    public ImageIcon inherite_icon;
    public ImageIcon remove_icon;

    public ImageIcon abstract_icon;
    public ImageIcon inherited_icon;
    public ImageIcon overloaded_icon;
    public ImageIcon unknown_icon;

    public ImageIcon reload_icon;
    public ImageIcon new_icon;
    public ImageIcon open_icon;
    public ImageIcon save_icon;

    public ImageIcon cppLogo;
    public ImageIcon javaLogo;
    public ImageIcon pythonLogo;

    //===============================================================
    //===============================================================
    private Utils() {
        root_icon = getIcon("TangoSplash.jpg", 0.2);
        class_icon = getIcon("TangoClass.gif", 0.4);
        collec_icon = getIcon("tools.gif");
        classprop_icon = getIcon("class.gif");
        cmd_icon = getIcon("command.gif");
        cmd_exp_icon = getIcon("command_expert.gif");
        scalar_icon = getIcon("scalar.gif");
        scalar_exp_icon = getIcon("scalar_expert.gif");
        spectrum_icon = getIcon("spectrum.gif");
        spectrum_exp_icon = getIcon("spectrum_expert.gif");
        image_icon = getIcon("image.gif", 0.25);
        image_exp_icon = getIcon("image_expert.gif", 0.25);
        devprop_icon = getIcon("device.gif");
        state_icon = getIcon("state.gif");
        inherite_icon = getIcon("inherite.gif", 0.5);
        remove_icon = getIcon("remove.gif");

        abstract_icon = getIcon("abstract.gif");
        inherited_icon = getIcon("inherited.gif");
        overloaded_icon = getIcon("concrete.gif");
        unknown_icon = getIcon("greyball.gif");

        reload_icon = getIcon("reload_icon.gif", 0.8);
        new_icon = getIcon("new_icon.gif", 1.0);
        open_icon = getIcon("open_icon.gif", 1.0);
        save_icon = getIcon("save_icon.gif", 1.0);

        cppLogo    = getIcon("CppLogo.gif",    0.66);
        javaLogo   = getIcon("JavaLogo.gif",   0.12);
        pythonLogo = getIcon("PythonLogo.gif", 0.33);

        //  Initialize pogoGuiRevision
        getPogoGuiRevision();
    }

    //===============================================================
    //===============================================================
    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();
        return instance;
    }

    //===============================================================
    //===============================================================
    private static double   pogoGuiRevision = -1.0;
    public static double getPogoGuiRevision() {
        
        //  Check if already done
        if (pogoGuiRevision<0) {
           StringTokenizer stk = new StringTokenizer(PogoConst.revNumber);
           String  s = stk.nextToken();    //  Rel number
            int end = s.indexOf('.');
            if (end>0) {
                //  Check if second '.'
                end = s.indexOf('.', end+1);
                if (end>0) {
                    s = s.substring(0, end);
               }
                try {
                    pogoGuiRevision = Double.parseDouble(s);
                }
                catch (NumberFormatException e) {
                    System.err.println("When trying to get PogoGuiRevision :\n" + e);
                }
            }
            System.out.println("********* Pogo GUI Release : " +
                    pogoGuiRevision + ".x *********");
        }
        return pogoGuiRevision;
    }
    //===============================================================
    //===============================================================
    public ImageIcon getIcon(String filename, double ratio) {
        ImageIcon icon = getIcon(filename);
        return getIcon(icon, ratio);
    }

    //===============================================================
    //===============================================================
    public ImageIcon getIcon(ImageIcon icon, double ratio) {
        if (icon != null) {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            width = (int) (ratio * width);
            height = (int) (ratio * height);

            icon = new ImageIcon(
                    icon.getImage().getScaledInstance(
                            width, height, Image.SCALE_SMOOTH));
        }
        return icon;
    }

    //===============================================================
    //===============================================================
    public java.net.URL getImageUrl(String filename) {
        return getClass().getResource("/org/tango/pogo/pogo_gui/img/" + filename);
    }

    //===============================================================
    //===============================================================
    public ImageIcon getIcon(String filename) {
        java.net.URL url = getImageUrl(filename);
        if (url == null) {
            System.err.println("Icon file  " + filename + "  not found");
            return null;
        }
        return new ImageIcon(url);
    }

    //===============================================================
    //===============================================================
    public static String getRelativeFilename(String absFilename) {
        String relativeFilename = absFilename;
        int pos = absFilename.lastIndexOf(
                System.getProperty("file.separator"));
        if (pos > 0)
            relativeFilename = absFilename.substring(pos + 1);
        return relativeFilename;
    }

    //===============================================================
    //===============================================================
    public static String getPath(String filename) {
        String separator = System.getProperty("file.separator");
        int pos = filename.lastIndexOf(separator);
        String path = "./";
        if (pos > 0)
            path = filename.substring(0, pos);
        return path;
    }

    //============================================================================
    //============================================================================
    public static int getLanguage(String lang) {
        for (int i = 0; i < PogoConst.strLang.length; i++)
            if (lang.toLowerCase().equals(PogoConst.strLang[i].toLowerCase()))
                return i;
        return -1;
    }

    //============================================================================
    //============================================================================
    public static String getLanguage(int lang) {
        return PogoConst.strLang[lang];
    }

    //============================================================================
    //============================================================================
    public static String getFileExtension(String lang) {
        return PogoConst.fileExtention[getLanguage(lang)];
    }
    //============================================================================

    /**
     * Build the execute method's name from command's name
     *
     * @param commandName the conmand specified name.
     * @return The method's name built.
     */
    //========================================================================
    public static String buildCppExecuteMethodName(String commandName) {
        //	Special case for State and Status cmd
        if (commandName.equals("State"))
            return "dev_state";
        if (commandName.equals("Status"))
            return "dev_status";

        //	Else replace upper case by '_' char and lowcase
        //---------------------------------------------------
        String str = "";
        for (int i = 0; i < commandName.length(); i++) {
            if (commandName.charAt(i) >= 'A' && commandName.charAt(i) <= 'Z')//	if upperr case
            {
                if (i > 0)
                    //	Check if previous char is not an upper case too
                    if (commandName.charAt(i - 1) < 'A' || commandName.charAt(i - 1) > 'Z')
                        str += '_';

                //	Set it to lower case
                //---------------------------
                str += (char) (commandName.charAt(i) + ('a' - 'A'));
            } else
                str += commandName.charAt(i);
        }
        return str;
    }

    //===============================================================
    //===============================================================
    public static int getCommandIndex(EList<Command> list, Command cmd) {
        int idx = -1;
        int i = 0;
        for (Command c : list) {
            if (c == cmd)
                idx = i;
            i++;
        }
        return idx;
    }

    //===============================================================
    //===============================================================
    public static int getAttributeIndex(EList<Attribute> list, Attribute attribute) {
        int idx = -1;
        int i = 0;
        for (Attribute att : list) {
            if (att == attribute)
                idx = i;
            i++;
        }
        return idx;
    }

    //===============================================================
    //===============================================================
    public static int getStateIndex(EList<State> list, State state) {
        int idx = -1;
        int i = 0;
        for (State st : list) {
            if (st == state)
                idx = i;
            i++;
        }
        return idx;
    }

    //===============================================================
    //===============================================================
    public static int getPropertyIndex(EList<Property> list, Property prop) {
        int idx = -1;
        int i = 0;
        for (Property p : list) {
            if (p == prop)
                idx = i;
            i++;
        }
        return idx;
    }

    //===================================================================
    //===================================================================
    public static String strReplaceSpecialCharToCode(String text) {
        if (text == null)
            return null;
        text = Utils.strReplace(text, "\"", "``").trim();
        text = Utils.strReplace(text, "\'", "`").trim();
        return text;
    }

    //===================================================================
    //===================================================================
    public static String strReplaceSpecialCharToDisplay(String text) {
        if (text == null)
            return null;
        text = Utils.strReplace(text, "\\\"", "\"").trim();
        text = Utils.strReplace(text, "\\\'", "\'").trim();
        return text;
    }

    //===================================================================
    //===================================================================
    public static String strReplace(String text, String old_str, String new_str) {
        if (text == null) return "";
        for (int pos = 0; (pos = text.indexOf(old_str, pos)) >= 0; pos += new_str.length())
            text = text.substring(0, pos) + new_str +
                    text.substring(pos + old_str.length());
        return text;
    }

    //===============================================================
    //===============================================================
    public static String checkNameSyntax(String name, boolean isStateStatus) throws DevFailed {
        if (name == null ||
                name.length() == 0)
            Except.throw_exception("BAD_PARM",
                    "Name not valid !",
                    "Utils.checkNameSyntax()");

        //	check if one word
        StringTokenizer stk = new StringTokenizer(name);
        ArrayList<String> vs = new ArrayList<String>();
        while (stk.hasMoreTokens())
            vs.add(stk.nextToken());
        name = "";
        for (String s : vs)
            name += s;

        //	Check for special char
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if ((c < '0' || (c > '9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z') && c != '_')
                Except.throw_exception("SyntaxError",
                        "Syntax error in name: Do not use '" + c + "' char.",
                        "Utils.checkNameSyntax()");
        }

        char firstChar = name.toUpperCase().charAt(0);
        /*  Not used any more
        if (!PogoProperty.attrFree) {

            //	First char must be a capital letter
            String first = name.substring(0, 1).toUpperCase();
            name = first + name.substring(1);
        }
        */

        //	First char must be a letter
        if (firstChar < 'A' || firstChar > 'Z')
            Except.throw_exception("SyntaxError",
                    name + ":\nSyntax error in name: The first char must be a letter",
                    "Utils.checkNameSyntax()");

        //	Check for NOT state or Status
        if (!isStateStatus)
            if (name.equals("State") || name.equals("Status"))
                Except.throw_exception("SyntaxError",
                        name + "  is reserved",
                        "Utils.checkNameSyntax()");

        return name;
    }
    //===============================================================

    /**
     * If b equals true, return "true" , otherwise return "false"
     *
     * @param b the specified input boolean
     * @return "true" if str equals true, otherwise return "false"
     */
    //===============================================================
    public static String strBoolean(boolean b) {
        return (b) ? "true" : "false";
    }
    //===============================================================
    /**
     * If str equals true, return "true" , otherwise return "false"
     *
     * @param str the specified input string
     * @return "true" if str equals true, otherwise return "false"
     */
    //===============================================================
    public static String strBoolean(String str) {
        return (isTrue(str)) ? "true" : "false";
    }
    //===============================================================
    /**
     * If str equals true, return true , otherwise return false
     *
     * @param str the specified input string
     * @return true if str equals true, otherwise return false
     */
    //===============================================================
    public static boolean isTrue(String str) {
        return str != null && str.equals("true");
    }
    //===============================================================
    /**
     * If str equals false or null, return true , otherwise return false
     *
     * @param str the specified input string
     * @return true if str equals false or null, otherwise return false
     */
    //===============================================================
    public static boolean isFalse(String str) {
        return ! isTrue(str);
    }

    //===============================================================
    //===============================================================
    public static boolean isSet(String str) {
        return str != null && str.length() > 0;
    }
    //===============================================================
    /**
     * Like String.equals() but check if input args are not null
     *
     * @param str1 string 1 to be compared
     * @param str2 string 2 to be compared
     * @return return true is str1 equals str2.
     */
    //===============================================================
    public static boolean isEquals(String str1, String str2) {
        return str1 != null &&
                str2 != null && str1.equals(str2);
    }

    //===============================================================
    //===============================================================
    public static boolean isExpert(Command cmd) {
        return isEquals(cmd.getDisplayLevel(),
                PogoConst.strLevel[PogoConst.EXPERT]);
    }

    //===============================================================
    //===============================================================
    public static boolean isExpert(Attribute att) {
        return isEquals(att.getDisplayLevel(),
                PogoConst.strLevel[PogoConst.EXPERT]);
    }

    //===============================================================
    //===============================================================
    public static void popupError(Component component, String message) {
        try {
            throw new Exception(message);
        } catch (Exception e) {
            Utils.getInstance().stopSplashRefresher();
            ErrorPane.showErrorMessage(component, null, e);
        }
    }
    //===============================================================
    /**
     * Returns a vector of file names found in specified directory
     *
     * @param dirName specified directory name
     * @return a vector of file names fond.
     */
    //===============================================================
    public ArrayList<String> getFileList(String dirName) {
        ArrayList<String> v = new ArrayList<String>();
        File dir = new File(dirName);
        String[] fileNames = dir.list();

        if (fileNames == null)
            return v;

        for (String name : fileNames) {
            String filename = dirName + "/" + name;
            File f = new File(filename);
            if (!f.isDirectory())
                v.add(name);
        }
        Collections.sort(v, new StringCompare());
        return v;
    }
    //===============================================================
    /**
     * Search a file from a directory (in it an sub directories)
     *
     * @param searched the serched file name
     * @param dirname  the disrectory to start search
     * @return the full file name (path/name)
     */
    //===============================================================
    public static String searchFileFromDirectory(String searched, String dirname) {
        File d = new File(dirname);
        String[] filenames = d.list();

        if (filenames == null)
            return null;
        for (String name : filenames) {
            //	Recursive if directory
            String filename = dirname + "/" + name;
            File f = new File(filename);
            if (f.isDirectory()) {
                String found = searchFileFromDirectory(searched, f.toString());
                if (found != null)
                    return found;
            } else if (name.equals(searched))
                return filename;
        }

        //	Not found
        return null;
    }
    //===============================================================
    /**
     * Check if OS is Unix
     *
     * @return true if OS is not windows
     */
    //===============================================================
    public static boolean osIsUnix() {
        String os = System.getProperty("os.name");
        return !os.toLowerCase().startsWith("windows");
    }

    //===============================================================
    //===============================================================
    public static void showInHtmBrowser(String url) {
        //  Check for browser
        String browser;
        if ((browser = System.getProperty("BROWSER")) == null) {
            if (osIsUnix())
                browser = "firefox - turbo";
            else
                browser = "explorer";
        }
        String cmd = browser + " " + url;
        try {
            executeShellCmdAndReturn(cmd);
        } catch (Exception e) {
            ErrorPane.showErrorMessage(new JFrame(), null, e);
        }
    }
    //===============================================================
    /**
     * Execute a shell command and throw exception if command failed.
     *
     * @param cmd shell command to be executed.
     * @throws java.io.IOException in case of execution failed
     */
    //===============================================================
    public static void executeShellCmdAndReturn(String cmd)
            throws IOException {
        Process proc = Runtime.getRuntime().exec(cmd);

        // get command's output stream and
        // put a buffered reader input stream on it.
        //-------------------------------------------
        InputStream istr = proc.getInputStream();
        new BufferedReader(new InputStreamReader(istr));

        // do not read output lines from command
        // Do not check its exit value
    }

    //===============================================================
    /**
     * Check the files to be excluded by XPand scans
     * it is used for cpp and python (java is org.tango.xxx)
     *
     * @param rootDir the directory to be checked (output code)
     * @return list of files to be excluded by XPand scans
     */
    //===============================================================
    static public String getExcludeFilesAndDir(String rootDir) {
        //  Define what will be generated
        //  ToDo
        String[] geneFiles = {".cpp", ".h", ".py",
                "Makefile", "Makefile.multi",
                ".sln", ".vcproj"};
        String[] geneDirs = { "", "vc8_proj", "vc9_proj", "vc10_proj",};

        ArrayList<String> excluded = new ArrayList<String>();
        for (String dir : geneDirs) {
            //  Get file list
            String dirName = rootDir;
            if (dir.length() > 0)
                dirName += "/" + dir;
            File d = new File(dirName);
            String[] fileNames = d.list();
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    //System.out.println(fileName);
                    //  Check if fileName must be generated
                    boolean generates = couldBeGenerated(fileName, geneFiles);
                    //if (!generates)
                    //    generates = couldBeGenerated(fileName, geneDirs);

                    if (!generates) {
                        //  if not -> add it to excluded ones
                        excluded.add(fileName);
                    }
                }
            }
        }
        //  Convert to String
        StringBuilder sb = new StringBuilder();
        for (String fileName : excluded) {
            sb.append(fileName).append(", ");
        }
        //  Remove last ", " if any
        String str = sb.toString();
        int pos = str.lastIndexOf(',');
        if (pos > 0)
            str = str.substring(0, pos);
        return str;
    }

    //===============================================================
    //===============================================================
    static private boolean couldBeGenerated(String fileName, String[] generated) {
        //  Check for hidden files
        if (fileName.startsWith(".") ||
                fileName.startsWith("#") ||
                fileName.startsWith("~"))
            return false;

        //  Check if file must be generated.
        boolean generates = false;
        for (String s : generated) {
          if (fileName.endsWith(s)) {
               generates = true;
            }
        }
        return generates;
    }

    //===============================================================
    //===============================================================
    public static String getXmiFile() {
        File f = new File(".");
        String[] fileList = f.list();
        for (String fileName : fileList) {
            if (fileName.endsWith(".xmi")) {
                if (new File(fileName).isFile())
                    return fileName;
            }
        }
        return null;
    }
    //===============================================================
    /**
     * Manage the doc_html location in "." or ".."
     * @param pogoClass the specified class.
     * @param beforeProcessing  before/after processing to know the way ro move
     */
    //===============================================================
    static void manageHtmlDirectory(PogoDeviceClass pogoClass, boolean beforeProcessing) {
        String defaultLocation  = "./doc_html";
        if (!PogoProperty.docHome.equals(defaultLocation)) {   //  Not the default location
            if (pogoClass.getDescription().getFilestogenerate().contains("html")) {
                File    defaultLocationFile = new File(
                        pogoClass.getDescription().getSourcePath()+"/"+defaultLocation);
                File    expectedLocationFile = new File(
                        pogoClass.getDescription().getSourcePath()+"/"+PogoProperty.docHome);
                if (beforeProcessing) {
                    //  try to move from location to default
                    if (expectedLocationFile.renameTo(defaultLocationFile))
                        System.err.println("Failed to move html dir to " + defaultLocation);
                }
                else {
                    //  try to move from location to default
                    if (defaultLocationFile.renameTo(expectedLocationFile))
                        System.err.println("Failed to move html dir to " + expectedLocationFile);
                }
            }
        }

        /*** Description file has been set to ClassDescription ***
        if (beforeProcessing) {
            //  Check html file to do not have ' char
            //  Problem during protected region parsing
            File description = new File(pogoClass.getDescription().getSourcePath() +
                    "/" + defaultLocation+"/Description.html");
            if (description.exists()) {
                try {
                    String          htmlChar = "&lsquo;";
                    StringBuilder   sb = new StringBuilder();
                    String  code = ParserTool.readFile(description.toString());
                    int start;
                    int end = 0;
                    boolean modified = false;
                    while ((start=code.indexOf('\'', end))>0) {
                        sb.append(code.substring(end, start)).append(htmlChar);
                        end = start+1;
                        modified = true;
                    }
                    sb.append(code.substring(end));
                    
                    if (modified) {
                        ParserTool.writeFile(description.toString(), sb.toString());
                    }
                }
                catch(DevFailed e) {
                    System.err.println(e.errors[0].desc);
                }
            }
        }
         ****************/
    }
    //===============================================================
    //===============================================================


    //  Multi lines tooltip methods
    private static final String tooltipHeader =
            "<html><BODY TEXT=\"#000000\" BGCOLOR=\"#FFFFD0\">";

    //===============================================================
    //===============================================================
    public static String buildToolTip(String text) {
        return buildToolTip(null, text);
    }

    //===============================================================
    //===============================================================
    public static String buildToolTip(String title, String text) {

        StringBuilder sb = new StringBuilder(tooltipHeader);
        if (title != null && title.length() > 0) {
            sb.append("<b><Center>")
                    .append(Utils.strReplace(title, "\n", "<br>\n"))
                    .append("</center></b><HR WIDTH=\"100%\">");
        }
        if (text != null && text.length() > 0) {
            sb.append(Utils.strReplace(text, "\n", "<br>\n"));
        }
        return sb.toString();
    }
    //===============================================================
    //===============================================================


    private static Splash splash = null;
    private static boolean splashOn;
    private SplashRefresher splash_refresher;
    private static boolean useDisplay = true;

    //=======================================================
    //=======================================================
    private void createSplash() {
        try {
            if (tango_icon == null)
                tango_icon = getIcon("TangoSplash.gif");
            JSmoothProgressBar myBar = new JSmoothProgressBar();
            myBar.setStringPainted(true);
            myBar.setBackground(Color.lightGray);
            myBar.setProgressBarColors(Color.gray, Color.lightGray, Color.darkGray);

            splash = new Splash(tango_icon, Color.black, myBar);
            splash.setMessage("POGO: Tango code generator");
            splash.setMaxProgress(100);
            splash.setTitle("POGO");
            splash.setCopyright(PogoConst.revNumber);
        } catch (Exception e) {
            useDisplay = false;
            System.err.println("Cannot create Splah: " + e);
        }
    }

    //=======================================================
    //=======================================================
    public void stopSplashRefresher() {
        if (splash != null && useDisplay)
            splash_refresher.setSplashOn(false, "");
    }

    //=======================================================
    //=======================================================
    private boolean splashInit = false;

    public void startSplashRefresher(String message) {
        if (!splashInit) {
            String env = System.getenv("Classes2www"); //  For classes2www, No display
            if (env != null && env.equals("true"))
                useDisplay = false;
        }
        splashInit = true;

        if (splash == null)
            if (useDisplay)
                createSplash();
        if (!useDisplay)
            splash = null;

        if (useDisplay) {
            splashOn = true;
            splash_refresher = new SplashRefresher();
            splash_refresher.start();
            splash_refresher.setSplashOn(true, message);
        }
    }

    //=======================================================
    //=======================================================
    private class SplashRefresher extends Thread {
        //===================================================
        private synchronized void setSplashOn(boolean b, String message) {
            if (useDisplay) {
                splashOn = b;
                splash.setMessage(message);
            }
        }

        //===================================================
        public void run() {
            int idx = 1;
            splash.initProgress();
            splash.setVisible(true);
            splash.repaint();
            while (splashOn) {
                splash.progress(idx);
                if (idx++ > 98)
                    idx = 10;
                doSleep(100);
            }
            splash.progress(100);
            splash.setVisible(false);
        }

        //===================================================
        private synchronized void doSleep(long millis) {
            try {
                wait(millis);
            } catch (InterruptedException e) {/*  */}
        }
        //====================================================
    }


    //======================================================

    /**
     * MyCompare class to sort collection
     */
    //======================================================
    class StringCompare implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }
}