package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware {

    public static void writeJSONString(List list, Writer out) throws IOException {
        if (list == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator iter = list.iterator();

        out.write('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                out.write(',');
            }

            Object value = iter.next();
            if (value == null) {
                out.write("null");
                continue;
            }

            JSONValue.writeJSONString(value, out);
        }
        out.write(']');
    }

    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }

    public static String toJSONString(List list) {
        if (list == null) {
            return "null";
        }

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        Iterator iter = list.iterator();

        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }

            Object value = iter.next();
            if (value == null) {
                sb.append("null");
                continue;
            }
            sb.append(JSONValue.toJSONString(value));
        }
        sb.append(']');
        return sb.toString();
    }

    public String toJSONString() {
        return toJSONString(this);
    }

    @Override
    public String toString() {
        return toJSONString();
    }
}
