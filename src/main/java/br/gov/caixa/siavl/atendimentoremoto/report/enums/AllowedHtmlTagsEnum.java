package br.gov.caixa.siavl.atendimentoremoto.report.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

@SuppressWarnings("all")
public enum AllowedHtmlTagsEnum {

	BOLD_START("<b>"), BOLD_END("</b>");

	private final String tag;

	AllowedHtmlTagsEnum(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public static String[] tags() {
		List<String> tags = new ArrayList<>();
		Arrays.stream(AllowedHtmlTagsEnum.values()).forEach(c -> tags.add(String.valueOf(c.getTag())));
		return StringUtils.toStringArray(tags);
	}

}
