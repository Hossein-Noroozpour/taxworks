/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package xml.text.element.preparer;

public class App {
    public static String prepareXmlText(final String s) {
        StringBuilder f = new StringBuilder();
        final String sa [] =  (s + " ").split("&");
        for(int i = 1 ; i < sa.length; ++i) {
            final String e = sa[i];
            System.out.println("e: " + e);
            if(!e.startsWith("amp;")) {
                f.append("&amp;");
            } else {
                f.append("&");
            }
            f.append(e);
        }
        return f.toString().replace(">", "&gt;").replace("<", "&t;").trim();
    }

    public static void main(String[] args) {
        System.out.println(prepareXmlText("&"));
    }
}
