package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class JSONObject extends LinkedHashMap<String, Object> implements Map<String, Object>,
	JSONAware, JSONStreamAware {

	public static void writeJSONString(Map<String, Object> map, Writer out) throws IOException {
		if (map == null) {
			out.write("null");
			return;
		}

		boolean first = true;
		Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();

		out.write('{');
		while (iter.hasNext()) {
			if (first) {
				first = false;
			} else {
				out.write(',');
			}
			Map.Entry<String, Object> entry = iter.next();
			out.write('\"');
			out.write(escape(String.valueOf(entry.getKey())));
			out.write('\"');
			out.write(':');
			JSONValue.writeJSONString(entry.getValue(), out);
		}
		out.write('}');
	}

	public void writeJSONString(Writer out) throws IOException {
		writeJSONString(this, out);
	}

	public static String toJSONString(Map<String, Object> map) {
		if (map == null) {
			return "null";
		}

		StringBuffer sb = new StringBuffer();
		boolean first = true;
		Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();

		sb.append('{');
		while (iter.hasNext()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}

			Map.Entry<String, Object> entry = iter.next();
			toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
		}
		sb.append('}');
		return sb.toString();
	}

	public String toJSONString() {
		return toJSONString(this);
	}

	private static String toJSONString(String key, Object value, StringBuffer sb) {
		sb.append('\"');
		if (key == null) {
			sb.append("null");
		} else {
			JSONValue.escape(key, sb);
		}
		sb.append('\"').append(':');

		sb.append(JSONValue.toJSONString(value));

		return sb.toString();
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	public static String toString(String key, Object value) {
		StringBuffer sb = new StringBuffer();
		toJSONString(key, value, sb);
		return sb.toString();
	}

	public static String escape(String s) {
		return JSONValue.escape(s);
	}
}
