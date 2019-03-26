package com.nyfz.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 验证是否为数字
	 *
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		char[] arr = str.toCharArray();
		for (char c : arr) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证某个字符是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str)) {
			return false;
		}
		// 该正则表达式可以匹配所有的数字 包括负数
		Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
		String bigStr;
		try {
			bigStr = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;// 异常 说明包含非数字。
		}

		Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	/**
	 * 替换空格、换行符等不可见字符
	 * @param str
	 * @return
	 */
	public static String replaceSpace(String str) {
		String result = str;
		if (str != null && str.length() > 0) { // 替换空格、换行符等不可见字符
			String regEx = "\\s|　{1,}";
			Pattern pattern = Pattern.compile(regEx);
			Matcher m = pattern.matcher(str);

			result = m.replaceAll("").trim();
		}
		return result;
	}

	/**
	 * 调用对象的toString()方法，如果对象为 null 返回空字符串("")
	 * @param o
	 * @return
	 */
	public static String toString(Object object) {
		return object == null ? "" : object.toString();
	}

	/**
	 * 验证为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * 验证不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

/*	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) || "null".equals(str) ? false
				: true;
	}*/

	/**
	 * 是否全都为空
	 *
	 * @param str
	 * @return
	 */
/*	public static boolean isBlank(String... str) {
		for (String s : str) {
			if (notBlank(s))
				return false;
		}
		return true;
	}*/

	/**
	 * 是否至少有一个为空
	 *
	 * @param str
	 * @return
	 */
/*	public static boolean isBlankOneLeast(String... str) {
		for (String s : str) {
			if (isBlank(s))
				return true;
		}
		return false;
	}*/

	/**
	 * @param offset
	 *            从第几位分割
	 * @param str
	 *            需要分割的字符串
	 * @param symbol
	 *            需要用什么分割
	 * @return 返回分割后的字符串
	 */
	public static String insertStrToString(int offset, String str, String symbol) {
		if (!isEmpty(str)) {
			StringBuilder sb = new StringBuilder(str);
			for (int i = offset; i < sb.length(); i += offset) {
				sb.insert(i++, symbol);
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 多个逗号转换为单个逗号 并去掉首尾逗号
	 *
	 * @param s
	 * @return
	 */
	public static String dealWithComma(String s) {
		if (s.length() > 0) {
			s = s.replaceAll("[',']+", ",");// 多个,,,,替换为,
			// 去掉首尾,
			if (s.startsWith(",")) {
				s = s.substring(1);
			}
			if (s.endsWith(",")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	/**
	 * 数组转换字符串方法 包含引号
	 *
	 * @param s
	 * @return 's1','s2','s3'
	 */
	public static String genStrWithQuote(String[] s) {
		String re = "";
		for (String tmp : s) {
			re = re + "'" + tmp + "',";
		}
		return dealWithComma(re);
	}

	/**
	 * 数组转换字符串方法 不含引号
	 *
	 * @param s
	 * @return s1, s2, s3
	 */
	public static String genStrNoQuote(String[] s) {
		return genStrWithQuote(s).replace("'", "");
	}

	/**
	 * 获取文件后缀名
	 * @param fileName 文件名称
	 * @return 后缀
	 */
	public static String getFileSuffix(String fileName) {
		if (isEmpty(fileName) || !fileName.contains(".")) {
			return "";
		}
		return fileName.substring(fileName.indexOf(".") + 1, fileName.length());
	}

	/**
	 * 手机号验证
	 *
	 * @param str
	 * @return 
	 */
	public static boolean isMobile(final String str) {
		Pattern p;
		Matcher m;
		boolean b;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 *
	 * @param str
	 * @return 
	 */
	public static boolean isPhone(final String str) {
		Pattern p1, p2;
		Matcher m;
		boolean b;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 验证是否为正确的邮箱
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		// 1、\\w+表示@之前至少要输入一个匹配字母或数字或下划线 \\w 单词字符：[a-zA-Z_0-9]
		// 2、(\\w+\\.)表示域名. 如新浪邮箱域名是sina.com.cn
		// {1,3}表示可以出现一次或两次或者三次.
		String reg = "\\w+@(\\w+\\.){1,3}\\w+";
		Pattern pattern = Pattern.compile(reg);
		boolean flag = false;
		if (email != null) {
			Matcher matcher = pattern.matcher(email);
			flag = matcher.matches();
		}
		return flag;
	}
	/**
	 * 将字符（保留前3位，保留后四位，中间替换为 *）用于身份证或者电话、银行卡号等敏感信息显示
	 * @param str
	 * @return
	 */
	public static String replaceSensitiveStr(String str) {
		if (isEmpty(str) || str.length() < 11)
			return str;
		str = str.replaceAll("(?<=[\\d]{3})\\d(?=([\\d]|[a-z]|[A-Z]){4})", "*"); // 这里*只要一个，因为会替代多次，每次一个。
		return str;
	}
	/**
	 * 剔除空格及换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (str != null) {
			str = str.replaceAll("\\s*|\t|\r|\n", "");
		}
		return str;
	}

	public static void main(String[] args) {
		System.out.println(isNumber("dddddd"));
		System.out.println(isNumber("123456"));
		 System.out.println(isValidEmail("500454000@qq.cn"));
		 System.out.println(isMobile("s1753393559"));
		 System.out.println(replaceSensitiveStr("17533935995"));
		 System.out.println(replaceSensitiveStr("426987561122314539"));
		 System.out.println("aaaaaaaaaaa  dddd dddd\nggggg");
		 System.out.println(replaceBlank("aaaaaaaaaaa  dddd dddd\nggggg"));
		 for(int i=0,len=6;i<len;i++){
			 System.out.println(i);
			 }
	}

}
