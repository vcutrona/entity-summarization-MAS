package org.complexsystems.tools;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;


public class StringStaticTools {
	
	private static String[] stopWords =  {"a", "of", "the", "is", "are", "or", "and", "him", "her", "his"};
	
	/**
	 * Metodo per creare una stringa con le parole separate, partendo
	 * da una stringa in camelCase
	 * @param s
	 * @return
	 */
	public static String splitCamelCase(String s) {
		if (s.contains(" ")) //no camelCase word
			return s.toLowerCase().trim();
		
		return StringUtils.join(
				StringUtils.splitByCharacterTypeCamelCase(s), " ")
				.toLowerCase().trim();
	}
	
	public static String removeStopWords(String string)
	{
		
		String [] strArray = StringUtils.splitByWholeSeparator(string, " ");
		String result = "";
		for(String word : strArray){
		    if (!Arrays.asList(stopWords).contains(word.toLowerCase()))
		    {
		    	result += word + " ";
		    }
		}
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(StringStaticTools.removeStopWords("place of birth"));
	}
}
