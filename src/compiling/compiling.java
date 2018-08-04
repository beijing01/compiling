package compiling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class compiling {
	/**
	 * 基本字（保留字）：组成命令的东西 查询c语言中原来自己定义的 标示符：用户自己定义 常量名 常数：整个运行过程中不会变的 50 运算符： 加减乘除
	 * = 界限符号：l
	 */
	/**
	 * public void compiling() {
	 * 
	 * }
	 * 
	 */
	// 从控制台读取字符
	String str[];
	static ArrayList<String> guanjianzi = new ArrayList<String>();

	public static String word(String sentence) {
		ArrayList<String> biaoshifu = new ArrayList<String>();
		// 标识符的种别码为1，常数为2，保留字为3，运算符为4，界符为5。
		/**/
		guanjianzi.add("char");
		guanjianzi.add("double");
		guanjianzi.add("float");
		guanjianzi.add("int");
		guanjianzi.add("long");
		guanjianzi.add("short");
		guanjianzi.add("void");
		guanjianzi.add("for");
		guanjianzi.add("do");
		guanjianzi.add("while");
		guanjianzi.add("break");
		guanjianzi.add("continue");
		guanjianzi.add("if");
		guanjianzi.add("else");
		guanjianzi.add("switch");
		guanjianzi.add("case");
		guanjianzi.add("default");
		guanjianzi.add("return");
		guanjianzi.add("static");
		guanjianzi.add("const");
		guanjianzi.add("print");
		int length = sentence.length();
		char result[] = sentence.toCharArray();

		boolean flag = false;
		int line = 1;
		for (int i = 0; i < result.length; i++) {
			// 忽略制表和换行
			// float afloat;
			if (result[i] == 9) {
				line++;
			}

			if (result[i] != 10 && result[i] != 9) {
				// 状态符，用于定位一个词语。遇到空格时，开始寻找第一个
				// System.out.println(result[i]);

				// 判断是否是标识符或者变量
				// 如果是，则判断字符是否合法
				// 若合法则判断是否在表示符号表中
				// 若不在则新增符号表，若在则不用新增

				// 通过记录下一个值判断当天值是否成为一个符号，若下一个字符是字母，数字，_则继续，否则就独立成为一个符号。然后开始新符号记录

				if ((result[i] >= 'a' && result[i] <= 'z') || (result[i] >= 'A' && result[i] <= 'Z')) {

					String word = "";
					while ((result[i] >= '0' && result[i] <= '9') || (result[i] >= 'a' && result[i] <= 'z')
							|| (result[i] >= 'A' && result[i] <= 'Z') || result[i] == 95) {
						word += result[i];
						i++;
					}
						
					// 判断是否在保留字表中
					if (guanjianzi.indexOf(word) != -1) {
						// 若是则保留字为3
						System.out.println("(" + "3" + "," + "\'" + word + "\'" + ")");

					} else {
						// 在biaoshifu表中搜索，如果不存在,则添加进去并输入当前值;如果存在，则输入入口地址
						if (biaoshifu.indexOf(word) != -1) {
							System.out.println(
									"(" + "1" + "," + "\'" + biaoshifu.indexOf(word) + "\'" + ")" + "标识符为：" + word);

						} else {
							biaoshifu.add(word);
							System.out.println(
									"(" + "1" + "," + "\'" + biaoshifu.indexOf(word) + "\'" + ")" + "标识符为:" + word);

						}
					}

					i--;
				} else if ((result[i] >= '0' && result[i] <= '9')) {
					// 判断下一个类型是否为数字和小数点，如果是则继续，扫描。如果不是判断是否为字母，若是则错。报非法字符。并输出所在位置。
					String num = "";
					int cishu = 1;
					
					while (result[i] >= '0' && result[i] <= '9') {
						num += result[i];
						i++;
						if (result[i] == 46) {

							num += result[i];
							i++;
							if (cishu >= 2) {
								System.out.println("数字非法字符串!当前行数为" + line+"内容为"+num+result[i]);
							}
							cishu++;
						}
					}

					if ((result[i] >= 'a' && result[i] <= 'z') || (result[i] >= 'A' && result[i] <= 'Z')) {
						System.out.println("错误字符串!当前行数为" + line+"内容为"+num+result[i]);
					} else {
						System.out.println("(" + "2" + "," + num + ")");
					}
					i--;
				} else if (result[i] == 40 || result[i] == 41 || result[i] == 44 || result[i] == 59 || result[i] == 123
						|| result[i] == 125) {
					// 识别分割符号40( 41) 44, 59,123{,125};
					System.out.println("(" + "5" + "," + result[i] + ")");
				} else if (result[i] == 42 || result[i] == 43 || result[i] == 45 || result[i] == 47 || result[i] == 60
						|| result[i] == 61 || result[i] == 62) {
					String operator = "";
					// 42*,43+,45-,47/,60<,61=,62>
					// 若是基本运算符，则直接输出，否则读取下一位
					// 用于判断运算符号和注释
					// 判断是否为/,如果是则判断下一位是否还为*,若是则为注释。i++知道遇到*／。否则就当作除号

					if (result[i] == 47) {
						int j = i + 1;
						if (result[j] == 42) {// 若是 则为注释
							i += 2;
							j += 2;
							while (result[i] != 42 || result[j] != 47) {
								i++;
								j++;
							}
							i++;
						} else {
							// 不是当作除号
							System.out.println("(" + "4" + "," + result[i] + ")");
						}
					} else if (result[i] == 42 || result[i] == 43 || result[i] == 45) {
						// 判断是否是+，-，*
						System.out.println("(" + "4" + "," + result[i] + ")");
					} else {
						while (result[i] == 60 || result[i] == 61 || result[i] == 62) {
							operator += result[i];
							i++;
						}
						System.out.println("(" + "4" + "," + operator + ")");
						i--;
					}

				} else if (result[i] == ' ') {
					continue;
				} else
					System.out.println("无法识别字符串!当前行数为" + line);

			}

		}

		System.out.println("文本长度为" + result.length);

		return "";
	}

	public static String readToString(String filename) {
		String encoding = "UTF-8";
		File file = new File(filename);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];

		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		String sentence; // = "";
		sentence = readToString("code.txt");
		System.out.print(sentence);
		System.out.println("___________________________________________________");
		word(sentence);

	}
}

/*
 * void jifloat2() { int y2,c,d; float x,a= 0.13333443,b=0.6534; x = a+b*50; }
 */

/*
 * 这是一个c语言函数
 */
/*
 * void jifloat2() { int y2,c,d; float x,a= 0.13333443;b=0.653; if(a<=###b) { x
 * = a+b*50; } }
 */
