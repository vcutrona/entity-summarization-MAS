package org.complexsystems.metrics;

import java.util.Hashtable;
import java.util.LinkedList;

import org.complexsystems.tools.StringStaticTools;

//calculates the cosine similarity between two texts / documents etc., (having each word separated by space)
public class CosineSimilarity {

	public class values {

		int val1;
		int val2;

		values(int v1, int v2) {
			this.val1 = v1;
			this.val2 = v2;
		}

		public void Update_VAl(int v1, int v2) {
			this.val1 = v1;
			this.val2 = v2;
		}

	}// end of class values

	public double computeScore(String Text1, String Text2) {
		Text1 = StringStaticTools.removeStopWords(StringStaticTools.splitCamelCase(Text1));
		Text2 = StringStaticTools.removeStopWords(StringStaticTools.splitCamelCase(Text2));

		double sim_score = 0.0000000;
		// 1. Identify distinct words from both documents
		String[] word_seq_text1 = Text1.split(" ");
		String[] word_seq_text2 = Text2.split(" ");
		Hashtable<String, values> word_freq_vector = new Hashtable<String, CosineSimilarity.values>();
		LinkedList<String> Distinct_words_text_1_2 = new LinkedList<String>();

		// prepare word frequency vector by using Text1
		for (int i = 0; i < word_seq_text1.length; i++) {
			String tmp_wd = word_seq_text1[i].trim();
			if (tmp_wd.length() > 0) {
				if (word_freq_vector.containsKey(tmp_wd)) {
					values vals1 = word_freq_vector.get(tmp_wd);
					int freq1 = vals1.val1 + 1;
					int freq2 = vals1.val2;
					vals1.Update_VAl(freq1, freq2);
					word_freq_vector.put(tmp_wd, vals1);
				} else {
					values vals1 = new values(1, 0);
					word_freq_vector.put(tmp_wd, vals1);
					Distinct_words_text_1_2.add(tmp_wd);
				}
			}
		}

		// prepare word frequency vector by using Text2
		for (int i = 0; i < word_seq_text2.length; i++) {
			String tmp_wd = word_seq_text2[i].trim();
			if (tmp_wd.length() > 0) {
				if (word_freq_vector.containsKey(tmp_wd)) {
					values vals1 = word_freq_vector.get(tmp_wd);
					int freq1 = vals1.val1;
					int freq2 = vals1.val2 + 1;
					vals1.Update_VAl(freq1, freq2);
					word_freq_vector.put(tmp_wd, vals1);
				} else {
					values vals1 = new values(0, 1);
					word_freq_vector.put(tmp_wd, vals1);
					Distinct_words_text_1_2.add(tmp_wd);
				}
			}
		}

		// calculate the cosine similarity score.
		double VectAB = 0.0000000;
		double VectA_Sq = 0.0000000;
		double VectB_Sq = 0.0000000;

		for (int i = 0; i < Distinct_words_text_1_2.size(); i++) {
			values vals12 = word_freq_vector
					.get(Distinct_words_text_1_2.get(i));

			double freq1 = (double) vals12.val1;
			double freq2 = (double) vals12.val2;


			VectAB = VectAB + (freq1 * freq2);

			VectA_Sq = VectA_Sq + freq1 * freq1;
			VectB_Sq = VectB_Sq + freq2 * freq2;
		}

		sim_score = ((VectAB) / (Math.sqrt(VectA_Sq) * Math.sqrt(VectB_Sq)));

		return (sim_score);

	}

	public static void main(String[] args) {

		CosineSimilarity cs1 = new CosineSimilarity();

		System.out.println("[Word # VectorA # VectorB]");
		String prima = "birthPlace";
		String seconda = "place of birth";

		prima = StringStaticTools.splitCamelCase(prima);
		seconda = StringStaticTools.splitCamelCase(seconda);
		double sim_score = cs1.computeScore(prima, seconda);
		System.out.println("Cosine similarity score = " + sim_score);

	}

}
