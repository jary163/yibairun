package cn.hi.eim.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import cn.hi.eim.model.Expression;

/**
 * ±íÇé¹¤¾ßÀà
 * @author Administrator
 *
 */
public class FaceUtils {

	private static List<Expression> expressionList;
	
	public static String chineseConvert(String message){
		
		
		String Regex = "(\\[[\u4E00-\u9FA5]*\\])";
		message = message.replaceAll(Regex,"<img src=\"" + "$1"+ "\" />");
		return message;
		
	}
	
	public static String msgConvert(String content) {
		String Regex = "\\[#(\\d+)\\]";
		content = content.replaceAll(Regex,"<img src=\"" + "$1"+ "\" />");
		return content;
	} 
	
	public static String msgConvert(List<Expression> expressionList,String content) {
		for (int i = 0; i < expressionList.size(); i++) {
			Log.e("main", "getCode:"+expressionList.get(i).getCode()+"   getDrableCode:"+expressionList.get(i).getDrableCode());
			content = content.replace(expressionList.get(i).getCode(),
					"<img src=\"" + expressionList.get(i).getDrableCode()
							+ "\" />");
		}
		return content;
	}

	public static String replaceSpaceToCode(String str) {
		String rt = str.replace(" ", "&nbsp;");
		rt = rt.replace("\n", "<br/>");
		return rt;
	}
	
	
	public static String getImgNum(String string){
		expressionList = initExpression();
		for (Expression expression : expressionList) {
			if(expression.getCode().equals(string)){
				return String.valueOf(expression.getDrableCode());
			}
		}
		return "1";
	}
	     
