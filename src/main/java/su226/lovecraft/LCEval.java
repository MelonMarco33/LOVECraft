package su226.lovecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public class LCEval {
  public Map<String, Double> var = new HashMap<String, Double>();
  public Map<String, Function<String, Double>> func = new HashMap<String, Function<String, Double>>();
  private static final List<Character> ops = Arrays.asList('+', '-', '*', '/', '%', '^');
  public LCEval() {
    func.put("", this ::calc);
    func.put("abs", this ::abs);
  }
  public double calc(String expr) {
    List<String> part = new ArrayList<String>();
    String tmp = "";
    int layer = 0;
    for (int i = 0; i < expr.length(); i++) {
      char chr = expr.charAt(i);
      if (layer == 0 && ops.contains(chr)) {
        tmp = tmp.trim();
        if (tmp.isEmpty()) {
          tmp = "0";
        }
        part.add(tmp);
        part.add(String.valueOf(chr));
        tmp = "";
      } else {
        if (chr == '(') {
          layer++;
        } else if (chr == ')') {
          layer--;
        }
        tmp += chr;
      }
    }
    part.add(tmp.trim());
    for (int i = 0; i < part.size(); i++) {
      String j = part.get(i);
      if (isOperator(j) || StringUtils.isNumeric(j)) {
      } else if (isVariable(j)) {
        part.set(i, String.valueOf(var.get(j)));
      } else {
        part.set(i, parseFunction(j));
      }
    }
    for (int i = 0; i < part.size() - 2; i++) {
      String next = part.get(i + 1);
      if (next.equals("^")) {
        part.set(i, String.valueOf(Math.pow(Double.parseDouble(part.get(i)), Double.parseDouble(part.get(i + 2)))));
        part.remove(i + 2);
        part.remove(i + 1);
      }
    }
    for (int i = 0; i < part.size() - 2; i++) {
      String next = part.get(i + 1);
      if (next.equals("*")) {
        part.set(i, String.valueOf(Double.parseDouble(part.get(i)) * Double.parseDouble(part.get(i + 2))));
        part.remove(i + 2);
        part.remove(i + 1);
      } else if (next.equals("/")) {
        part.set(i, String.valueOf(Double.parseDouble(part.get(i)) / Double.parseDouble(part.get(i + 2))));
        part.remove(i + 2);
        part.remove(i + 1);
      } else if (next.equals("%")) {
        part.set(i, String.valueOf(Double.parseDouble(part.get(i)) % Double.parseDouble(part.get(i + 2))));
        part.remove(i + 2);
        part.remove(i + 1);
      }
    }
    for (int i = 0; i < part.size() - 2; i++) {
      String next = part.get(i + 1);
      if (next.equals("+")) {
        part.set(i, String.valueOf(Double.parseDouble(part.get(i)) + Double.parseDouble(part.get(i + 2))));
        part.remove(i + 2);
        part.remove(i + 1);
      } else if (next.equals("-")) {
        part.set(i, String.valueOf(Double.parseDouble(part.get(i)) - Double.parseDouble(part.get(i + 2))));
        part.remove(i + 2);
        part.remove(i + 1);
      }
    }
    if (part.size() != 1) {
      return 0; //未知错误
    }
    return Double.parseDouble(part.get(0));
  }
  private String parseFunction(String str) {
    String funcName = "";
    String funcBody = "";
    int i = 0;
    int layer = 0;
    for (; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (chr == '(') {
        break;
      } else {
        funcName += chr;
      }
    }
    for (; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (chr == '(') {
        layer++;
      } else if (chr == ')') {
        layer--;
      }
      funcBody += chr;
    }
    if (layer != 0) {
      return "0"; //括号不匹配
    }
    funcBody = funcBody.substring(1, funcBody.length() - 1);
    return String.valueOf(func.get(funcName).apply(funcBody));
  }
  private boolean isOperator(String i) {
    return i.length() == 1 && ops.contains(i.charAt(0));
  }
  private boolean isVariable(String str) {
    for (int i = 0; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (!((chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z') || chr == ' ')) {
        return false;
      }
    }
    return true;
  }
  public double abs(String expr) {
    return Math.abs(calc(expr));
  }
}