	/**
	 * ³õÊ¼»¯±íÇéÐÅÏ¢
	 */
	public static List<Expression> initExpression() {
		if(expressionList!=null){ 
			return expressionList;
		}
		expressionList = new ArrayList<Expression>();
		Expression exp1 = new Expression(1, "[Î¢Ð¦]");
		Expression exp2 = new Expression(2, "[¿É°®]");
		Expression exp3 = new Expression(3, "[ÐßÉ¬]");
		Expression exp4 = new Expression(4, "[ÉËÐÄ]");
		Expression exp5 = new Expression(5, "[¿É¶ñ]");
		Expression exp6 = new Expression(6, "[Ã°ÅÝ]");
		Expression exp7 = new Expression(7, "[¿Ù×ó±Ç]");
		Expression exp8 = new Expression(8, "[Îæ×ì]");
		Expression exp9 = new Expression(9, "[Àäº¹]");
		Expression exp10 = new Expression(10, "[¿ÛÓÒ±Ç]");

		Expression exp11 = new Expression(11, "[²»½â]");
		Expression exp12 = new Expression(12, "[ö·ÑÀÐ¦]");
		Expression exp13 = new Expression(13, "[ÐêÐê]");
		Expression exp14 = new Expression(14, "[ÃêÊÓ]");
		Expression exp15 = new Expression(15, "[ÒÉÎÊ]");
		Expression exp16 = new Expression(16, "[ÔÎ²Ë]");
		Expression exp17 = new Expression(17, "[ÕÅ´ó×ì]");
		Expression exp18 = new Expression(18, "[×óµÉÑÛ]");
		Expression exp19 = new Expression(19, "[ÓÒµÉÑÛ]");
		Expression exp20 = new Expression(20, "[¹ÄÕÆ]");

		Expression exp21 = new Expression(21, "[Ã°º¹]");
		Expression exp22 = new Expression(22, "[¿ÞÆü]");
		Expression exp23 = new Expression(23, "[¾ªÑÈ]");
		Expression exp24 = new Expression(24, "[¿á¿á]");
		Expression exp25 = new Expression(25, "[ÖíÍ·]");
		Expression exp26 = new Expression(26, "[ÉÏ»ð]");
		Expression exp27 = new Expression(27, "[°®ÐÄ]");
		Expression exp28 = new Expression(28, "[ÐÄËé]");
		Expression exp29 = new Expression(29, "[ÔÞ]");
		Expression exp30 = new Expression(30, "[¹´Òý]");

		Expression exp31 = new Expression(31, "[OK]");
		Expression exp32 = new Expression(32, "[È­Í·]");
		Expression exp33 = new Expression(33, "[ÎÕÊÖ]");
		Expression exp34 = new Expression(34, "[²È]");
		Expression exp35 = new Expression(35, "[Ê¤Àû]");
		Expression exp36 = new Expression(36, "[µ°¸â]");
		Expression exp37 = new Expression(37, "[ÀñÎï]");
		Expression exp38 = new Expression(38, "[À¯Öò]");
		Expression exp39 = new Expression(39, "[Ì«Ñô]");
		Expression exp40 = new Expression(40, "[ÔÂÁÁ]");

		Expression exp41 = new Expression(41, "[ÉÁµç]");
		Expression exp42 = new Expression(42, "[‡å]");
		Expression exp43 = new Expression(43, "[»¨¶ä]");
		Expression exp44 = new Expression(44, "[ÒûÁÏ]");
		Expression exp45 = new Expression(45, "[¼ªËû]");
		Expression exp46 = new Expression(46, "[Ç®´ü]");
		Expression exp47 = new Expression(47, "[ÓêÉ¡]");
		Expression exp48 = new Expression(48, "[ºº±¤]");
		Expression exp49 = new Expression(49, "[ÃæÌõ]");
		Expression exp50 = new Expression(50, "[Æ»¹û]");

		Expression exp51 = new Expression(51, "[Âó¿Ë·ç]");
		Expression exp52 = new Expression(52, "[Ê¥µ®Ê÷]");
		Expression exp53 = new Expression(53, "[Ð¡°×ÍÃ]");
		Expression exp54 = new Expression(54, "[¹âµú]");
		Expression exp55 = new Expression(55, "[ÒôÀÖ]");
		Expression exp56 = new Expression(56, "[ÆøÇò]");
		Expression exp57 = new Expression(57, "[ÅÄÕÕ]");
		Expression exp58 = new Expression(58, "[ÊÖ»ú]");
		Expression exp59 = new Expression(59, "[ÏÂÓê]");
		Expression exp60 = new Expression(60, "[Ñ©ÈË]");

		Expression exp61 = new Expression(61, "[ÉñÂí]");
		Expression exp62 = new Expression(62, "[V5]");
		Expression exp63 = new Expression(63, "[·ãÒ¶]");
		Expression exp64 = new Expression(64, "[ÂÌÒ¶]");
		expressionList.clear();
		expressionList.add(exp1);
		expressionList.add(exp2);
		expressionList.add(exp3);
		expressionList.add(exp4);
		expressionList.add(exp5);
		expressionList.add(exp6);
		expressionList.add(exp7);
		expressionList.add(exp8);
		expressionList.add(exp9);
		expressionList.add(exp10);

		expressionList.add(exp11);
		expressionList.add(exp12);
		expressionList.add(exp13);
		expressionList.add(exp14);
		expressionList.add(exp15);
		expressionList.add(exp16);
		expressionList.add(exp17);
		expressionList.add(exp18);
		expressionList.add(exp19);
		expressionList.add(exp20);

		expressionList.add(exp21);
		expressionList.add(exp22);
		expressionList.add(exp23);
		expressionList.add(exp24);
		expressionList.add(exp25);
		expressionList.add(exp26);
		expressionList.add(exp27);
		expressionList.add(exp28);
		expressionList.add(exp29);
		expressionList.add(exp30);

		expressionList.add(exp31);
		expressionList.add(exp32);
		expressionList.add(exp33);
		expressionList.add(exp34);
		expressionList.add(exp35);
		expressionList.add(exp36);
		expressionList.add(exp37);
		expressionList.add(exp38);
		expressionList.add(exp39);
		expressionList.add(exp40);

		expressionList.add(exp41);
		expressionList.add(exp42);
		expressionList.add(exp43);
		expressionList.add(exp44);
		expressionList.add(exp45);
		expressionList.add(exp46);
		expressionList.add(exp47);
		expressionList.add(exp48);
		expressionList.add(exp49);
		expressionList.add(exp50);

		expressionList.add(exp51);
		expressionList.add(exp52);
		expressionList.add(exp53);
		expressionList.add(exp54);
		expressionList.add(exp55);
		expressionList.add(exp56);
		expressionList.add(exp57);
		expressionList.add(exp58);
		expressionList.add(exp59);
		expressionList.add(exp60);

		expressionList.add(exp61);
		expressionList.add(exp62);
		expressionList.add(exp63);
		expressionList.add(exp64);
		
		return expressionList;
	}
}
